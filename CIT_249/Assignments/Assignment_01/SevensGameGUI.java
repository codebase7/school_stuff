/***********************************
	class SevensGameGUI
		- A GUI application that plays a game of "Sevens".
	Author: Patrick Hibbs
	E-Mail: phibbs0003@kctcs.edu
	Last changed: 11/2/2015
	Assignment 01
***********************************/

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import java.util.Random;				/* Random number generator. */
import java.lang.Exception;				/* Exceptions for bad programmers. */

public class SevensGameGUI extends Application {
	/* Declare constant game vars. */
	final static int MAX_THROWS = 3;
	final static int BAD_DICE_TOTAL = 7;
	final static int NUMBER_OF_DICE = 6;
	final static int NUMBER_OF_DICE_SIDES = 6;
	final static int NUMBER_OF_PLAYERS = 5;
	final static int FAKE_TEXT_AREA_LENGTH = 100;	/* Fake length because the TextArea's getColumns() method keeps giving me a "cannot find symbol" error. */
	int diceCount;			/* Current number of dice in play for the given round / player. */
	int playerNumber;		/* Who the current player is.... why not just "currentPlayer"? */
	int firstPlayerRolls;	/* Number of roll attempts the first player made. */
	int currentPlayerRolls;	/* Number of roll attempts the current player has made. */
	int winningPlayer;

	/* Basic game variables. */
	int diceRoll[];		/* Holds the values of the rolled dice. */
	int playerScore[];	/* Holds the score for each player. */
	Random rng;			/* Random number generator. */

	/* Init GUI Stuff. */
	final static String TOTAL_SCORE_BTN_TXT = "Calculate Score";
	final static String ROLL_DICE_BTN_TXT = "Roll the dice";
	final static String END_TURN_BTN_TXT = "End My Turn";
	final static String DISCARD_BTN_TXT = "Discard";
	final static String EXIT_BTN_TXT = "Exit program";
	final static String WINDOW_TITLE_TXT = "SevensGameGUI";
	final static String INVALID_DICE_TXT = "You still have some dice to remove that equal ";
	String invalidDiceErrorMsg;
	Stage guiStage;
	Scene guiScene;
	GridPane guiGridPane;	/* Scene root node. */
	GridPane diceGridPane;
	HBox userControlBtnsHBox;
	Button rollDiceButton;
	Button totalScoreButton;
	Button endTurnButton;
	Button exitButton;
	TextField diceTextFields[];
	Button diceDiscardButtons[];
	Label diceTextLabels[];
	TextArea outputTextArea;
	SevensGameHandler sevensGameHandler;	/* GUI Event handler. */

	/* Declare DEBUG_MODE. */
	final static boolean DEBUG_MODE = false;

