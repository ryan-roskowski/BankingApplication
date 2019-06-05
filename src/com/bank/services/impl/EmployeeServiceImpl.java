package com.bank.services.impl;

import java.io.IOException;
import java.sql.SQLException;

import com.bank.dao.impl.CustomerDaoImpl;
import com.bank.dao.impl.UserDaoImpl;

public class EmployeeServiceImpl implements com.bank.services.EmployeeService {
	UserDaoImpl userDao;
	CustomerDaoImpl customerDao;
	
	public EmployeeServiceImpl(UserDaoImpl userDao, CustomerDaoImpl customerDao) {
		this.userDao = userDao;
		this.customerDao = customerDao;
	}
	@Override
	public void createCustomerUser(String username, String password, String firstname, String lastname, String address, String phonenumber) throws Exception {
		userDao.addUser(username, password, "Customer");
		customerDao.addCustomerWithUserId(username, firstname, lastname, address, phonenumber);
	}
	
}
