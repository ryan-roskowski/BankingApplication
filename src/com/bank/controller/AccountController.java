package com.bank.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import com.bank.beans.Account;
import com.bank.beans.User;
import com.bank.dao.impl.AccountDaoImpl;
import com.bank.services.impl.AccountServiceImpl;

public class AccountController {
	AccountDaoImpl accountDao;
	AccountServiceImpl accountService;
	
	public AccountController(AccountDaoImpl accountDao) {
		this.accountDao = accountDao;
		accountService = new AccountServiceImpl(accountDao);
	}
	
	public ArrayList<Account> getAccounts(User user) throws SQLException {
		return accountDao.getAccounts(user);
	}
}
