package com.example.provider.Service;

import java.util.List;


import com.example.provider.Entity.Book;

public interface BookService {

	Book findByBookid(String bookid);
	List<Book> findAll();
	List<Book> findByBooknameLikeOrAuthorLikeOrPublichouseLikeOrIntroductionLikeOrBookidLike(String bookname,String author,String publichouse,String introduction,String bookid);
	List<Book> findByBookidLike(String bookid);
	Book saveAndFlush(Book book);
	void deleteByBookid(String bookid);
	boolean existsByBookid(String bookid);
}
