package utopia;

import java.sql.SQLException;

import java.util.List;


import domain.Flight;
import service.EmployeeService;

public class Employee extends TextPrompt {
	
	private EmployeeService service = new EmployeeService();

	
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
		List<Flight> flights = service.getFlights();
		System.out.println(flights.size()+1 + ") Quit to previous \r\n");
		
		// get input
		choice = getIntInput(1, flights.size()+1);
		// quit to previous menu
		if (choice == flights.size()+1) {
			EMP1();
		}
		// manipulate the data chosen
		else {
			// -1 accounts for the sql starting at index 1
			EMP3(flights.get(choice-1));
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
			service.getFlightDetails(flight);
			EMP3(flight);
			break;
		case 2:
			flight = service.updateFlightDetails(flight);
			EMP3(flight);
			break;
		case 3:
			flight = service.addSeats(flight);
			EMP3(flight);
			break;
		case 4:
			EMP2();
			break;
		}
	}
		
	

}
