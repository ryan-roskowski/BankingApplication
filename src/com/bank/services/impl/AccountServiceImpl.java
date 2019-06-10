package com.bank.services.impl;

import java.io.IOException;
import java.sql.SQLException;

import com.bank.beans.Account;
import com.bank.dao.impl.AccountDaoImpl;
import com.bank.enums.*;
public class AccountServiceImpl implements com.bank.services.AccountService {
	AccountDaoImpl accountDao;
	

	public AccountServiceImpl(AccountDaoImpl accountDao) {
		this.accountDao = accountDao;
	}

	@Override
	public DepositResult deposit(Account account, int amount) throws IOException, SQLException {
		if(accountDao.deposit(account, amount) == DepositResult.SUCCESS) {
			account.setBalance(account.getBalance()+amount);
			return DepositResult.SUCCESS;
		}
		return DepositResult.FAILURE;
	}

	@Override
	public WithdrawResult withdraw(Account account, int amount) throws SQLException, IOException{
		WithdrawResult res = accountDao.withdraw(account, amount);
		if(res == WithdrawResult.SUCCESS) {
			account.setBalance(account.getBalance()-amount);
			return WithdrawResult.SUCCESS;
		}
		else if(res == WithdrawResult.OVERDRAFT) {
			return WithdrawResult.OVERDRAFT;
		}
		return WithdrawResult.FAILURE;
	}

	

}
