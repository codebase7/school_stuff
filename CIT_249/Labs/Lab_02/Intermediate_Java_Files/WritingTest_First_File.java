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

public class WritingTest {
	public static void main(String[] args) {
		PrintWriter out = null;
		try {		
			out = new PrintWriter("myFile.txt");
			out.println("Writing my first file in Java!");
		}
		catch (FileNotFoundException fnfe) {
			System.out.println("\nError: Could not write to the file!");
		}
		finally {
			if (out != null) {
				System.out.println("\nClosing the file.");
				out.close();
			}
		}
	}
}

