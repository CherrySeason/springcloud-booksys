package com.example.provider.Service;

import java.util.List;


import com.example.provider.Entity.Classific;

public interface ClassService {

	Classific findByClassid(String classid);
	List<Classific> findByClassnameLike(String classname);
	List<Classific> findAll();
}
