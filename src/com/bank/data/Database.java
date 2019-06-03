package com.bank.data;

import java.util.HashMap;

import com.bank.beans.User;
import com.bank.beans.Account;
import com.bank.beans.Employee;

public class Database {
	HashMap<Integer, User> userList;
	HashMap<Integer, Account> accountList;
	
	public HashMap<Integer, Account> getAccountList() {
		return accountList;
	}

	public void setAccountList(HashMap<Integer, Account> accountList) {
		this.accountList = accountList;
	}

	public void setUserList(HashMap<Integer, User> userList) {
		this.userList = userList;
	}

	public Database() {
		userList = new HashMap<Integer, User>();
		accountList = new HashMap<Integer, Account>();
		
	}
	
	public HashMap<Integer, User> getUserList(){
		return userList;
	}
	
	public void generateStaticUsers(){
		userList.put(111, new Employee(111, "password", "Employee", "Bob", "Evans", "Manager"));
		userList.put(222, new Employee(222, "helloworld", "Employee", "John", "Doe", "Manager"));
		userList.put(333, new Employee(333, "greatscott!", "Employee", "Billy", "Hammond", "Teller"));
		userList.put(444, new Employee(444, "eureka!", "Employee", "James", "Mackeral", "Teller"));
	}
}
