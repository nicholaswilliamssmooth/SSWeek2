package utopia;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.AirplaneDAO;
import dao.AirplaneTypeDAO;
import dao.AirportDAO;
import dao.FlightDAO;
import dao.RouteDAO;
import dao.UserDAO;
import domain.AirplaneType;
import domain.Flight;
import domain.Route;
import domain.User;

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
		UserDAO userList = new UserDAO();
		List<User> userMatch = userList.readUserByLogin(username, password);
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
			break;
		case 3:
			// Does nothing and goes back to the main menu
			break;
		}
	}
	public void bookTicket(Flight flight) throws ClassNotFoundException, SQLException {
		
		// Most of these variables are mainly for checking for open seats
		Integer choice = 0;
		AirplaneTypeDAO planeType = new AirplaneTypeDAO(); // for calculating remaining seats
		Integer economy, business, first = 0;
		AirplaneDAO plane = new AirplaneDAO();
		AirplaneType flightSeats = planeType.readAirplaneTypeByID(plane.readAirplaneByID(flight.getAirplaneID()).get(0).getAirplaneModel()).get(0);
		business = flightSeats.getBusinessClass()
				- flight.getReservedBusiness();
		first = flightSeats.getFirstClass()
				- flight.getReservedFirstClass();
		// economy isn't in the database so we get it from subtraction
		economy = (flightSeats.getMaxCapacity() - flightSeats.getFirstClass() - flightSeats.getBusinessClass())
				- (flight.getReservedSeats() - flight.getReservedBusiness() - flight.getReservedFirstClass());
		
		// We format like this so that we only show available chairs, if everything is reserved that chair won't show up
		List<String> funcHolder = new ArrayList<>();
		System.out.println("Pick the Seat you want to book a ticket for :\n"
				+ "1)	View Flight Details");
		Integer validFunctionCount = 2;
		if (first > 0) {
			funcHolder.add("firstClassAdd");
			System.out.println(validFunctionCount + ")	First");
			validFunctionCount++;
		}
		if (business > 0) {
			funcHolder.add("businessClassAdd");
			System.out.println(validFunctionCount + ")	Business");
			validFunctionCount++;			
		}
		if (economy > 0) {
			funcHolder.add("economyClassAdd");
			System.out.println(validFunctionCount + ")	Economy");
			validFunctionCount++;			
		}
		System.out.println(validFunctionCount + ")	Quit to cancel operation \r\n");
		
		choice = getIntInput(1, validFunctionCount);
		if (choice == validFunctionCount) {
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
				firstClassAdd();
			else if (funcHolder.get(choice-2).equals("businessClassAdd"))
				businessClassAdd();
			else if (funcHolder.get(choice-2).equals("economyClassAdd"))
				economyClassAdd();
			
		}

		
	}
	
	public void economyClassAdd() {
		System.out.println("When editing this, add our information to passengers, but also edit the info in flight to represent a new reserved seat");
	}
	
	public void businessClassAdd() {
		System.out.println("business");
	}
	
	public void firstClassAdd() {
		System.out.println("first");
	}
	
	public void pickFlight() throws ClassNotFoundException, SQLException {
		// Pick Flight
		// Declare and initialize variables
		Integer choice = 0;
		RouteDAO routes = new RouteDAO(); // Used to get the list of flight paths that the user chooses
		FlightDAO flights = new FlightDAO(); // what we'll be using
		List<Flight> flightList = flights.readAllFlights(); // read the SQL table
		List<Route> routeList = routes.readAllRoutes(); // read the SQL table
		
		
		System.out.println("Pick the Flight you want to book a ticket for:\n");
		// list out all the options
		Integer count = 1;
		for (Flight f : flightList) {
			//System.out.println(count + ") " + f.getId() + " " + f.getRouteID().getId() + " " + f.getAirplaneID().getId() + " " + f.getDepartureTime() + " " + f.getReservedSeats() + " " + f.getSeatPrice());
			System.out.println(count + ") " + routes.readRouteByID(f.getRouteID()).get(0).getOriginID().getAirportCode() + " -> " + routes.readRouteByID(f.getRouteID()).get(0).getDestinationID().getAirportCode());
			count++;
		} 
		System.out.println(count + ") Quit to previous \r\n");
		
		// get input
		choice = getIntInput(1, count);
		// quit to previous menu
		if (choice == count) {
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
		// for output
		AirportDAO airport = new AirportDAO();
		RouteDAO routes = new RouteDAO();
		AirplaneTypeDAO planeType = new AirplaneTypeDAO(); // for calculating remaining seats
		AirplaneDAO plane = new AirplaneDAO();
		// Available seats
		Integer economy, business, first = 0;
		Integer choice = 0;

		
		//LocalDateTime time = LocalDateTime.parse(flight.getDepartureTime());
		//System.out.println(time);
		
		System.out.println("You have chosen to view the Flight with Flight Id: " + flight.getId() + "\n"
				+ "and Departure Airport: " + airport.readAirportByCode(routes.readRouteByID(flight.getRouteID()).get(0).getOriginID()).get(0).getCity() + "\n"
				+ "and Arrival Airport: " + airport.readAirportByCode(routes.readRouteByID(flight.getRouteID()).get(0).getDestinationID()).get(0).getCity() + "\n"
				+ "\r\n"
				+ "Departure Airport: " + routes.readRouteByID(flight.getRouteID()).get(0).getOriginID().getAirportCode()
				+ " | Arrival Airport: " + routes.readRouteByID(flight.getRouteID()).get(0).getDestinationID().getAirportCode()
				+ " | \r\n"
				+ "Departure Date: " + flight.getDepartureTime().toLocalDate()
				+ " | Departure Time: " + flight.getDepartureTime().toLocalTime() + "\n"
				+ "| Arrival Date: " + flight.getArrivalTime().toLocalDate()
				+ " | Arrival Time: " + flight.getArrivalTime().toLocalTime()
				+ "");
		// calculate the available seats
		AirplaneType flightSeats = planeType.readAirplaneTypeByID(plane.readAirplaneByID(flight.getAirplaneID()).get(0).getAirplaneModel()).get(0);
		business = flightSeats.getBusinessClass()
				- flight.getReservedBusiness();
		first = flightSeats.getFirstClass()
				- flight.getReservedFirstClass();
		// economy isn't in the database so we get it from subtraction
		economy = (flightSeats.getMaxCapacity() - flightSeats.getFirstClass() - flightSeats.getBusinessClass())
				- (flight.getReservedSeats() - flight.getReservedBusiness() - flight.getReservedFirstClass());
		
		System.out.println("\nAvailable Seats by Class:\r\n"
				+ "1) First -> " + first + "\n"
				+ "2) Business -> " + business + "\n"
				+ "3) Economy -> " + economy + "\n"
				+ "4) Return to previous menu \r\n"
				+ "");
		// Just a prompt so the user exits manually to the previous menu
		choice = getIntInput(4, 4);
		bookTicket(flight);
	}
	
}
