package com.bank.beans;

public class SavingsAccount extends Account {

	private final int withdrawLimit = 500;
	private final double interest = 0.02;
	public SavingsAccount(int accountId, int userId, long balance, String type, String firstname, String lastname) {
		super(accountId, userId, balance, type, firstname, lastname);
		// TODO Auto-generated constructor stub
	}
	public int getWithdrawLimit() {
		return withdrawLimit;
	}
	public double getInterest() {
		return interest;
	}

}
