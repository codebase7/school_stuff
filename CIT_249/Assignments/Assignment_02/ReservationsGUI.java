/***************************************************************
	class ReservationsGUI
	Author: Patrick Hibbs
	Last modified: 11/14/2015
	phibbs0003@kctcs.edu
	Assignment 02
***************************************************************/

/* Import the needed JavaFX components. */
import javafx.application.Application;	/* Application container. */
import javafx.event.ActionEvent;	/* Event from GUI. */
import javafx.event.EventHandler;	/* Call back handler for events from GUI. */
import javafx.stage.Stage;			/* Window. */
import javafx.scene.Scene;			/* Top level container for window objects. */
import javafx.scene.layout.VBox;		/* Container for holding MULTIPLE window objects. */
import javafx.scene.layout.HBox;		/* Container for holding MULTIPLE window objects. */
import javafx.scene.layout.GridPane;	/* Container for holding MULTIPLE window objects. */
import javafx.scene.control.Label;		/* GUI Labels. */
import javafx.scene.control.RadioButton; /* GUI radio button. */
import javafx.scene.control.ToggleGroup; /* Used to control selections. */
import javafx.geometry.Insets;			/* Used to compute margins for the border pane. */
import javafx.scene.control.Button;	/* GUI Button. */
import javafx.stage.Modality;		/* Window control for stopping the parent window from accepting input. */
import javafx.scene.control.TextField;		/* Contains user input. */
import javafx.scene.control.TextArea;		/* Contains output messages. */
import javafx.scene.text.Font;			/* Used to create fonts. */
import javafx.scene.control.ScrollPane; /* Used to create scrollable controls. */
import java.lang.Exception;				/* Exceptions for bad programmers. */

public class ReservationsGUI extends Application {
	/* Finals. */
	private final static int MAX_TICKETS = 1000;
	private final static String MAIN_WINDOW_TITLE_STR = "ReservationsGUI: Main Menu";
	private final static String SEARCH_WINDOW_TITLE_STR = "ReservationsGUI: Search Flight";
	private final static String RESERVE_WINDOW_TITLE_STR = "ReservationsGUI: Reserve Flight";
	private final static String RESERVE_SEAT_WINDOW_TITLE_STR = "ReservationsGUI: Reserve seat for flight: ";
	private final static String CANCEL_FLIGHT_WINDOW_TITLE_STR = "ReservationsGUI: Cancel Reservation";
	private final static String SEARCH_FLIGHT_RBTN_STR = "Search for a flight";
	private final static String RESERVE_FLIGHT_RBTN_STR = "Reserve a seat";
	private final static String CANCEL_FLIGHT_RBTN_STR = "Cancel your reservation";
	private final static String EXIT_PROGRAM_RBTN_STR = "Exit program";
	private final static String NOTE_FLIGHT_MSG = "Be sure to note the flight number for reserving a flight.\n\nAlternatively, use the new reserve button to automatically\ngo to the reserve window for the flight that was found.";
	private final static String CLOSE_BTN_TXT = "Close window";
	private final static String CLEAR_BTN_TXT = "Clear input";
	private final static String SEARCH_BTN_TXT = "Search";
	private final static String RESERVE_FLIGHT_BTN_TXT = "Reserve Flight";
	private final static String RESERVE_SEAT_BTN_TXT = "Reserve Seat";
	private final static String SEARCH_ORIGIN_LABEL_TXT = "Flight's city of origin:	";
	private final static String SEARCH_DEST_LABEL_TXT = "Flight's city of destination:	";
	private final static String FLIGHT_NUMBER_LABEL_TXT = "Flight Number:	";
	private final static String PASSENGER_NAME_LABEL_TXT = "Passenger Name:	";
	private final static String CHOOSE_SEAT_LABEL_TXT = "Please select the seats you want to reserve.";
	private final static String TICKET_NUMBER_LABEL_TXT = "Ticket Number: ";
	private final static String CANCEL_FLIGHT_BTN_TXT = "Cancel Reservation";

	/* Instance vars. */
	Flight flights[];
	Ticket ticket[];
	int numberOfCreatedTickets;

	/* Main Window GUI vars. */
	private Stage mainStage;
	private Scene mainScene;
	private VBox mainVBox;
	private ToggleGroup mainToggleGroup;
	private RadioButton searchFlightRBtn;
	private RadioButton reserveFlightRBtn;
	private RadioButton cancelFlightRBtn;
	private RadioButton closeRBtn;

