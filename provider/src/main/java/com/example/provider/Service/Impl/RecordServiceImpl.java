package com.example.provider.Service.Impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.example.provider.Redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.provider.Dao.RecordDao;
import com.example.provider.Entity.Borrowid;
import com.example.provider.Entity.Record;

import com.example.provider.Service.RecordService;

@Service
@Transactional
public class RecordServiceImpl implements RecordService{

	@Autowired
	private RecordDao recordDao;
	@Autowired
	private RedisService redisService;

	@Override
	public Record saveAndFlush(Record record) {
		// TODO Auto-generated method stub
		recordDao.saveAndFlush(record);
		redisService.delete("record");
		redisService.set("records", recordDao.findAll());
		return record;
	}

	@Override
	public List<Record> findAll() {
		// TODO Auto-generated method stub
		List<Record> records;
		String key = "records";
//		redisService.delete(key);
		Object recordCache = redisService.get(key);
//		System.out.println(recordCache.getClass());

		if(recordCache==null){
			records=recordDao.findAll();
			redisService.set(key,records);
		}else{
			records = objectConvertToList(recordCache, Record.class);
		}
//		records=recordDao.findAll();
		return records;
	}

	@Override
	public List<Record> findByBorrowidUsersidLikeOrBorrowidBooksidLike(String usersid,String booksid) {
		// TODO Auto-generated method stub
		return recordDao.findByBorrowidUsersidLikeOrBorrowidBooksidLike("%"+usersid+"%","%"+booksid+"%");
	}

	@Override
	public List<Record> findByBorrowidUsersid(String usersid) {
		// TODO Auto-generated method stub
		return recordDao.findByBorrowidUsersid(usersid);
	}

	@Override
	public List<Record> findByReturntimeIsNull() {
		// TODO Auto-generated method stub
		return recordDao.findByReturntimeIsNull();
	}
	
	@Override
	public void deleteByBorrowidUsersid(String usersid) {
		// TODO Auto-generated method stub
		recordDao.deleteByBorrowidUsersid(usersid);
		redisService.delete("record");
		redisService.set("records", recordDao.findAll());
	}

	@Override
	public void deleteByBorrowidBooksid(String booksid) {
		// TODO Auto-generated method stub
		recordDao.deleteByBorrowidBooksid(booksid);
		redisService.delete("record");
		redisService.set("records", recordDao.findAll());
	}

	@Override
	public void deleteByBorrowid(Borrowid borrowid) {
		// TODO Auto-generated method stub
		recordDao.deleteByBorrowid(borrowid);
		redisService.delete("record");
		redisService.set("records", recordDao.findAll());
	}

	@Override
	public List<Record> findByBorrowidAndBorrowtime(Borrowid borrowid, Timestamp borrowtime) {
		// TODO Auto-generated method stub
		return recordDao.findByBorrowidAndBorrowtime(borrowid, borrowtime);
	}

	@Override
	public boolean existByBorrowid(Borrowid borrowid) {
		// TODO Auto-generated method stub
		return recordDao.existsByBorrowid(borrowid);
	}

	@Override
	public Record findByBorrowid(Borrowid borrowid) {
		// TODO Auto-generated method stub
		return recordDao.findByBorrowid(borrowid);
	}

	public static <T> List<T> objectConvertToList(Object obj, Class<T> clazz) {
		List<T> result = new ArrayList<>();
		if(obj instanceof List<?>)
		{
			for (Object o : (List<?>) obj)
			{
				result.add(clazz.cast(o));
			}
			return result;
		}
		return null;
	}

}
