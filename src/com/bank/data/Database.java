package com.bank.data;

import java.util.HashMap;

import com.bank.beans.User;
import com.bank.beans.Account;
import com.bank.beans.Customer;
import com.bank.beans.Employee;

public class Database {
	HashMap<String, User> userList;
	HashMap<Integer, Customer> customerList;
	HashMap<Integer, Employee> employeeList;
	HashMap<Integer, Account> accountList;
	
	public Database() {
		userList = new HashMap<String, User>();
		customerList = new HashMap<Integer, Customer>();
		employeeList = new HashMap<Integer, Employee>();
		accountList = new HashMap<Integer, Account>();
		
	}
	
	public HashMap<String, User> getUserList() {
		return userList;
	}




	public void setUserList(HashMap<String, User> userList) {
		this.userList = userList;
	}




	public HashMap<Integer, Customer> getCustomerList() {
		return customerList;
	}




	public void setCustomerList(HashMap<Integer, Customer> customerList) {
		this.customerList = customerList;
	}




	public HashMap<Integer, Employee> getEmployeeList() {
		return employeeList;
	}




	public void setEmployeeList(HashMap<Integer, Employee> employeeList) {
		this.employeeList = employeeList;
	}




	public HashMap<Integer, Account> getAccountList() {
		return accountList;
	}




	public void setAccountList(HashMap<Integer, Account> accountList) {
		this.accountList = accountList;
	}

	public void generateDefaultData(){
		userList.put("employee1", new User(1, "employee1", "password1", "Employee"));
		userList.put("employee2", new User(2, "employee2", "password2", "Employee"));
		userList.put("employee3", new User(3, "employee3", "password3", "Employee"));
		userList.put("employee4", new User(4, "employee4", "password4", "Employee"));
		userList.put("customer1", new User(5, "customer1", "password1", "Customer"));
		userList.put("customer2", new User(6, "customer2", "password2", "Customer"));
		userList.put("customer3", new User(7, "customer3", "password3", "Customer"));
		userList.put("customer4", new User(8, "customer4", "password4", "Customer"));
		
		employeeList.put(1, new Employee(1, 1,"Employee1FirstName", "Employee1LastName", "Employee1Address", "Employee1Phone", "Manager"));
		employeeList.put(2, new Employee(2, 2,"Employee2FirstName", "Employee2LastName", "Employee2Address", "Employee2Phone", "Manager"));
		employeeList.put(3, new Employee(3, 3,"Employee3FirstName", "Employee3LastName", "Employee3Address", "Employee3Phone", "Teller"));
		employeeList.put(4, new Employee(4, 4,"Employee4FirstName", "Employee4LastName", "Employee4Address", "Employee4Phone", "Teller"));
		
		customerList.put(1,  new Customer(1, 5, "Customer1FirstName", "Customer1LastName", "Customer1Address", "Customer1Phone"));
		customerList.put(2,  new Customer(2, 6, "Customer2FirstName", "Customer2LastName", "Customer2Address", "Customer2Phone"));
		customerList.put(3,  new Customer(3, 7, "Customer3FirstName", "Customer3LastName", "Customer3Address", "Customer3Phone"));
		customerList.put(4,  new Customer(4, 8, "Customer4FirstName", "Customer4LastName", "Customer4Address", "Customer4Phone"));
		
		accountList.put(1, new Account(1, 100000000, 1, 100, "Checking"));
		accountList.put(2, new Account(2, 100000001, 2, 200, "Savings"));
		accountList.put(3, new Account(3, 100000002, 3, 300, "Checking"));
		accountList.put(4, new Account(4, 100003000, 4, 400, "Savings"));
		
	}
}
