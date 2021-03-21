package utopia;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import dao.UserDAO;
import domain.User;

public class Traveler extends TextPrompt {
	
	// After the user is logged in let them interact with the rest of the class
	private User loggedInUser;
	
	public void LoginPrompt() throws ClassNotFoundException, SQLException {
		String user;
		String pass;
		Boolean loggedIn = false;
		while (!loggedIn) {
			Scanner scan = new Scanner(System.in);
			System.out.println("Please log in to continue");
			System.out.print("Username: ");
			user = scan.nextLine();
			System.out.print("Password: ");
			pass = scan.nextLine();
			loggedIn = login(user, pass);

		}
		TRAV1();
		

		

	}
	/**
	 * 
	 * @param username
	 * @param password
	 * @return true or false if the login was successful
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Boolean login(String username, String password) throws ClassNotFoundException, SQLException {
		UserDAO userList = new UserDAO();
		List<User> userMatch = userList.readUserByLogin(username, password);
		if (userMatch.size() > 0) {
			loggedInUser = userMatch.get(0);
			return true;
		}
		else {
			System.out.println("User not found, try again");
			return false;

		}
		
	}
	public void TRAV1() {
		System.out.println("Hello " + loggedInUser.getGivenName() + ", please choose an option:\n"
				+ "1)	Book a Ticket\r\n"
				+ "2)	Cancel an Upcoming Trip\r\n"
				+ "3)	Quit to Previous\r\n"
				+ "");
	}
	
	
}
