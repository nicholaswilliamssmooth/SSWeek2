package utopia;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


import domain.Booking;
import domain.Flight;
import domain.User;
import service.TravelerService;

public class Traveler extends TextPrompt {
	
	// After the user is logged in let them interact with the rest of the class
	private User loggedInUser;
	
	public void LoginPrompt() throws ClassNotFoundException, SQLException {
		String user;
		String pass;
		Boolean loggedIn = false;
		while (!loggedIn) {
			Scanner scan = new Scanner(System.in);
			System.out.println("Please log in to continue");
			System.out.print("Username: ");
			user = scan.nextLine();
			System.out.print("Password: ");
			pass = scan.nextLine();
			loggedIn = login(user, pass);

		}
		TRAV1();
		

		

	}
	/**
	 * 
	 * @param username
	 * @param password
	 * @return true or false if the login was successful
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Boolean login(String username, String password) throws ClassNotFoundException, SQLException {
		TravelerService gtMatch = new TravelerService();

		List<User> userMatch = gtMatch.getMatchingUserList(username, password);
		if (userMatch.size() > 0) {
			loggedInUser = userMatch.get(0);
			return true;
		}
		else {
			System.out.println("User not found, try again");
			return false;

		}
		
	}
	public void TRAV1() throws ClassNotFoundException, SQLException {
		Integer choice = 0;
		System.out.println("Hello " + loggedInUser.getGivenName() + ", please choose an option:\n"
				+ "1)	Book a Ticket\r\n"
				+ "2)	Cancel an Upcoming Trip\r\n"
				+ "3)	Quit to Previous\r\n"
				+ "");
		choice = getIntInput(1, 3);
		switch (choice) {
		case 1:
			pickFlight();
			break;
		case 2:
			cancelBooking();
			break;
		case 3:
			// Does nothing and goes back to the main menu
			break;
		}
	}
	
	public void cancelBooking() throws ClassNotFoundException, SQLException {
		TravelerService admin = new TravelerService();
		Integer choice = 0;
		List<Booking> allBookedFlights = admin.getBookedFlights(loggedInUser);
		
		choice = getIntInput(1, allBookedFlights.size() + 1);
		
		if (choice == allBookedFlights.size() + 1) {
			TRAV1();
		}
		else {
			System.out.println(allBookedFlights.get(choice-1).getId());
			String message = admin.cancelTicket(allBookedFlights.get(choice-1));
			System.out.println(message);
			TRAV1();
		}

		
		
		
		// Display flights we're booked for, get the code and the depart date
		
	}
	
	public void bookTicket(Flight flight) throws ClassNotFoundException, SQLException {
		TravelerService admin = new TravelerService();
		Integer choice = 0;
		
		List<String> funcHolder = admin.getBookingList(flight);

		// 1 + funcHolder.size() + 1 represents the menu options, funcHolder is dynamic
		choice = getIntInput(1, 1 + funcHolder.size() + 1);
		if (choice == 1 + funcHolder.size() + 1) {
			TRAV1();
			return;
		}
		else if (choice == 1) {
			viewFlightDetails(flight);
		}
		// dynamic options
		else {
			// -2 offset, one for the index starting at 1, another from the validFunctionCount going 1 over to get the cancel operation
			if (funcHolder.get(choice-2).equals("firstClassAdd"))
				firstClassAdd(flight);
			else if (funcHolder.get(choice-2).equals("businessClassAdd"))
				businessClassAdd(flight);
			else if (funcHolder.get(choice-2).equals("economyClassAdd"))
				economyClassAdd(flight);	
			TRAV1();
		}

		
	}
	
	public void economyClassAdd(Flight flight) throws ClassNotFoundException, SQLException {
		TravelerService admin = new TravelerService();
		// get dob from input
		System.out.print("Please enter your date of birth: ");
		String dateStr = getDateInput();
		LocalDate dob = LocalDate.parse(dateStr);
		
		// get Gender
		System.out.print("Please enter your Gender (\'M\' for male, \'F\' for female): ");
		String gender = getGenderInput();
		// get address
		System.out.print("Please enter your address: ");
		Scanner scan = new Scanner(System.in);
		String address = scan.nextLine();
		
		String message = admin.addEconomyBooking(flight, loggedInUser, dob, gender, address);
		
		System.out.println(message);
		
	}
	
	public void businessClassAdd(Flight flight) throws SQLException {
		TravelerService admin = new TravelerService();
		// get dob from input
		System.out.print("Please enter your date of birth: ");
		String dateStr = getDateInput();
		LocalDate dob = LocalDate.parse(dateStr);
		
		// get Gender
		System.out.print("Please enter your Gender (\'M\' for male, \'F\' for female): ");
		String gender = getGenderInput();
		// get address
		System.out.print("Please enter your address: ");
		Scanner scan = new Scanner(System.in);
		String address = scan.nextLine();
		
		String message = admin.addBusinessBooking(flight, loggedInUser, dob, gender, address);
		
		System.out.println(message);
	}
	
	public void firstClassAdd(Flight flight) throws SQLException {
		TravelerService admin = new TravelerService();
		// get dob from input
		System.out.print("Please enter your date of birth: ");
		String dateStr = getDateInput();
		LocalDate dob = LocalDate.parse(dateStr);
		
		// get Gender
		System.out.print("Please enter your Gender (\'M\' for male, \'F\' for female): ");
		String gender = getGenderInput();
		// get address
		System.out.print("Please enter your address: ");
		Scanner scan = new Scanner(System.in);
		String address = scan.nextLine();
		
		String message = admin.addFirstClassBooking(flight, loggedInUser, dob, gender, address);
		
		System.out.println(message);
	}
	
	public void pickFlight() throws ClassNotFoundException, SQLException {
		// Pick Flight
		// Declare and initialize variables
		Integer choice = 0;
		TravelerService gtflight = new TravelerService();
		List<Flight> flightList = gtflight.getFlights();
		
		System.out.println(flightList.size()+1 + ") Quit to previous \r\n");

		
		// get input
		choice = getIntInput(1, flightList.size()+1);
		// quit to previous menu
		if (choice == flightList.size()+1) {
			return;
		}
		// assign our flight
		else {
			// -1 accounts for the sql starting at index 1
			//return flightList.get(choice-1);
			bookTicket(flightList.get(choice-1));
		}
		
		
	}
	
	public void viewFlightDetails(Flight flight) throws ClassNotFoundException, SQLException {
		TravelerService admin = new TravelerService();
		admin.getFlightDetails(flight);
		// Just a prompt to exit the menu
		getIntInput(4, 4);
		bookTicket(flight);
	}
	
}
