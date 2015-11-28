/**
	class TicTacToeGUI
		-  Setup program for the TicTacToeGrid class.
	Author: Patrick Hibbs
	E-mail address: phibbs0003@kctcs.edu
	Last changed: 10/26/2015
	Lab 01
*/

/* Import needed components. */
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;	
import javafx.event.ActionEvent;	
import javafx.event.EventHandler;		
import javafx.geometry.Insets;			
import javafx.scene.control.Button;	
import javafx.stage.Modality;		
import javafx.scene.control.TextField;		
import javafx.scene.control.TextArea;	
import javafx.scene.control.Label;			
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import java.lang.Exception;				/* Exceptions for bad programmers. */

public class TicTacToeGUI extends Application {
	/* Custom game creation window stuff. */
	Stage customGameCreationStage;
	Scene customGameCreationScene;
	GridPane customGameGP;
	GridPane inputFieldGP;
	Label gridSizeLabel;
	Label player1CharLabel;
	Label player2CharLabel;
	TextField gridSizeTextField;
	TextField player1CharTextField;
	TextField player2CharTextField;
	Button okButton;
	Button cancelButton;
	
	/* Game type selection window stuff. */
	Stage gameTypeSelectionStage;
	Scene gameTypeSelectionScene;
	VBox radioBtnVBox; 
	RadioButton stdGameRadioButton;
	RadioButton custGameRadioButton;
	ToggleGroup radioBtnToggleGroup;
	
