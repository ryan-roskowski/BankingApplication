package com.bank.dao;

import com.bank.beans.User;


public interface UserDao {
	public User getUser(int id);
	public void addCustomer(int id, String password, String firstname, String lastname, String phonenumber, String email);
}
