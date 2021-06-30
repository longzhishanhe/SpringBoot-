package com.school.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.market.dao.AdminDao;
import com.school.market.domain.Admin;
@Service
public class AdminService {
	@Autowired
	private AdminDao adminDao;
	
	/**
	 * //根据用户名和密码查找用户
	 * @param username
	 * @param password
	 * @return
	 */
	public Admin login(String username,String password) {
		return adminDao.findAdmin(username, password);
	}
}
