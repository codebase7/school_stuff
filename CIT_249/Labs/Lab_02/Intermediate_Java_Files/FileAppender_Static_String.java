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

import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class FileAppender {
	public static void main(String[] args) {
		File myFile = new File("myFile.txt");
		PrintWriter out = null;

		//check if the file exists before trying to open it
		if (myFile.exists()) {

			
			try {
				System.out.println("\nTrying to open the file...");
				out = new PrintWriter(
							new BufferedWriter(
								new FileWriter(myFile, true)));
				System.out.println("\nOpened the file...");
				out.println("Writing my second file in java!");
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

