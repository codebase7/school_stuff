/***************************************************************
	class MessageWindow
	Author: Patrick Hibbs
	Last modified: 11/27/2015
	phibbs0003@kctcs.edu
***************************************************************/

/* Import the needed JavaFX components. */
import javafx.application.Application;	/* Application container. */
import javafx.event.ActionEvent;	/* Event from GUI. */
import javafx.event.EventHandler;	/* Call back handler for events from GUI. */
import javafx.stage.Stage;			/* Window. */
import javafx.scene.Scene;			/* Top level container for window objects. */
import javafx.scene.layout.BorderPane;	/* Container for holding MULTIPLE window objects. */
import javafx.geometry.Insets;			/* Used to compute margins for the border pane. */
import javafx.scene.control.Button;	/* GUI Button. */
import javafx.stage.Modality;		/* Window control for stopping the parent window from accepting input. */
import javafx.scene.control.Label;		/* Contains the message to display. */
import java.lang.Exception;				/* Exceptions for bad programmers. */

public class MessageWindow {

/* Declare class level vars. */

 private Boolean isShown;		/* Whether or not we have been shown. */
 private Stage messageStage;		/* Stage for the message. */
 private Scene messageScene;		/* The scene for the message. */
 private BorderPane messageBP;	/* The borderpane for the message. */
 private Button messageOKBtn;	/* The ok (dismiss) button. */
 private Label messageLBL;	/* The text for the window to display. */

MessageWindow(final String messageTitle, final String messageText) throws Exception {
	/* Check for valid data. */
	if ((messageText.length() > 0) && (messageTitle.length() > 0)) {
		this.CreateMessage(messageTitle, messageText, false);
	}
	else {
		throw new Exception("Invalid arguments to MessageWindow.");
	}

	/* Exit function. */
	return;
}

MessageWindow(final String messageTitle, final String messageText, final boolean usePrefSize) throws Exception {
	/* Check for valid data. */
	if ((messageText.length() > 0) && (messageTitle.length() > 0)) {
		this.CreateMessage(messageTitle, messageText, usePrefSize);
	}
	else {
		throw new Exception("Invalid arguments to MessageWindow.");
	}

	/* Exit function. */
	return;
}

/* Method for creating the pop-up window. */
	public void CreateMessage(final String messageTitle, final String messageText, final boolean usePrefSize) {
		/* Create the boolean. */
		this.isShown = new Boolean(false);

		/* Create the pop-up window's stage and root node (In this case the root node is a BorderPane.) */
		this.messageStage = new Stage();
		this.messageBP = new BorderPane();

		/* Create the pop-up window's scene. 
			(Requires the root node to be passed to it, and must 
			exist prior to creating the Scene object.) */
		if (usePrefSize) {
			this.messageScene = new Scene(this.messageBP);
		}
		else {
			this.messageScene = new Scene(this.messageBP, 200, 200);
		}

		/* Set the title bar text for the pop-up window. */
		this.messageStage.setTitle(messageTitle);

		/* Create the Button to display in the pop-up window. */
		this.messageOKBtn = new Button();

		/* Set the text to display on the button in the pop-up window. */
		this.messageOKBtn.setText("OK");

		/* Set the button to be the default. */
		this.messageOKBtn.setDefaultButton(true);

		/* Set the event handler for the pop-up window's button.
			This is what allows the button to do something. */
		this.messageOKBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/* All this button does is close the window, but we could do something else here.
					we could even choose to do something different depending on the type of event
					that was passed to us. */
				messageStage.close();
			}
		});

		/* Set the margins for the messageOKBtn. */
		this.messageBP.setMargin(this.messageOKBtn, (new Insets(12)));

		/* Create the messageText object. */
		this.messageLBL = new Label(messageText);
		this.messageBP.setCenter(this.messageLBL);
		this.messageBP.setMargin(this.messageLBL, (new Insets(12)));

		/* Add the pop-up window's button to the pop-up window's border plane. */
		this.messageBP.setBottom(this.messageOKBtn);

		/* Set the pop-up window's scene. */
		this.messageStage.setScene(this.messageScene);

		/* This sets the the modality for the pop-up window.
			Basicly, it makes the pop-up window's parent window 
			(the main window in this case) not accept input until
			the pop-up window closes. */
		this.messageStage.initModality(Modality.WINDOW_MODAL);

		/* Exit function. */
		return;
	}

	public void ShowMessage() {
		/* Call the real function with a NULL pointer. */
		this.ShowMessage(null);

		/* Exit function. */
		return;
	}	/* End of show message */

	public void ShowMessage(Stage parentWindow) {
		/* Only show the stage if it's initialized. */
		if (this.messageStage != null) {
			/* Set the window owner if needed. */
			if ((parentWindow != null) && (!(this.isShown))) {
				/* Set the window owner. */
				this.messageStage.initOwner(parentWindow);
			}

			/* Disallow setting the message window owner if we are shown. */
			if (!(this.isShown)) {
				this.isShown = true;
			}

			/* Show the window. */
			this.messageStage.showAndWait();
		}

		/* Exit function. */
		return;
	}	/* End of show message. */

}	/* End of MessageWindow */
