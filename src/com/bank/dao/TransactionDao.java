package com.bank.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bank.beans.Account;
import com.bank.beans.Customer;
import com.bank.beans.Transaction;

public interface TransactionDao {
	public ArrayList<Transaction> getTransactions(Customer customer, Account account) throws SQLException, IOException;
	public void addTransaction(Account account, Transaction transaction) throws SQLException, IOException;
}
