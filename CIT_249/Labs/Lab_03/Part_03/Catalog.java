/**
	Class Catalog defines a catalog for a book store. An object of this class provides 
	the ability to
		- add BookStoreItems to the catalog
		- search for BookStoreItems in the catalog
		
	Author: Patrick Hibbs
	E-mail address: phibbs0003@kctcs.edu
	Last changed: 11/16/2015
	Lab 03
*/

import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
		FileInputStream inputFileStream = null;
		ObjectInputStream inputStream = null;
		int numberOfEntriesInArray = 0;
		int itemType = 0;
		Object tempObj = null;

		/* Begin try block. */
		try {
			/* Open the file. */
			inputFile = new File(CATALOG_FILE_NAME);
			inputFileStream = new FileInputStream(inputFile);
			inputStream = new ObjectInputStream(inputFileStream);

			/* Read in the number of objects in the file. */
			numberOfEntriesInArray = inputStream.readInt();

			/* Begin catalog file parsing loop. */
			for (int x = 0; (x < numberOfEntriesInArray); x++) {
					/* Read in the item type. */
					itemType = inputStream.readInt();

					/* Read in the object. */
					tempObj = inputStream.readObject();

					/* Create the object by calling the correct constructor. */
					switch (itemType) {
						case BOOK_TYPE:
							this.add(((Book)(tempObj)));
							break;
						case CD_TYPE:
							this.add(((CD)(tempObj)));
							break;
						case DVD_TYPE:
							this.add(((DVD)(tempObj)));
							break;
						case INVALID_TYPE:
						default:
							break;
					};
			}

			/* Close the file. */
			inputFileStream.close();
			inputFileStream = null;
		}
		catch (Exception e) {
			/* Close the input file if needed. */
			if (inputFileStream != null) {
				inputFileStream.close();
				inputFileStream = null;
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
		FileOutputStream outputFileStream = null;
		ObjectOutputStream outputStream = null;
		int numberOfEntriesInArray = 0;

		/* Begin try block. */
		try {
			/* Open the files. */
			outputFile = new File(CATALOG_FILE_NAME);
			outputFileStream = new FileOutputStream(outputFile);
			outputStream = new ObjectOutputStream(outputFileStream);

			/* Output the number of entries in the array. */
			for (int x = 0; (x < CAPACITY); x++) {
				if (this.inventory[x] != null) {
					numberOfEntriesInArray++;
				}
			}
			outputStream.writeInt(numberOfEntriesInArray);

			/* Output data. */
			for (int x = 0; (x < CAPACITY); x++) {
				if (this.inventory[x] != null) {
					outputStream.writeInt(((this.inventory[x] instanceof Book) ? (BOOK_TYPE) :
					 ((this.inventory[x] instanceof CD) ? (CD_TYPE) : 
					 ((this.inventory[x] instanceof DVD) ? (DVD_TYPE):
					 (INVALID_TYPE)))));

					/* Output the object data. */
					outputStream.writeObject(this.inventory[x]);
				}
			}

			/* Close the file. */
			outputFileStream.close();
			outputFileStream = null;
		}
		catch (Exception e) {
			/* Close the output file if needed. */
			if (outputFileStream != null) {
				outputFileStream.close();
				outputFileStream = null;
			}

			/* Rethrow the exception. */
			throw e;
		}

		/* Exit function. */
		return;
	}
	
}
		