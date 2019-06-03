package com.bank.clientsim;

import com.bank.beans.*;
import com.bank.controller.*;
import com.bank.dao.impl.*;
import com.bank.data.Database;
import com.bank.services.impl.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankingApplication {
	Database data;
	UserDaoImpl userDao;
	UserServiceImpl userService;
	AccountDaoImpl accountDao;
	LoginController loginController;
	AccountController accountController;
	EmployeeServiceImpl employeeService;
	Properties properties;
	AccountServiceImpl accountService;
	
	public BankingApplication() throws Exception {
		data = new Database();
		properties = new Properties("C:\\Users\\Ryan\\git\\Bank\\BankingApplication\\properties.txt");
		userDao = new UserDaoImpl(properties, data);
		accountDao = new AccountDaoImpl(properties, data);
		userService = new UserServiceImpl();
		loginController = new LoginController(userDao);
		accountController = new AccountController(accountDao);
		employeeService = new EmployeeServiceImpl(userDao);
		accountService = new AccountServiceImpl(accountDao);
	}
	
	public void runApplication() throws Exception {
		Scanner sc = new Scanner(System.in);
		String input;
		String response;
		String menu;
		int id;
		String password;
		User user = null; 
		Customer customer;
		
		mainMenu:
		while(true) {
			System.out.println("1. Login");
			System.out.println("2. Exit Application");
			System.out.print("Please select an action from the menu above: ");
			input = sc.nextLine();
			switch(input) {
			case "1":
				System.out.println("You chose login. Please enter userId and password below.");
				System.out.print("userId: ");
				try {
					id = sc.nextInt();
				} catch(InputMismatchException ex) {
					sc.nextLine();
					System.out.println("Invalid userId. Aborting login...");
					break;
				}
				sc.nextLine();
				System.out.print("password: ");
				password = sc.nextLine();
				try {
					user = loginController.login(id, password);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(user != null) {
					System.out.println("Successfully logged in");
					userSession:
					while(true) {
						menu = loginController.getMenu(user);
						if(menu.equals("invalid")) {
							System.out.println("Invalid user type. Please contact administrator.");
							System.out.println("logging out...");
							break;
						}
						else {
							System.out.println(menu);
						}
						input = sc.nextLine();
						response = loginController.performAction(user, input);
						switch(response) {
						case "Create Customer":
							int customerId;
							String customerPassword;
							String firstname;
							String lastname;
							String phonenumber;
							String email;
							System.out.println("Please enter Customer information below");
							try {
								System.out.print("Customer ID Number: ");
								customerId = sc.nextInt();
								sc.nextLine();
								System.out.print("Customer Password: ");
								customerPassword = sc.nextLine();
								System.out.print("Customer First Name: ");
								firstname = sc.nextLine();
								System.out.print("Customer Last Name: ");
								lastname = sc.nextLine();
								System.out.print("Customer Phone Number: ");
								phonenumber = sc.nextLine();
								System.out.print("Customer Email: ");
								email = sc.nextLine();
								employeeService.createCustomer(customerId, customerPassword, firstname, lastname, phonenumber, email);
								System.out.println("Successfully created customer");
							} catch (InputMismatchException ex){
								sc.nextLine();
								System.out.println("Invalid Customer ID Number. Aborting...");
							} catch (SQLException e) {
								System.out.println(e.getMessage());
							}
							break;
						case "logout":
							System.out.println("You chose logout. Returning to main menu...");
							break userSession;
						case "Create Account for Customer":
							try {
								int accountId;
								int userId;
								String type;
								System.out.println("Enter Account information below.");
								System.out.print("User ID Number: ");
								userId = sc.nextInt();
								sc.nextLine();
								User accUser = userDao.getUser(userId);
								if(accUser == null || !accUser.getType().equals("Customer")) {
									System.out.println("Invalid user.");
								}
								else {	
									System.out.print("Account ID Number: ");
									accountId = sc.nextInt();
									sc.nextLine();
									System.out.print("Account Type: ");
									type = sc.nextLine();
									accountDao.addAccount(accountId, accUser, type);
								}
							} catch (InputMismatchException ex){
								sc.nextLine();
								System.out.println("Invalid Account ID or User ID. Aborting...");
							} catch (SQLException e) {
								System.out.println(e.getMessage());
							}
							break;
						case "View Balance":
							customer = (Customer) user;
							customer.setAccounts(accountController.getAccounts(customer));
							if(customer.getAccounts().isEmpty()) {
								System.out.println("This user has no accounts, please have an employee add an account.");
							}
							else {
								System.out.println("Please select an account: ");
								for(int i = 0; i < customer.getAccounts().size(); i++) {
									System.out.println((i+1)+". "+customer.getAccounts().get(i).getType());
								}
								try {
									int choice = sc.nextInt();
									sc.nextLine();
									if(choice <= 0 || choice > customer.getAccounts().size()) {
										System.out.println("Invalid account choice.");
									}
									else {
										System.out.println("Balance : "+customer.getAccounts().get(choice-1).getBalance());
									}
								}catch(InputMismatchException e) {
									System.out.println("Invalid account choice.");
								}
							}
							break;
						case "Deposit":
						    customer = (Customer) user;
							customer.setAccounts(accountController.getAccounts(customer));
							if(customer.getAccounts().isEmpty()) {
								System.out.println("This user has no accounts, please have an employee add an account.");
							}
							else {
								System.out.println("Please select an account: ");
								for(int i = 0; i < customer.getAccounts().size(); i++) {
									System.out.println((i+1)+". "+customer.getAccounts().get(i).getType());
								}
								try {
									int choice = sc.nextInt();
									sc.nextLine();
									if(choice <= 0 || choice > customer.getAccounts().size()) {
										System.out.println("Invalid account choice.");
									}
									else {
										int amount;
										long prevBalance;
										try {
											System.out.print("Please enter deposit amount: ");
											amount = sc.nextInt();
											sc.nextLine();
											prevBalance = customer.getAccounts().get(choice-1).getBalance();
											if(accountService.deposit(customer.getAccounts().get(choice-1), amount)) {
												System.out.println("Deposit Successful.");
												System.out.println("Previous Balance: "+prevBalance);
												System.out.println("New Balance: "+customer.getAccounts().get(choice-1).getBalance());
											}
											else {
												System.out.println("Deposit failed.");
											}
										} catch(InputMismatchException e) {
											System.out.println("Invalid deposit amount");
										}
									}
								}catch(InputMismatchException e) {
									System.out.println("Invalid account choice.");
								}
							}
							break;
						case "Withdraw":
							customer = (Customer) user;
							customer.setAccounts(accountController.getAccounts(customer));
							if(customer.getAccounts().isEmpty()) {
								System.out.println("This user has no accounts, please have an employee add an account.");
							}
							else {
								System.out.println("Please select an account: ");
								for(int i = 0; i < customer.getAccounts().size(); i++) {
									System.out.println((i+1)+". "+customer.getAccounts().get(i).getType());
								}
								try {
									int choice = sc.nextInt();
									sc.nextLine();
									if(choice <= 0 || choice > customer.getAccounts().size()) {
										System.out.println("Invalid account choice.");
									}
									else {
										int amount;
										long prevBalance;
										try {
											System.out.print("Please enter withdraw amount: ");
											amount = sc.nextInt();
											sc.nextLine();
											prevBalance = customer.getAccounts().get(choice-1).getBalance();
											if(accountService.withdraw(customer.getAccounts().get(choice-1), amount)) {
												System.out.println("Withdraw Successful.");
												System.out.println("Previous Balance: "+prevBalance);
												System.out.println("New Balance: "+customer.getAccounts().get(choice-1).getBalance());
											}
											else {
												System.out.println("Withdraw failed.");
											}
										} catch(InputMismatchException e) {
											System.out.println("Invalid Withdraw amount");
										}
									}
								}catch(InputMismatchException e) {
									System.out.println("Invalid account choice.");
								}
							}
							break;
						default:
							System.out.println(response);
						}
					}
				}
				else {
					System.out.println("Invalid User");
				}
				break;
			case "2":
				System.out.println("You chose Exit Application");
				System.out.println("Terminating...");
				break mainMenu;
			default:
				System.out.println("Invalid menu option: "+input);
				break;
			}
		}
		sc.close();
	}
	

	public static void main(String[] args) {
		BankingApplication app;
		try {
			app = new BankingApplication();
			app.runApplication();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("Terminating...");
		}
		
	}

}
