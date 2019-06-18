package com.bank.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import com.bank.beans.Account;
import com.bank.beans.Customer;
import com.bank.beans.User;
import com.bank.dao.impl.AccountDaoImpl;
import com.bank.dao.impl.TransactionDaoImpl;
import com.bank.services.impl.AccountServiceImpl;

public class AccountController {
	AccountDaoImpl accountDao;
	AccountServiceImpl accountService;
	TransactionDaoImpl transactionDao;
	
	public AccountController(AccountDaoImpl accountDao, TransactionDaoImpl transactionDao) {
		this.accountDao = accountDao;
		this.transactionDao = transactionDao;
		accountService = new AccountServiceImpl(accountDao, transactionDao);
	}
	
	public ArrayList<Account> getAccounts(Customer customer) throws SQLException {
		return accountDao.getAccounts(customer);
	}
}