	/* Search Flight Window GUI vars. */
	private Stage searchStage;
	private Scene searchScene;
	private TextField searchOriginTextField;
	private TextField searchDestTextField;
	private TextArea searchOutputTextArea;
	private Button searchWindowSearchBtn;
	private Button searchWindowClearBtn;
	private Button searchWindowReserveBtn;
	private Button searchWindowCloseBtn;
	private VBox searchVBox;
	private HBox searchUserButtonsHBox;
	private GridPane searchTextFieldsGP;
	private Label searchOriginLabel;
	private Label searchDestLabel;
	private String searchResult;

	/* Reserve Flight Window GUI vars. */
	private Stage reserveStage;
	private Scene reserveScene;
	private GridPane reserveTextFieldsGP;
	private Label reserveFlightNumberLabel;
	private Label reservePassengerNameLabel;
	private TextField reserveFlightNumberTxtField;
	private TextField reservePassengerNameTxtField;
	private TextArea reserveOutputTextArea;
	private HBox reserveUserInputHBox;
	private Button reserveWindowReserveSeatBtn;
	private Button reserveWindowReserveFirstBtn;
	private Button reserveWindowClearBtn;
	private Button reserveWindowCloseBtn;
	private VBox reserveVBox;

	/* Reserve Seat Window GUI vars. */
	private Stage reserveSeatStage;
	private Scene reserveSeatScene;
	private Label reserveSeatInstLabel;
	private ScrollPane reserveSeatBtnsSP;
	private GridPane reserveSeatBtnsGP;
	private Button reserveSeatBtns[][];
	private Button reserveSeatCloseBtn;
	private HBox reserveSeatUserInputHBox;
	private VBox reserveSeatVBox;
	private int reserveSeatFlightIDX;

	/* Cancel Reservation Window GUI vars. */
	private Stage cancelStage;
	private Scene cancelScene;
	private VBox cancelVBox;
	private Label cancelTicketNumberLabel;
	private TextField cancelTicketNumberTextField;
	private HBox cancelTicketHBox;
	private TextArea cancelOutputTextArea;
	private Button cancelWindowCancelBtn;
	private Button cancelWindowClearBtn;
	private Button cancelWindowCloseBtn;
	private HBox cancelUserInputHBox;

