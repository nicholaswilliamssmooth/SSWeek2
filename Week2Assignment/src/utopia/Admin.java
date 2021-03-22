package utopia;

import java.sql.SQLException;

import service.Administrator;

public class Admin extends TextPrompt {
	
	private Administrator admin = new Administrator();
	
	public void ADMIN1() throws SQLException, ClassNotFoundException {
		 System.out.println("1)	Add/Update/Delete/Read Flights\r\n"
		 		+ "2)	Add/Update/Delete/Read Seats\r\n"
		 		+ "3)	Add/Update/Delete/Read Tickets and Passengers\r\n"
		 		+ "4)	Add/Update/Delete/Read Airports\r\n"
		 		+ "5)	Add/Update/Delete/Read Travelers\r\n"
		 		+ "6)	Over-ride Trip Cancellation for a ticket.\r\n"
		 		+ "");
		 
		Integer choice = getIntInput(1, 7);
		switch (choice) {
		case 1:
			displayCRUD();
			Integer fChoice = getIntInput(1, 4);
			switch (fChoice) {
			case 1:
				addFlight();
				break;
			case 2:
				updateFlight();
				break;
			case 3:
				deleteFlight();
				break;
			case 4:
				readFlight();
				break;
			}
			break;
		case 2:
			displayCRUD();
			Integer pChoice = getIntInput(1, 4);
			switch (pChoice) {
			case 1:
				addSeats();
				break;
			case 2:
				updateSeats();
				break;
			case 3:
				deleteSeats();
				break;
			case 4:
				readSeats();
				break;
			}
			break;
		case 3:
			displayCRUD();
			Integer sChoice = getIntInput(1, 4);
			switch (sChoice) {
			case 1:
				addPassengers();
				break;
			case 2:
				updatePassengers();
				break;
			case 3:
				deletePassengers();
				break;
			case 4:
				readPassengers();
				break;
			}
			break;
		case 4:
			displayCRUD();
			Integer aChoice = getIntInput(1, 4);
			switch (aChoice) {
			case 1:
				addAirport();
				break;
			case 2:
				updateAirport();
				break;
			case 3:
				deleteAirport();
				break;
			case 4:
				readAirports();
				break;
			}
			break;
		case 5:
			displayCRUD();
			Integer uChoice = getIntInput(1, 4);
			switch (uChoice) {
			case 1:
				addTraveler();
				break;
			case 2:
				updateTraveler();
				break;
			case 3:
				deleteTraveler();
				break;
			case 4:
				readTravelers();
				break;
			}
			break;
		case 6:
			overrideTicket();
			break;
		}
	}
		
	private void overrideTicket() throws SQLException {
		System.out.println(admin.overrideTicket());
		
	}

	private void readTravelers() throws ClassNotFoundException, SQLException {
		admin.readTravelers();
		
	}

	private void deleteTraveler() throws SQLException {
		System.out.println(admin.deleteTraveler());
		
	}

	private void updateTraveler() throws SQLException {
		System.out.println(admin.updateTraveler());
		
	}

	private void addTraveler() throws SQLException {
		System.out.println(admin.addTraveler());
		
	}

	private void readAirports() throws ClassNotFoundException, SQLException {
		admin.readAirport();
		
	}

	private void deleteAirport() throws SQLException {
		System.out.println(admin.deleteAirport());
		
	}

	private void updateAirport() throws SQLException {
		System.out.println(admin.updateAirport());
		
	}

	private void addAirport() throws SQLException {
		System.out.println(admin.addAirport());
		
	}

	private void readPassengers() throws ClassNotFoundException, SQLException {
		admin.readPassenger();
		
	}

	private void deletePassengers() throws SQLException {
		System.out.println(admin.deletePassenger());
		
	}

	private void updatePassengers() throws SQLException {
		System.out.println(admin.updatePassenger());
		
	}

	private void addPassengers() throws SQLException {
		System.out.println(admin.addPassenger());
		
	}

	private void readSeats() throws ClassNotFoundException, SQLException {
		admin.readSeats();
		
	}

	private void deleteSeats() throws SQLException {
		System.out.println(admin.deleteSeats());
		
	}

	private void updateSeats() throws SQLException {
		System.out.println(admin.updateSeats());

		
	}

	private void addSeats() throws SQLException {
		System.out.println(admin.addSeats());
		
	}

	// 1) Add/Update/Delete/Read Flights

	private void readFlight() throws ClassNotFoundException, SQLException {
		admin.readFlight();
		
	}

	private void deleteFlight() throws SQLException {
		System.out.println(admin.deleteFlight());
		
	}

	private void updateFlight() throws SQLException {
		System.out.println(admin.UpdateFlight());
		
	}

	private void addFlight() throws SQLException {
		System.out.println(admin.addFlight());
	}


	public void displayCRUD() {
		System.out.println("1) Add");
		System.out.println("2) Update");
		System.out.println("3) Delete");
		System.out.println("4) Read");
	}
	
	
	

	
	/*
		2)	Add/Update/Delete/Read Seats
		3)	Add/Update/Delete/Read Tickets and Passengers
		4)	Add/Update/Delete/Read Airports
		5)	Add/Update/Delete/Read Travelers
		6)	Add/Update/Delete/Read Employees
		7)	Over-ride Trip Cancellation for a ticket.

	 */

}
