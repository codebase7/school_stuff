/***************************************************************
	class Ticket
	Author: Patrick Hibbs
	Last modified: 11/14/2015
	phibbs0003@kctcs.edu
	Assignment 02
***************************************************************/

public class Ticket {
	/* Static vars. */
	private static int nextTicketNumber = 100;

	/* Instance vars. */
	private int ticketNumber;
	private String flightNumber;
	private int seatNumber;
	private boolean active;
	private String passenger;

	Ticket(String flightNumber, int seatNumber, String passenger) {
		if ((((flightNumber.trim()).length()) > 0) && (seatNumber > 0) && (((passenger.trim()).length()) > 0)) {
			this.flightNumber = flightNumber.trim();
			this.seatNumber = seatNumber;
			this.passenger = passenger.trim();
			this.ticketNumber = nextTicketNumber;
			nextTicketNumber++;
			this.active = true;
		}
	}

	public int getTicketNumber() {
		return this.ticketNumber;
	}

	public String getFlightNumber() {
		return this.flightNumber;
	}

	public int getSeatNumber() {
		return this.seatNumber;
	}

	public String getPassenger() {
		return this.passenger;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean value) {
		this.active = value;
		return;
	}

}	/* End of class Ticket. */
