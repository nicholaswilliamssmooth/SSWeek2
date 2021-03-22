/**
 * 
 */
package utopia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * @author Nicholas Williams
 *
 */
public class MainClass extends TextPromptTest {

	private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/utopia";
	private static final String username = "root";
	private static final String password = "john15";

	/**
	 * This makes sure that the inputs being used are valid for the prompt
	 * @param floor minimum entered int
	 * @param ceiling maximum entered int
	 * @return the valid integer that fits with the prompt
	 */
	public int getIntInput(Integer floor, Integer ceiling) {
		Scanner scan = new Scanner(System.in);
		Boolean valid = false;
		String choice = "";
		Integer choiceToInt = 0;
		while (!valid) {
			choice = scan.nextLine();
			try {
				// Test to see if you entered an integer
				choiceToInt = Integer.parseInt(choice);
				if (choiceToInt > ceiling || choiceToInt < floor)
					System.out.println("Invalid input, must type in a number between " + floor + " and " + ceiling + ".");
				else
					valid = true;

			}
			catch(Exception InputMismatchException) {
				System.out.println("Invalid input, must type in a number between " + floor + " and " + ceiling + ".");
			}
		}
		
		return choiceToInt;


	}
	
	/**
	 * @param args
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		MainClass mnObj = new MainClass();
		int choice = 0;
		System.out.println("Welcome to the Utopia Airlines Management System. Which category of a user are you?\n"
				+ "1)	Employee\r\n"
				+ "2)	Administrator\r\n"
				+ "3)	Traveler\r\n"
				+ "");
		choice = mnObj.getIntInput(1, 3);
		switch (choice) {
		case 1:
			Employee emp = new Employee();
			emp.EMP1();
			break;
		case 2:
			Admin admin = new Admin();
			admin.ADMIN1();
			break;		
		case 3:
			Traveler tra = new Traveler();
			tra.LoginPrompt();
			break;
		}
		
		System.out.println("Thank you for using this software");
		

	}

}
