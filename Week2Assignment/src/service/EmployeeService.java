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
import dao.FlightDAO;
import dao.RouteDAO;
import domain.AirplaneType;
import domain.Airport;
import domain.Flight;
import domain.Route;
import utopia.TextPrompt;
import utopia.TextPromptTest;

public class EmployeeService extends TextPrompt {

	Util util = new Util();
	

	/**
	 * Make new seats available, we don't over over the number of seats in the plane type
	 * @param flight
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Flight addSeats(Flight flight) throws ClassNotFoundException, SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			// Declare and initialize variables
			Integer choice = 0;
			AirplaneTypeDAO planeType = new AirplaneTypeDAO(conn);
			AirplaneDAO plane = new AirplaneDAO(conn);
			FlightDAO flights = new FlightDAO(conn);
			Integer newSeats = 0;

			// Get max capacity of all seats on the plane
			AirplaneType flightSeats = planeType.readAirplaneTypeByID(plane.readAirplaneByID(flight.getAirplaneID()).get(0).getAirplaneModel()).get(0);
			Integer business = flightSeats.getBusinessClass()
					- flight.getReservedBusiness();
			Integer first = flightSeats.getFirstClass()
					- flight.getReservedFirstClass();
			Integer totalEconomySeats = flightSeats.getMaxCapacity() - flightSeats.getFirstClass() - flightSeats.getBusinessClass();
			Integer economy = (totalEconomySeats)
					- (flight.getReservedSeats() - flight.getReservedBusiness() - flight.getReservedFirstClass());
			
			System.out.println("Pick the Seat Class you want to add seats of, to your flight:\r\n"
					+ "1)	First\r\n"
					+ "2)	Business\r\n"
					+ "3)	Economy\r\n"
					+ "4)	Quit to cancel operation\r\n"
					+ "");
			choice = getIntInput(1, 4);
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
			case 4:
				break;
			}

			conn.commit();
			return flight;

		}
		catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}
		return flight;
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
		getIntInput(4, 4);
	
	}

	public Flight updateFlightDetails(Flight flight) throws SQLException {
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


			
			System.out.println("You have chosen to update the Flight with Flight Id: "+ flight.getId() + " "
					+ "and Flight Origin: " + airport.readAirportByCode(routes.readRouteByID(flight.getRouteID()).get(0).getOriginID()).get(0).getCity() + " "
					+ "and Flight Destination: " + airport.readAirportByCode(routes.readRouteByID(flight.getRouteID()).get(0).getDestinationID()).get(0).getCity() + "\r\n" 
					+ "Enter ‘0’ at any prompt to cancel operation.\r\n"
					+ "\r\n"
					+ "Please enter new Origin Airport and City or enter N/A for no change:\r\n"
					+ "");
			
			// List all available airports
			List<Airport> airportList = airport.readAllAirports(); // read the SQL table
			Integer count = 1;
			for (Airport a : airportList) {
				System.out.println(count + ") " + a.getCity());
				count++;
			} 
			System.out.println(count + ") No Change");
			// choice origin airport
			choice = getIntInput(0, count);
			if (choice == 0) { return flight; }
			else if (choice < count) { 
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
				choice = getIntInput(0, count);
				if (choice == 0) { return flight; }
				else if (choice < count) { 
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
			else if (dateStr.equals("quit")) {
				return flight;
			}
			else {
				newDepartDate = LocalDate.parse(dateStr);
			}
			System.out.println(newDepartDate);
			
			// New Departure Time
			System.out.println("Please enter new Departure Time or enter N/A for no change");
			String timeStr = getTimeInput();
			if (timeStr.equals("N/A"))
				newDepartTime = flight.getDepartureTime().toLocalTime();
			else if (timeStr.equals("quit")) {
				return flight;
			}
			else {
				newDepartTime = LocalTime.parse(timeStr);
			}
			System.out.println(newDepartTime);
			
			// New Arrival Date
			System.out.println("Please enter new Arrival Date or enter N/A for no change");
			dateStr = getDateInput();
			if (dateStr.equals("N/A"))
				newArriveDate = flight.getArrivalTime().toLocalDate();
			else if (dateStr.equals("quit")) {
				return flight;
			}
			else {
				newArriveDate = LocalDate.parse(dateStr);
			}
			System.out.println(newDepartDate);
			
			// New Arrival Time
			System.out.println("Please enter new Arrival Time or enter N/A for no change");
			timeStr = getTimeInput();
			if (timeStr.equals("N/A"))
				newArriveTime = flight.getArrivalTime().toLocalTime();
			else if (timeStr.equals("quit")) {
				return flight;
			}
			else {
				newArriveTime = LocalTime.parse(timeStr);
			}
			System.out.println(newDepartTime);
			
			flight.setArrivalTime(LocalDateTime.of(newArriveDate, newArriveTime));
			flight.setDepartureTime(LocalDateTime.of(newDepartDate, newDepartTime));
			flight.setRouteID(newRoute);
			// sql update
			flights.updateFlight(flight);
			System.out.println("Successfully updated the flight");
			conn.commit();

		}
		catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}
		return flight;
	}

}
