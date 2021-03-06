/**
	Class BookStoreGUI version 0.4 represents a book store.
	It gives the user the option to
		- add a book to the store's inventory
		- list all books in the store's inventory
		- search for a specific book
		
	Author: Patrick Hibbs
	E-mail address: phibbs0003@kctcs.edu
	Last changed: 10/26/2015
	Lab 01
*/
import java.util.Scanner;
import javafx.application.Application;	
import javafx.event.ActionEvent;	
import javafx.event.EventHandler;	
import javafx.stage.Stage;			
import javafx.scene.Scene;			
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

public class BookStoreGUI extends Application {
	
	static Catalog storeCatalog = new Catalog();

	public static final int ADD_ITEM = 0;
	public static final int SHOW_ITEM_LIST = 1;
	public static final int SEARCH = 2;
	public static final int QUIT = 3;
	
	public static final int BOOK = 4;
	public static final int CD = 5;
	public static final int DVD = 6;
	public static final int CANCEL = 7; 

	static int count = 0;

	static Scanner keyboard = new Scanner(System.in);
	
	/* GUI components. */
	Label titleLabel;
	Label authorLabel;
	Label priceLabel;
	TextField titleTextField;
	TextField authorTextField;
	TextField priceTextField;
	ToggleGroup radionBtnToggleGroup;
	RadioButton bookRadioButton;
	RadioButton cdRadioButton;
	RadioButton dvdRadioButton;
	Button addItemButton;
	Button showInventoryBtn;
	GridPane sceneRootNode;
	GridPane inputFieldGP;
	VBox radionBtnVBox;
	HBox buttonHBox;
	HBox userInputHBox;
	TextArea outputTextArea;
	BookStoreHandler bookStoreHandler;
	Stage guiStage;
	Scene guiScene;
	final static String SHOW_INVENTORY_STR = "Show Inventory";
	final static String ADD_ITEM_STR = "Add Item";
	
	/**************************************************
		class BookStoreHandler
			* Private event handler class for BookStoreGUI. (Handles button events for the add item and show inventory buttons.)
			
		Author: Patrick Hibbs
		E-mail: phibbs0003@kctcs.edu
		Last changed: 10/26/2015
		Lab 01
	***************************************************/
	private class BookStoreHandler implements EventHandler<ActionEvent> {
		@Override
			public void handle(ActionEvent event) {
				/* Declare vars. */
				Object sourceObject;			/* Object pointer to the object that created the event. */
				Button sourceButton;			/* Pointer to the button obejct that was clicked. */
				BookStoreItem newItem = null;	/* Pointer for a bookstore item. */
				
				/* Get the source of the event. */
				sourceObject = event.getSource();
				if (sourceObject != null) {
					/* Determine what the source object is. */
					if (sourceObject instanceof Button) {
						/* Cast the pointer. */
						sourceButton = (Button)sourceObject;
						
						/* OK now we need to determine if the button that got clicked on was the addItem or showInventory button. */
						if (sourceButton.getText().equals(ADD_ITEM_STR)) {
							/* Adding the item. */
							try {
								newItem = ((bookRadioButton.isSelected()) ? (new Book(titleTextField.getText(), authorTextField.getText(), Double.parseDouble(priceTextField.getText().trim()), 0)) : 
								 (cdRadioButton.isSelected()) ? (new CD(titleTextField.getText(), authorTextField.getText(), Double.parseDouble(priceTextField.getText().trim()), 0)) :
								 (dvdRadioButton.isSelected()) ? (new DVD(titleTextField.getText(), authorTextField.getText(), Double.parseDouble(priceTextField.getText().trim()), 0)) :
								 (null));
							}
							catch (Exception e) {
								if (e instanceof java.lang.NumberFormatException) {
									outputTextArea.appendText("\nINPUT ERROR: You can't insert non-numeric characters into the Price field.\n");
								}
								else {
									/* We didn't handle the exception so rethrow it. */
									throw e;
								}
							}
							
							/* Make sure we got a new item, before adding it. */
							if (newItem != null) {
								storeCatalog.add(newItem);
								outputTextArea.appendText("\nItem added successfully.\n");
							}
							else{
								outputTextArea.appendText("\nCould not add unknown / invalid item to inventory.\n");
							}
						}
						else if (sourceButton.getText().equals(SHOW_INVENTORY_STR)) {
							/* Init vars. */
							BookStoreItem[] list = storeCatalog.getList();
		
							/* Showing the inventory. */
							for (int i = 0; ((i < list.length) && (list[i] != null)); i++) {
								outputTextArea.appendText("\nItem " + (i + 1) + ":\n" + list[i]);
							}
						}
					}
				}
				
				/* Exit funtion. */
				return;
			}
	}
	
