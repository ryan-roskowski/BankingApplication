package com.bank.services.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.bank.beans.Account;
import com.bank.beans.Transaction;
import com.bank.dao.impl.AccountDaoImpl;
import com.bank.dao.impl.CustomerDaoImpl;
import com.bank.dao.impl.TransactionDaoImpl;
import com.bank.enums.*;
public class AccountServiceImpl implements com.bank.services.AccountService {
	AccountDaoImpl accountDao;
	TransactionDaoImpl transactionDao;
	

	public AccountServiceImpl(AccountDaoImpl accountDao, TransactionDaoImpl transactionDao) {
		this.accountDao = accountDao;
		this.transactionDao = transactionDao;
	}

	@Override
	public DepositResult deposit(Account account, int amount) throws IOException, SQLException {
		if(accountDao.deposit(account, amount) == DepositResult.SUCCESS) {
			account.setBalance(account.getBalance()+amount);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDate localDate = LocalDate.now();
			transactionDao.addTransaction(account, new Transaction(TransactionType.DEPOSIT, amount, dtf.format(localDate)));
			return DepositResult.SUCCESS;
		}
		return DepositResult.FAILURE;
	}

	@Override
	public WithdrawResult withdraw(Account account, int amount) throws SQLException, IOException{
		WithdrawResult res = accountDao.withdraw(account, amount);
		if(res == WithdrawResult.SUCCESS) {
			account.setBalance(account.getBalance()-amount);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDate localDate = LocalDate.now();
			transactionDao.addTransaction(account, new Transaction(TransactionType.WITHDRAW, amount, dtf.format(localDate)));
			return WithdrawResult.SUCCESS;
		}
		else if(res == WithdrawResult.OVERDRAFT) {
			return WithdrawResult.OVERDRAFT;
		}
		return WithdrawResult.FAILURE;
	}

	

}
