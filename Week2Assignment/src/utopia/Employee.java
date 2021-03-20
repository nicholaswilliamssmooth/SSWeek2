package utopia;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.RouteDAO;
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
		Integer choice = 0;
		RouteDAO routes = new RouteDAO();
		List<Route> routeList = routes.readAllRoutes();
		
		System.out.println("EMP2\n");
		Integer count = 1;
		for (Route r : routeList) {
			System.out.println(count + ") " + r.getOriginID().getAirportCode() + " -> " + r.getDestinationID().getAirportCode());
			count++;
		}

		System.out.println(count + ") Quit to previous \r\n");

		choice = getIntInput(1, count);
		// quit to previous menu
		if (choice == count) {
			EMP1();
		}
		// manipulate the data
		else {
			routeList.get(choice);
			
		}
		switch(choice) {
		case 1:
			break;
		case 2:
			EMP1();
			break;
		}
		
	}

}
