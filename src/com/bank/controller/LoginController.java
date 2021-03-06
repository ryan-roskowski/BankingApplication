package com.bank.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.bank.beans.Customer;
import com.bank.beans.Employee;
import com.bank.beans.User;
import com.bank.dao.impl.CustomerDaoImpl;
import com.bank.dao.impl.EmployeeDaoImpl;
import com.bank.dao.impl.UserDaoImpl;
import com.bank.services.impl.UserServiceImpl;
import com.bank.enums.UserAction;

public class LoginController {
	UserDaoImpl userDao;
	EmployeeDaoImpl employeeDao;
	CustomerDaoImpl customerDao;
	UserServiceImpl userService;
	
	public LoginController(UserDaoImpl userDao, EmployeeDaoImpl employeeDao, CustomerDaoImpl customerDao) {
		this.userDao = userDao;
		this.employeeDao = employeeDao;
		this.customerDao = customerDao;
		this.userService = new UserServiceImpl();
	}
	public User login(String username, String password) throws SQLException, IOException {
		User user = userDao.getUser(username);
		if(user != null && userService.verifyUser(user, username, password)) {
			switch(user.getType()) {
			case "Employee":
				Employee e = employeeDao.getEmployee(user.getUserId());
				if(e == null) System.out.println("e null.");
				System.out.println(user.getUserId());
				e.setUserId(user.getUserId());
				e.setUsername(user.getUsername());
				e.setPassword(user.getPassword());
				e.setType(user.getType());
				return e;
			case "Customer":
				Customer c = customerDao.getCustomer(user.getUserId());
				c.setUserId(user.getUserId());
				c.setUsername(user.getUsername());
				c.setPassword(user.getPassword());
				c.setType(user.getType());
				return c;
			default:
				return null;
			}
			
		}
		else {
			return null;
		}
	}
	
	public String getMenu(User user) {
		switch(user.getType()) {
		case "Employee":
			Employee employee = (Employee) user;
			switch(employee.getEmployeeType()) {
			case "Manager":
				return "1. Create Customer With Login\n"+
					   "2. Create Account for Customer\n"+
					   "3. Manager option 1\n"+
					   "4. Manager option 2\n"+
					   "5. Logout\n"+
					   "Please select an action from the menu above";
			case "Teller":
				return "1. Create Customer With Login\n"+
					   "2. Create Account for Customer\n"+
				   	   "3. Teller option 1\n"+
				       "4. Teller option 2\n"+
				   	   "5. Logout\n"+
					   "Please select an action from the menu above";
			default: return "invalid";
			}
		case "Customer":
			return "1. View balance\n"+
				   "2. Deposit\n"+
				   "3. Withdraw\n"+
				   "4. View Statement\n"+
				   "5. Logout\n"+
				   "Please select an action from the menu above";
		default: return "invalid";
		}
	}
	
	public UserAction performAction(User user, String input) {
		switch (user.getType()) {
		case "Employee":
			Employee employee = (Employee) user;
			switch (employee.getEmployeeType()) {
			case "Manager":
				switch (input) {
				case "1":
					return UserAction.CREATE_CUSTOMER;
				case "2":
					return UserAction.CREATE_ACCOUNT_FOR_CUSTOMER;
				case "3":
					return UserAction.MANAGER_1;
				case "4":
					return UserAction.MANAGER_2;
				case "5":
					return UserAction.LOGOUT;
				default:
					return UserAction.INVALID;
				}
			case "Teller":
				switch (input) {
				case "1":
					return UserAction.CREATE_CUSTOMER;
				case "2":
					return UserAction.CREATE_ACCOUNT_FOR_CUSTOMER;
				case "3":
					return UserAction.TELLER_1;
				case "4":
					return UserAction.TELLER_2;
				case "5":
					return UserAction.LOGOUT;
				default:
					return UserAction.INVALID;
				}
			default:
				return UserAction.INVALID;
			}
		case "Customer":
			switch (input) {
			case "1":
				return UserAction.VIEW_BALANCE;
			case "2":
				return UserAction.DEPOSIT;
			case "3":
				return UserAction.WITHDRAW;
			case "4":
				return UserAction.VIEW_TRANSACTIONS;
			case "5":
				return UserAction.LOGOUT;
			default:
				return UserAction.INVALID;
			}
		default:
			return UserAction.INVALID;
		}
	}
}
