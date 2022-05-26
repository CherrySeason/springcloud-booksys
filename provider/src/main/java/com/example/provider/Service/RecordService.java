package com.example.provider.Service;


import java.sql.Timestamp;
import java.util.List;

import com.example.provider.Entity.Borrowid;
import com.example.provider.Entity.Record;

public interface RecordService {
	List<Record> findByBorrowidAndBorrowtime(Borrowid borrowid,Timestamp borrowtime);
	Record saveAndFlush(Record record);
	void deleteByBorrowid(Borrowid borrowid);
	List<Record> findAll();
	List<Record> findByBorrowidUsersidLikeOrBorrowidBooksidLike(String usersid,String booksid);
	List<Record> findByBorrowidUsersid(String usersid);
	//Record findByBorrowidAndBorrowtimeAndFineisNull(Borrowid borrowid,Timestamp borrowtime);
	List<Record> findByReturntimeIsNull();
	void deleteByBorrowidUsersid(String usersid);
	void deleteByBorrowidBooksid(String booksid);
	boolean existByBorrowid(Borrowid borrowid);
	Record findByBorrowid(Borrowid id);
}
