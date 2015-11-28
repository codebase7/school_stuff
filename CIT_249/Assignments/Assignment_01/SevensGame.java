/************************************
	class SevensGame
		- Impliments the dice game "Sevens" in a java class.
		
		- Default behvaior is to automaticly clean up the dice (check for dice that add up to the value defined by BAD_DICE_TOTAL),
		 and end a players turn if only the number of players is given to the constructor.
		
		- Note about the state of the class when the game is over:
			When the game has ended, the current player will be (the number of players - 1),
			and the remaining number of rolls for that player will be zero. The winning player
			and score will be only updated automaticly, if the game has been set to auto end a
			player's turn. If the game has been set to end a player's turn manually, you will
			need to call Declare_Winner() before calling Get_Winning_Player / Score().
			
	Author: Patrick Hibbs
	Last changed: 10/28/2015
	E-mail: phibbs0003@kctcs.edu
	Assignment 01
************************************/

import java.util.Random;

public class SevensGame {
	/* Declare constant vars. */
	final static int MAX_DICE_ROLLS = 3;
	final static int BAD_DICE_TOTAL = 7;
	final static int NUMBER_OF_DICE = 6;
	final static int NUMBER_OF_DICE_SIDES = 6;
	
	/* Declare normal vars. */
	int dice[];
	int playerTotals[];
	int numberOfPlayers;
	int numberOfFirstPlayerDiceRolls;
	int currentPlayer;
	int currentPlayerRollAttempts;
	int winningPlayer;
	int winningScore;
	boolean autoEndTurn;		/* Whether or not to end the player's turn automaticly. */
	boolean autoDiceCleanup;	/* Whether or not to check the dice for bad pairs automaticly. */
	Random rnd;
	
	public SevensGame(int numberOfPlayers) {
		this(numberOfPlayers, true, true);	
	}
	
	public SevensGame(int numberOfPlayers, boolean autoEndTurn, boolean autoDiceCleanup) {
		if (numberOfPlayers > 0) {
			this.numberOfPlayers = numberOfPlayers;
			this.playerTotals = new int[(this.numberOfPlayers)];
			this.numberOfFirstPlayerDiceRolls = 0;
			this.currentPlayer = 0;
			this.currentPlayerRollAttempts = 0;
			this.winningPlayer = 0;
			this.winningScore = 0;
			this.dice = new int[NUMBER_OF_DICE];
			this.rnd = new Random();
			this.autoEndTurn = autoEndTurn;
			this.autoDiceCleanup = autoDiceCleanup;
		}
	}
	
	public void Reset_Game() {
		/* Check for valid arrays. */
		if (this.dice != null) {
			for (int x = 0; (x < NUMBER_OF_DICE); x++) {
				this.dice[x] = 0;	
			}
		}
		if (this.playerTotals != null) {
			for (int x = 0; (x < this.numberOfPlayers); x++) {
				this.playerTotals[x] = 0;	
			}	
		}
		
		/* Reset the other vars. */
		this.winningPlayer = 0;
		this.winningScore = 0;
		this.currentPlayer = 0;
		this.currentPlayerRollAttempts = 0;
		this.numberOfFirstPlayerDiceRolls = 0;
	
		/* Exit function. */
		return;
	}
	
	public boolean Check_Dice(boolean clean) {
		/* Init vars. */
		boolean ret = true;

		/* Check for valid dice array. */
		if (this.dice != null) {
			for (int x = 0; ((x < NUMBER_OF_DICE) && (ret)); x++) {
				for (int y = 0; ((y < NUMBER_OF_DICE) && (ret)); y++) {
					if ((this.dice[x] + this.dice[y]) == BAD_DICE_TOTAL) {
						/* Are we cleaning up or erroring out? */
						if (clean) {
							/* Remove this dice pair from play. */
							this.dice[x] = 0;
							this.dice[y] = 0;
						}
						else {
							/* They did not clean up correctly! */
							ret = false;
						}
					}
				}
			}
		}
		
		/* Exit function. */
		return ret;
	}
	
	public void Reset_Dice() {
		/* Check for valid dice array. */
		if (this.dice != null) {
			for (int x = 0; (x < NUMBER_OF_DICE); x++) {
				this.dice[x] = 0;
			}
		}
		
		/* Exit function. */
		return;
	}
	
