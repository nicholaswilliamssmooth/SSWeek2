package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.AirplaneDAO;
import dao.AirplaneTypeDAO;
import dao.AirportDAO;
import dao.BookingDAO;
import dao.BookingUserDAO;
import dao.FlightBookingDAO;
import dao.FlightDAO;
import dao.PassengerDAO;
import dao.RouteDAO;
import dao.UserDAO;
import domain.Airplane;
import domain.AirplaneType;
import domain.Airport;
import domain.Booking;
import domain.BookingUser;
import domain.Flight;
import domain.FlightBooking;
import domain.Passenger;
import domain.Route;
import domain.User;
import domain.UserRole;

public class Administrator extends utopia.TextPrompt {
	
	Util util = new Util();
	
	public String overrideTicket() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			Integer choice = 0;
			BookingDAO bookingDAO = new BookingDAO(conn); // create a new booking row

			
			
			// get all the unactive tickets
			List<Booking> allInactiveBookings = bookingDAO.readAllUnactive();
			
			System.out.println("Pick a booking to re-enable\n");
			Integer count = 1;
			for (Booking b : allInactiveBookings) {
				System.out.println(count + ") " + b.getConfirmationCode());
				count++;
			}
			choice = getIntInput(1, count-1);
			
			// use this to get booking
			Booking editBook = allInactiveBookings.get(choice-1);
			editBook.setIsActive(1);
			bookingDAO.updateBooking(editBook);
			
			conn.commit();

