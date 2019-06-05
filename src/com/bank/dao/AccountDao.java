package com.bank.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bank.beans.Account;
import com.bank.beans.Customer;
import com.bank.beans.User;

public interface AccountDao {
	public ArrayList<Account> getAccounts(Customer customer) throws SQLException;
	public void addAccount(Customer customer, String type) throws SQLException, IOException;
	public boolean deposit(Account account, int amount) throws IOException, SQLException;
	public boolean withdraw(Account account, int amount) throws IOException, SQLException;
}
