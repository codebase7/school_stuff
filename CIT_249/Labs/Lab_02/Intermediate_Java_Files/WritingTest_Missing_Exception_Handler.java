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

public class WritingTest {
	public static void main(String[] args) {
		
		PrintWriter out = new PrintWriter("myFile.txt");
		out.println("Writing my first file in Java!");

	}
}

