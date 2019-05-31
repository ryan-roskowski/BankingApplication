package com.bank.dao;

import java.io.IOException;
import java.sql.SQLException;

import com.bank.beans.User;


public interface UserDao {
	public User getUser(int id) throws SQLException;
	public void addCustomer(int id, String password, String firstname, String lastname, String phonenumber, String email) throws IOException, SQLException;
	public void addEmployee(int id, String password, String firstname, String lastname, String type) throws IOException, SQLException;
}
