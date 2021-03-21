package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import dao.AirplaneDAO;
import dao.AirplaneTypeDAO;
import dao.AirportDAO;
import dao.BookingDAO;
import dao.BookingUserDAO;
import dao.FlightBookingDAO;
import dao.FlightDAO;
import dao.PassengerDAO;
import dao.RouteDAO;
import domain.Airplane;
import domain.AirplaneType;
import domain.Airport;
import domain.Booking;
import domain.BookingUser;
import domain.Flight;
import domain.FlightBooking;
import domain.Passenger;
import domain.Route;

public class Administrator extends utopia.TextPrompt {
	
	Util util = new Util();
	
	public void readFlight() throws SQLException, ClassNotFoundException {
		Connection conn = util.getConnection();
		RouteDAO routes = new RouteDAO(conn);
		AirportDAO airport = new AirportDAO(conn);
		Integer choice = 0;
		FlightDAO flights = new FlightDAO(conn);
		
		List<Flight> flightList = flights.readAllFlights(); // read the SQL table
		
		
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

}