	/* 
		class SevensGameHandler
			- A private inner class event-handler object.

	*/
	private class SevensGameHandler implements EventHandler<ActionEvent> {
		@Override
			public void handle(ActionEvent event) {
				/* Declare vars. */
				Object sourceObject = null;			/* Object pointer to the object that created the event. */
				Button sourceButton = null;			/* Pointer to the button obejct that was clicked. */
				int sourceDisableBtnIdx = 0;		/* Index of the clicked discard button in the diceDiscardButtons array. */
				MessageWindow msgWin = null;		/* MessageWindow for displaying an error message. */

				/* Get the source of the event. */
				sourceObject = event.getSource();
				if (sourceObject != null) {
					/* Determine what the source object is. */
					if (sourceObject instanceof Button) {
						/* Cast the pointer. */
						sourceButton = (Button)sourceObject;

						/* OK now we need to determine what button was pressed. */
						if (sourceButton.getText().equals(TOTAL_SCORE_BTN_TXT)) {
							if (Check_Dice()) {
								/* Total the score, and display it. */
								outputTextArea.appendText("\nPlayer " + (playerNumber + 1) + " has a score of " + Calculate_Score() + ", and has rolled the dice " +
								currentPlayerRolls + " out of " + (currentPlayerRolls + Get_Current_Players_Remaining_Roll_Attempts()) + " times.\n");
							}
							else {
								try {
									msgWin = new MessageWindow("ERROR", invalidDiceErrorMsg.toString());
								}
								catch (Exception e) {
									System.out.println("MessageWindow EXCEPTION: " + e.toString() + "\n" + invalidDiceErrorMsg + "\n");
								}
							}
						}
						else if (sourceButton.getText().equals(ROLL_DICE_BTN_TXT)) {
							/* Check and see if we need to roll the dice. */
							if ((Check_Dice()) && (Get_Current_Players_Remaining_Roll_Attempts() > 0)) {
								/* Roll the dice. */
								for (int x = 0; (x < NUMBER_OF_DICE); x++) {
									/* Check and see if we need to generate a new dice roll for this dice. */
									if ((!(diceDiscardButtons[x].isDisabled())) || (diceCount == NUMBER_OF_DICE)) {
										/* Generate the new dice values. (Value cannot be zero.) */
										do {
											diceRoll[x] = rng.nextInt(((NUMBER_OF_DICE_SIDES) + 1));
										} while (diceRoll[x] == 0);

										/* Copy the generated values to the text fields. */
										diceTextFields[x].setText(Integer.toString(diceRoll[x]));

										/* Reset the color for the text field. */
										diceTextFields[x].setStyle("-fx-background-color: white;");

										/* Enable each discard button. */
										diceDiscardButtons[x].setDisable(false);
										
										/* Enable the Calculate_Score button. */
										totalScoreButton.setDisable(false);
									}
								}

								/* Increment the currentPlayerRolls counter. */
								currentPlayerRolls++;

								/* Check the number of rolls the player has made and,
									disable the Roll Dice button if they cannot roll again. */
								if (Get_Current_Players_Remaining_Roll_Attempts() <= 0) {
										rollDiceButton.setDisable(true);
								}
							}
							else if (!Check_Dice()) {
								/* Warn player that there are more dice pairs for them to remove. */
								try {
									msgWin = new MessageWindow("ERROR", invalidDiceErrorMsg);
								}
								catch (Exception e) {
									System.out.println("MessageWindow EXCEPTION: " + e.toString() + "\n" + invalidDiceErrorMsg + "\n");
								}
							}
						}
						else if (sourceButton.getText().equals(END_TURN_BTN_TXT)) {
							/* Check and see if the player has removed all of the pairs of dice that equal BAD_DICE_TOTAL. */
							if (Check_Dice()) {
								/* Total the score, and display it. */
								playerScore[(playerNumber)] = Calculate_Score();
								outputTextArea.appendText("\nPlayer " + (playerNumber + 1) + " has a score of " + playerScore[(playerNumber)] + ".\n");

								/* Check and see if the player has the current high score.
									(We assume the player who rolled the winning score first
									keeps winning in the event of a tie.)
								*/
								if ((playerScore[(playerNumber)] != 0) &&
									((playerNumber == 0) || (playerScore[(winningPlayer)] < playerScore[(playerNumber)]))) {
									/* The player is now winning. */
									winningPlayer = playerNumber;

									/* Tell the player that they are winning. */
									outputTextArea.appendText("Player " + (playerNumber + 1) + " is now..... Winning!\n");
								}
								else {
									/* The player has lost, tell them who they lost to. */
									outputTextArea.appendText("Player " + (playerNumber + 1) + " has lost the game.\n" +
									((playerScore[(winningPlayer)] != 0) ? ("The currently winning player is:\nPlayer " +
									(winningPlayer + 1) + " with a score of " + playerScore[(winningPlayer)] + ".\n") :
									("There are no winners currently.\n")));
								}

								/* If this is the first player, we need to set the number of throws they made. */
								if (playerNumber == 0) {
									firstPlayerRolls = currentPlayerRolls;
								}

								/* Reset the number of throws made. */
								currentPlayerRolls = 0;
								
								/* Check and see if there are any players left. */
								if ((playerNumber + 1) < NUMBER_OF_PLAYERS) {
									/* Reset the gui. */
									Reset_GUI();

									/* Re-enable the Discard buttons..... why? The player has nothing to discard yet.
										Also reset the diceRoll array, and the color and text for the text fields too.
									 */
									for (int x = 0; (x < NUMBER_OF_DICE); x++) {
										diceDiscardButtons[x].setDisable(false);
										diceTextFields[x].setStyle("-fx-background-color: white;");
										diceTextFields[x].setText("");
										diceRoll[x] = 0;
									}

									/* Reset the number of dice. */
									diceCount = NUMBER_OF_DICE;

									/* Re-enable the dice roll button. */
									rollDiceButton.setDisable(false);

									/* Increment to the next player. */
									playerNumber++;
								}
								else {
									/* Game over, display the winner and the winning score. */
									outputTextArea.appendText(((playerScore[(winningPlayer)] != 0) ? ("\nPlayer " + (winningPlayer + 1) +
									" wins with a score of: " + playerScore[(winningPlayer)] + "!\n") :
									("Everyone lost because everyone rolled a score of zero....Wow.\n")));

									/* Output line border. */
									outputTextArea.appendText("\n");
									/*
										The below does not work for me,
										I keep getting a "cannot find symbol" error for the getColumns() method.
										for (int x = 0; (x < outputTextArea.getColumns()); x++) {

										So, I'm using a pre-set value instead....
									*/
									for (int x = 0; (x < FAKE_TEXT_AREA_LENGTH); x++) {
										outputTextArea.appendText("-");
									}
									outputTextArea.appendText("\n");

									/* Reset the gui. */
									Reset_GUI();

									/* Reset the game vars. */
									Reset_Game_Vars();
								}
							}
							else {
								/* Warn player that there are more dice pairs for them to remove. */
								try {
									msgWin = new MessageWindow("ERROR", invalidDiceErrorMsg);
								}
								catch (Exception e) {
									System.out.println("MessageWindow EXCEPTION: " + e.toString() + "\n" + invalidDiceErrorMsg + "\n");
								}
							}
						}
						else if (sourceButton.getText().equals(EXIT_BTN_TXT)) {
							/* Exit program. */
							guiStage.hide();
						}
						else if (sourceButton.getText().equals(DISCARD_BTN_TXT)) {
							/* Dice discard button triggered, figure out which one it was. */
							for (int x = 0; (x < NUMBER_OF_DICE); x++) {
								if (sourceButton == diceDiscardButtons[x]) {
									sourceDisableBtnIdx = x;
								}
							}

							/* Make sure the value for that field is not zero. */
							if (diceRoll[sourceDisableBtnIdx] != 0) {
								/* Clear the data from that position in the dice text field array and, change the textfield color for that dice. */
								diceTextFields[sourceDisableBtnIdx].clear();
								diceTextFields[sourceDisableBtnIdx].setStyle("-fx-background-color: red;");

								/* Disable the button. */
								sourceButton.setDisable(true);

								/* Decrement the diceCount. */
								diceCount--;

								/* Disable the roll dice button if all of the dice have been discarded. */
								if (diceCount <= 0) {
									rollDiceButton.setDisable(true);
								}
							}
						}
					}
				}

				/* Display an error message if needed. */
				if (msgWin != null) {
					msgWin.ShowMessage(guiStage);
					msgWin = null;
				}

				/* Exit funtion. */
				return;
			}
	}	/* End of SevensGameHandler. */
	
