/**
	Class ReadingText
		- Reads a file written by WritingTest and displays it's contents.
		- handles any exceptions that occur.

	Author: Patrick Hibbs
	E-mail address: phibbs0003@kctcs.edu
	Last modified: 11/5/2015
	Lab 02
*/

import java.util.Scanner;
import java.io.File;

public class ReadingTest {
	public static void main(String[] args) {
		/* Init vars. */
		Scanner inputScanner = null;
		File inputFile = null;
		String inputString = "";
		
		/* Begin try block. */
		try {
			/* Create the file. */
			inputFile = new File("myFile.txt");

			/* Check and see if the file exists. */
			if (inputFile.exists()) {
				/* Create the scanner. */
				inputScanner = new Scanner(inputFile);

				/* Read all data from the file and display it. */
				do {
					inputString = inputScanner.nextLine();
					System.out.print(inputString);
				} while(inputScanner.hasNextLine());

				/* Output a newline. */
				System.out.print("\n");

				/* Close the file. */
				inputScanner.close();
				inputScanner = null;
			}
		}
		catch (Exception e) {
			System.out.println("\nERROR: An exception occured: " + e.getMessage());
		}
		finally {
			System.out.println("\nChecking for open file; will close file if it is.");
			if (inputScanner != null) {
				inputScanner.close();
				inputScanner = null;
			}
		}

		/* Exit program. */
		return;
	}
}

