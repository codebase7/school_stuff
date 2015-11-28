/**
	Class Catalog defines a catalog for a book store. An object of this class provides 
	the ability to
		- add BookStoreItems to the catalog
		- search for BookStoreItems in the catalog
		
	Author: Patrick Hibbs
	E-mail address: phibbs0003@kctcs.edu
	Last changed: 11/05/2015
	Lab 02
*/

import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

public class Catalog {
	private final String CATALOG_FILE_NAME = "catalog.txt";

	private BookStoreItem[] inventory;
	private final int CAPACITY = 100;
	private int count;

	private final static int INVALID_TYPE = 0;
	private final static int BOOK_TYPE = 1;
	private final static int CD_TYPE = 2;
	private final static int DVD_TYPE = 3;

	public Catalog() {
		inventory = new BookStoreItem[CAPACITY];
		count = 0;
	}
	
	public void add(BookStoreItem newItem) {
		inventory[count] = newItem;
		count++;
	}
	
	public boolean isAvailable(String title) {
		
		boolean found = false;
		
		for (int i = 0;i < count && !found;i++) {
			if (title.equals(inventory[i].getTitle())) {
				found = true;
			}
		}
		
		return found;
	}
	
	public BookStoreItem getItem(String title) {
		
		BookStoreItem desiredItem = null;
		
		boolean found = false;
		
		for (int i = 0;i < count && !found;i++) {
			if (title.equals(inventory[i].getTitle())) {
				desiredItem = inventory[i];
				found = true;
			}
		}
		
		return desiredItem;
		
	}
	
	public BookStoreItem[] getList() {
		return inventory;
	}

	public boolean load() throws Exception {
		/* Init vars. */
		File inputFile = null;
		Scanner inputScanner = null;
		String inputString = "";
		int itemType = INVALID_TYPE;
		int itemSize = 0;
		String itemAuthor = "";
		String itemTitle = "";
		Double itemPrice = 0.0;
		
		/* Begin try block. */
		try {
			/* Open the file. */
			inputFile = new File(CATALOG_FILE_NAME);
			inputScanner = new Scanner(inputFile);

			/* Begin catalog file parsing loop. */
			do {
					/* Read in the object's type. */
					inputString = (inputScanner.nextLine()).trim();
					itemType = Integer.parseInt(inputString);

					/* Read in the object's title. */
					inputString = (inputScanner.nextLine()).trim();
					itemTitle = inputString;

					/* Read in the object's author. */
					inputString = (inputScanner.nextLine()).trim();
					itemAuthor = inputString;

					/* Read in the object's price. */
					inputString = (inputScanner.nextLine()).trim();
					itemPrice = Double.parseDouble(inputString);

					/* Read in the obejct's size. */
					inputString = (inputScanner.nextLine()).trim();
					itemSize = Integer.parseInt(inputString);

					/* Create the object by calling the correct constructor. */
					switch (itemType) {
						case BOOK_TYPE:
							this.add(new Book(itemTitle, itemAuthor, itemPrice, itemSize));
							break;
						case CD_TYPE:
							this.add(new CD(itemTitle, itemAuthor, itemPrice, itemSize));
							break;
						case DVD_TYPE:
							this.add(new DVD(itemTitle, itemAuthor, itemPrice, itemSize));
							break;
						case INVALID_TYPE:
						default:
							break;
					};
			} while(inputScanner.hasNextLine());

			/* Close the file. */
			inputScanner.close();
			inputScanner = null;
		}
		catch (Exception e) {
			/* Close the input file if needed. */
			if (inputScanner != null) {
				inputScanner.close();
				inputScanner = null;
			}

			/* Rethrow the exception. */
			throw e;
		}

		/* Exit function. */
		return true;
	}
	
	public void save() throws Exception {
		/* Init vars. */
		File outputFile = null;
		PrintWriter outputWriter = null;

		/* Begin try block. */
		try {
			/* Open the files. */
			outputFile = new File(CATALOG_FILE_NAME);
			outputWriter = new PrintWriter(outputFile);

			/* Output data. */
			for (int x = 0; (x < CAPACITY); x++) {
				if (this.inventory[x] != null) {
					outputWriter.println(
					Integer.toString(((this.inventory[x] instanceof Book) ? (BOOK_TYPE) :
					 ((this.inventory[x] instanceof CD) ? (CD_TYPE) : 
					 ((this.inventory[x] instanceof DVD) ? (DVD_TYPE):
					 (INVALID_TYPE))))));

					outputWriter.println(this.inventory[x].getTitle());
					outputWriter.println(this.inventory[x].getAuthor());
					outputWriter.println(Double.toString(this.inventory[x].getPrice()));
					outputWriter.println(Integer.toString(this.inventory[x].getSize()));
				}
			}

			/* Close the file. */
			outputWriter.close();
			outputWriter = null;
		}
		catch (Exception e) {
			/* Close the output file if needed. */
			if (outputWriter != null) {
				outputWriter.close();
				outputWriter = null;
			}

			/* Rethrow the exception. */
			throw e;
		}

		/* Exit function. */
		return;
	}
	
}
		