	/* Create the GUI stages. */
	private void Create_MainWindow() {
		/* Create the ToggleGroup. */
		this.mainToggleGroup = new ToggleGroup();

		/* Create the Radio Buttons. */
		this.searchFlightRBtn = new RadioButton(SEARCH_FLIGHT_RBTN_STR);
		this.reserveFlightRBtn = new RadioButton(RESERVE_FLIGHT_RBTN_STR);
		this.cancelFlightRBtn = new RadioButton(CANCEL_FLIGHT_RBTN_STR);
		this.closeRBtn = new RadioButton(EXIT_PROGRAM_RBTN_STR);
		this.searchFlightRBtn.setFont(new Font(20));
		this.reserveFlightRBtn.setFont(new Font(20));
		this.cancelFlightRBtn.setFont(new Font(20));
		this.closeRBtn.setFont(new Font(20));
		this.searchFlightRBtn.setToggleGroup(this.mainToggleGroup);
		this.reserveFlightRBtn.setToggleGroup(this.mainToggleGroup);
		this.cancelFlightRBtn.setToggleGroup(this.mainToggleGroup);
		this.closeRBtn.setToggleGroup(this.mainToggleGroup);

		/* Set the event handler for searchFlightRBtn.
			This is what allows the button to do something. */
		this.searchFlightRBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mainStage.close();
				if (searchStage != null) {
					searchStage.showAndWait();
				}
				searchFlightRBtn.setSelected(false);
			}
		});

		/* Set the event handler for reserveFlightRBtn.
			This is what allows the button to do something. */
		this.reserveFlightRBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mainStage.close();
				if (reserveStage != null) {
					reserveStage.showAndWait();
				}
				reserveFlightRBtn.setSelected(false);
			}
		});

		/* Set the event handler for cancelFlightRBtn.
			This is what allows the button to do something. */
		this.cancelFlightRBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mainStage.close();
				if (cancelStage != null) {
					cancelStage.showAndWait();
				}
				cancelFlightRBtn.setSelected(false);
			}
		});

		/* Set the event handler for closeRBtn.
			This is what allows the button to do something. */
		this.closeRBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mainStage.close();
				closeRBtn.setSelected(false);
			}
		});

		/* Create the main window's VBox. */
		this.mainVBox = new VBox();
		this.mainVBox.getChildren().addAll(	this.searchFlightRBtn,
											this.reserveFlightRBtn,
											this.cancelFlightRBtn,
											this.closeRBtn);

		/* Create the Stage and Scene. */
		this.mainStage = new Stage();
		this.mainScene = new Scene(this.mainVBox);
		this.mainStage.setTitle(this.MAIN_WINDOW_TITLE_STR);
		this.mainStage.setScene(this.mainScene);

		/* Exit function. */
		return;
	}

	private void Create_SearchWindow() {
	
		/* Create the text field labels. */
		this.searchOriginLabel = new Label(SEARCH_ORIGIN_LABEL_TXT);
		this.searchDestLabel = new Label(SEARCH_DEST_LABEL_TXT);

		/* Create the input text fields. */
		this.searchOriginTextField = new TextField();
		this.searchDestTextField = new TextField();

		/* Create the input text fields GridPane. */
		this.searchTextFieldsGP = new GridPane();
		this.searchTextFieldsGP.add(this.searchOriginLabel, 0, 0);
		this.searchTextFieldsGP.add(this.searchOriginTextField, 1, 0);
		this.searchTextFieldsGP.add(this.searchDestLabel, 0, 1);
		this.searchTextFieldsGP.add(this.searchDestTextField, 1, 1);

		/* Create the output text field. */
		this.searchOutputTextArea = new TextArea();
		this.searchOutputTextArea.setEditable(false);

		/* Create the user input buttons. */
		this.searchWindowSearchBtn = new Button(SEARCH_BTN_TXT);
		this.searchWindowClearBtn = new Button(CLEAR_BTN_TXT);
		this.searchWindowReserveBtn = new Button(RESERVE_FLIGHT_BTN_TXT);
		this.searchWindowCloseBtn = new Button(CLOSE_BTN_TXT);

		/* Disable the Reserve button by default. (Until we find a flight there is nothing to reserve.) */
		this.searchWindowReserveBtn.setDisable(true);

		/* Set the event handler for searchWindowSearchBtn.
			This is what allows the button to do something. */
		this.searchWindowSearchBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/* Init vars. */
				boolean done = false;

				/* Check for valid vars. */
				if (((((searchOriginTextField.getText()).trim()).length()) > 0) &&
				((((searchDestTextField.getText()).trim()).length()) > 0)) {
					/* Search the flights. */
					for (int x = 0; ((x < flights.length) && (!done)); x++) {
						if (flights[x] != null) {
							if (flights[x].getOrigin().equals(((searchOriginTextField.getText()).trim()))) {
								if (flights[x].getDestination().equals(((searchDestTextField.getText()).trim()))) {
									/* Found a flight, print the flight number, fare and note message. */
									searchOutputTextArea.appendText("\nA flight from " + 
									flights[x].getOrigin() + " to " +
									flights[x].getDestination() + " has been found.\nFlight Number: " +
									flights[x].getFlightNumber() + "\nFare: $" +
									String.format("%.2f", flights[x].getFare()) + "\n" +
									NOTE_FLIGHT_MSG);

									/* Copy the flight number to the searchResult string. */
									searchResult = flights[x].getFlightNumber();

									/* Enable the reserve button. */
									searchWindowReserveBtn.setDisable(false);

									/* We are done searching for flights. */
									done = true;
								}
							}
						}
					}

					/* Check and see if we found a flight. */
					if (!done) {
						searchOutputTextArea.appendText("\nWe're sorry, a flight from " +
						(((searchOriginTextField.getText()).trim())) + " to " +
						(((searchDestTextField.getText()).trim())) +
						" is not available at this time.\n" +
						"We apologize for the inconvenience.");

						/* Clear the search result string. */
						searchResult = new String();

						/* Disable the Reserve button. */
						searchWindowReserveBtn.setDisable(true);
					}
				}
				else
				{
					/* Invalid args. */
					searchOutputTextArea.appendText("\nERROR: Please input a valid origin and destination city.");
				}
			}
		});	

		/* Set the event handler for searchWindowReserveBtn.
			This is what allows the button to do something. */
		this.searchWindowReserveBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if ((searchResult.length() > 0) && (reserveStage != null)) {
					reserveFlightNumberTxtField.clear();
					reserveFlightNumberTxtField.setText(searchResult);
					searchResult = new String();
					searchWindowReserveBtn.setDisable(true);
					searchOutputTextArea.clear();
					searchStage.close();
					reserveStage.show();
				}
			}
		});

		/* Set the event handler for searchWindowClearBtn.
			This is what allows the button to do something. */
		this.searchWindowClearBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				searchOriginTextField.clear();
				searchDestTextField.clear();
			}
		});

		/* Set the event handler for searchWindowCloseBtn.
			This is what allows the button to do something. */
		this.searchWindowCloseBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				searchStage.close();
				searchOutputTextArea.clear();
				if (mainStage != null) {
					mainStage.show();
				}
			}
		});

		/* Create the user buttons hbox. */
		this.searchUserButtonsHBox = new HBox();
		this.searchUserButtonsHBox.getChildren().addAll(this.searchWindowSearchBtn,
														this.searchWindowClearBtn,
														this.searchWindowReserveBtn,
														this.searchWindowCloseBtn);

		/* Create the search window's vbox. */
		this.searchVBox = new VBox();
		this.searchVBox.getChildren().addAll(this.searchTextFieldsGP,
											this.searchOutputTextArea,
											this.searchUserButtonsHBox);

		/* Create the Stage and Scene. */
		this.searchStage = new Stage();
		this.searchScene = new Scene(this.searchVBox);
		this.searchStage.setTitle(this.SEARCH_WINDOW_TITLE_STR);
		this.searchStage.setScene(this.searchScene);

		/* This sets the the modality for the search window.
			Basicly, it makes the search window's parent window
			not accept input until the search window closes. */
		if (this.mainStage != null) {
			this.searchStage.initOwner(this.mainStage);
			this.searchStage.initModality(Modality.WINDOW_MODAL);
		}

		/* Exit function. */
		return;
	}

	private void Create_ReserveWindow() {
		
		/* Create the labels for the text fields. */
		this.reserveFlightNumberLabel = new Label(FLIGHT_NUMBER_LABEL_TXT);
		this.reservePassengerNameLabel = new Label(PASSENGER_NAME_LABEL_TXT);

		/* Create the text fields. */
		this.reserveFlightNumberTxtField = new TextField();
		this.reservePassengerNameTxtField = new TextField();

		/* Create the grid pane for the input text fields. */
		this.reserveTextFieldsGP = new GridPane();
		this.reserveTextFieldsGP.add(this.reserveFlightNumberLabel, 0, 0);
		this.reserveTextFieldsGP.add(this.reserveFlightNumberTxtField, 1, 0);
		this.reserveTextFieldsGP.add(this.reservePassengerNameLabel, 0, 1);
		this.reserveTextFieldsGP.add(this.reservePassengerNameTxtField, 1, 1);

		/* Create the output text area. */
		this.reserveOutputTextArea = new TextArea();
		this.reserveOutputTextArea.setEditable(false);

		/* Create user input buttons. */
		this.reserveWindowReserveFirstBtn = new Button(RESERVE_FLIGHT_BTN_TXT);
		this.reserveWindowReserveSeatBtn = new Button(RESERVE_SEAT_BTN_TXT);
		this.reserveWindowClearBtn = new Button(CLEAR_BTN_TXT);
		this.reserveWindowCloseBtn = new Button(CLOSE_BTN_TXT);

		/* Set the event handler for reserveWindowReserveFirstBtn.
			This is what allows the button to do something. */
		this.reserveWindowReserveFirstBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/* Init vars. */
				boolean done = false;
				boolean createdTicket = false;
				int reservedSeatNum = 0;

				/* Check for valid args. */
				if (((((reserveFlightNumberTxtField.getText()).trim()).length()) > 0) &&
					((((reservePassengerNameTxtField.getText()).trim()).length()) > 0)) {
						/* Find the correct flight number. */
						for (int x = 0; ((x < flights.length) && (!done)); x++) {
							if (flights[x].getFlightNumber().equals(((reserveFlightNumberTxtField.getText()).trim()))) {
								reservedSeatNum = flights[x].reserve();
								if (reservedSeatNum != 0) {
									/* Create a ticket. */
									for (int y = 0; ((y < ticket.length) && (!createdTicket)); y++) {
										if (ticket[y] == null) {
											ticket[y] = new Ticket(flights[x].getFlightNumber(),
											reservedSeatNum,
											((reservePassengerNameTxtField.getText()).trim()));

											/* Increment ticket counter. */
											numberOfCreatedTickets++;

											/* Created the ticket. */
											createdTicket = true;

											/* Output the info. */
											reserveOutputTextArea.appendText("\nYour flight info:\n" +
											"Passenger name: " +
											((reservePassengerNameTxtField.getText()).trim()) + "\n" +
											"Flight number: " + flights[x].getFlightNumber() + "\n" +
											"Ticket number: " + ticket[y].getTicketNumber() + "\n" +
											"City of origin: " + flights[x].getOrigin() + "\n" +
											"Destination city: " + flights[x].getDestination() + "\n" +
											"Fare: " + String.format("%.2f", flights[x].getFare()) + "\n" +
											"Seat number: " + ticket[y].getSeatNumber() + "\n" +
											"We hope you enjoy your flight!\n");
										}
									}

									/* Check for created ticket. */
									if (!createdTicket) {
										reserveOutputTextArea.appendText("\nWe're sorry, our ticket system is full and" +
										" we cannot create more tickets at this time.\n" +
										"Please blame the programmer of our software for" +
										" creating a hardcoded ticket storage array, " +
										"instead of a dynamic one.\n" +
										"We apologize for the inconvenience caused by our incompetent" +
										"programmers.");
									}
								}
								else {
									reserveOutputTextArea.appendText("\nWe're sorry, the requested flight is full.\n" +
									"We apologize for the inconvenience.");
								}

								/* We are done. */
								done = true;
							}
						}

						/* Check and see if we are done. */
						if (!done) {
							reserveOutputTextArea.appendText("\nWe're sorry, there is no flight with the given flight number.\n" +
							"Please check the flight number and try again.");
						}
				}
				else
				{
					reserveOutputTextArea.appendText("\nERROR: Please input a valid flight number and passenger name.");
				}
			}
		});

		/* Set the event handler for reserveWindowReserveSeatBtn.
			This is what allows the button to do something. */
		this.reserveWindowReserveSeatBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/* Init vars. */
				boolean done = false;

				/* Check for valid args. */
				if (((((reserveFlightNumberTxtField.getText()).trim()).length()) > 0) &&
					((((reservePassengerNameTxtField.getText()).trim()).length()) > 0)) {
						/* Find the correct flight number. */
						for (int x = 0; ((x < flights.length) && (!done)); x++) {
							if (flights[x].getFlightNumber().equals(((reserveFlightNumberTxtField.getText()).trim()))) {
								/* Create the seat window. */
								Create_SeatWindow(	flights[x].getRows(),
													flights[x].getSeatsPerRow(),
													x);
								if (reserveSeatStage != null) {
									reserveStage.close();
									reserveSeatStage.show();
								}

								/* Done. */
								done = true;
							}
						}

						/* Check and see if we are done. */
						if (!done) {
							reserveOutputTextArea.appendText("\nWe're sorry, there is no flight with the given flight number.\n" +
							"Please check the flight number and try again.");
						}
				}
				else {
					reserveOutputTextArea.appendText("\nERROR: Please input a valid flight number and passenger name.");
				}
			}
		});

		/* Set the event handler for reserveWindowClearBtn.
			This is what allows the button to do something. */
		this.reserveWindowClearBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				reserveFlightNumberTxtField.clear();
				reservePassengerNameTxtField.clear();
			}
		});

		/* Set the event handler for reserveWindowCloseBtn.
			This is what allows the button to do something. */
		this.reserveWindowCloseBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				reserveStage.close();
				reserveOutputTextArea.clear();
				if (mainStage != null) {
					mainStage.show();
				}
			}
		});

		/* Create HBox for the user input buttons. */
		this.reserveUserInputHBox = new HBox();
		this.reserveUserInputHBox.getChildren().addAll(	this.reserveWindowReserveFirstBtn,
														this.reserveWindowReserveSeatBtn,
														this.reserveWindowClearBtn,
														this.reserveWindowCloseBtn);

		/* Create the vbox. */
		this.reserveVBox = new VBox();
		this.reserveVBox.getChildren().addAll(	this.reserveTextFieldsGP,
												this.reserveOutputTextArea,
												this.reserveUserInputHBox);

		/* Create the Stage and Scene. */
		this.reserveStage = new Stage();
		this.reserveScene = new Scene(this.reserveVBox);
		this.reserveStage.setTitle(this.RESERVE_WINDOW_TITLE_STR);
		this.reserveStage.setScene(this.reserveScene);

		/* This sets the the modality for the reserve window.
			Basicly, it makes the reserve window's parent window
			not accept input until the reserve window closes. */
		if (this.mainStage != null) {
			this.reserveStage.initOwner(this.mainStage);
			this.reserveStage.initModality(Modality.WINDOW_MODAL);
		}

		/* Exit function. */
		return;
	}

	private void Create_SeatWindow(int rows, int numberOfSeatsPerRow, int reserveSeatFlightIDX) {
		/* Init vars. */
		Flight flight = null;
		int seatNumber = 0;

		/* Set the index for the flight. */
		this.reserveSeatFlightIDX = reserveSeatFlightIDX;

		/* Get the flight object. */
		flight = ((reserveSeatFlightIDX < this.flights.length) ?
					(this.flights[reserveSeatFlightIDX]) : (null));
		if (flight != null) {
			/* Create the instructions label */
			this.reserveSeatInstLabel = new Label(this.CHOOSE_SEAT_LABEL_TXT);

			/* Create the GridPane. */
			this.reserveSeatBtnsGP = new GridPane();

			/* Create the buttons array. */
			this.reserveSeatBtns = new Button[(flight.getRows())][(flight.getSeatsPerRow())];
			for (int x = 0; (x < flight.getRows()); x++) {
				for (int y = 0; (y < flight.getSeatsPerRow()); y++) {
					seatNumber++;
					this.reserveSeatBtns[x][y] = new Button("Seat " + seatNumber);
					this.reserveSeatBtns[x][y].setDisable((!(flight.isAvailable((seatNumber)))));
					this.reserveSeatBtns[x][y].setStyle(((flight.isAvailable(seatNumber)) ?
					("-fx-background-color: green;") :
					("-fx-background-color: red;")));
					this.reserveSeatBtnsGP.add((this.reserveSeatBtns[x][y]), y, x);
					this.reserveSeatBtnsGP.setMargin((this.reserveSeatBtns[x][y]), (new Insets(5)));

					/* Set the event handler for reserveWindowCloseBtn.
					This is what allows the button to do something. */
					this.reserveSeatBtns[x][y].setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							/* Init vars. */
							Object sourceObj = null;
							boolean done = false;
							boolean createdTicket = false;
							int idx = 0;

							/* Get memory address of the source. */
							sourceObj = event.getSource();
							if (sourceObj instanceof Button) {
								/* Make sure we have a valid passenger name and flight number. */
								if (((((reservePassengerNameTxtField.getText()).trim()).length()) > 0) &&
								((((reserveFlightNumberTxtField.getText()).trim()).length()) > 0)) {
									/* Figure out what button got pressed. */
									for (int x = 0; ((x < reserveSeatBtns.length) && (!done)); x++) {
										for (int y = 0; ((y < reserveSeatBtns[x].length) && (!done)); y++) {
											/* Increment idx. */
											idx++;

											/* Check for matching button. */
											if (reserveSeatBtns[x][y] == sourceObj) {
												if (!(reserveSeatBtns[x][y].isDisabled())) {
													/* Reserve the seat. */
													if ((reserveSeatFlightIDX < flights.length) &&
													(flights[reserveSeatFlightIDX].reserve(idx))) {
														/* Create a ticket. */
														for (int b = 0; ((b < ticket.length) && (!createdTicket)); b++) {
															if (ticket[b] == null) {
																ticket[b] = new Ticket(flights[reserveSeatFlightIDX].getFlightNumber(),
																idx,
																((reservePassengerNameTxtField.getText()).trim()));

																/* Increment ticket counter. */
																numberOfCreatedTickets++;

																/* Created the ticket. */
																createdTicket = true;

																/* Disable the button. */
																reserveSeatBtns[x][y].setDisable(true);
																reserveSeatBtns[x][y].setStyle("-fx-background-color: red;");

																/* Output the info. */
																reserveOutputTextArea.appendText("\nYour flight info:\n" +
																"Passenger name: " +
																ticket[b].getPassenger() + "\n" +
																"Flight number: " + flights[reserveSeatFlightIDX].getFlightNumber() + "\n" +
																"Ticket number: " + ticket[b].getTicketNumber() + "\n" +
																"City of origin: " + flights[reserveSeatFlightIDX].getOrigin() + "\n" +
																"Destination city: " + flights[reserveSeatFlightIDX].getDestination() + "\n" +
																"Fare: " + String.format("%.2f", flights[reserveSeatFlightIDX].getFare()) + "\n" +
																"Seat number: " + ticket[b].getSeatNumber() + "\n" +
																"We hope you enjoy your flight!\n");
															}
														}

														/* Check for created ticket. */
														if (!createdTicket) {
															reserveOutputTextArea.appendText("\nWe're sorry, our ticket system is full and" +
															" we cannotcreate more tickets at this time.\n" +
															"Please blame the programmer of our software for creating a hardcoded ticket " +
															"storage array, instead of a dynamic one.\n" +
															"We apologize for the inconvenience caused by our incompetent programmers."
															);
														}
													}
												}

												/* Done. */
												done = true;
											}
										}
									}
								}
								else {
									reserveOutputTextArea.appendText("\nERROR: Please input a valid flight number and passenger name.");
								}
							}
						}
					});
				}
			}

			/* Create the ScrollPane for the button's GridPane. */
			this.reserveSeatBtnsSP = new ScrollPane(this.reserveSeatBtnsGP);
			this.reserveSeatBtnsSP.setPrefViewportWidth(300);
			this.reserveSeatBtnsSP.setPrefViewportHeight(300);

			/* Create the close window button. */
			this.reserveSeatCloseBtn = new Button(CLOSE_BTN_TXT);

			/* Set the event handler for reserveSeatCloseBtn.
				This is what allows the button to do something. */
			this.reserveSeatCloseBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					reserveSeatStage.close();
					if (reserveStage != null) {
						reserveStage.show();
					}
				}
			});

			/* Create the user input hbox */
			this.reserveSeatUserInputHBox = new HBox();
			this.reserveSeatUserInputHBox.getChildren().addAll(this.reserveSeatCloseBtn);

			/* Create the vbox. */
			this.reserveSeatVBox = new VBox();
			this.reserveSeatVBox.getChildren().addAll(	this.reserveSeatInstLabel,
														this.reserveSeatBtnsSP,
														this.reserveSeatUserInputHBox);

			/* Create the Stage and Scene. */
			this.reserveSeatStage = new Stage();
			this.reserveSeatScene = new Scene(this.reserveSeatVBox, 400, 350);
			this.reserveSeatStage.setTitle((this.RESERVE_SEAT_WINDOW_TITLE_STR + flight.getFlightNumber()));
			this.reserveSeatStage.setScene(this.reserveSeatScene);

			/* This sets the the modality for the seat window.
				Basicly, it makes the seat window's parent window
				not accept input until the seat window closes. */
			if (this.reserveStage != null) {
				this.reserveSeatStage.initOwner(this.reserveStage);
				this.reserveSeatStage.initModality(Modality.WINDOW_MODAL);
			}
		}
		else {
			/* Invalid reserveSeatFlightIDX. */
			this.reserveSeatStage = null;
			this.reserveSeatScene = null;
		}

		/* Exit function. */
		return;
	}

	private void Create_CancelWindow() {

		/* Create the ticket number label and text field. */
		this.cancelTicketNumberLabel = new Label(TICKET_NUMBER_LABEL_TXT);
		this.cancelTicketNumberTextField = new TextField();

		/* Create the ticket number hbox. */
		this.cancelTicketHBox = new HBox();
		this.cancelTicketHBox.getChildren().addAll(	this.cancelTicketNumberLabel,
													this.cancelTicketNumberTextField);

		/* Create the output text area. */
		this.cancelOutputTextArea = new TextArea();
		this.cancelOutputTextArea.setEditable(false);

		/* Create the user input buttons. */
		this.cancelWindowCancelBtn = new Button(CANCEL_FLIGHT_BTN_TXT);
		this.cancelWindowClearBtn = new Button(CLEAR_BTN_TXT);
		this.cancelWindowCloseBtn = new Button(CLOSE_BTN_TXT);

		/* Set the event handler for cancelWindowCancelBtn.
			This is what allows the button to do something. */
		this.cancelWindowCancelBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/* Init vars. */
				boolean done = false;
				Flight flight = null;

				if (ticket != null) {
					if ((((cancelTicketNumberTextField.getText()).trim()).length()) > 0) {
						/* Delete the ticket. */
						for (int b = 0; ((b < ticket.length) && (!done)); b++) {
							if (ticket[b] != null) {
								if ((Integer.toString(ticket[b].getTicketNumber())).equals(((cancelTicketNumberTextField.getText()).trim()))) {
									if (ticket[b].isActive()) {
										/* Get the flight from the flight number. */
										for (int y = 0; ((y < flights.length) && (flight == null)); y++) {
											if ((flights[y].getFlightNumber()).equals(ticket[b].getFlightNumber())) {
												flight = flights[y];
											}
										}

										/* Cancel the reservation. */
										if (flight != null) {
											flight.cancel(ticket[b].getSeatNumber());
										}
										else {
											System.out.println("\nDEBUG ERROR: Non-existant flight number <" +
											ticket[b].getFlightNumber() + "> when canceling flight reservation.\n");
										}

										/* Output the ticket and flight info. */
										cancelOutputTextArea.appendText("The following reservation has been canceled:\n" +
										"Flight number: " + ((flight != null) ? (ticket[b].getFlightNumber()) :
										("ERROR: NON EXISTANT FLIGHT.")) + "\n" +
										"City of origin: " + ((flight != null) ? (flight.getOrigin()) :
										("ERROR: NON EXISTANT FLIGHT.")) + "\n" +
										"Destination city: " + ((flight != null) ? (flight.getDestination()) :
										("ERROR: NON EXISTANT FLIGHT.")) + "\n" +
										"Fare: " + ((flight != null) ? (String.format("%.2f", flights[reserveSeatFlightIDX].getFare())) :
										("ERROR: NON EXISTANT FLIGHT.")) + "\n" +
										"Passenger name: " + ticket[b].getPassenger() + "\n" +
										"Seat number: " + ((flight != null) ? (ticket[b].getSeatNumber()) :
										("ERROR: NON EXISTANT FLIGHT.")) + "\n" +
										"We hope to see you again on your next flight.");

										/* Cancel the ticket. */
										ticket[b].setActive(false);

										/* Delete the ticket. */
										ticket[b] = null;

										/* Decrement the number of tickets. */
										numberOfCreatedTickets--;

										/* Done. */
										done = true;
									}
								}
							}
						}

						/* Check if done. */
						if (!done) {
							cancelOutputTextArea.appendText("\n\nWe're sorry, we do not have a ticket on record for ticket number: " +
							((cancelTicketNumberTextField.getText()).trim()) + "\nPlease check the ticket number and try again.\n");
						}
					}
					else {
						cancelOutputTextArea.appendText("\nERROR: Please input a valid ticket number.\n");
					}
				}
			}
		});

		/* Set the event handler for cancelWindowClearBtn.
			This is what allows the button to do something. */
		this.cancelWindowClearBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				cancelTicketNumberTextField.clear();
			}
		});

		/* Set the event handler for cancelWindowCloseBtn.
			This is what allows the button to do something. */
		this.cancelWindowCloseBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				cancelStage.close();
				cancelOutputTextArea.clear();
				cancelTicketNumberTextField.clear();
				if (mainStage != null) {
					mainStage.show();
				}
			}
		});

		/* Create the user input hbox. */
		this.cancelUserInputHBox = new HBox();
		this.cancelUserInputHBox.getChildren().addAll(	this.cancelWindowCancelBtn,
														this.cancelWindowClearBtn,
														this.cancelWindowCloseBtn);

		/* Create the vbox. */
		this.cancelVBox = new VBox();
		this.cancelVBox.getChildren().addAll(	this.cancelTicketHBox,
												this.cancelOutputTextArea,
												this.cancelUserInputHBox);

		/* Create the Stage and Scene. */
		this.cancelStage = new Stage();
		this.cancelScene = new Scene(this.cancelVBox);
		this.cancelStage.setTitle(this.CANCEL_FLIGHT_WINDOW_TITLE_STR);
		this.cancelStage.setScene(this.cancelScene);
		
		/* This sets the the modality for the cancel window.
			Basicly, it makes the cancel window's parent window
			not accept input until the cancel window closes. */
		if (this.mainStage != null) {
			this.cancelStage.initOwner(this.mainStage);
			this.cancelStage.initModality(Modality.WINDOW_MODAL);
		}

		/* Exit function. */
		return;
	}

	public void start(Stage primaryStage) {
		try {
			/* Create the flights. */
			this.flights = new Flight[10];
			this.flights[0] = new Flight("LA001", 21, 6, 39.00, "Louisville", "Austin");
			this.flights[1] = new Flight("AL001", 21, 6, 39.00, "Austin", "Louisville");
			this.flights[2] = new Flight("LT001", 30, 6, 59.00, "Louisville", "Tokyo");
			this.flights[3] = new Flight("TL001", 30, 6, 59.00, "Tokyo", "Louisville");
			this.flights[4] = new Flight("LY011", 23, 6, 34.00, "Louisville", "New York City");
			this.flights[5] = new Flight("YL101", 23, 6, 34.00, "New York City", "Louisville");
			this.flights[6] = new Flight("LL001", 20, 6, 12.00, "Louisville", "Lexington");
			this.flights[7] = new Flight("LL002", 20, 6, 12.00, "Lexington", "Louisville");
			this.flights[8] = new Flight("LW001", 26, 6, 42.00, "Louisville", "Wales");
			this.flights[9] = new Flight("WL001", 26, 6, 42.00, "Wales", "Louisville");

			/* Init the tickets array. */
			this.ticket = new Ticket[MAX_TICKETS];
			this.numberOfCreatedTickets = 0;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

		/* Create GUI windows. */
		Create_MainWindow();
		Create_SearchWindow();
		Create_ReserveWindow();
		Create_CancelWindow();

		/* Start main window. */
		try {
			if (this.mainStage != null) {
				this.mainStage.show();
			}
		}
		/* Lazy exception handling, to catch anything we missed. 
			So that the user does not have to see a scary backtrace
			when the exception hits the JVM.
		*/
		catch(Exception e) {
			try {
				MessageWindow msgWin = new MessageWindow("GUI EXCEPTION:", e.getMessage());
				msgWin.ShowMessage();
			}
			catch (Exception e2) {
				System.out.println("EXCEPTION: Exception thrown while attepmting to create window to display exception from GUI: "+
				e2.getMessage() + "\n\nOriginal GUI exception: " + e.getMessage());
			}
		}

		/* Exit function. */
		return;
	}

}	/* End of class ReservationsGUI. */
