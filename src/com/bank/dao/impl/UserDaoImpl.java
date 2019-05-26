package com.bank.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.bank.beans.Customer;
import com.bank.beans.Employee;
import com.bank.beans.Properties;
import com.bank.beans.User;
import com.bank.data.Database;;

public class UserDaoImpl implements com.bank.dao.UserDao {
	private Database data;
	private Properties properties;
	
	public UserDaoImpl(Properties properties) throws IOException  {
		this.properties = properties;
		this.data = new Database();
		if(properties.getProperties().get("data-source").equals("file")) {
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(properties.getProperties().get("data-file")));
				String line;
				String[] userData;
				while((line = reader.readLine()) != null) {
					userData = line.split(":");
					if(userData[2].equals("Employee")) {
						data.getUserList().put(Integer.parseInt(userData[0]), new Employee(Integer.parseInt(userData[0]),userData[1],"Employee",userData[3],userData[4],userData[5]));
					}
					else if(userData[2].equals("Customer")) {
						data.getUserList().put(Integer.parseInt(userData[0]), new Customer(Integer.parseInt(userData[0]),userData[1],"Customer",userData[3],userData[4],userData[5],userData[6]));
					}
				}
				reader.close();
			} catch (FileNotFoundException e) {
				System.out.println("Error, data file not found.");
				throw e;
			} catch(IOException e) {
				System.out.println("Error reading data file.");
			}
			
		}
		else {
			data.generateStaticUsers();
		}
		
	}
	
	@Override
	public User getUser(int id) {
		return (User) data.getUserList().get(id);
	}

	@Override
	public void addCustomer(int id, String password, String firstname, String lastname, String phonenumber, String email) throws IOException {
		data.getUserList().put(id, new Customer(id, password, "Customer",firstname, lastname, phonenumber, email));
		if(properties.getProperties().get("data-source").equals("file")) {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(properties.getProperties().get("data-file"), true));
				writer.write(id+":"+password+":"+"Customer"+":"+firstname+":"+lastname+":"+phonenumber+":"+email);
				writer.newLine();
				writer.close();
			} catch(IOException e) {
				System.out.println("Error writing new customer to data file.");
				throw e;
			}
		}
	}
	
	public void addEmployee(int id, String password, String firstname, String lastname, String type) throws IOException {
		data.getUserList().put(id, new Employee(id, password, "Employee", firstname, lastname, type));
		if(properties.getProperties().get("data-source").equals("file")) {
			try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(properties.getProperties().get("data-source"), true));
			writer.write(id+":"+password+":"+"Employee"+":"+firstname+":"+lastname);
			writer.newLine();
			writer.close();
			} catch(IOException e) {
				System.out.println("Error writing new customer to data file");
				throw e;
			}
		}
	}

}
