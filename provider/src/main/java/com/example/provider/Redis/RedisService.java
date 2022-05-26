package com.example.provider.Redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RedisService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    //设置缓存
    public void set(String key, Object value){
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set(key, value);
    }

    //根据key得到缓存
    public Object get(String key){
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate.opsForValue().get(key);
    }

    //根据key删除缓存
    public Boolean delete(String key){
        return redisTemplate.delete(key);
    }

}

