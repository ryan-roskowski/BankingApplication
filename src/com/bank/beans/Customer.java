package com.bank.beans;

public class Customer extends User {
	private String phoneNumber;
	private String email;
	

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
