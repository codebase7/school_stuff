/**
	Class EditorPane
		- Contains a scrollable / editable text field.
		- Can save data in the text field to a given file.

	Author: Patrick Hibbs
	E-mail address: phibbs0003@kctcs.edu
	Last changed: 11/26/2015
	Assignment 03
*/

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextArea;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.lang.IllegalStateException;
import java.util.Scanner;
import java.io.FileWriter;

public class EditorPane extends BorderPane {
	/* Init vars. */
	private ScrollPane textAreaScrollPane;
	private TextArea textArea;
	private File currentFile;

	EditorPane() {
		this.currentFile = null;
		this.textArea = new TextArea();
		this.textArea.setEditable(true);
		this.textAreaScrollPane = new ScrollPane();
		this.textAreaScrollPane.setContent(this.textArea);

		/* Set the scroll pane as the center of this border pane. */
		this.setCenter(this.textAreaScrollPane);
	}

	public long getTextLength() {
		return ((this.textArea != null) ? ((this.textArea.getText()).length()) : (0));
	}

	public boolean isEmpty() {
		return ((this.textArea != null) ? (((this.textArea.getText()).length() > 0) ?
				(false) : (true)) : (true));
	}

	public void addLine(String line) {
		/* Add the string to the textArea. */
		if ((line != null) && (line.length() > 0) && (this.textArea != null)) {
			this.textArea.appendText(line);
		}

		/* Exit function. */
		return;
	}

	public File getCurrentFile() {
		return this.currentFile;
	}

	public void setCurrentFile(File newFile) {
		/* Set the current file. */
		this.currentFile = newFile;

		/* Exit function. */
		return;
	}

	public void saveCurrentFile() {
		/* Init vars. */
		FileWriter outputStream = null;
		String tempString = null;

		/* NOTE: 
			DataOutputStream.writeUTF() generates a binary length for the string when it runs.
			The Java compiler does not know what to do with this length data and errors out.
			So to avoid getting errors when attempting to compile a source code file made by
			this EditorPane, we use a FileWriter here instead of the usual FileOutputStream,
			BufferedOutputStream, and DataOutputStream combo.
		*/

		/* Check for valid file. */
		if ((this.currentFile != null) && ((this.textArea.getText()).length() > 0)) {
			/* Begin try block. */
			try {
				/* Open the files. */
				outputStream = new FileWriter(this.currentFile, false);
				if (outputStream != null) {
					/* Get the data from the textArea. */
					tempString = this.textArea.getText();
					if (tempString != null) {
						outputStream.write(tempString);
					}
				}
			}
			catch (IOException ioe) {
				System.out.println("\nEXCEPTION: Exception thrown while saving file to disk.");
				System.out.println("The exception was: " + ioe.getMessage());
			}
			finally {
				/* Close the file. */
				if (outputStream != null) {
					try {
						outputStream.close();
						outputStream = null;
					}
					catch (IOException ioe2) {
						System.out.println("\nEXCEPTION: Exception thrown while closing file on disk.");
						System.out.println("The exception was: " + ioe2.getMessage());
					}
				}
			}
		}

		/* Exit function. */
		return;
	}

	public void openFile(File inputFile) {
		/* Init vars. */
		Scanner inputScanner = null;
		String tempString = null;

		/* NOTE: 
			DataOutputStream.writeUTF() generates a binary length for the string when it runs.
			The Java compiler does not know what to do with this length data and errors out.
			So to avoid getting errors when attempting to compile a source code file made by
			this EditorPane, we use a Scanner here instead of the usual FileInputStream,
			BufferedInputStream, and DataInputStream combo. (As we use a FileWriter in the
			saveCurrentFile function.)
		*/

		/* Check for valid file and text area. */
		if ((inputFile != null) && (this.textArea != null)) {
			/* Check and see if the given inputFile is not the currentFile. */
			if (this.currentFile != inputFile) {
				/* Make the given inputFile the currentFile. */ 
				this.setCurrentFile(inputFile);
			}

			/* Begin try block. */
			try {
				/* Open the file. */
				inputScanner = new Scanner(this.currentFile);
				if (inputScanner != null) {
					/* Begin file parsing loop. */
					while (inputScanner.hasNextLine()) {
						/* Get the next line. */
						tempString = inputScanner.nextLine();
						/* Check for end of file. */
						if (inputScanner.hasNextLine()) {
							this.textArea.appendText(tempString + "\n");
						}
						else {
							/* Don't append a new line into the TextArea if there is nothing left in the file. */
							this.textArea.appendText(tempString);
						}
					}
				}
			}
			catch (FileNotFoundException fnfe) {
				System.out.println("\nEXCEPTION: Exception thrown while opening the file from disk.");
				System.out.println("The exception was: " + fnfe.getMessage());
			}
			catch (IllegalStateException ise) {
				System.out.println("\nEXCEPTION: Exception thrown while reading the file from disk.");
				System.out.println("The exception was: " + ise.getMessage());
			}
			catch (NoSuchElementException nsee) {
				System.out.println("\nEXCEPTION: Exception thrown while reading the file from disk.");
				System.out.println("The exception was: " + nsee.getMessage());
			}
			finally {
				/* Close the input file if needed. */
				if (inputScanner != null) {
					inputScanner.close();
					inputScanner = null;
				}
			}
		}

		/* Exit function. */
		return;
	}

	public void openCurrentFile() {
		/* Call real function. */
		openFile(this.currentFile);

		/* Exit function. */
		return;
	}

}	/* End of EditorPane. */
