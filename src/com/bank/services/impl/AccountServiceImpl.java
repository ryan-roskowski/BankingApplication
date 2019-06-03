package com.bank.services.impl;

import java.io.IOException;
import java.sql.SQLException;

import com.bank.beans.Account;
import com.bank.dao.impl.AccountDaoImpl;

public class AccountServiceImpl implements com.bank.services.AccountService {
	AccountDaoImpl accountDao;
	

	public AccountServiceImpl(AccountDaoImpl accountDao) {
		this.accountDao = accountDao;
	}

	@Override
	public boolean deposit(Account account, int amount) throws IOException, SQLException {
		if(accountDao.deposit(account, amount)) {
			account.setBalance(account.getBalance()+amount);
			return true;
		}
		return false;
	}

	@Override
	public boolean withdraw(Account account, int amount) throws SQLException, IOException{
		if(accountDao.withdraw(account, amount)) {
			account.setBalance(account.getBalance()-amount);
			return true;
		}
		return false;
	}

	

}
