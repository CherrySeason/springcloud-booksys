package com.example.provider.Service;

import java.util.List;

import com.example.provider.Entity.View;

public interface ViewService {

	List<View> findByRecordidUsersid(String usersid);
}
