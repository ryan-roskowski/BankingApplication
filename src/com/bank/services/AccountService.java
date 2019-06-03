package com.bank.services;

import java.io.IOException;
import java.sql.SQLException;

import com.bank.beans.Account;

public interface AccountService {
	public boolean deposit(Account account, int amount) throws IOException, SQLException;
	public boolean withdraw(Account account, int amount) throws IOException, SQLException;
}
