package com.bank.services;

import com.bank.beans.User;

public interface UserService {
	public boolean verifyUser(User u, int id, String password);
}
