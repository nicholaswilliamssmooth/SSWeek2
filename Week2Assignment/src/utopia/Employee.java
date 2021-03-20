package utopia;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dao.AirportDAO;
import dao.FlightDAO;
import dao.RouteDAO;
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
			break;
		case 3:
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
				+ "| Departure Time: " + flight.getDepartureTime().toLocalTime() + "\n"
				+ "| Arrival Date: E | Arriva1l Time: F \r\n"
				+ "");
	}

}
