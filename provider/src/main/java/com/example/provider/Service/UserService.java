package com.example.provider.Service;

import java.util.List;

import com.example.provider.Entity.User;

public interface UserService {

	
	User findByUserid(String userid);
	List<User> findByUsernameLikeOrPhoneLikeOrUseridLike(String username,String phone,String userid);
	User saveAndFlush(User user);
	void deleteByUserid(String userid);
	List<User> findByIdLike(String id) ;
	boolean existsByUserid(String userid);
}