			return "Ticket was overriden";
		}
		catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Ticket could not be re-enabled";
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	public void readTravelers() throws SQLException, ClassNotFoundException {
		Connection conn = null;
		conn = util.getConnection();
		UserDAO userDAO = new UserDAO(conn);		
		List<User> allUsers = userDAO.readAllUsers();
			
		System.out.println("List of all users: ");
		Integer count = 1;
		for (User u : allUsers) {
			System.out.println(count + ") Username: " + u.getUsername() + "\t\t\tName: " + u.getGivenName() + " " + u.getFamilyName());
			count++;
		}

	
	}
	
	public String deleteTraveler() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			UserDAO userDAO = new UserDAO(conn);
			User user = new User();
			
			List<User> allUsers = userDAO.readAllUsers();
			
			System.out.println("Select user to update: ");
			Integer count = 1;
			for (User u : allUsers) {
				System.out.println(count + ") Username: " + u.getUsername() + "\t\t\tName: " + u.getGivenName() + " " + u.getFamilyName());
				count++;
			}
			Integer choice = getIntInput(1, count-1);
			user = allUsers.get(choice-1);
			
			userDAO.deleteUser(user);
						
			conn.commit();
			return "Successfully deleted traveler";
		}
		catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Could not deleted traveler";
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}		
	}
	
	public String addTraveler() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			UserDAO userDAO = new UserDAO(conn);
			User newUser = new User();
			
			List<User> allUsers = userDAO.readAllUsers();
			
			System.out.println("Creating a new user, set the following information: ");
			Integer count = 1;
			for (User u : allUsers) {
				count++;
			}
			newUser.setId(count); // set ID to the size of the current list +1
			UserRole urole = new UserRole();
			urole.setId(1);
			newUser.setRoleID(urole); // Normal user, not agent
			
			System.out.println("First name: ");
			Scanner scan = new Scanner(System.in);
			String firstName = scan.nextLine();
			newUser.setGivenName(firstName);
			
			System.out.println("Last name: ");
			scan = new Scanner(System.in);
			String lastName = scan.nextLine();
			newUser.setFamilyName(lastName);
			
		
			System.out.println("Username: ");
			scan = new Scanner(System.in);
			String newUserName = scan.nextLine();
			newUser.setUsername(newUserName);
			
			System.out.println("Password: ");
			scan = new Scanner(System.in);
			String newPass = scan.nextLine();
			newUser.setPassword(newPass);
			
			System.out.println("New email: ");
			scan = new Scanner(System.in);
			String newMail = scan.nextLine();
			newUser.setEmail(newMail);
			
			System.out.println("New Phone: ");
			scan = new Scanner(System.in);
			String newPhone = scan.nextLine();
			newUser.setPhone(newPhone);
			
			userDAO.addUser(newUser);
						
			conn.commit();
			return "Successfully created traveler";
		}
		catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Could not create traveler";
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}		
	}
	
	public String updateTraveler() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			UserDAO userDAO = new UserDAO(conn);
			User updatedUser = new User();
			
			List<User> allUsers = userDAO.readAllUsers();
			
			System.out.println("Select user to update: ");
			Integer count = 1;
			for (User u : allUsers) {
				System.out.println(count + ") Username: " + u.getUsername() + "\t\t\tName: " + u.getGivenName() + " " + u.getFamilyName());
				count++;
			}
			Integer choice = getIntInput(1, count-1);
			updatedUser = allUsers.get(choice-1);
			
			System.out.println("New username: ");
			Scanner scan = new Scanner(System.in);
			String newUserName = scan.nextLine();
			updatedUser.setUsername(newUserName);
			
			System.out.println("New password: ");
			scan = new Scanner(System.in);
			String newPass = scan.nextLine();
			updatedUser.setPassword(newPass);
			
			System.out.println("New email: ");
			scan = new Scanner(System.in);
			String newMail = scan.nextLine();
			updatedUser.setEmail(newMail);
			
			System.out.println("New Phone: ");
			scan = new Scanner(System.in);
			String newPhone = scan.nextLine();
			updatedUser.setPhone(newPhone);
			
			userDAO.updateUser(updatedUser);
						
			conn.commit();
			return "Successfully updated traveler";
		}
		catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Could not update traveler";
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}		
	}
	
	public void readAirport() throws SQLException, ClassNotFoundException {
		Connection conn = null;
		conn = util.getConnection();
		AirportDAO airportDAO = new AirportDAO(conn);
			
		List<Airport> allAirports = airportDAO.readAllAirports();
			
		System.out.println("Select airport to delete: ");
		Integer count = 1;
		for (Airport a : allAirports) {
			System.out.println("City: " + a.getCity() + "\t\t\tCode: " + a.getAirportCode());
			count++;
		}

		
	}
	
	public String deleteAirport() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			AirportDAO airportDAO = new AirportDAO(conn);
			Airport airport = new Airport();
			
			List<Airport> allAirports = airportDAO.readAllAirports();
			
			System.out.println("Select airport to delete: ");
			Integer count = 1;
			for (Airport a : allAirports) {
				System.out.println(count + ") City: " + a.getCity() + "\t\t\tCode: " + a.getAirportCode());
				count++;
			}
			Integer choice = getIntInput(1, count-1);
			airport = allAirports.get(choice-1);
			
			airportDAO.deleteAirport(airport);
			
			conn.commit();
			return "Successfully updated airport";
		}
		catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Could not update airport";
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}		
	}
	
	public String updateAirport() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			AirportDAO airportDAO = new AirportDAO(conn);
			Airport airport = new Airport();
			String newCity;
			
			List<Airport> allAirports = airportDAO.readAllAirports();
			
			System.out.println("Select airport to update: ");
			Integer count = 1;
			for (Airport a : allAirports) {
				System.out.println(count + ") City: " + a.getCity() + "\t\t\tCode: " + a.getAirportCode());
				count++;
			}
			Integer choice = getIntInput(1, count-1);
			airport = allAirports.get(choice-1);
			
			System.out.println("Where is the new airport located?: ");
			Scanner scan = new Scanner(System.in);
			newCity = scan.nextLine();
			
			airport.setCity(newCity);
			
			airportDAO.updateAirport(airport);
			
			conn.commit();
			return "Successfully updated airport";
		}
		catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Could not update airport";
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}		
	}
	
	public String addAirport() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			AirportDAO airportDAO = new AirportDAO(conn);
			Airport airport = new Airport();
			String newCity, newCode;
			
			System.out.println("Where is the new airport located?: ");
			Scanner scan = new Scanner(System.in);
			newCity = scan.nextLine();
			
			System.out.println("What is the code for this airport? ");
			newCode = getAirportCodeInput();
			
			airport.setAirportCode(newCode);
			airport.setCity(newCity);
			
			airportDAO.addAirport(airport);
			
			conn.commit();
			return "Successfully added airport";
		}
		catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Could not add airport";
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}		
	}
		
	
	public void readPassenger() throws SQLException, ClassNotFoundException {
		Connection conn = null;

		conn = util.getConnection();
		Integer choice = 0;
		PassengerDAO passengerDAO = new PassengerDAO(conn);
		BookingDAO bookingDAO = new BookingDAO(conn); // create a new booking row

			
			
			
		List<Booking> allBookings = bookingDAO.readAllBookings();
			
		System.out.println("Pick a booking to delete\n");
		Integer count = 1;
		for (Booking b : allBookings) {
			System.out.println(count + ") " + b.getConfirmationCode());
			count++;
		}
		choice = getIntInput(1, count-1);
			
		// use this to get booking
		Booking editBook = allBookings.get(choice-1);

			
		// use booking to get passenger
			
		Passenger passenger = new Passenger();
		passenger.setBookingID(editBook);
		passenger = passengerDAO.readPassengerFromBookingID(passenger).get(0);
			
		System.out.println("Passenger: "
				+ "\n\tName:" + passenger.getGivenName() + " " + passenger.getFamilyName()
				+ "\n\tID: " + passenger.getId()
				+ "\n\tGender: " + passenger.getGender()
				+ "\n\tDate of Birth: " + passenger.getDateOfBirth()
				+ "\n\tAddress" + passenger.getAddress() 
				+ "\nPassenger ticket: "
				+ "\n\tTicket ID: " + editBook.getId()
				+ "\n\tActive: " + editBook.getIsActive()
				+ "\n\tConfirmation Code: " + editBook.getConfirmationCode());
		
	}	

	public String deletePassenger() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			Integer choice = 0;
			FlightDAO flights = new FlightDAO(conn);
			PassengerDAO passengerDAO = new PassengerDAO(conn);
			BookingDAO bookingDAO = new BookingDAO(conn); // create a new booking row

			
			
			
			List<Booking> allBookings = bookingDAO.readAllBookings();
			
			System.out.println("Pick a booking to delete\n");
			Integer count = 1;
			for (Booking b : allBookings) {
				System.out.println(count + ") " + b.getConfirmationCode());
				count++;
			}
			choice = getIntInput(1, count-1);
			
			// use this to get booking
			Booking editBook = allBookings.get(choice-1);

			
			// use booking to get passenger
			
			Passenger passenger = new Passenger();
			passenger.setBookingID(editBook);
			passenger = passengerDAO.readPassengerFromBookingID(passenger).get(0);
			
			
			bookingDAO.deleteBooking(editBook);
			passengerDAO.deletePassenger(passenger);

			
			conn.commit();

			return "Passenger was successfully deleted";
		}
		catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Passenger was not deleted due to an error";
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	public String updatePassenger() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			Integer choice = 0;
			PassengerDAO passengerDAO = new PassengerDAO(conn);
			BookingDAO bookingDAO = new BookingDAO(conn); // create a new booking row
			Booking newBooking = new Booking();

			
			
			
			List<Booking> allBookings = bookingDAO.readAllBookings();
			
			System.out.println("Pick a booking to change Passenger\n");
			Integer count = 1;
			for (Booking b : allBookings) {
				System.out.println(count + ") " + b.getConfirmationCode());
				count++;
			}
			choice = getIntInput(1, count-1);
			
			// use this to get booking
			Booking editBook = allBookings.get(choice-1);

			
			// use booking to get passenger
			
			Passenger passenger = new Passenger();
			passenger.setBookingID(editBook);
			passenger = passengerDAO.readPassengerFromBookingID(passenger).get(0);


					
			// We don't know what seat this passenger has so we can't properly edit it or the flight
			// something to do on the next project
			
			System.out.print("Please enter the passenger first name: ");
			Scanner scan = new Scanner(System.in);
			String firstName = scan.nextLine();
			
			System.out.print("Please enter the passenger last name: ");
			String lastName = scan.nextLine();
			
			System.out.print("Please enter the passenger date of birth: ");
			String dateStr = getDateInput();
			LocalDate dob = LocalDate.parse(dateStr);
			
			
			// get Gender
			System.out.print("Please enter the passenger Gender (\'M\' for male, \'F\' for female): ");
			String gender = getGenderInput();
			// get address
			System.out.print("Please enter the passenger address: ");
			String address = scan.nextLine();
			
			// Create our booking
			newBooking.setId(allBookings.size()+1);
			// 1 = active
			newBooking.setIsActive(1);
			
			// Hardcoded, might write something to generate these automatically later
			newBooking.setConfirmationCode("RIK-248");
			bookingDAO.addBooking(newBooking);
			

			// Create passenger listing
			List<Passenger> allPassengers = passengerDAO.readAllPassengers();
			passenger.setGivenName(firstName);
			passenger.setFamilyName(lastName);
			passenger.setBookingID(newBooking);
			
			passenger.setDateOfBirth(dob);
			passenger.setGender(gender);
			passenger.setAddress(address);
			
			passengerDAO.updatePassenger(passenger);

			
			conn.commit();

			return "Passenger was successfully updated";
		}
		catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Passenger was not updated due to an error";
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	public String addPassenger() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			RouteDAO routes = new RouteDAO(conn);
			AirportDAO airport = new AirportDAO(conn);
			Integer choice = 0;
			FlightDAO flights = new FlightDAO(conn);
			AirplaneTypeDAO planeType = new AirplaneTypeDAO(conn);
			AirplaneDAO plane = new AirplaneDAO(conn);
			Integer newSeats = 0;
			PassengerDAO passengerDAO = new PassengerDAO(conn);
			FlightBookingDAO flightBookingDAO = new FlightBookingDAO(conn); // connect to selected flightID to the booking
			FlightBooking newFlightBooking = new FlightBooking();
			Passenger newPassenger = new Passenger();
			BookingDAO bookingDAO = new BookingDAO(conn); // create a new booking row
			Booking newBooking = new Booking();
			BookingUserDAO bookingUserDAO = new BookingUserDAO(conn); // create a connection between the booking and user
			BookingUser newBookingUser = new BookingUser();
			
			
			List<Flight> flightList = flights.readAllFlights(); // read the SQL table
			
			
			System.out.println("Pick a flight to add Passenger to\n");
			// list out all the options
			Integer count = 1;
			for (Flight f : flightList) {
				//System.out.println(count + ") " + f.getId() + " " + f.getRouteID().getId() + " " + f.getAirplaneID().getId() + " " + f.getDepartureTime() + " " + f.getReservedSeats() + " " + f.getSeatPrice());
				System.out.println(count + ") " + routes.readRouteByID(f.getRouteID()).get(0).getOriginID().getAirportCode() + " -> " + routes.readRouteByID(f.getRouteID()).get(0).getDestinationID().getAirportCode());
				count++;
			} 
			
			// get input
			choice = getIntInput(1, count-1);
	
			// get our flight seats
			Flight flight = flightList.get(choice-1);
			
			List<String> funcHolder = getBookingList(flight);

			choice = getIntInput(1, funcHolder.size() + 1);
			
			if (funcHolder.get(choice-1).equals("firstClassAdd"))
				flight.setReservedFirstClass(flight.getReservedBusiness()+1);
			else if (funcHolder.get(choice-1).equals("businessClassAdd"))
				flight.setReservedBusiness(flight.getReservedBusiness()+1);


			// Increment up with the flight
			flight.setReservedSeats(flight.getReservedSeats()+1);
			flights.updateFlight(flight);
			
			System.out.print("Please enter the passenger first name: ");
			Scanner scan = new Scanner(System.in);
			String firstName = scan.nextLine();
			
			System.out.print("Please enter the passenger last name: ");
			String lastName = scan.nextLine();
			
			System.out.print("Please enter the passenger date of birth: ");
			String dateStr = getDateInput();
			LocalDate dob = LocalDate.parse(dateStr);
			
			// Hardcoded for now, get ID for user
			System.out.print("Please enter the passenger ID: ");
			Integer bookID = getIntInput(1, 100);
			User settingUser = new User();
			settingUser.setId(bookID);
			
			// get Gender
			System.out.print("Please enter the passenger Gender (\'M\' for male, \'F\' for female): ");
			String gender = getGenderInput();
			// get address
			System.out.print("Please enter the passenger address: ");
			String address = scan.nextLine();
			
			// Create our booking
			List<Booking> allBookings = bookingDAO.readAllBookings();
			newBooking.setId(allBookings.size()+1);
			// 1 = active
			newBooking.setIsActive(1);
			
			// Hardcoded, might write something to generate these automatically later
			newBooking.setConfirmationCode("RIK-248");
			bookingDAO.addBooking(newBooking);
			
			// Connect booking to user
			newBookingUser.setBookingID(newBooking.getId());
			newBookingUser.setUserID(settingUser);
			bookingUserDAO.addBookingUser(newBookingUser);

			// Create passenger listing
			List<Passenger> allPassengers = passengerDAO.readAllPassengers();
			newPassenger.setId(allPassengers.size()+1);
			newPassenger.setGivenName(firstName);
			newPassenger.setFamilyName(lastName);
			newPassenger.setBookingID(newBooking);
			
			newPassenger.setDateOfBirth(dob);
			newPassenger.setGender(gender);
			newPassenger.setAddress(address);
			
			passengerDAO.addPassenger(newPassenger);
			
			// Connect booking to flight
			newFlightBooking.setBookingID(newBooking);
			newFlightBooking.setFlightID(flight);
			flightBookingDAO.addFlightBooking(newFlightBooking);
			
			conn.commit();

			return "Passenger was successfully added";
		}
		catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Passenger was no added due to an error";
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	
	public void readSeats() throws SQLException, ClassNotFoundException {
		Connection conn = util.getConnection();
		RouteDAO routes = new RouteDAO(conn);
		AirportDAO airport = new AirportDAO(conn);
		Integer choice = 0;
		FlightDAO flights = new FlightDAO(conn);
		
		List<Flight> flightList = flights.readAllFlights(); // read the SQL table
		
		
		System.out.println("Pick a flight to read seats from\n");
		// list out all the options
		Integer count = 1;
		for (Flight f : flightList) {
			//System.out.println(count + ") " + f.getId() + " " + f.getRouteID().getId() + " " + f.getAirplaneID().getId() + " " + f.getDepartureTime() + " " + f.getReservedSeats() + " " + f.getSeatPrice());
			System.out.println(count + ") " + routes.readRouteByID(f.getRouteID()).get(0).getOriginID().getAirportCode() + " -> " + routes.readRouteByID(f.getRouteID()).get(0).getDestinationID().getAirportCode());
			count++;
		} 
		
		// get input
		choice = getIntInput(1, count-1);

		// get our flight to update
		Flight flight = flightList.get(choice-1);
		

		AirplaneTypeDAO planeType = new AirplaneTypeDAO(conn); // for calculating remaining seats
		AirplaneDAO plane = new AirplaneDAO(conn);
		// Available seats
		Integer economy, business, first = 0;
		
		//LocalDateTime time = LocalDateTime.parse(flight.getDepartureTime());
		//System.out.println(time);

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
	}
	
	public String deleteSeats() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			RouteDAO routes = new RouteDAO(conn);
			AirportDAO airport = new AirportDAO(conn);
			Integer choice = 0;
			FlightDAO flights = new FlightDAO(conn);
			AirplaneTypeDAO planeType = new AirplaneTypeDAO(conn);
			AirplaneDAO plane = new AirplaneDAO(conn);
			Integer newSeats = 0;
			
			List<Flight> flightList = flights.readAllFlights(); // read the SQL table
			
			
			System.out.println("Pick a flight to delete all the seats on\n");
			// list out all the options
			Integer count = 1;
			for (Flight f : flightList) {
				//System.out.println(count + ") " + f.getId() + " " + f.getRouteID().getId() + " " + f.getAirplaneID().getId() + " " + f.getDepartureTime() + " " + f.getReservedSeats() + " " + f.getSeatPrice());
				System.out.println(count + ") " + routes.readRouteByID(f.getRouteID()).get(0).getOriginID().getAirportCode() + " -> " + routes.readRouteByID(f.getRouteID()).get(0).getDestinationID().getAirportCode());
				count++;
			} 
			
			// get input
			choice = getIntInput(1, count-1);
	
			// get our flight seats
			Flight flight = flightList.get(choice-1);
			
			flight.setReservedSeats(0);
			flight.setReservedBusiness(0);
			flight.setReservedFirstClass(0);
			flights.updateFlight(flight);

			return "Seats were successfully delete";
		}
		catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Seats could not be delete";
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	public String updateSeats() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			RouteDAO routes = new RouteDAO(conn);
			AirportDAO airport = new AirportDAO(conn);
			Integer choice = 0;
			FlightDAO flights = new FlightDAO(conn);
			AirplaneTypeDAO planeType = new AirplaneTypeDAO(conn);
			AirplaneDAO plane = new AirplaneDAO(conn);
			Integer newSeats = 0;
			
			List<Flight> flightList = flights.readAllFlights(); // read the SQL table
			
			
			System.out.println("Pick a flight to add seats\n");
			// list out all the options
			Integer count = 1;
			for (Flight f : flightList) {
				//System.out.println(count + ") " + f.getId() + " " + f.getRouteID().getId() + " " + f.getAirplaneID().getId() + " " + f.getDepartureTime() + " " + f.getReservedSeats() + " " + f.getSeatPrice());
				System.out.println(count + ") " + routes.readRouteByID(f.getRouteID()).get(0).getOriginID().getAirportCode() + " -> " + routes.readRouteByID(f.getRouteID()).get(0).getDestinationID().getAirportCode());
				count++;
			} 
			
			// get input
			choice = getIntInput(1, count-1);
	
			// get our flight seats
			Flight flight = flightList.get(choice-1);
			
	
	
			// Get max capacity of all seats on the plane
			AirplaneType flightSeats = planeType.readAirplaneTypeByID(plane.readAirplaneByID(flight.getAirplaneID()).get(0).getAirplaneModel()).get(0);
			Integer business = flightSeats.getBusinessClass()
					- flight.getReservedBusiness();
			Integer first = flightSeats.getFirstClass()
					- flight.getReservedFirstClass();
			Integer totalEconomySeats = flightSeats.getMaxCapacity() - flightSeats.getFirstClass() - flightSeats.getBusinessClass();
			Integer economy = (totalEconomySeats)
					- (flight.getReservedSeats() - flight.getReservedBusiness() - flight.getReservedFirstClass());
			
			System.out.println("Pick the Seat Class you want to update to the flight:\r\n"
					+ "1)	First\r\n"
					+ "2)	Business\r\n"
					+ "3)	Economy\r\n"
					+ "");
			choice = getIntInput(1, 3);
			switch(choice) {
			case 1:
				newSeats = 0;
				System.out.println("Reserved first class seats: " + flight.getReservedFirstClass()
						+ "\r\n"
						+ "Enter new number of reserved seats: "
						+ "");
				newSeats = getIntInput(0, flightSeats.getFirstClass());
				// update the total seats
				flight.setReservedSeats(flight.getReservedSeats() + (newSeats -  flight.getReservedFirstClass()));
				// update business
				flight.setReservedFirstClass(newSeats);
				flights.updateFlight(flight);
				
				break;
			case 2:
				newSeats = 0;
				System.out.println("Reserved business class seats: " + flight.getReservedBusiness()
						+ "\r\n"
						+ "Enter new number of reserved seats: "
						+ "");
				newSeats = getIntInput(0, flightSeats.getBusinessClass());
				// update the total seats
				flight.setReservedSeats(flight.getReservedSeats() + (newSeats -  flight.getReservedBusiness()));
				// update business
				flight.setReservedBusiness(newSeats);
				flights.updateFlight(flight);	
				break;
				
			case 3:
				newSeats = 0;
				Integer reservedEconomy = flight.getReservedSeats() - flight.getReservedBusiness() - flight.getReservedFirstClass();
				System.out.println("Reserved Economy seats: " + reservedEconomy
						+ "\r\n"
						+ "Enter new number of reserved seats: "
						+ "");
				newSeats = getIntInput(0, reservedEconomy);
				flight.setReservedSeats(newSeats);
				flights.updateFlight(flight);
				
				break;
			}
			return "Seats were successfully updated";
		}
		catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Seats could not be updated";
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	public String addSeats() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			RouteDAO routes = new RouteDAO(conn);
			AirportDAO airport = new AirportDAO(conn);
			Integer choice = 0;
			FlightDAO flights = new FlightDAO(conn);
			AirplaneTypeDAO planeType = new AirplaneTypeDAO(conn);
			AirplaneDAO plane = new AirplaneDAO(conn);
			Integer newSeats = 0;
			
			List<Flight> flightList = flights.readAllFlights(); // read the SQL table
			
			
			System.out.println("Pick a flight to add seats\n");
			// list out all the options
			Integer count = 1;
			for (Flight f : flightList) {
				//System.out.println(count + ") " + f.getId() + " " + f.getRouteID().getId() + " " + f.getAirplaneID().getId() + " " + f.getDepartureTime() + " " + f.getReservedSeats() + " " + f.getSeatPrice());
				System.out.println(count + ") " + routes.readRouteByID(f.getRouteID()).get(0).getOriginID().getAirportCode() + " -> " + routes.readRouteByID(f.getRouteID()).get(0).getDestinationID().getAirportCode());
				count++;
			} 
			
			// get input
			choice = getIntInput(1, count-1);
	
			// get our flight seats
			Flight flight = flightList.get(choice-1);
			
	
	
			// Get max capacity of all seats on the plane
			AirplaneType flightSeats = planeType.readAirplaneTypeByID(plane.readAirplaneByID(flight.getAirplaneID()).get(0).getAirplaneModel()).get(0);
			Integer business = flightSeats.getBusinessClass()
					- flight.getReservedBusiness();
			Integer first = flightSeats.getFirstClass()
					- flight.getReservedFirstClass();
			Integer totalEconomySeats = flightSeats.getMaxCapacity() - flightSeats.getFirstClass() - flightSeats.getBusinessClass();
			Integer economy = (totalEconomySeats)
					- (flight.getReservedSeats() - flight.getReservedBusiness() - flight.getReservedFirstClass());
			
			System.out.println("Pick the Seat Class you want to update to the flight:\r\n"
					+ "1)	First\r\n"
					+ "2)	Business\r\n"
					+ "3)	Economy\r\n"
					+ "");
			choice = getIntInput(1, 3);
			switch(choice) {
			case 1:
				newSeats = 0;
				System.out.println("Existing number of seats: " + first
						+ "\r\n"
						+ "Enter new number of seats: "
						+ "");
				newSeats = getIntInput(first, flightSeats.getFirstClass());
				System.out.println(flight.getReservedFirstClass() + " " + first + " " + newSeats);
				flight.setReservedFirstClass(flight.getReservedFirstClass() - (newSeats - first));
				// update the total seats
				flight.setReservedSeats(flight.getReservedSeats() - (newSeats - first));
				flights.updateFlight(flight);
				
				break;
			case 2:
				newSeats = 0;
				System.out.println("Existing number of seats: " + business
						+ "\r\n"
						+ "Enter new number of seats: "
						+ "");
				newSeats = getIntInput(business, flightSeats.getBusinessClass());
				flight.setReservedBusiness(flight.getReservedBusiness() - (newSeats - business));
				// update the total seats
				flight.setReservedSeats(flight.getReservedSeats() - (newSeats - business));
				flights.updateFlight(flight);	
				break;
				
			case 3:
				newSeats = 0;
				System.out.println("Existing number of seats: " + economy
						+ "\r\n"
						+ "Enter new number of seats: "
						+ "");
				newSeats = getIntInput(economy, totalEconomySeats);
				flight.setReservedSeats(flight.getReservedSeats() - (newSeats - economy));
				flights.updateFlight(flight);
				
				break;
			}
			return "Seats were successfully updated";
		}
		catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Seats could not be updated";
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	public void readFlight() throws SQLException, ClassNotFoundException {
		Connection conn = util.getConnection();
		RouteDAO routes = new RouteDAO(conn);
		AirportDAO airport = new AirportDAO(conn);
		Integer choice = 0;
		FlightDAO flights = new FlightDAO(conn);
		
		List<Flight> flightList = flights.readAllFlights(); // read the SQL table
		
		
		System.out.println("Pick a flight to read from\n");
		// list out all the options
		Integer count = 1;
		for (Flight f : flightList) {
			//System.out.println(count + ") " + f.getId() + " " + f.getRouteID().getId() + " " + f.getAirplaneID().getId() + " " + f.getDepartureTime() + " " + f.getReservedSeats() + " " + f.getSeatPrice());
			System.out.println(count + ") " + routes.readRouteByID(f.getRouteID()).get(0).getOriginID().getAirportCode() + " -> " + routes.readRouteByID(f.getRouteID()).get(0).getDestinationID().getAirportCode());
			count++;
		} 
		
		// get input
		choice = getIntInput(1, count-1);

		// get our flight to update
		Flight flight = flightList.get(choice-1);
		

		AirplaneTypeDAO planeType = new AirplaneTypeDAO(conn); // for calculating remaining seats
		AirplaneDAO plane = new AirplaneDAO(conn);
		// Available seats
		Integer economy, business, first = 0;
		
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
		
	}
	
	public String deleteFlight() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			Route newRoute = new Route();
			RouteDAO routes = new RouteDAO(conn);
			AirportDAO airport = new AirportDAO(conn);
			Integer choice = 0;
			FlightDAO flights = new FlightDAO(conn);
			
			List<Flight> flightList = flights.readAllFlights(); // read the SQL table
			List<Route> routeList = routes.readAllRoutes(); // read the SQL table
			
			
			System.out.println("Pick a flight to delete\n");
			// list out all the options
			Integer count = 1;
			for (Flight f : flightList) {
				//System.out.println(count + ") " + f.getId() + " " + f.getRouteID().getId() + " " + f.getAirplaneID().getId() + " " + f.getDepartureTime() + " " + f.getReservedSeats() + " " + f.getSeatPrice());
				System.out.println(count + ") " + routes.readRouteByID(f.getRouteID()).get(0).getOriginID().getAirportCode() + " -> " + routes.readRouteByID(f.getRouteID()).get(0).getDestinationID().getAirportCode());
				count++;
			} 
			
			// get input
			choice = getIntInput(1, count-1);

			// get our flight to update
			Flight flight = flightList.get(choice-1);
			
			flights.deleteFlight(flight);
				
			conn.commit();
			return "Successfully deleted flight";
		}
		catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Could not delete flight";
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}		
	}
	
	public String addFlight() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			Route newRoute = new Route();
			RouteDAO routes = new RouteDAO(conn);
			AirportDAO airport = new AirportDAO(conn);
			AirplaneDAO airplane = new AirplaneDAO(conn);
			Integer choice = 0;
			Airport originAP = new Airport();
			Airport destinationAP = new Airport();
			LocalDate newDepartDate = LocalDate.of(1, 1, 1);
			LocalTime newDepartTime = LocalTime.of(1, 1);
			LocalDate newArriveDate = LocalDate.of(1, 1, 1);
			LocalTime newArriveTime = LocalTime.of(1, 1);
			FlightDAO flights = new FlightDAO(conn);
			Integer count = 0;

			// make our new flight, initialize the seats as all empty
			Flight flight = new Flight();
			flight.setReservedBusiness(0);
			flight.setReservedFirstClass(0);
			flight.setReservedSeats(0);
			List<Flight> allFlights = flights.readAllFlights();
			flight.setId(allFlights.size()+1);
			flight.setSeatPrice(500.0); // hard coded for now
			Airplane plane = new Airplane();
			plane.setId(2); // hard coded for now
			flight.setAirplaneID(plane);
			
				

			
			// List all available airports
			System.out.println("Please enter the Origin Airport and City:");

			List<Airport> airportList = airport.readAllAirports(); // read the SQL table
			count = 1;
			for (Airport a : airportList) {
				System.out.println(count + ") " + a.getCity());
				count++;
			}
			// choice origin airport
			choice = getIntInput(1, count-1);
			if (choice < count) { 
				originAP = airportList.get(choice-1);
			}
			
			// Test to see if there are any available routes for that airport
			List<Route> matchingRoutes = routes.readRouteByOrigin(originAP);
			count = 1;
			for (Route r : matchingRoutes) {
				count++;
			}
			// No results, just assign the route to what it was originally
			if (count == 1) {
				System.out.println("There are currently no routes with that city, using route 1");
				flight.setRouteID(routes.readAllRoutes().get(0));
			}
			// List all the routes that contain that airport as the origin
			else {
				System.out.println("Please enter a Destination Airport and City:\n");
				count = 1;
				for (Route r : matchingRoutes) {
					System.out.println(count + ") " + airport.readAirportByCode(r.getDestinationID()).get(0).getCity());
					count++;

				}
				choice = getIntInput(1, count-1);
				if (choice < count) { 
					destinationAP = airport.readAirportByCode(
							matchingRoutes.get(choice-1).getDestinationID()
							).get(0);
				}

				flight.setRouteID(routes.readRouteByPlaneCodes(originAP, destinationAP).get(0));
			}
			
			// New Departure Date
			System.out.println("Please enter a Departure Date");
			String dateStr = getDateInput();
			newDepartDate = LocalDate.parse(dateStr);
			
			// New Departure Time
			System.out.println("Please enter a Departure Time");
			String timeStr = getTimeInput();
			newDepartTime = LocalTime.parse(timeStr);
			
			// New Arrival Date
			System.out.println("Please enter an Arrival Date");
			dateStr = getDateInput();
			newArriveDate = LocalDate.parse(dateStr);
			
			// New Arrival Time
			System.out.println("Please enter an Arrival Time");
			timeStr = getTimeInput();
			newArriveTime = LocalTime.parse(timeStr);
						
			flight.setArrivalTime(LocalDateTime.of(newArriveDate, newArriveTime));
			flight.setDepartureTime(LocalDateTime.of(newDepartDate, newDepartTime));
			// sql update
			flights.addFlight(flight);
			conn.commit();
			return "Successfully add flight";
		}
		catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Could not add flight";
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	public String UpdateFlight() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			Route newRoute = new Route();
			RouteDAO routes = new RouteDAO(conn);
			AirportDAO airport = new AirportDAO(conn);
			Integer choice = 0;
			Airport originAP = new Airport();
			Airport destinationAP = new Airport();
			LocalDate newDepartDate = LocalDate.of(1, 1, 1);
			LocalTime newDepartTime = LocalTime.of(1, 1);
			LocalDate newArriveDate = LocalDate.of(1, 1, 1);
			LocalTime newArriveTime = LocalTime.of(1, 1);
			FlightDAO flights = new FlightDAO(conn);
			
			List<Flight> flightList = flights.readAllFlights(); // read the SQL table
			List<Route> routeList = routes.readAllRoutes(); // read the SQL table
			
			
			System.out.println("Pick a flight to update\n");
			// list out all the options
			Integer count = 1;
			for (Flight f : flightList) {
				//System.out.println(count + ") " + f.getId() + " " + f.getRouteID().getId() + " " + f.getAirplaneID().getId() + " " + f.getDepartureTime() + " " + f.getReservedSeats() + " " + f.getSeatPrice());
				System.out.println(count + ") " + routes.readRouteByID(f.getRouteID()).get(0).getOriginID().getAirportCode() + " -> " + routes.readRouteByID(f.getRouteID()).get(0).getDestinationID().getAirportCode());
				count++;
			} 
			
			// get input
			choice = getIntInput(1, count-1);

			// get our flight to update
			Flight flight = flightList.get(choice-1);
				


			
			System.out.println("You have chosen to update the Flight with Flight Id: "+ flight.getId() + " "
					+ "and Flight Origin: " + airport.readAirportByCode(routes.readRouteByID(flight.getRouteID()).get(0).getOriginID()).get(0).getCity() + " "
					+ "and Flight Destination: " + airport.readAirportByCode(routes.readRouteByID(flight.getRouteID()).get(0).getDestinationID()).get(0).getCity() + "\r\n" 
					+ "\r\n"
					+ "Please enter new Origin Airport and City:\r\n"
					+ "");
			
			// List all available airports
			List<Airport> airportList = airport.readAllAirports(); // read the SQL table
			count = 1;
			for (Airport a : airportList) {
				System.out.println(count + ") " + a.getCity());
				count++;
			}
			System.out.println(count + ") No Change");
			// choice origin airport
			choice = getIntInput(1, count);
			if (choice < count) { 
				originAP = airportList.get(choice-1);
			}
			else { 
				originAP = airport.readAirportByCode(
					routes.readRouteByID(
							flight.getRouteID()
					).get(0).getOriginID()
				).get(0);
			}
			
			// Test to see if there are any available routes for that airport
			List<Route> matchingRoutes = routes.readRouteByOrigin(originAP);
			count = 1;
			for (Route r : matchingRoutes) {
				count++;
			}
			// No results, just assign the route to what it was originally
			if (count == 1) {
				System.out.println("There are currently no routes with that city, using the original route");
				newRoute = routes.readRouteByID(
						flight.getRouteID()
						).get(0);
			}
			// List all the routes that contain that airport as the origin
			else {
				System.out.println("Please enter new Destination Airport and City:\n");
				count = 1;
				for (Route r : matchingRoutes) {
					System.out.println(count + ") " + airport.readAirportByCode(r.getDestinationID()).get(0).getCity());
					count++;

				}
				System.out.println(count + ") No Change");
				choice = getIntInput(1, count);
				if (choice < count) { 
					destinationAP = airport.readAirportByCode(
							matchingRoutes.get(choice-1).getDestinationID()
							).get(0);
				}
				else { 
					destinationAP = airport.readAirportByCode(
						routes.readRouteByID(
								flight.getRouteID()
						).get(0).getDestinationID()
					).get(0);
				}
				newRoute = routes.readRouteByPlaneCodes(originAP, destinationAP).get(0);
			}
			
			// New Departure Date
			System.out.println("Please enter new Departure Date or enter N/A for no change");
			String dateStr = getDateInput();
			if (dateStr.equals("N/A"))
				newDepartDate = flight.getDepartureTime().toLocalDate();
			else {
				newDepartDate = LocalDate.parse(dateStr);
			}
			System.out.println(newDepartDate);
			
			// New Departure Time
			System.out.println("Please enter new Departure Time or enter N/A for no change");
			String timeStr = getTimeInput();
			if (timeStr.equals("N/A"))
				newDepartTime = flight.getDepartureTime().toLocalTime();
			else {
				newDepartTime = LocalTime.parse(timeStr);
			}
			System.out.println(newDepartTime);
			
			// New Arrival Date
			System.out.println("Please enter new Arrival Date or enter N/A for no change");
			dateStr = getDateInput();
			if (dateStr.equals("N/A"))
				newArriveDate = flight.getArrivalTime().toLocalDate();
			else {
				newArriveDate = LocalDate.parse(dateStr);
			}
			System.out.println(newDepartDate);
			
			// New Arrival Time
			System.out.println("Please enter new Arrival Time or enter N/A for no change");
			timeStr = getTimeInput();
			if (timeStr.equals("N/A"))
				newArriveTime = flight.getArrivalTime().toLocalTime();
			else {
				newArriveTime = LocalTime.parse(timeStr);
			}
			System.out.println(newDepartTime);
			
			flight.setArrivalTime(LocalDateTime.of(newArriveDate, newArriveTime));
			flight.setDepartureTime(LocalDateTime.of(newDepartDate, newDepartTime));
			flight.setRouteID(newRoute);
			// sql update
			flights.updateFlight(flight);
			conn.commit();
			return "Successfully update flight";
		}
		catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Could not update flight";
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}
	}


	public List<String> getBookingList(Flight flight) throws ClassNotFoundException, SQLException {
		// Most of these variables are mainly for checking for open seats
		Connection conn = null;
		conn = util.getConnection();
		Integer choice = 0;
		AirplaneTypeDAO planeType = new AirplaneTypeDAO(conn); // for calculating remaining seats
		Integer economy, business, first = 0;
		AirplaneDAO plane = new AirplaneDAO(conn);
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
		System.out.println("Pick the Seat you want to book the Passenger for :\n");
		Integer validFunctionCount = 1;
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
		return funcHolder;
		
	}

}
