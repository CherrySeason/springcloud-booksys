package com.example.provider.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.provider.Dao.ViewDao;
import com.example.provider.Entity.View;
import com.example.provider.Service.ViewService;

@Service
@Transactional
public class ViewServiceImpl implements ViewService{

	@Autowired
	private ViewDao viewDao;

	@Override
	public List<View> findByRecordidUsersid(String usersid) {
		// TODO Auto-generated method stub
		return viewDao.findByRecordidUsersid(usersid);
	}
	

}
