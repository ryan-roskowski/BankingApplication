package com.bank.controller;

import com.bank.beans.Employee;
import com.bank.beans.User;
import com.bank.dao.impl.UserDaoImpl;

public class LoginController {
	UserDaoImpl userDao;
	
	public LoginController(UserDaoImpl userDao) {
		this.userDao = userDao;
	}
	public User login(int id, String password) {
		User user = userDao.getUser(id);
		if(user != null && user.getPassword().equals(password)) {
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
					   "2. Manager option 1\n"+
					   "3. Manager option 2\n"+
					   "4. Logout\n"+
					   "Please select an action from the menu above";
			case "Teller":
				return "1. Create Customer\n"+
				   	   "2. Teller option 1\n"+
				       "3. Teller option 2\n"+
				   	   "4. Logout\n"+
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
					return "Performed Manager option 1";
				case "3":
					return "Performed Manager option 2";
				case "4":
					return "logout";
				default:
					return "invalid menu item";
				}
			case "Teller":
				switch (input) {
				case "1":
					return "Create Customer";
				case "2":
					return "Performed Teller option 1";
				case "3":
					return "Performed Teller option 2";
				case "4":
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
				return "Your balance is $100";
			case "2":
				return "Deposited $100";
			case "3":
				return "Withdrew $100";
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
