/***********************************
	class SevensGameApplication
		- A text-mode application that tests the SevensGame class.
	Author: Patrick Hibbs
	E-Mail: phibbs0003@kctcs.edu
	Last changed: 10/28/2015
	Assignment 01
***********************************/

import java.util.Scanner;
import java.lang.Exception;

public class SevensGameApplication {
	private static void printGameStatus(SevensGame game) {
		/* Init vars. */
		int[] dice = null;
		int tempTotal = 0;
	
		/* Check for valid game. */
		if (game != null) {
			/* Output the current player and number of remaining attempts. */
			System.out.println("\nCurrent Player: " + (game.Get_Current_Player() + 1) + "\t\t" + "Roll: " + game.Get_Current_Players_Number_Of_Roll_Attempts() +
			"/" + (game.Get_Current_Players_Number_Of_Roll_Attempts() + game.Get_Current_Players_Remaining_Roll_Attempts()) + "\n\n");
			
			/* Output the current dice. */
			dice = game.Get_Current_Dice();
			if (dice != null) {
				System.out.print("Dice:\t\t");
				for (int x = 0; (x < dice.length); x++) {
					System.out.print("[" + ((dice[x] != 0) ? (dice[x]) : " ") + "]\t");
					tempTotal += dice[x];
				}
				/* Print the total. */
				System.out.print(" = [" + tempTotal + "]\n");
				
				/* Reset the total. */
				tempTotal = 0;
			}
			else {
				System.out.println("ERROR: COULD NOT GET CURRENT DICE DATA.");
			}
		}
	
		/* Exit function. */
		return;
	}
	
	private static void mainGame() {
		/* Init vars. */
		boolean fatalError = false;
		int numberOfPlayers = 0;
		int currentPlayer = 0;
		int action = 0;
		SevensGame game = null;

		/* Ask the user for the number of players. */
		do {
			try {
				numberOfPlayers = promptUser("\nPlease indicate the number of players you have: ");
			}
			catch (Exception e) {
				System.out.println("\nTry again, the number of players should be a positive integer.");
			}
		}	while (numberOfPlayers <= 0);
		
		/* Create the game. */
		game = new SevensGame(numberOfPlayers, false, true);
		if (game != null) {
			/* Begin players loop. */
			while (((currentPlayer = game.Get_Current_Player()) < numberOfPlayers) && (game.Get_Current_Players_Remaining_Roll_Attempts() > 0)) {
				while ((currentPlayer == game.Get_Current_Player()) && (game.Get_Current_Players_Remaining_Roll_Attempts() > 0)) {
					/* Roll the dice for the player. */
					game.Roll_Dice();
				
					/* Check the current dice. */
					game.Check_Dice(true);
					
					/* Output the current data. */
					printGameStatus(game);
					
					/* Prompt user for action if needed. */
					if ((currentPlayer == game.Get_Current_Player()) && (game.Get_Current_Players_Remaining_Roll_Attempts() > 0)) {
						do {
							try {
								/* Reset action. */
								action = 0;
								action = promptUser("\nPress [1] to end your turn.\nPress [2] re-try your roll: ");
								switch(action) {
									case 1:
										/* End turn. */
										game.End_Turn();
										break;
									case 2:
										break;
									default:
										System.out.println("\nInvalid action, please re-input your selection.");
										break;
								};
							}
							catch (Exception e) {
								System.out.println("\nPlease reinput your selection again.");
							}
						}	while ((action <= 0) || (action > 2));
					}
					else {
						try {
							System.out.println("\nPlayer " + (currentPlayer + 1) + " your turn has ended.");
							waitOnUser();
							game.End_Turn();
						}
						catch (Exception e) {
							fatalError = true;
							System.out.println("\nEXCEPTION: " + e.toString() + "\n");
						}
					}
				}
			}
			
			/* Output the winner. */
			System.out.println("\nThe winner is Player " + (game.Get_Winning_Player() + 1) + "!\nThe high score was: " + game.Get_Winning_Score() + ".");
		}
		
		/* Exit function. */
		return;

	}	/* End of mainGame(). */

	/* Used to denote when the promptUser() and waitOnUser() functions have errored out. */
	private static class promptUserException extends Exception {
		public promptUserException() { super(); }
		public promptUserException(String message) { super(message); }
		public promptUserException(String message, Throwable cause) { super(message, cause); }
		public promptUserException(Throwable cause) { super(cause); }
	}

	/*!
			waitOnUser()

			Prompts the user to press the enter key.

			Note: This function blocks the caller until the user hits the enter key.

			WARNING: This function will throw an exception if the scanner allocation fails.
	*/
	private static void waitOnUser() throws Exception, promptUserException {
		/* Init vars. */
		Scanner inputScanner = new Scanner(System.in);
		
		/* Check for valid scanner object. */
		if (inputScanner != null) {
			System.out.print("Press ENTER to continue.");
			inputScanner.nextLine();
		}
		else {
			throw new promptUserException("Could not allocate scanner object.");
		}
		
		/* Exit function. */
		return;
	}
	
	/*!
		promptUser()
		
		Prompts the user via the system console with the given prompt string.
		Returns the int given by the user.
		
		Note: This function blocks the caller until the user hits the enter key.
		
		WARNING: This function will throw an exception if the scanner allocation fails, or the user does not type in an int!
	*/
	private static int promptUser(String prompt) throws Exception, promptUserException {
		/* Init vars. */
		int ret = 0;
		Scanner inputScanner = new Scanner(System.in);
		
		if (inputScanner != null) {
			/* Prompt the user. */
			System.out.print(prompt);
		
			/* Check for valid input. */
			ret = inputScanner.nextInt();
			
			/* Clear the scanner's input. */
			inputScanner.nextLine();
		}
		else {
			/* Throw memory error. */
			throw new promptUserException("Could not allocate scanner object.");
		}
		
		/* Exit function. */
		return ret;
	}
	
	/*!
		promptUser()
		
		Prompts the user via the system console with the given prompt string.
		Returns true if the user types in the given confirmationChar,
		otherwise returns false.
		
		Note: This function blocks the caller until the user hits the enter key.
		
		WARNING: This function will throw an exception if the scanner allocation fails!
	*/
	private static boolean promptUser(String prompt, char confirmationChar) throws Exception, promptUserException {
		/* Init vars. */
		boolean ret = false;
		Scanner inputScanner = new Scanner(System.in);
		int inData = 0;

		if (inputScanner != null) {
			/* Prompt the user. */
			System.out.print(prompt);
		
			/* Check for valid input. */
			inData = System.in.read();
			if (inData == confirmationChar) {
				ret = true;
			}
			
			/* Clear the scanner's input. */
			inputScanner.nextLine();
		}
		else {
			/* Throw memory error. */
			throw new promptUserException("Could not allocate scanner object.");
		}
		
		/* Exit function. */
		return ret;
	}
	
	public static void main(String[] args) {
		/* Init vars. */
		int numberOfPlayers = 0;
		boolean exitGame = false;
		
		/* Begin main loop. */
		do {
			/* Run the main game loop. */
			mainGame();
			
			/* Ask the user if they want to continue. */
			try {
				exitGame = promptUser("\nDo you want to quit? (Press 'y' + ENTER for yes, anything else + ENTER for no. (Must be lowercase.)): ", 'y');
			}
			catch (Exception e) {
				/* Only catch our errors. */
				if (e instanceof promptUserException) {
					System.out.println("EXCEPTION: " + e.toString());	
				}
			}
			
		} while (!exitGame);
		
		/* Exit function. */
		return;
	}	/* End of main(). */
	
}	/* End of SevensGameApplication. */