	public void CreateGUIObjects(Stage primaryStage) {
		/* Create containers and spacing for the input fields and radio buttons. */
		this.buttonHBox = new HBox(15);
		this.inputFieldGP = new GridPane();
		this.inputFieldGP.setHgap(15);
		this.inputFieldGP.setVgap(15);
		this.radionBtnVBox = new VBox(25);
		this.userInputHBox = new HBox(15);
		
		/* Create the buttons. */
		this.addItemButton = new Button();
		this.addItemButton.setText(ADD_ITEM_STR);
		
		this.showInventoryBtn = new Button();
		this.showInventoryBtn.setText(SHOW_INVENTORY_STR);
		
		/* Create the labels and text fields for the user's input. */
		this.titleLabel = new Label("Title: ");
		this.authorLabel = new Label("Author: ");
		this.priceLabel = new Label("Price: ");
		this.titleTextField = new TextField();
		this.authorTextField = new TextField();
		this.priceTextField = new TextField();
		
		/* Create the radiobutton's toggle group. */ 
		this.radionBtnToggleGroup = new ToggleGroup();
		
		/* Create the radio buttons for the type of media. */
		this.bookRadioButton = new RadioButton("Book");
		this.cdRadioButton = new RadioButton("CD");
		this.dvdRadioButton = new RadioButton("DVD");

		/* Set the toggle group for the radio buttons. */
		this.bookRadioButton.setToggleGroup(this.radionBtnToggleGroup);
		this.cdRadioButton.setToggleGroup(this.radionBtnToggleGroup);
		this.dvdRadioButton.setToggleGroup(this.radionBtnToggleGroup);
		
		/* Set the default selection for the radio buttons. */
		this.bookRadioButton.setSelected(true);
		
		/* Create the text area for the window. */
		this.outputTextArea = new TextArea();
		
		/* Disable editing by the user. */
		this.outputTextArea.setEditable(false);
		
		/* Create the window's stage and root node (In this case the root node is a GridPane.) */
		this.guiStage = new Stage();
		this.sceneRootNode = new GridPane();
		
		/* Create the window's scene. 
			(Requires the root node to be passed to it, and must 
			exist prior to creating the Scene object.) */
		this.guiScene = new Scene(this.sceneRootNode);
		
		/* Set the title bar text for the window. */
		this.guiStage.setTitle("BookStoreGUI");
		
		/* Set the window's scene. */
		this.guiStage.setScene(this.guiScene);
		
		/* This sets the the modality for the pop-up window.
			Basicly, it makes the pop-up window's parent window 
			(the main window in this case) not accept input until
			the pop-up window closes. */
		this.guiStage.initModality(Modality.WINDOW_MODAL);
		
		/* Exit function. */
		return;
	}
	
	public void ConstructGUIStage(Stage primaryStage) {
		
		/* Add labels and text fields to their grid pane. */
		this.inputFieldGP.add(this.titleLabel, 0, 0);
		this.inputFieldGP.add(this.authorLabel, 0, 1);
		this.inputFieldGP.add(this.priceLabel, 0, 2);
		this.inputFieldGP.add(this.titleTextField, 1, 0);
		this.inputFieldGP.add(this.authorTextField, 1, 1);
		this.inputFieldGP.add(this.priceTextField, 1, 2);
		
		/* Set the Radio buttons into their VBox. */
		this.radionBtnVBox.getChildren().addAll(this.bookRadioButton, 
												this.cdRadioButton,
												this.dvdRadioButton);
		
		/* Set the VBox and gridpane into the userInputHBox. */
		this.userInputHBox.getChildren().addAll(this.inputFieldGP, this.radionBtnVBox);
		
		/* Set the userInputHBox into the main window's root node. */
		this.sceneRootNode.add(this.userInputHBox, 0, 0);
		
		/* Add the text area to the window. */
		this.sceneRootNode.add(this.outputTextArea, 0, 1);
		
		/* Add the addItemButton and showInventoryBtn buttons to the buttonHBox. */
		this.buttonHBox.getChildren().addAll(this.addItemButton, this.showInventoryBtn);
		
		/* Add the buttonHBox to the root node. */
		this.sceneRootNode.add(this.buttonHBox, 0, 2);
		
		/* Exit function. */
		return;
	}
	
	public void CreateEventHandler() {
		/* Create the BookStoreHandler. */
		this.bookStoreHandler = new BookStoreHandler();
		
		/* Set the handler, if we can. */
		if (this.addItemButton != null) {
			this.addItemButton.setOnAction(this.bookStoreHandler);
		}
		if (this.showInventoryBtn != null) {
			this.showInventoryBtn.setOnAction(this.bookStoreHandler);
		}
		
		/* Exit function. */
		return;
	}
	
	@Override
	public void start(Stage primaryStage) {
		
		/* Create the GUI objects. */
		this.CreateGUIObjects(primaryStage);
		
		/* Construct the stage. */
		this.ConstructGUIStage(primaryStage);
		
		/* Create the event handler. */
		this.CreateEventHandler();
		
		/* Show the stage. */
		if (this.guiStage != null) {
			this.guiStage.showAndWait();
		}
		
		/* Exit function. */
		return;
	}
}


	
	
