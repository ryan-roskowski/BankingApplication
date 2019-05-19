package com.bank.beans;

public class Employee extends User {

	private String employeeType;
	public Employee(int id, String password, String type, String firstName, String lastName, String employeeType) {
		super(id, password, type, firstName, lastName);
		this.employeeType = employeeType;
		
	}
	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	
}
