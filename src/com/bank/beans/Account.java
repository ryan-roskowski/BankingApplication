package com.bank.beans;

public class Account {
	private int userId;
	private long balance;
	private String firstname;
	private String lastname;
	
	public Account(int userId, long balance, String firstname, String lastname) {
		this.userId = userId;
		this.balance = balance;
		this.firstname = firstname;
		this.lastname = lastname;
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
