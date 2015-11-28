/**
	Class FileAppender demonstrates how to
		- check if a file exists before trying to open it
		- append text to an existing file
		- write to the file efficiently using a BufferedWriter
		- handle exceptions that occur during this process

	Author: Patrick Hibbs
	E-mail address: phibbs0003@kctcs.edu
	Last changed: 11/5/2015
	Lab 02
*/

import java.io.*;
import java.util.Scanner;

public class FileAppender {
	public static void main(String[] args) {
		File myFile = new File("myFile.txt");
		PrintWriter out = null;
		Scanner inputScanner = null;
		String userInput = "";

		//check if the file exists before trying to open it
		if (myFile.exists()) {			
			try {
				inputScanner = new Scanner(System.in);

				System.out.println("\nTrying to open the file...");
				out = new PrintWriter(
							new BufferedWriter(
								new FileWriter(myFile, true)));
				System.out.println("\nOpened the file...");
				System.out.print("Please input what you would like to append to the file: ");
				userInput = (inputScanner.nextLine()).trim();
				out.println(userInput);
				System.out.println("\nFinished writing to the file.");
			}
			catch (IOException ioe) {
				System.out.println("\nError: " +
									"Could not write to the file!");
				ioe.printStackTrace();
			}
			finally {
				System.out.println("\nChecking if the file is open. " +
								"Will close it if it is.");
				if (out != null) {
					System.out.println("\nClosing the file.");
					out.close();
				}
			}
		} //end of if sstatement
	}//end of main method
}//end of class