	public int Get_Current_Players_Remaining_Roll_Attempts() {
		return ((this.playerNumber == 0) ? (((MAX_THROWS - this.currentPlayerRolls) > 0) ? 
				((MAX_THROWS - this.currentPlayerRolls)) : (0)) :
				(((this.firstPlayerRolls - this.currentPlayerRolls) > 0) ? 
				(this.firstPlayerRolls - this.currentPlayerRolls) : (0)));
	}

	public int Calculate_Score() {
		/* Init ret. */
		int ret = 0;

		/* Check for valid dice array. */
		if ((this.diceRoll != null) && (this.diceDiscardButtons != null)) {
			/* Begin adding loop. */
			for (int x = 0; (x < this.NUMBER_OF_DICE); x++) {
				/* Check and see if the dice is in play. */
				if (!(this.diceDiscardButtons[x].isDisabled())) {
					ret += this.diceRoll[x];
				}
			}
		}

		/* Exit function. */
		return ret;
	}

	/*! Verifiy_Values 
		- A class used to store the state of a dice value's use during verification.
	*/
	private class Verifiy_Values {
		public boolean used;			/* Whether or not the value is used in the current set. */
		public boolean discarded;		/* Whether or not the value is already discarded by value set. */
		public boolean user_discarded;	/* Whether or not the value was discarded by the user. */

		Verifiy_Values() {
			this.used = false;
			this.discarded = false;
			this.user_discarded = false;
		}
	}

