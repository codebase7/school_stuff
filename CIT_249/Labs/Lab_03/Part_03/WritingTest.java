/**
	Class WritingTest demonstrates how to
		- open a file for writing using a PrintWriter object
		- writing to the file using the PrintWriter
		- handle exceptions that occur during this process

	Author: Patrick Hibbs
	E-mail address: phibbs0003@kctcs.edu
	Last changed: 11/5/2015
	Lab 02
*/

import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WritingTest {
	public static void main(String[] args) {
		PrintWriter out = null;
		String userInput = "";
		Scanner input = new Scanner(System.in);

		System.out.print("Please enter what you want to write to a file: ");
		userInput = (input.nextLine()).trim();
		try {
			System.out.println("\nTrying to open the file...");
			out = new PrintWriter("myFile.txt");
			System.out.println("\nOpened the file...");
			out.println(userInput);
			System.out.println("\nFinished writing to the file.");
		}
		catch (FileNotFoundException fnfe) {
			System.out.println("\nError: Could not write to the file!");
		}
		finally {
			System.out.println("\nChecking if the file is open. " +
								 "Will close it if it is.");
			if (out != null) {
				System.out.println("\nClosing the file.");
				out.close();
			}
		}
	}
}
