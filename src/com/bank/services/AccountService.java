package com.bank.services;

import java.io.IOException;

import java.sql.SQLException;

import com.bank.beans.Account;
import com.bank.enums.*;

public interface AccountService {
	public DepositResult deposit(Account account, int amount) throws IOException, SQLException;
	public WithdrawResult withdraw(Account account, int amount) throws IOException, SQLException;
}
