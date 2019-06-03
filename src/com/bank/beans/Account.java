package com.bank.beans;

public class Account {
	private int accountId;
	private int userId;
	private long balance;
	private String type;
	private String firstname;
	private String lastname;
	
	public Account(int accountId, int userId, long balance, String type, String firstname, String lastname) {
		super();
		this.accountId = accountId;
		this.userId = userId;
		this.balance = balance;
		this.type = type;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	
}