	public boolean Check_Dice() {
		/* Init ret. */
		boolean ret = true;
		boolean partOfDiscardSet;	/* Whether or not the current dice can be part of a dice set to be discarded. */
		int accumulator = 0;
		Verifiy_Values[] valueCheck = null;
		String discardString = null;
		if (this.DEBUG_MODE) {
			discardString = new String();
		}

		/* Init value check. */
		valueCheck = new Verifiy_Values[(this.NUMBER_OF_DICE)];
		for (int x = 0; (x < this.NUMBER_OF_DICE); x++) {
			valueCheck[x] = new Verifiy_Values();
		}

		/* Check for valid dice array. */
		if ((this.diceRoll != null) && (this.diceDiscardButtons != null)) {
			/* Get the values that the user has discarded. */
			for (int x = 0; (x < this.NUMBER_OF_DICE); x++) {
				if (this.diceDiscardButtons[x].isDisabled()) {
					valueCheck[x].user_discarded = true;
				}
			}

			/* Check the values that the user has discarded to see if they add up to BAD_DICE_TOTAL. */
			for (int x = 0; ((x < this.NUMBER_OF_DICE)); x++) {
				if (valueCheck[x].user_discarded) {
					/* Reset the accumulator and partOfDiscardSet. */
					accumulator = diceRoll[x];
					partOfDiscardSet = false;

					/* Set value to used. */
					valueCheck[x].used = true;

					/* Begin inner loop. */
					for (int y = ((x != 0) ? (0) : (1)); ((y < this.NUMBER_OF_DICE) && (!partOfDiscardSet)); y += (((y + 1) != x) ? (1) : (2))) {
						/* Check for a user discarded value that we have not used or discarded. */
						if ((valueCheck[y].user_discarded) && (!(valueCheck[y].used)) && (!(valueCheck[y].discarded))) {
							/* Check and see if adding the current y offset value to accumulator will result in a
								value less than or equal to BAD_DICE_TOTAL.
							*/
							if ((accumulator + diceRoll[y]) <= this.BAD_DICE_TOTAL) {
								/* Add the current value at offset y in the diceRoll array to the accumulator. */
								accumulator += this.diceRoll[y];

								/* Set the current y offset value to be used. */
								valueCheck[y].used = true;

								/* Check and see if the given accumulator value equals the BAD_DICE_TOTAL. */
								if (accumulator == this.BAD_DICE_TOTAL) {
									/* The current set equals BAD_DICE_TOTAL and must be discarded by the player, mark them as such. */
									for (int z = 0; (z < this.NUMBER_OF_DICE); z++) {
										/* If the value was used in this set, we need to set it to discarded instead of used. */
										if (valueCheck[z].used) {
											valueCheck[z].used = false;
											valueCheck[z].discarded = true;
										}
									}

									/* Set partOfDiscardSet to break the loop. */
									partOfDiscardSet = true;
								}
							}
						}
					}

					/* If the values used were not part of a discard set, then we need to mark them unused. */
					if (!partOfDiscardSet) {
						for (int z = 0; (z < this.NUMBER_OF_DICE); z++) {
							if (valueCheck[z].used) {
								valueCheck[z].used = false;
							}
						}
					}
				}
			}

			/* Now that the user discarded values are taken care of, we need to do the following: 
					- Check the remaining values to determine if they need to be discarded.
				
					- Use the values discarded by the user that do not belong to a discard set
					when checking the remaining values. (The user may have forgotten which values to use or
					may be trying to cheat.)
			 */
			for (int x = 0; ((x < this.NUMBER_OF_DICE) && (ret)); x++) {
				/* Check and see if the current x offset value is already used or discarded. */
				if ((!(valueCheck[x].used)) && (!(valueCheck[x].discarded))) {
					/* Reset the accumulator, and partOfDiscardSet. */
					accumulator = this.diceRoll[x];
					partOfDiscardSet = false;

					/* Set the current x offset value to be used. */
					valueCheck[x].used = true;

					/* Begin inner loop. */
					for (int y = ((x != 0) ? (0) : (1)); ((y < this.NUMBER_OF_DICE) && (!partOfDiscardSet)); y += (((y + 1) != x) ? (1) : (2))) {
						/* Check and see if the current y offset value is already used or discarded. */
						if ((!(valueCheck[y].used)) && (!(valueCheck[y].discarded))) {
							/* Check and see if adding the current y offset value to accumulator will result in a
								value less than or equal to BAD_DICE_TOTAL.
							*/
							if ((accumulator + diceRoll[y]) <= this.BAD_DICE_TOTAL) {
								/* Add the current value at offset y in the diceRoll array to the accumulator. */
								accumulator += this.diceRoll[y];

								/* Set the current y offset value to be used. */
								valueCheck[y].used = true;

								/* Check and see if the given accumulator value equals the BAD_DICE_TOTAL. */
								if (accumulator == this.BAD_DICE_TOTAL) {
									/* The current set equals BAD_DICE_TOTAL and must be discarded by the player, mark them as such. */
									for (int z = 0; (z < this.NUMBER_OF_DICE); z++) {
										/* If the value was used in this set, we need to set it to discarded instead of used. */
										if (valueCheck[z].used) {
											valueCheck[z].used = false;
											valueCheck[z].discarded = true;
										}
									}

									/* Set partOfDiscardSet to break the loop. */
									partOfDiscardSet = true;
								}
							}
						}
					}

					/* If the values used were not part of a discard set, then we need to mark them unused. */
					if (!partOfDiscardSet) {
						for (int z = 0; (z < this.NUMBER_OF_DICE); z++) {
							if (valueCheck[z].used) {
								valueCheck[z].used = false;
							}
						}
					}
				}
			}

			/* Output debug info if needed. */
			if (this.DEBUG_MODE) {
				System.out.println("------------------------------------------------------------------\n");
				for (int z = 0; z < this.NUMBER_OF_DICE; z++) {
					System.out.print("Player " + (playerNumber + 1) + ": Dice value " + (z + 1) + ": " + ((valueCheck[z].used) ? ("USED, ") : ("NOT USED, ")) +
					((valueCheck[z].discarded) ? ("DISCARDED, ") : ("NOT DISCARDED, ")) +
					((valueCheck[z].user_discarded) ? ("USER DISCARDED") : ("NOT USER DISCARDED")) +
					((valueCheck[z].user_discarded && !(valueCheck[z].discarded)) ? (", ERROR: ID10T\n") : ("\n")));
				}
				System.out.println("\n------------------------------------------------------------------\n");
			}

			/* Now, check and see if the values marked as discarded, have been actually discarded. */
			for (int x = 0; ((x < this.NUMBER_OF_DICE) && (ret)); x++) {
				if ((valueCheck[x].discarded) && (!(this.diceDiscardButtons[x].isDisabled()))) {
					/* The user has not discarded some values that the rules say they need to.
						So set ret to false to indicate this.
					 */
					 ret = false;
				}
			}
		}

		/* Exit function. */
		return ret;
	}

