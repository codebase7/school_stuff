/**
	Class BookStoreApplication version 0.4 represents a book store.
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

public class BookStoreApplication {
	
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
	
	public static void main(String[] args) {
		runPayrollApplication();
	}
	
	static void runPayrollApplication() {
		int itemType;
		int userChoice = QUIT;
		
		do {
			printMenu();
			userChoice = getUserChoice();
			
			switch (userChoice) {
				case ADD_ITEM:
					itemType = getItemType();
					addItem(itemType);
					break;
				case SHOW_ITEM_LIST:
					showItemList();
					break;
				case SEARCH:
					searchItem();
					break;
				case QUIT:
					System.out.println(/*quitMessage*/);
					break;
				default:
					System.out.println(/*invalidOptionMessage*/);	
			}
		} while(userChoice != QUIT);	
	}
	
	static void printMenu() {
		System.out.println("\nPress:");
		System.out.println("\t" + ADD_ITEM + "\tTo add an item to the inventory\n");
		System.out.println("\t" + SHOW_ITEM_LIST + "\tTo display a list of available books\n");
		System.out.println("\t" + SEARCH + "\tTo search for a specific book\n");
		System.out.println("\t" + QUIT + "\tTo exit\n");
	}
	
	static void addItem(int itemType) {
		String title;
		String author;
		double price;
		int size;
		BookStoreItem newItem = null;
		
		System.out.print("\nEnter the item's title: ");
		title = keyboard.nextLine();
		
		System.out.print("\nEnter the name of the item's author: ");
		author = keyboard.nextLine();
		
		System.out.print("\nEnter the item's price: ");
		price = keyboard.nextDouble();
		
		switch(itemType) {
			case BOOK:
				System.out.print("\nEnter the number of pages: ");
				size = keyboard.nextInt();
				newItem = new Book(title, author, price, size);
				break;
			case CD:
				System.out.print("\nEnter the playing time: ");
				size = keyboard.nextInt();
				newItem = new CD(title, author, price, size);
				break;
			case DVD:
				System.out.print("\nEnter the playing time: ");
				size = keyboard.nextInt();
				newItem = new DVD(title, author, price, size);
				break;
			default:
				System.out.println("\nInvalid item type.\n");
		}
		
		storeCatalog.add(newItem);
	}
	
	static void searchItem() {
		
		String title;

		boolean isAvailable = false;
		
		System.out.print("\nEnter the title you wish to search for: ");
		title = keyboard.nextLine();

		isAvailable = storeCatalog.isAvailable(title);

		if (isAvailable) {
			System.out.println("\nTitle found: " + storeCatalog.getItem(title));
		}
		else {
			System.out.println("\nTitle not found: " + title);
		}	
				
	}
	
	static int getUserChoice() {
		int choice;
		
		System.out.print("Please enter your choice: ");
		choice = keyboard.nextInt();
		
		keyboard.nextLine();
		 
		return choice;	
	}
	
	static int getItemType() {
		
		int itemType;
		
		System.out.println("\nEnter the type of item you want to add. Press:");
		System.out.println("\t" + BOOK + "\tfor BOOK\n");
		System.out.println("\t" + CD + "\tfor CD\n");
		System.out.println("\t" + DVD + "\tfor DVD\n");
		System.out.println("\t" + CANCEL + "\tTo CANCEL\n");
		
		itemType = keyboard.nextInt();
		
		keyboard.nextLine();
		 
		return itemType;
		
	}
		
		
		
	
	static void showItemList() {
		BookStoreItem[] list = storeCatalog.getList();
		
		for (int i = 0; ((i < list.length) && (list[i] != null)); i++) {
			System.out.println("\nItem " + (i + 1) + ":\n" + list[i]);
		}
		
		for (int i = 0; ((i < list.length) && (list[i] != null)); i++) {
			System.out.println(((list[i] instanceof Book) ? ("\nNumber of pages for item [ " + i + " ]: ") : ("\nPlaying time for item [ " + i + " ]: ")) +
			list[i].getSize() + "\n");
		}
	}
}


	
	
