package com.bank.dao.impl;

import com.bank.beans.Customer;
import com.bank.beans.User;
import com.bank.data.Database;;

public class UserDaoImpl implements com.bank.dao.UserDao {
	private Database data;
	
	public UserDaoImpl(Database data) {
		this.data = data;
	}
	
	@Override
	public User getUser(int id) {
		return (User) data.getUserList().get(id);
	}

	@Override
	public void addCustomer(int id, String password, String firstname, String lastname, String phonenumber, String email) {
		data.getUserList().put(id, new Customer(id, password, "Customer",firstname, lastname, phonenumber, email));
	}

}
