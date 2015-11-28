/**
	Class TextEditorGUI
	Author: Patrick Hibbs
	E-mail address: phibbs0003@kctcs.edu
	Last changed: 11/16/2015
	Lab 03
*/

import java.util.Scanner;
import javafx.application.Application;	
import javafx.event.ActionEvent;	
import javafx.event.EventHandler;	
import javafx.stage.Stage;			
import javafx.scene.Scene;			
import javafx.scene.control.Button;	
import javafx.stage.Modality;		
import javafx.scene.control.TextArea;			
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.Exception;				/* Exceptions for bad programmers. */

public class TextEditorGUI extends Application {

	/* GUI components. */
	private TextArea guiTextArea;
	private Button loadButton;
	private Button saveButton;
	private Button clearButton;
	private Button closeButton;
	private FileChooser fileChooser;
	private Stage guiStage;
	private Scene guiScene;
	private HBox guiUserInputBtnsHBox;
	private VBox guiVBox;
	private final static String MAIN_WINDOW_TITLE_TXT = "TextEditorGUI";
	private final static String OPEN_FILE_TITLE_TXT = "Open File";
	private final static String SAVE_FILE_TITLE_TXT = "Save File";
	private final static String CLEAR_BTN_TXT = "Clear";
	private final static String CLOSE_BTN_TXT = "Exit program";

	public void CreateGUIObjects() {
		/* Create the file chooser. */
		this.fileChooser = new FileChooser();
		this.fileChooser.getExtensionFilters().addAll(
			new FileChooser.ExtensionFilter("Text Files", "*.txt"),
			new FileChooser.ExtensionFilter("Java Source Code", "*.java", "*.jav"),
			new FileChooser.ExtensionFilter("All Text Files", "*.*"));

		/* Create the Text Area. */
		this.guiTextArea = new TextArea();

		/* Create the buttons. */
		this.loadButton = new Button(OPEN_FILE_TITLE_TXT);
		this.saveButton = new Button(SAVE_FILE_TITLE_TXT);
		this.clearButton = new Button(CLEAR_BTN_TXT);
		this.closeButton = new Button(CLOSE_BTN_TXT);

		/* Set the event handler for loadButton.
			This is what allows the button to do something. */
		this.loadButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/* Init vars. */
				File inputFile = null;

				/* Ask the user for a file. */
				inputFile = fileChooser.showOpenDialog(null);
				if (inputFile != null) {
					try {
						load(inputFile);
					}
					catch (Exception e) {
						try {
							MessageWindow msgWin = new MessageWindow("EXCEPTION", ("An exception was thrown: " + e.getMessage()));
							msgWin.ShowMessage();
						}
						catch (Exception e2) {
							System.out.println("EXCEPTION: An exception was thrown while attempting to display\n" +
							"a MessageWindow: " + e2.getMessage() + "\n\nIn addition the MessageWindow was trying " +
							"to display an exception thrown while attempting to load a file: " + e.getMessage() + "\n");
						}
					}
				}
			}
		});

		/* Set the event handler for saveButton.
			This is what allows the button to do something. */
		this.saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/* Init vars. */
				File outputFile = null;

				/* Ask the user for a file. */
				outputFile = fileChooser.showSaveDialog(null);
				if (outputFile != null) {
					try {
						save(outputFile);
					}
					catch (Exception e) {
						try {
							MessageWindow msgWin = new MessageWindow("EXCEPTION", ("An exception was thrown: " + e.getMessage()));
							msgWin.ShowMessage();
						}
						catch (Exception e2) {
							System.out.println("EXCEPTION: An exception was thrown while attempting to display\n" +
							"a MessageWindow: " + e2.getMessage() + "\n\nIn addition the MessageWindow was trying " +
							"to display an exception thrown while attempting to save a file: " + e.getMessage() + "\n");
						}
					}
				}
			}
		});

		/* Set the event handler for clearButton.
			This is what allows the button to do something. */
		this.clearButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				guiTextArea.clear();
			}
		});

		/* Set the event handler for closeButton.
			This is what allows the button to do something. */
		this.closeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				guiStage.close();
			}
		});

		/* Create the needed HBox for the user input buttons. */
		this.guiUserInputBtnsHBox = new HBox();
		this.guiUserInputBtnsHBox.getChildren().addAll(	this.loadButton,
														this.saveButton,
														this.clearButton,
														this.closeButton);

		/* Create the main window's VBox. */
		this.guiVBox = new VBox();
		this.guiVBox.getChildren().addAll(	this.guiTextArea,
											this.guiUserInputBtnsHBox);

		/* Create the Stage and Scene. */
		this.guiStage = new Stage();
		this.guiScene = new Scene(this.guiVBox);
		this.guiStage.setTitle(this.MAIN_WINDOW_TITLE_TXT);
		this.guiStage.setScene(this.guiScene);

		/* Exit function. */
		return;
	}
	
	public boolean load(File inputFile) throws Exception {
		/* Init vars. */
		FileInputStream inputFileStream = null;
		BufferedInputStream inputBufStream = null;
		DataInputStream inputStream = null;

		/* Check for valid args. */
		if ((inputFile != null) && (this.guiTextArea != null)) {
			/* Begin try block. */
			try {
				/* Open the file. */
				inputFileStream = new FileInputStream(inputFile);
				inputBufStream = new BufferedInputStream(inputFileStream);
				inputStream = new DataInputStream(inputBufStream);

				/* Clear the gui text area. */
				this.guiTextArea.clear();

				/* Begin catalog file parsing loop. */
				do {
						/* Read in the data. */
						this.guiTextArea.appendText(inputStream.readUTF());
				} while(inputBufStream.available() > 0);

				/* Close the file. */
				inputBufStream.close();
				inputBufStream = null;
			}
			catch (Exception e) {
				/* Close the input file if needed. */
				if (inputBufStream != null) {
					inputBufStream.close();
					inputBufStream = null;
				}

				/* Rethrow the exception. */
				throw e;
			}
		}

		/* Exit function. */
		return true;
	}

	public void save(File outputFile) throws Exception {
		/* Init vars. */
		FileOutputStream outputFileStream = null;
		BufferedOutputStream outputBufStream = null;
		DataOutputStream outputStream = null;

		/* Check for vaild file. */
		if ((outputFile != null) && (this.guiTextArea != null)) {
			/* Begin try block. */
			try {
				/* Open the files. */
				outputFileStream = new FileOutputStream(outputFile);
				outputBufStream = new BufferedOutputStream(outputFileStream);
				outputStream = new DataOutputStream(outputBufStream);

				/* Output data. */
				outputStream.writeUTF(guiTextArea.getText());

				/* Close the file. */
				outputBufStream.close();
				outputBufStream = null;
			}
			catch (Exception e) {
				/* Close the output file if needed. */
				if (outputBufStream != null) {
					outputBufStream.close();
					outputBufStream = null;
				}

				/* Rethrow the exception. */
				throw e;
			}
		}

		/* Exit function. */
		return;
	}
	
	public void start(Stage primaryStage) {
		/* Create the GUI. */
		CreateGUIObjects();

		/* Showtime. */
		if (this.guiStage != null) {
			this.guiStage.show();
		}

		/* Exit function. */
		return;
	}

}	/* End of class TextEditorGUI. */
