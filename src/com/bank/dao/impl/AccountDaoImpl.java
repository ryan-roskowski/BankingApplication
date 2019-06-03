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
import java.util.ArrayList;

import com.bank.beans.Account;
import com.bank.beans.CheckingAccount;
import com.bank.beans.Properties;
import com.bank.beans.SavingsAccount;
import com.bank.beans.User;
import com.bank.data.Database;

public class AccountDaoImpl implements com.bank.dao.AccountDao {
	private Properties properties;
	private Database data;
	private boolean useDatabase;
	public AccountDaoImpl(Properties properties, Database data) throws ClassNotFoundException, IOException {
		this.data = data;
		this.properties = properties;
		if(properties.getProperties().get("data-source").equals("database")) {
			this.useDatabase = true;
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				throw new ClassNotFoundException("oracle.jdbc.driver.OracleDriver class not found.");
			}
		}
		else if(properties.getProperties().get("data-source").equals("file")) {
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(properties.getProperties().get("accountData")));
				String line;
				String[] accountData;
				while((line = reader.readLine()) != null) {
					accountData = line.split(":");
					if(accountData[3].equals("Checking")) {
						data.getAccountList().put(Integer.parseInt(accountData[0]), new CheckingAccount(Integer.parseInt(accountData[0]), Integer.parseInt(accountData[1]), Long.parseLong(accountData[2]), "Checking", accountData[4], accountData[5]));
					}
					else if(accountData[3].equals("Savings")) {
						data.getAccountList().put(Integer.parseInt(accountData[0]), new SavingsAccount(Integer.parseInt(accountData[0]), Integer.parseInt(accountData[1]), Long.parseLong(accountData[2]), "Savings", accountData[4], accountData[5]));
					}
				}			
				reader.close();
			} catch (FileNotFoundException e) {
				throw new FileNotFoundException("Error, user data file not found.");
			} catch(IOException e) {
				throw new IOException("Error reading user data file.");
			}
			
		}
	}
	@Override
	public ArrayList<Account> getAccounts(User user) throws SQLException {
		ArrayList<Account> accounts = new ArrayList<Account>();
		if(!isUseDatabase()) {
			for(Account a : data.getAccountList().values()) {
				if(user.getId() == a.getUserId()) {
					accounts.add(a);
				}
			}
			return accounts;
		}
		try {
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","password");
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select * from ACCOUNTS where USERID = "+user.getId());
			while(rs.next()) {
				accounts.add(new Account(Integer.parseInt(rs.getString("ID")), Integer.parseInt(rs.getString("USERID")), Long.parseLong(rs.getString("BALANCE")), rs.getString("TYPE"), rs.getString("FIRST_NAME"), rs.getString("LAST_NAME")));
			}
			return accounts;
		} catch(SQLException e) {
			throw new SQLException("Error getting accounts from database");
		}
	}
	public boolean isUseDatabase() {
		return useDatabase;
	}
	public void setUseDatabase(boolean useDatabase) {
		this.useDatabase = useDatabase;
	}
	@Override
	public void addAccount(int accountId, User user, String type) throws SQLException, IOException {
		if(!isUseDatabase()) {
			data.getAccountList().put(accountId, new Account(accountId, user.getId(), 0, type, user.getFirstName(), user.getLastName()));
			if(properties.getProperties().get("data-source").equals("file")) {
				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter(properties.getProperties().get("accountData"), true));
					writer.write(accountId+":"+user.getId()+":"+0+":"+type+":"+user.getFirstName()+":"+user.getLastName());
					writer.newLine();
					writer.close();
				} catch(IOException e) {
					throw new IOException("Error writing new account to data file.");
				}
				
			}
		}
		else {
			try {
				Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","password");
				Statement st = conn.createStatement();
				PreparedStatement ps = conn.prepareStatement("Insert into ACCOUNTS (ID, USERID, TYPE, FIRST_NAME, LAST_NAME, BALANCE) VALUES (?, ?, ?, ?, ?, ?)");
				ps.setString(1, Integer.toString(accountId));
				ps.setString(2, Integer.toString(user.getId()));
				ps.setString(3, type);
				ps.setString(4, user.getFirstName());
				ps.setString(5, user.getLastName());
				ps.setString(6, "0");
				ps.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
				throw new SQLException("Database error trying to add account.");
			}
		}
		
	}
	@Override
	public boolean deposit(Account account, int amount) throws IOException, SQLException {
		if(!isUseDatabase() && properties.getProperties().get("data-source").equals("file")) {
			try {
				String line;
				ArrayList<String> lines = new ArrayList<String>();
				BufferedReader reader = new BufferedReader(new FileReader(properties.getProperties().get("accountData")));
				while((line = reader.readLine()) != null) {
					lines.add(line);
				}
				reader.close();
				BufferedWriter writer = new BufferedWriter(new FileWriter(properties.getProperties().get("accountData")));
				String[] segments;
				for(String l : lines) {
					segments = l.split(":");
					if(segments[0].equals(Integer.toString(account.getAccountId()))) {
						writer.write(account.getAccountId()+":"+account.getUserId()+":"+(account.getBalance()+amount)+":"+account.getType()+":"+account.getFirstname()+":"+account.getLastname());
						writer.newLine();
					}
					else {
						writer.write(l);
					}
				}
				writer.close();
				return true;
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				throw new FileNotFoundException("Error, account data file not found on deposit.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new IOException("Error reading/writing account data file on deposit.");
			}
		}
		else {
			Connection conn;
			try {
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","password");
				Statement st = conn.createStatement();
				st.executeUpdate("UPDATE ACCOUNTS SET BALANCE = "+(account.getBalance()+amount)+" WHERE ID = "+account.getAccountId());
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new SQLException("Database error on deposit");
			}
			
		}
	}
	@Override
	public boolean withdraw(Account account, int amount) throws SQLException, IOException {
		// TODO Auto-generated method stub
		if(!isUseDatabase() && properties.getProperties().get("data-source").equals("file")) {
			try {
				String line;
				ArrayList<String> lines = new ArrayList<String>();
				BufferedReader reader = new BufferedReader(new FileReader(properties.getProperties().get("accountData")));
				while((line = reader.readLine()) != null) {
					lines.add(line);
				}
				reader.close();
				BufferedWriter writer = new BufferedWriter(new FileWriter(properties.getProperties().get("accountData")));
				String[] segments;
				for(String l : lines) {
					segments = l.split(":");
					if(segments[0].equals(Integer.toString(account.getAccountId()))) {
						writer.write(account.getAccountId()+":"+account.getUserId()+":"+(account.getBalance()-amount)+":"+account.getType()+":"+account.getFirstname()+":"+account.getLastname());
						writer.newLine();
					}
					else {
						writer.write(l);
					}
				}
				writer.close();
				return true;
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				throw new FileNotFoundException("Error, account data file not found on withdraw.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new IOException("Error reading/writing account data file on withdraw.");
			}
		}
		else {
			Connection conn;
			try {
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","password");
				Statement st = conn.createStatement();
				st.executeUpdate("UPDATE ACCOUNTS SET BALANCE = "+(account.getBalance()-amount)+" WHERE ID = "+account.getAccountId());
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new SQLException("Database error on withdraw");
			}
			
		}
	}
	
}
