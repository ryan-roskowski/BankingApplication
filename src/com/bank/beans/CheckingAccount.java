package com.bank.beans;

public class CheckingAccount extends Account {
	
	private final int withdrawLimit = 10000;
	private final double interest = 0.0;
	public int getWithdrawLimit() {
		return withdrawLimit;
	}
	public double getInterest() {
		return interest;
	}
	public CheckingAccount(int accountId, int userId, long balance, String type, String firstname, String lastname) {
		super(accountId, userId, balance, type, firstname, lastname);
		// TODO Auto-generated constructor stub
	}
	
}
