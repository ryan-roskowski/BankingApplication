package com.bank.services.impl;

import com.bank.beans.User;

public class UserServiceImpl implements com.bank.services.UserService {

	@Override
	public boolean verifyUser(User u, String username, String password) {
		return (u.getUsername().equals(username) && u.getPassword().equals(password));
	}

}
