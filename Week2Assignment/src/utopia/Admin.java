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
			break;
		case 3:
			displayCRUD();
			break;
		case 4:
			displayCRUD();
			break;
		case 5:
			displayCRUD();
			break;
		case 6:
			break;
		}
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
