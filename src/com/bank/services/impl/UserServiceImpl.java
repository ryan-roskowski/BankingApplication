package com.bank.services.impl;

import com.bank.beans.User;

public class UserServiceImpl implements com.bank.services.UserService {

	@Override
	public boolean verifyUser(User u, int id, String password) {
		return (u.getId() == id && u.getPassword().equals(password));
	}

}
