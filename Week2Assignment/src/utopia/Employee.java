package utopia;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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

public class Employee extends TextPrompt {
	
	public void EMP1() throws ClassNotFoundException, SQLException {
		Integer choice = 0;
		System.out.println("EMP1\n"
				+ "1)	Enter Flights You Manage\r\n"
				+ "2)	Quit to previous \r\n"
				+ "");
		choice = getIntInput(1, 2);
		switch(choice) {
		case 1:
			EMP2();
		case 2:
			break;
		}
		
	}
	
	public void EMP2() throws ClassNotFoundException, SQLException {
		// Declare and initialize variables
		Integer choice = 0;
		RouteDAO routes = new RouteDAO(); // Used to get the list of flight paths that the user chooses
		FlightDAO flights = new FlightDAO(); // what we'll be using
		List<Flight> flightList = flights.readAllFlights(); // read the SQL table
		List<Route> routeList = routes.readAllRoutes(); // read the SQL table
		
		
		System.out.println("EMP2\n");
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
			EMP1();
		}
		// manipulate the data chosen
		else {
			// -1 accounts for the sql starting at index 1
			EMP3(flightList.get(choice-1));
			//System.out.println(flightList.get(choice-1).getId());
			
		}

	}
	
	public void EMP3(Flight flight) throws ClassNotFoundException, SQLException {
		Integer choice = 0;

		System.out.println("EMP3\n"
				+ "1)	View more details about the flight\r\n"
				+ "2)	Update the details of the Flight \r\n"
				+ "3)	Add Seats to Flight\r\n"
				+ "4)	Quit to previous (should take you menu EMP2)\r\n");
		choice = getIntInput(1, 4);
		switch(choice) {
		case 1:
			viewFlightDetails(flight);
			break;
		case 2:
			flight = updateFlightDetails(flight);
			EMP3(flight);
			break;
		case 3:
			flight = addSeats(flight);
			EMP3(flight);
			break;
		case 4:
			EMP2();
			break;
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
		EMP3(flight);
	}
	
	public Flight updateFlightDetails(Flight flight) throws ClassNotFoundException, SQLException {
		
		Route newRoute = new Route();
		RouteDAO routes = new RouteDAO();
		AirportDAO airport = new AirportDAO();
		Integer choice = 0;
		Airport originAP = new Airport();
		Airport destinationAP = new Airport();
		LocalDate newDepartDate = LocalDate.of(1, 1, 1);
		LocalTime newDepartTime = LocalTime.of(1, 1);
		LocalDate newArriveDate = LocalDate.of(1, 1, 1);
		LocalTime newArriveTime = LocalTime.of(1, 1);
		FlightDAO flights = new FlightDAO();


		
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
		return flight;

		
	}
	/**
	 * Make new seats available, we don't over over the number of seats in the plane type
	 * @param flight
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Flight addSeats(Flight flight) throws ClassNotFoundException, SQLException {
		// Declare and initialize variables
		Integer choice = 0;
		AirplaneTypeDAO planeType = new AirplaneTypeDAO();
		AirplaneDAO plane = new AirplaneDAO();
		FlightDAO flights = new FlightDAO();
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
			EMP3(flight);
			break;
		}

		
		return flight;
	}
		
		
	

}
