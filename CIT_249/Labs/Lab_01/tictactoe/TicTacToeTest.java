/**
	class TicTacToeTest
		-  Test program for the TicTacToeGrid class.
	Author: Patrick Hibbs
	E-mail address: phibbs0003@kctcs.edu
	Last changed: 10/26/2015
	Lab 01
*/

/* Import needed components. */
import javafx.application.Application;
import javafx.stage.Stage;

public class TicTacToeTest extends Application {
	@Override
	public void start(Stage primaryStage) {
		/* Init vars. */
		TicTacToeGrid grid;
		
		/* Create the TicTacToeGrid. */
		grid = new TicTacToeGrid();
		
		/* Show the stage. */
		grid.showAndWait();
		
		/* Exit program. */
		return;
	}
	
} /* End of TicTacToeTest */
