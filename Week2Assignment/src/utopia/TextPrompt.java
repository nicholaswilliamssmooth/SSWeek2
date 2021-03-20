package utopia;

import java.util.Scanner;

// Mainly contains helper functions to do things for the console output.
public abstract class TextPrompt {
	
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
}
