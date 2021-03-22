/**
 * 
 */
package utopia;

import java.sql.SQLException;

/**
 * @author Nicholas Williams
 *
 */
public class MainClass extends TextPrompt {

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
