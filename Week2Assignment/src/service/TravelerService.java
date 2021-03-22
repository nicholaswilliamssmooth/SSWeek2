package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
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
import domain.AirplaneType;
import domain.Booking;
import domain.BookingUser;
import domain.Flight;
import domain.FlightBooking;
import domain.Passenger;
import domain.Route;
import domain.User;

public class TravelerService {

	Util util = new Util();
	
	public String addFirstClassBooking(Flight flight, User loggedInUser, LocalDate dob, String gender, String address) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			FlightDAO editFlight = new FlightDAO(conn); // edit the reserved seats
			FlightBookingDAO flightBookingDAO = new FlightBookingDAO(conn); // connect to selected flightID to the booking
			FlightBooking newFlightBooking = new FlightBooking();
			PassengerDAO passengerDAO = new PassengerDAO(conn); // create a new passenger row
			Passenger newPassenger = new Passenger();
			BookingDAO bookingDAO = new BookingDAO(conn); // create a new booking row
			Booking newBooking = new Booking();
			BookingUserDAO bookingUserDAO = new BookingUserDAO(conn); // create a connection between the booking and user
			BookingUser newBookingUser = new BookingUser();
			
			// Increment up with the flight
			flight.setReservedSeats(flight.getReservedSeats()+1);
			flight.setReservedFirstClass(flight.getReservedFirstClass()+1);
			editFlight.updateFlight(flight);
			
			// Create our booking
			List<Booking> allBookings = bookingDAO.readAllBookings();
			newBooking.setId(allBookings.size()+1);
			// 1 = active
			newBooking.setIsActive(1);
			// Hardcoded, might write something to generate these automatically later
			newBooking.setConfirmationCode("RIK-248");
			bookingDAO.addBooking(newBooking);
			
			// Connect booking to flight
			newFlightBooking.setBookingID(newBooking);
			newFlightBooking.setFlightID(flight);
			flightBookingDAO.addFlightBooking(newFlightBooking);
			
			// Connect booking to user
			newBookingUser.setBookingID(newBooking.getId());
			newBookingUser.setUserID(loggedInUser);
			bookingUserDAO.addBookingUser(newBookingUser);

			// Create passenger listing
			List<Passenger> allPassengers = passengerDAO.readAllPassengers();
			newPassenger.setId(allPassengers.size()+1);
			newPassenger.setGivenName(loggedInUser.getGivenName());
			newPassenger.setFamilyName(loggedInUser.getFamilyName());
			newPassenger.setBookingID(newBooking);
			
			newPassenger.setDateOfBirth(dob);
			newPassenger.setGender(gender);
			newPassenger.setAddress(address);
			
			passengerDAO.addPassenger(newPassenger);
			
