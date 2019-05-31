package com.bank.services.impl;

import java.io.IOException;
import java.sql.SQLException;

import com.bank.dao.impl.UserDaoImpl;

public class EmployeeServiceImpl implements com.bank.services.EmployeeService {
	UserDaoImpl userDao;
	
	public EmployeeServiceImpl(UserDaoImpl userDao) {
		this.userDao = userDao;
	}
	@Override
	public void createCustomer(int id, String password, String firstname, String lastname, String phonenumber,String email) throws IOException, SQLException {
		try {
			userDao.addCustomer(id, password, firstname, lastname, phonenumber, email);
		} catch (IOException e) {
			throw e;
		}
	}
	
}
