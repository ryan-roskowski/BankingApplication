package com.bank.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bank.beans.Account;
import com.bank.beans.Customer;
import com.bank.beans.User;
import com.bank.enums.*;

public interface AccountDao {
	public ArrayList<Account> getAccounts(Customer customer) throws SQLException;
	public void addAccount(Customer customer, String type) throws SQLException, IOException;
	public DepositResult deposit(Account account, int amount) throws IOException, SQLException;
	public WithdrawResult withdraw(Account account, int amount) throws IOException, SQLException;
}
