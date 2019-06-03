package com.bank.controller;

import java.sql.SQLException;

import com.bank.beans.Employee;
import com.bank.beans.User;
import com.bank.dao.impl.UserDaoImpl;
import com.bank.services.impl.UserServiceImpl;

public class LoginController {
	UserDaoImpl userDao;
	UserServiceImpl userService;
	
	public LoginController(UserDaoImpl userDao) {
		this.userDao = userDao;
		this.userService = new UserServiceImpl();
	}
	public User login(int id, String password) throws SQLException {
		User user = userDao.getUser(id);
		if(user != null && userService.verifyUser(user, id, password)) {
			return user;
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
				return "1. Create Customer\n"+
					   "2. Create Account for Customer\n"+
					   "3. Manager option 1\n"+
					   "4. Manager option 2\n"+
					   "5. Logout\n"+
					   "Please select an action from the menu above";
			case "Teller":
				return "1. Create Customer\n"+
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
				   "4. Logout\n"+
				   "Please select an action from the menu above";
		default: return "invalid";
		}
	}
	
	public String performAction(User user, String input) {
		switch (user.getType()) {
		case "Employee":
			Employee employee = (Employee) user;
			switch (employee.getEmployeeType()) {
			case "Manager":
				switch (input) {
				case "1":
					return "Create Customer";
				case "2":
					return "Create Account for Customer";
				case "3":
					return "Performed Manager option 1";
				case "4":
					return "Performed Manager option 2";
				case "5":
					return "logout";
				default:
					return "invalid menu item";
				}
			case "Teller":
				switch (input) {
				case "1":
					return "Create Customer";
				case "2":
					return "Create Account for Customer";
				case "3":
					return "Performed Teller option 1";
				case "4":
					return "Performed Teller option 2";
				case "5":
					return "logout";
				default:
					return "invalid menu item";
				}
			default:
				return "Error: invalid employee type.";
			}
		case "Customer":
			switch (input) {
			case "1":
				return "View Balance";
			case "2":
				return "Deposit";
			case "3":
				return "Withdraw";
			case "4":
				return "logout";
			default:
				return "invalid menu item";
			}
		default:
			return "Error: invalid user type";
		}
	}
}