	public void Total_Current_Players_Score() {
		/* Check for valid state. */
		if ((this.playerTotals != null) && (this.dice != null) && (this.currentPlayer < this.numberOfPlayers) && (this.Check_Dice(this.autoDiceCleanup))) {
			/* Set the tally for this player. */
			for (int x = 0; (x < NUMBER_OF_DICE); x++) {
				this.playerTotals[(this.currentPlayer)] += this.dice[x];
			}
		}
		
		/* Exit function. */
		return;
	}
	
	public boolean End_Turn() {
		/* Init vars. */
		boolean success = false;
		
		/* Check for valid state. */
		if ((this.playerTotals != null) && (this.dice != null) && (this.currentPlayer < this.numberOfPlayers)) {
			/* Check for clean dice. */
			success = this.Check_Dice(this.autoDiceCleanup);
			if (success) {
				/* Check and see if we need to set first player's number of attmepted rolls. */
				if (this.currentPlayer == 0) {
					this.numberOfFirstPlayerDiceRolls = this.currentPlayerRollAttempts;
				}
				
				/* Calculate the score. */
				this.Total_Current_Players_Score();

				/* Check and see if this was the last player. */
				if ((this.currentPlayer + 1) >= this.numberOfPlayers) {
					/* Set the player's number of attempted rolls to it's maximum. (So they can't roll again after ending their turn.) */
					this.currentPlayerRollAttempts += this.Get_Current_Players_Remaining_Roll_Attempts();
					
					/* Determine the winner. */
					this.Declare_Winner();
				}
				else {
					/* Change to the next player. */
					this.currentPlayer++;
	
					/* Reset the number of attempted rolls. */
					this.currentPlayerRollAttempts = 0;
					
					/* Reset the dice. */
					this.Reset_Dice();
				}
			}
		}
		
		/* Exit function. */
		return success;
	}

	public void Roll_Dice() {
		/* Make sure that we have a player to roll for, and that they have remaining attempts. */
		if ((this.dice != null) && (this.playerTotals != null) && (this.currentPlayer < this.numberOfPlayers) &&
			(this.Get_Current_Players_Remaining_Roll_Attempts() > 0)) {

			/* Roll the dice. */
			for (int x = 0; (x < NUMBER_OF_DICE); x++) {
				this.dice[x] = (rnd.nextInt(NUMBER_OF_DICE_SIDES) + 1);
			}

			/* Increase the number of rolls for this player. */
			this.currentPlayerRollAttempts++;
			
			/* Check and see if we need to end the player's turn. */
			if (((this.Get_Current_Players_Remaining_Roll_Attempts()) <= 0) && (this.autoEndTurn)) {
				/* End the player's turn. */
				this.End_Turn();
			}
		}
		
		/* Exit function. */
		return;
	}

	public void Declare_Winner() {
		/* Check for a valid playerTotals array. */
		if (this.playerTotals != null) {	
			for (int x = 0; (x < this.numberOfPlayers); x++) {
				/* Check for a higher score. */	
				if (this.winningScore < this.playerTotals[x]) {
					this.winningScore = this.playerTotals[x];
					this.winningPlayer = x;
				}
				/* In the event of a tie, the player who rolled the winning score first wins. */
			}
		}
		
		/* Exit function. */
		return;
	}

	/* Accessor functions. */
	public int[] Get_Current_Dice() {
		return this.dice;
	}

	public int Get_Current_Player() {
		return this.currentPlayer;
	}

	public int Get_Current_Players_Remaining_Roll_Attempts() {
		return (this.currentPlayer == 0) ? (((MAX_DICE_ROLLS - this.currentPlayerRollAttempts) > 0) ? 
				((MAX_DICE_ROLLS - this.currentPlayerRollAttempts)) : (0)) :
				(((this.numberOfFirstPlayerDiceRolls - this.currentPlayerRollAttempts) > 0) ? 
				(this.numberOfFirstPlayerDiceRolls - this.currentPlayerRollAttempts) : (0));
	}

	public int Get_Current_Players_Number_Of_Roll_Attempts() {
		return this.currentPlayerRollAttempts;	
	}

	public int Get_Winning_Player() {
		return this.winningPlayer;
	}

	public int Get_Winning_Score() {
		return this.winningScore;
	}

}	/* End of SevensGame */
