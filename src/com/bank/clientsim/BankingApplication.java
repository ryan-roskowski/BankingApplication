package com.bank.clientsim;

import com.bank.beans.*;
import com.bank.controller.*;
import com.bank.dao.impl.*;
import com.bank.services.impl.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankingApplication {
	UserDaoImpl userDao;
	UserServiceImpl userService;
	LoginController loginController;
	EmployeeServiceImpl employeeService;
	Properties properties;
	
	public BankingApplication() throws Exception {
		properties = new Properties("D:\\eclipse_workspace\\BankingApplication\\properties.txt");
		userDao = new UserDaoImpl(properties);
		userService = new UserServiceImpl();
		loginController = new LoginController(userDao);
		employeeService = new EmployeeServiceImpl(userDao);
	}
	
	public void runApplication() throws Exception {
		Scanner sc = new Scanner(System.in);
		String input;
		String response;
		String menu;
		int id;
		String password;
		User user = null; 
		
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
						if(response.equals("Create Customer")) {
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
						}
						else if(response.equals("logout")) {
							System.out.println("You chose logout. Returning to main menu...");
							break;
						}
						else {
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
			System.out.println(e.getMessage());
			System.out.println("Terminating...");
		}
		
	}

}