	private void CreateGUI(Stage primaryStage) {
		/* Create the stuff for the game type selection window. */
		
		/* Create the toggle group for the radio buttons. */
		this.radioBtnToggleGroup = new ToggleGroup();
		
		/* Create the radio buttons. */
		this.stdGameRadioButton = new RadioButton("Standard Game");
		this.custGameRadioButton = new RadioButton("Custom Game");
		
		/* Set the action handlers. */
		/* Set the event handler for the window's stdGameRadioButton.
			This is what allows the button to do something. */
		this.stdGameRadioButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/* Call the startStandardGame() function. */
				gameTypeSelectionStage.hide();
				startStandardGame();
				gameTypeSelectionStage.show();
				stdGameRadioButton.setSelected(false);
			}
		});
		
		/* Set the event handler for the window's custGameRadioButton.
			This is what allows the button to do something. */
		this.custGameRadioButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/* Show the customGameCreationStage. */
				gameTypeSelectionStage.hide();
				customGameCreationStage.showAndWait();
				gameTypeSelectionStage.show();
				custGameRadioButton.setSelected(false);
			}
		});
		
		/* Add the radio buttons to the toggle group. */
		this.stdGameRadioButton.setToggleGroup(this.radioBtnToggleGroup);
		this.custGameRadioButton.setToggleGroup(this.radioBtnToggleGroup);
		
		/* Create the VBox. */
		this.radioBtnVBox = new VBox();
		
		/* Add the radio buttons to the VBox. */
		this.radioBtnVBox.getChildren().addAll(this.stdGameRadioButton, this.custGameRadioButton);
		
		/* Create the stage and scene. */
		this.gameTypeSelectionStage = new Stage();
		this.gameTypeSelectionScene = new Scene(this.radioBtnVBox);
		
		/* Set the scene. */
		this.gameTypeSelectionStage.setScene(this.gameTypeSelectionScene);
		
		/* Set the title bar text for the window. */
		this.gameTypeSelectionStage.setTitle("Game Type Selection");
		
		/* This sets the the modality for the pop-up window.
			Basicly, it makes the pop-up window's parent window 
			(the main window in this case) not accept input until
			the pop-up window closes. */
		this.gameTypeSelectionStage.initModality(Modality.WINDOW_MODAL);
		
		
		
		
		
		
		
		
		/* Create the Custom Game Creation window. */ 
		this.customGameGP = new GridPane();
		this.inputFieldGP = new GridPane();
		this.inputFieldGP.setHgap(15);
		this.inputFieldGP.setVgap(15);
		
		/* Create the labels and text fields for the user's input. */
		this.gridSizeLabel = new Label("Grid Size: ");
		this.player1CharLabel = new Label("Player 1's Character: ");
		this.player2CharLabel = new Label("Player 2's Character: ");
		this.gridSizeTextField = new TextField();
		this.player1CharTextField = new TextField();
		this.player2CharTextField = new TextField();
		
		/* Add labels and text fields to the grid pane. */
		this.inputFieldGP.add(this.gridSizeLabel, 0, 0);
		this.inputFieldGP.add(this.player1CharLabel, 0, 1);
		this.inputFieldGP.add(this.player2CharLabel, 0, 2);
		this.inputFieldGP.add(this.gridSizeTextField, 1, 0);
		this.inputFieldGP.add(this.player1CharTextField, 1, 1);
		this.inputFieldGP.add(this.player2CharTextField, 1, 2);
		
		/* Add the input GP to the root node. */
		this.customGameGP.add(this.inputFieldGP, 0, 0);
		
		/* Create the ok and cancel buttons. */
		this.okButton = new Button();
		this.cancelButton = new Button();
		this.okButton.setText("OK");
		this.cancelButton.setText("Cancel");
		
		/* Set the event handlers for the ok and cancel buttons. */
		this.okButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/* Init vars. */
				int gridSize = 0;
				char player1Char = '\0';
				char player2Char = '\0';
				TicTacToeGrid ticTacToeGrid = null;
				MessageWindow msgWin = null;
				
				/* Fetch the size of the grid. */
				try {
					if (gridSizeTextField.getText().trim().length() > 0) {
						gridSize = Integer.parseInt(gridSizeTextField.getText().trim());
						if ((gridSize > 0) && (gridSize <= 9)) {
							/* Fetch the chars to use for the players. */
							if ((player1CharTextField.getText().length() > 0) && 
								(Character.toString(player1Char = (player1CharTextField.getText().trim().charAt(0))).matches("[a-zA-Z0-9]"))) {
								if ((player2CharTextField.getText().length() > 0) && 
								((Character.toString(player2Char = (player2CharTextField.getText().trim().charAt(0))).matches("[a-zA-Z0-9]")))) {
									/* Create the TicTacToeGrid. */
									ticTacToeGrid = new TicTacToeGrid(gridSize, player1Char, player2Char);
									if (ticTacToeGrid != null) {
										customGameCreationStage.hide();
										ticTacToeGrid.showAndWait();
									}
								} else {
									msgWin = new MessageWindow("ERROR", "Player 2's character must be a printable ASCII character. One character only.");
								}
							}
							else {
								msgWin = new MessageWindow("ERROR", "Player 1's character must be a printable ASCII character. One character only.");
							}
						}
						else {
							msgWin = new MessageWindow("ERROR", "The grid size must be no bigger than 9, and bigger than 0.");
						}
					}
					else {
						msgWin = new MessageWindow("ERROR", "You must specifiy a grid size.");	
					}
				}
				catch (Exception e) {
					try {
						msgWin = new MessageWindow("EXCEPTION", e.toString());
						System.out.println(e.toString());
					}
					catch (Exception e2) {
						System.out.println("Multiple exceptions! Original Exception: " + e.toString() + "\nException from MessageWindow(): " + e2.toString());
					}
				}
				
				/* Show a message if needed. */
				if (msgWin != null) {
					msgWin.ShowMessage(customGameCreationStage);
					msgWin = null;
				}
			}
		});
		this.cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/* Clear the input. */
				gridSizeTextField.clear();
				player1CharTextField.clear();
				player2CharTextField.clear();
				
			}
		});
		
		/* Add the buttons to the root node. */
		this.customGameGP.add(this.okButton, 0, 1);
		this.customGameGP.add(this.cancelButton, 1, 1);
		
		/* Create the stage and scene. */
		this.customGameCreationStage = new Stage();
		this.customGameCreationScene = new Scene(this.customGameGP);
		
		/* Set the title bar text for the window. */
		this.customGameCreationStage.setTitle("Custom Game Creation");
		
		/* Set the scene. */
		this.customGameCreationStage.setScene(this.customGameCreationScene);
		
		/* This sets the the modality for the pop-up window.
			Basicly, it makes the pop-up window's parent window 
			(the main window in this case) not accept input until
			the pop-up window closes. */
		this.customGameCreationStage.initModality(Modality.WINDOW_MODAL);
		
		/* Exit function. */
		return;
	}
	
	@Override
	public void start(Stage primaryStage) {
		
		/* Create the GUI windows. */
		this.CreateGUI(primaryStage);
		
		/* Display the game type selection window. */
		if (this.gameTypeSelectionStage != null) {
			this.gameTypeSelectionStage.show();
		}
		
		/* Exit program. */
		return;
	}
	
	public void startStandardGame() {
		/* Init vars. */
		TicTacToeGrid grid;
		
		/* Create the TicTacToeGrid. */
		grid = new TicTacToeGrid();
		
		/* Show the stage. */
		grid.showAndWait();
		
		/* Exit function. */
		return;
	}

} /* End of TicTacToeTestGUI */