			conn.commit();
			return "Successfully booked your flight";
		}
		catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Flight could not be booked";
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	public String addBusinessBooking(Flight flight, User loggedInUser, LocalDate dob, String gender, String address) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			FlightDAO editFlight = new FlightDAO(conn); // edit the reserved seats
			FlightBookingDAO flightBookingDAO = new FlightBookingDAO(conn); // connect to selected flightID to the booking
			FlightBooking newFlightBooking = new FlightBooking();
			PassengerDAO passengerDAO = new PassengerDAO(conn); // create a new passenger row
			Passenger newPassenger = new Passenger();
			BookingDAO bookingDAO = new BookingDAO(conn); // create a new booking row
			Booking newBooking = new Booking();
			BookingUserDAO bookingUserDAO = new BookingUserDAO(conn); // create a connection between the booking and user
			BookingUser newBookingUser = new BookingUser();
			
			// Increment up with the flight
			flight.setReservedSeats(flight.getReservedSeats()+1);
			flight.setReservedBusiness(flight.getReservedBusiness()+1);
			editFlight.updateFlight(flight);
			
			// Create our booking
			List<Booking> allBookings = bookingDAO.readAllBookings();
			newBooking.setId(allBookings.size()+1);
			// 1 = active
			newBooking.setIsActive(1);
			// Hardcoded, might write something to generate these automatically later
			newBooking.setConfirmationCode("RIK-248");
			bookingDAO.addBooking(newBooking);
			
			// Connect booking to flight
			newFlightBooking.setBookingID(newBooking);
			newFlightBooking.setFlightID(flight);
			flightBookingDAO.addFlightBooking(newFlightBooking);
			
			// Connect booking to user
			newBookingUser.setBookingID(newBooking.getId());
			newBookingUser.setUserID(loggedInUser);
			bookingUserDAO.addBookingUser(newBookingUser);

			// Create passenger listing
			List<Passenger> allPassengers = passengerDAO.readAllPassengers();
			newPassenger.setId(allPassengers.size()+1);
			newPassenger.setGivenName(loggedInUser.getGivenName());
			newPassenger.setFamilyName(loggedInUser.getFamilyName());
			newPassenger.setBookingID(newBooking);
			
			newPassenger.setDateOfBirth(dob);
			newPassenger.setGender(gender);
			newPassenger.setAddress(address);
			
			passengerDAO.addPassenger(newPassenger);
			
			conn.commit();
			return "Successfully booked your flight";
		}
		catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Flight could not be booked";
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	public String addEconomyBooking(Flight flight, User loggedInUser, LocalDate dob, String gender, String address) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			FlightDAO editFlight = new FlightDAO(conn); // edit the reserved seats
			FlightBookingDAO flightBookingDAO = new FlightBookingDAO(conn); // connect to selected flightID to the booking
			FlightBooking newFlightBooking = new FlightBooking();
			PassengerDAO passengerDAO = new PassengerDAO(conn); // create a new passenger row
			Passenger newPassenger = new Passenger();
			BookingDAO bookingDAO = new BookingDAO(conn); // create a new booking row
			Booking newBooking = new Booking();
			BookingUserDAO bookingUserDAO = new BookingUserDAO(conn); // create a connection between the booking and user
			BookingUser newBookingUser = new BookingUser();
			
			// Increment up with the flight
			flight.setReservedSeats(flight.getReservedSeats()+1);
			editFlight.updateFlight(flight);
			
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
			newBookingUser.setUserID(loggedInUser);
			bookingUserDAO.addBookingUser(newBookingUser);

			// Create passenger listing
			List<Passenger> allPassengers = passengerDAO.readAllPassengers();
			newPassenger.setId(allPassengers.size()+1);
			newPassenger.setGivenName(loggedInUser.getGivenName());
			newPassenger.setFamilyName(loggedInUser.getFamilyName());
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
			return "Successfully booked your flight";
		}
		catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Flight could not be booked";
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	public List<Flight> getFlights() throws SQLException, ClassNotFoundException {
		Connection conn = null;
		conn = util.getConnection();

		RouteDAO routes = new RouteDAO(conn); // Used to get the list of flight paths that the user chooses
		FlightDAO flights = new FlightDAO(conn); // what we'll be using
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
		return flightList;

	}
	
	public List<Booking> getBookedFlights(User loggedInUser) throws SQLException, ClassNotFoundException {
		Connection conn = null;
		conn = util.getConnection();
		
		BookingUserDAO bookingUserDAO = new BookingUserDAO(conn);
		BookingUser userBooking = new BookingUser();
		BookingDAO bookingDAO = new BookingDAO(conn);
		
		List<BookingUser> allBookings = bookingUserDAO.readAllBookingsOfUser(loggedInUser);
		
		List<List<Booking>> matchingBookings = bookingDAO.readAllMatchingID(allBookings);
		List<Booking> exportBookings = new ArrayList<>();
		
		Integer count = 1;
		System.out.println("Please select one of your bookings to cancel:");
		for (List<Booking> m : matchingBookings) {
			System.out.println(count + ") ID: " + m.get(0).getId() + "\t Confirmation Code: " + m.get(0).getConfirmationCode());
			exportBookings.add(m.get(0));
			count++;
		}
		System.out.println(count + ") Exit to previous menu");
		
		return exportBookings;


	}
	
	public String cancelTicket(Booking booking) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();

			BookingDAO bookingDAO = new BookingDAO(conn); // create a new booking row
			Booking newBooking = new Booking();

			
			newBooking.setConfirmationCode(booking.getConfirmationCode());
			newBooking.setId(booking.getId());
			// No longer in the booking
			newBooking.setIsActive(0);
			System.out.println("Updating");
			bookingDAO.updateBooking(newBooking);
			System.out.println("Updated");
			conn.commit();
			return "Successfully cancelled your flight";
		}
		catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Flight could not be cancelled";
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	public void getFlightDetails(Flight flight) throws SQLException, ClassNotFoundException {
		Connection conn = null;
		conn = util.getConnection();
		// for output
		AirportDAO airport = new AirportDAO(conn);
		RouteDAO routes = new RouteDAO(conn);
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
	
	public List<User> getMatchingUserList(String username, String password) throws SQLException, ClassNotFoundException {
		Connection conn = null;
		conn = util.getConnection();
		UserDAO userList = new UserDAO(conn);
		return userList.readUserByLogin(username, password);
		

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
		return funcHolder;
		
	}
}
