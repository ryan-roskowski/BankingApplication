package com.bank.dao;

import java.io.IOException;

import com.bank.beans.User;


public interface UserDao {
	public User getUser(int id);
	public void addCustomer(int id, String password, String firstname, String lastname, String phonenumber, String email) throws IOException;
	public void addEmployee(int id, String password, String firstname, String lastname, String type) throws IOException;
}
