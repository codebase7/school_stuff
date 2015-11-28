/**
	Class BookStoreItem represents an item for sale at a book store.
	This version of the class is abstract. This is in order to
		- prevent BookStoreItem objects from being created. Instead, only
		  Book, CD and Tape objects can be created
		- require all subclasses to provide certain methods (the compiler requires
		  all subclasses to override any abstract methods that they inherit
		  from this class
	Author: Patrick Hibbs
	E-mail address: phibbs0003@kctcs.edu
	Last changed: 10/26/2015
	Lab 01
*/

public abstract class BookStoreItem {
	
	protected String title;
	protected String author;
	protected double price;
	
	
	/*
	* The constructor
	* Contains code that initially sets up each Book object when it is
	* first created. Always has the same name as the class.
	*/
	public BookStoreItem (String title, String author, double price) {
		//call the mutator methods and let them initialize the instance variables
		setTitle(title);
		setAuthor(author);
		setPrice(price);
	}
	
	public BookStoreItem () {
		//call the other constructor and pass it a generic  title, author and price
		this("title", "author", 0.0);
	}
	
	/*
	* The accessor methods
	* These report the current values of the Book object's instance variables
	*/
	public String getTitle() {
		return this.title;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public abstract int getSize();
	
	/*
	* The mutator methods
	* These allow the Book object's instance variables to be assigned new values
	*/
	public void setTitle(String title) {
		
		//trim the title parameter to remove any white space at the ends of the
		//String. Then test if the remaining String contains at least one 
		//character. If so, title is valid.
		if ((title.trim()).length() > 0) {
			this.title = title;
		}
	}
	
	public void setAuthor(String author) {
		if ((author.trim()).length() > 0) {
			this.author = author;
		}
	}
	
	public void setPrice(double price) {
		if (price >= 0) {
			this.price = price;
		}
	}
	
	/*
	* method toString() returns a String representation of this Book object
	*/
	public String toString() {
		String item = "Title: " + this.title +
						"\nAuthor: " + this.author +
						"\nPrice: " + this.price;
		return item;
	}
	
	public boolean equals(Object obj) {
		boolean equalItems;
		BookStoreItem otherItem;
		
		equalItems = false;
		otherItem = null;
		
		if (obj == null) {
			throw new NullPointerException();
		}
		else if (this == obj) {
			equalItems = true;
		}
		else {
			if (obj instanceof BookStoreItem) {
				otherItem = (BookStoreItem)obj;
				if (this.title.equals(otherItem.title) &&
						this.author.equals(otherItem.author) &&
						Math.abs(this.price - otherItem.price) > 0.01) {
					equalItems = true;
				}
			}
		}
		
		return equalItems;		
	}		
}