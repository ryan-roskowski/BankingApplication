package com.bank.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bank.beans.Account;
import com.bank.beans.User;

public interface AccountDao {
	public ArrayList<Account> getAccounts(User user) throws SQLException;
	public void addAccount(int accountId, User user, String type) throws SQLException, IOException;
	public boolean deposit(Account account, int amount) throws IOException, SQLException;
	public boolean withdraw(Account account, int amount) throws IOException, SQLException;
}
