/**
	Class Book represents a book for sale at a book store.
	This version of the class is a subclass of class BookStoreItem
	and inherits variables and methods from it. This class overrides
	the getSize() method that it inherits from BookStoreItem. This
	class also declares one variable named pages (declared here because
	other items sold at a book store (CDs and Tapes) do not contain pages.
	The getSize() method returns this variable's value, since the size of
	a book is how many pages are in it.
	Author: Patrick Hibbs
	E-mail address: phibbs0003@kctcs.edu
	Last changed: 10/26/2015
	Lab 02
*/

public class Book extends BookStoreItem {
	
	/* Declare variables. */
	int pages;
	
	/*
	* The constructor
	* Contains code that initially sets up each Book object when it is
	* first created. Always has the same name as the class.
	*/
	public Book (String title, String author, double price, int pages) {
		super(title, author, price);
		this.pages = pages;
	}
	
	public Book () {
		//call the other constructor and pass it a generic  title, author, price, and page count.
		this("title", "author", 0.0, 0);
	}
	
	/* Override getSize() so that it may return the number of pages. */
	@Override
	public int getSize() {
		return this.pages;
	}
	
	/*
	* method toString() returns a String representation of this Book object
	* It calls the superclass's version of the toString() method to reuse
	* the code there, and only adds what details it needs to
	*/
	public String toString() {
		String book;
		book = "Book:\n" + super.toString() + 
				"\nPages: " + this.pages;
		return book;
	}
	
	public boolean equals(Object obj) {
		boolean equalBooks;
			
		equalBooks = super.equals(obj);
		
		if (equalBooks) {
			if (obj instanceof Book) {
				if (this.pages != ((Book)obj).pages) {
					equalBooks = false;
				}
			}
		}
		
		return equalBooks;
	}
		
}