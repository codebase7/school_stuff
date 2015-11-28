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
import java.io.DataInputStream;
import java.io.DataOutputStream;

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
		DataInputStream inputStream = null;
		int itemType = INVALID_TYPE;
		int itemSize = 0;
		String itemAuthor = "";
		String itemTitle = "";
		Double itemPrice = 0.0;

		/* Begin try block. */
		try {
			/* Open the file. */
			inputFile = new File(CATALOG_FILE_NAME);
			inputFileStream = new FileInputStream(inputFile);
			inputStream = new DataInputStream(inputFileStream);

			/* Begin catalog file parsing loop. */
			do {
					/* Read in the object's type. */
					itemType = inputStream.readInt();

					/* Read in the object's title. */
					itemTitle = inputStream.readUTF();

					/* Read in the object's author. */
					itemAuthor = inputStream.readUTF();

					/* Read in the object's price. */
					itemPrice = inputStream.readDouble();

					/* Read in the obejct's size. */
					itemSize = inputStream.readInt();

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
			} while(inputFileStream.available() > 0);

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
		DataOutputStream outputStream = null;

		/* Begin try block. */
		try {
			/* Open the files. */
			outputFile = new File(CATALOG_FILE_NAME);
			outputFileStream = new FileOutputStream(outputFile);
			outputStream = new DataOutputStream(outputFileStream);

			/* Output data. */
			for (int x = 0; (x < CAPACITY); x++) {
				if (this.inventory[x] != null) {
					outputStream.writeInt(((this.inventory[x] instanceof Book) ? (BOOK_TYPE) :
					 ((this.inventory[x] instanceof CD) ? (CD_TYPE) : 
					 ((this.inventory[x] instanceof DVD) ? (DVD_TYPE):
					 (INVALID_TYPE)))));

					outputStream.writeUTF(this.inventory[x].getTitle());
					outputStream.writeUTF(this.inventory[x].getAuthor());
					outputStream.writeDouble((this.inventory[x].getPrice()));
					outputStream.writeInt((this.inventory[x].getSize()));
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
		