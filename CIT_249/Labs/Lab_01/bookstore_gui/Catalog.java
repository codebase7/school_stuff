/**
	Class Catalog defines a catalog for a book store. An object of this class provides 
	the ability to
		- add BookStoreItems to the catalog
		- search for BookStoreItems in the catalog
		
	Author: Patrick Hibbs
	E-mail address: phibbs0003@kctcs.edu
	Last changed: 10/26/2015
	Lab 01
*/

public class Catalog {
	
	private BookStoreItem[] inventory;
	private final int CAPACITY = 100;
	private int count;
	
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
	
}
		