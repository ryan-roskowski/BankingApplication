package com.bank.services.impl;

import com.bank.dao.impl.UserDaoImpl;

public class EmployeeServiceImpl implements com.bank.services.EmployeeService {
	UserDaoImpl userDao;
	
	public EmployeeServiceImpl(UserDaoImpl userDao) {
		this.userDao = userDao;
	}
	@Override
	public void createUser(int id, String password, String firstname, String lastname, String phonenumber,String email) {
		userDao.addCustomer(id, password, firstname, lastname, phonenumber, email);
	}
	
}