	public void Reset_GUI() {
		/* Check for valid GUI. */
		if ((this.diceTextFields != null) && (this.diceDiscardButtons != null) && (this.rollDiceButton != null) &&
		(this.totalScoreButton != null) && (this.endTurnButton != null)) {
			/* Reset the text fields. */
			for (int x = 0; (x < this.NUMBER_OF_DICE); x++) {
				this.diceTextFields[x].setText("");
				this.diceTextFields[x].setStyle("-fx-background-color: white;");
			}

			/* Reset the buttons. */
			for (int x = 0; (x < this.NUMBER_OF_DICE); x++) {
				this.diceDiscardButtons[x].setDisable(true);
			}
			this.rollDiceButton.setDisable(false);
			this.totalScoreButton.setDisable(true);
		}

		/* Exit function. */
		return;	
	}

	public void Reset_Game_Vars() {
		/* Reset the vars. */
		this.winningPlayer = 0;
		this.playerNumber = 0;
		this.currentPlayerRolls = 0;
		this.diceCount = this.NUMBER_OF_DICE;
		this.firstPlayerRolls = 0;

		/* Reset player score and diceRoll. */
		if (this.diceRoll != null) {
			for (int x = 0; (x < this.NUMBER_OF_DICE); x++) {
				this.diceRoll[x] = 0;
			}
		}
		if (this.playerScore != null) {
			for (int x = 0; (x < this.NUMBER_OF_PLAYERS); x++) {
				this.playerScore[x] = 0;
			}
		}

		/* Exit function. */
		return;	
	}

