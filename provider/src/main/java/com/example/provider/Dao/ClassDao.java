package com.example.provider.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.provider.Entity.Classific;

public interface ClassDao extends JpaRepository<Classific, String>{

	Classific findByClassid(String classid);
	List<Classific> findByClassnameLike(String classname);
	List<Classific> findAll();
}
