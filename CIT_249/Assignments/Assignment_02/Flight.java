/***************************************************************
	class Flight
	Author: Patrick Hibbs
	Last modified: 11/14/2015
	phibbs0003@kctcs.edu
	Assignment 02
***************************************************************/

public class Flight {

	/* Instance variables. */
	private String flightNumber;
	private int rows;
	private int seatsPerRow;
	private boolean seat[][];	/* If true, then the given seat is avaiable. */
	private double fare;
	private String origin;
	private String destination;

	Flight(String flightNumber, int rows, int seatsPerRow, double fare, String origin, String destination) {
		if ((((flightNumber.trim()).length()) > 0) && (rows > 0) && (seatsPerRow > 0) && (fare >= 0.0) &&
		(((origin.trim()).length()) > 0) && (((destination.trim()).length()) > 0)) {
			this.flightNumber = flightNumber.trim();
			this.rows = rows;
			this.seatsPerRow = seatsPerRow;
			this.fare = fare;
			this.origin = origin.trim();
			this.destination = destination.trim();

			/* Init array. */
			this.seat = new boolean[this.rows][this.seatsPerRow];
			for (int x = 0; (x < this.rows); x++) {
				for (int y = 0; (y < this.seatsPerRow); y++) {
					/* Make all seats avaiable. */
					this.seat[x][y] = true;
				}
			}
		}
	}

	public String getFlightNumber() {
		return this.flightNumber;
	}

	public int getRows() {
		return this.rows;
	}

	public int getSeatsPerRow() {
		return this.seatsPerRow;
	}

	public double getFare() {
		return this.fare;
	}

	public String getOrigin() {
		return this.origin;
	}

	public String getDestination() {
		return this.destination;
	}

	/* Specific seat check version. */
	public boolean isAvailable(int seatNumber) {
		/* Init vars. */
		boolean ret = false; 	/* The result of this function. */
		CalcSeatIndexResult seatIdx = null;

		/* Get the seatIdx. */
		seatIdx = CalculateSeatIndex(seatNumber);
		if (seatIdx != null) {
			/* Set ret. */
			ret = this.seat[(seatIdx.row)][(seatIdx.column)];
		}

		/* Exit function. */
		return ret;
	}

	/* Any avaiable seat version. */
	public boolean isAvailable() {
		/* Init vars. */
		boolean ret = false; 	/* The result of this function. */

		/* Check for valid array. */
		if (this.seat != null) {
			/* Check for an open seat. */
			for (int x = 0; ((x < this.rows) && (!ret)); x++) {
				for (int y = 0; ((y < this.seatsPerRow) && (!ret)); y++) {
					if (this.seat[x][y]) {
						/* Found an open seat. */
						ret = true;
					}
				}
			}
		}

		/* Exit function. */
		return ret;
	}

	/* First open seat version. */
	public int reserve() {
		/* Init vars. */
		int ret = 0;	/* The result of this function. */
		int seatNumber = 0;

		/* Check for valid array. */
		if (this.seat != null) {
			/* Check for an open seat. */
			for (int x = 0; ((x < this.rows) && (ret == 0)); x++) {
				for (int y = 0; ((y < this.seatsPerRow) && (ret == 0)); y++) {
					seatNumber++;
					if (this.seat[x][y]) {
						/* Found an open seat. Reserve it. */
						this.seat[x][y] = false;
						ret = seatNumber;
					}
				}
			}
		}

		/* Exit function. */
		return ret;
	}

	/* Specific seat version */
	public boolean reserve(int seatNumber) {
		/* Init vars. */
		boolean ret = false;	/* The result of this function. */
		CalcSeatIndexResult seatIdx = null;

		/* Get the seatIdx. */
		seatIdx = CalculateSeatIndex(seatNumber);
		if (seatIdx != null) {
			/* Set ret. (Status of the seat before the check determines if we succeed or not.) */
			ret = this.seat[(seatIdx.row)][(seatIdx.column)];

			/* Check for open seat. */
			if (this.seat[(seatIdx.row)][(seatIdx.column)])
			{
				/* Seat is open, so reserve it. */
				this.seat[(seatIdx.row)][(seatIdx.column)] = false;
			}
		}

		/* Exit function. */
		return ret;
	}

	public void cancel(int seatNumber) {
		/* Init vars. */
		CalcSeatIndexResult seatIdx = null;

		/* Get the seatIdx. */
		seatIdx = CalculateSeatIndex(seatNumber);
		if (seatIdx != null) {
			/* Check for reserved seat. */
			if (!(this.seat[(seatIdx.row)][(seatIdx.column)]))
			{
				/* Seat is reserved, so cancel the reservation. */
				this.seat[(seatIdx.row)][(seatIdx.column)] = true;
			}
		}

		/* Exit function. */
		return;
	}

	/*!
		private class CalcSeatIndexResult
			- A class that is used to store the results of the CalculateSeatIndex() function.
			(Because Java will not allow us to pass an int by reference...)
	 */
	private class CalcSeatIndexResult {
		public int row;
		public int column;

		CalcSeatIndexResult() {
			this.row = 0;
			this.column = 0;
		}
	}

	/*!
		private CalculateSeatIndex()
		
		A function that calculates the array indexes for a given seat number.
		(Prevents needing to copy the code into 3 different functions.)
	 */
	private CalcSeatIndexResult CalculateSeatIndex(int seatNumber) {
		/* Init vars. */
		CalcSeatIndexResult ret = null;
		boolean done = false;
		int count = 0;

		/* Check for valid args. */
		if ((this.seat != null) && (seatNumber > 0) && (seatNumber <= (this.rows * this.seatsPerRow))) {
			/* Create new result object. */
			ret = new CalcSeatIndexResult();

			/* Calculate the array indexes. */
			//ret.row = (seatNumber - 1) / this.rows;
			//ret.column = (seatNumber - 1) % this.seatsPerRow;

			for (int x = 0; ((x < this.rows) && (!done)); x++) {
				for (int y = 0; ((y < this.seatsPerRow) && (!done)); y++) {
					count++;
					if (count == seatNumber) {
						ret.row = x;
						ret.column = y;
						done = true;
					}
				}
			}
		}

		/* Exit function. */
		return ret;
	}

}	/* End of class Flight */
