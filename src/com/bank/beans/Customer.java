package com.bank.beans;

import java.util.ArrayList;

public class Customer extends User {
	private String phoneNumber;
	private String email;
	private ArrayList<Account> accounts;

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}

	public Customer(int id, String password, String type, String firstName, String lastName, String phoneNumber, String email) {
		super(id, password, type, firstName, lastName);
		this.phoneNumber = phoneNumber;
		this.email = email;
		
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
	
}