	public void Create_GUI() {
		/* Create the event handler first. */
		this.sevensGameHandler = new SevensGameHandler();

		/* Create the rng. */
		this.rng = new Random();

		/* Blank out the game related vars. */
		this.Reset_Game_Vars();

		/* Create error message for invalid dice pairs. */
		this.invalidDiceErrorMsg = new String("\n" + INVALID_DICE_TXT + BAD_DICE_TOTAL + ".\n");

		/* Create the HBoxes for the buttons and text fields. */
		this.diceGridPane = new GridPane();

		/* Create the basic arrays. */
		this.diceRoll = new int[(this.NUMBER_OF_DICE)];
		this.playerScore = new int[(this.NUMBER_OF_PLAYERS)];
		this.diceTextFields = new TextField[(this.NUMBER_OF_DICE)];
		this.diceDiscardButtons = new Button[(this.NUMBER_OF_DICE)];
		this.diceTextLabels = new Label[(this.NUMBER_OF_DICE)];

		/* Add the text labels for the dice. */
		for (int x = 0; (x < this.NUMBER_OF_DICE); x++) {
			this.diceTextLabels[x] = new Label("Dice " + (x + 1) + ":");
			this.diceGridPane.add(this.diceTextLabels[x], x, 0);
		}

		/* Create and disable the text fields, then place them in the grid pane. */
		for (int x = 0; (x < this.NUMBER_OF_DICE); x++) {
			this.diceTextFields[x] = new TextField();
			this.diceTextFields[x].setEditable(false);
			this.diceGridPane.add(this.diceTextFields[x], x, 1);
		}

		/* Create and set the text and event handler for each discard button, then place the button in it's HBox. */
		for (int x = 0; (x < this.NUMBER_OF_DICE); x++) {
			this.diceDiscardButtons[x] = new Button(this.DISCARD_BTN_TXT);
			this.diceDiscardButtons[x].setOnAction(this.sevensGameHandler);
			this.diceDiscardButtons[x].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			this.diceGridPane.add(this.diceDiscardButtons[x], x, 2);
		}

		/* Create the output text area, and disable user modification. */
		this.outputTextArea = new TextArea();
		this.outputTextArea.setEditable(false);
		this.outputTextArea.setStyle("-fx-background-color: white;");

		/* Create the user control buttons. */
		this.rollDiceButton = new Button(ROLL_DICE_BTN_TXT);
		this.totalScoreButton = new Button(TOTAL_SCORE_BTN_TXT);
		this.endTurnButton = new Button(END_TURN_BTN_TXT);
		this.exitButton = new Button(EXIT_BTN_TXT);

		/* Set the event handlers for the user control buttons. */
		this.rollDiceButton.setOnAction(this.sevensGameHandler);
		this.totalScoreButton.setOnAction(this.sevensGameHandler);
		this.endTurnButton.setOnAction(this.sevensGameHandler);
		this.exitButton.setOnAction(this.sevensGameHandler);

		/* Reset the GUI buttons and text fields to their initial state. */
		Reset_GUI();

		/* Create the user control button HBox. */
		this.userControlBtnsHBox = new HBox();

		/* Add the buttons to the HBox. */
		this.userControlBtnsHBox.getChildren().addAll(this.rollDiceButton, this.totalScoreButton, this.endTurnButton, this.exitButton);

		/* Create the scene root node. */
		this.guiGridPane = new GridPane();

		/* Add the HBoxes and output TextArea to the root node. */
		this.guiGridPane.add(this.diceGridPane, 0, 0);
		this.guiGridPane.add(this.outputTextArea, 0, 1);
		this.guiGridPane.add(this.userControlBtnsHBox, 0, 2);

		/* Create the Scene and Stage. */
		this.guiScene = new Scene(this.guiGridPane);
		this.guiStage = new Stage();

		/* Set the stage. */
		this.guiStage.setScene(this.guiScene);
		this.guiStage.setTitle(WINDOW_TITLE_TXT);

		/* This sets the the modality for the pop-up window.
			Basicly, it makes the pop-up window's parent window
			(the main window in this case) not accept input until
			the pop-up window closes. */
		this.guiStage.initModality(Modality.WINDOW_MODAL);

		/* Exit function. */
		return;

	}	/* End of Create_GUI(). */

	@Override
	public void start(Stage primaryStage) {
		/* Call Create_GUI(). */
		Create_GUI();

		/* Check for valid gui stage. */
		if (this.guiStage != null) {
			this.guiStage.showAndWait();
		}

		/* Exit function. */
		return;

	}	/* End of start(). */

}	/* End of SevensGameGUI. */
