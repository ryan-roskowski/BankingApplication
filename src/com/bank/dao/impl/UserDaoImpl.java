package com.bank.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bank.beans.Customer;
import com.bank.beans.Employee;
import com.bank.beans.Properties;
import com.bank.beans.User;
import com.bank.data.Database;;

public class UserDaoImpl implements com.bank.dao.UserDao {
	private Database data;
	private Properties properties;
	private boolean useDatabase;
	
	public UserDaoImpl(Properties properties) throws IOException, ClassNotFoundException  {
		this.properties = properties;
		if(properties.getProperties().get("data-source").equals("database")) {
			this.useDatabase = true;
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				throw e;
			}
		}
		else if(properties.getProperties().get("data-source").equals("file")) {
			this.data = new Database();
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
			this.data = new Database();
			data.generateStaticUsers();
		}
		
	}
	
	public boolean isUseDatabase() {
		return useDatabase;
	}

	public void setUseDatabase(boolean useDatabase) {
		this.useDatabase = useDatabase;
	}

	@Override
	public User getUser(int id) throws SQLException {
		if(!isUseDatabase())
			return (User) data.getUserList().get(id);
		else {
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","password");
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select * from BANKING_APPLICATION_USERS where ID = "+id);
			if(rs.next() == false) {
				return null;
			}
			else {
				switch(rs.getString("TYPE")) {
				case "Customer":
					return new Customer(id, rs.getString("PASSWORD"), rs.getString("TYPE"), rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"), rs.getString("PHONE"), rs.getString("EMAIL"));
				case "Employee":
					return new Employee(id, rs.getString("PASSWORD"), rs.getString("TYPE"), rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"), rs.getString("EMPLOYEE_TYPE"));
				default:
					return null;
					
				}
				
			}
			
		}
	}

	@Override
	public void addCustomer(int id, String password, String firstname, String lastname, String phonenumber, String email) throws IOException, SQLException {
		if(!isUseDatabase()) {
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
		else {
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","password");
			Statement st = conn.createStatement();
			PreparedStatement ps = conn.prepareStatement("Insert into BANKING_APPLICATION_USERS (ID, PASSWORD, TYPE, FIRST_NAME, LAST_NAME, PHONE, EMAIL) VALUES (?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, Integer.toString(id));
			ps.setString(2, password);
			ps.setString(3, "Customer");
			ps.setString(4, firstname);
			ps.setString(5, lastname);
			ps.setString(6, phonenumber);
			ps.setString(7, email);
			ps.executeUpdate();
		}
	}
	
	public void addEmployee(int id, String password, String firstname, String lastname, String type) throws IOException, SQLException {
		if(!isUseDatabase()) {
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
		else {
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","password");
			Statement st = conn.createStatement();
			PreparedStatement ps = conn.prepareStatement("Insert into BANKING_APPLICATION_USERS (ID, PASSWORD, TYPE, FIRSTNAME, LASTNAME, EMPLOYEE_TYPE) VALUES (? ? ? ? ? ?");
			ps.setInt(0, id);
			ps.setString(1, password);
			ps.setString(2, "Employee");
			ps.setString(3, firstname);
			ps.setString(4, lastname);
			ps.setString(5, type);
			ps.executeUpdate();
		}
	}

}
