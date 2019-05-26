package com.bank.services;

import java.io.IOException;

public interface EmployeeService {
	public void createCustomer(int id, String password, String firstname, String lastname, String phonenumber, String email) throws IOException;
}
