package stock;

import humans.Author;
import utilities.PersistentData;

public class Book extends Product {
	
	private Author author;
	private String ISBN;
	
	public Book() {}
	
	public Book(String ISBN, String name, double price, Author author, int availableQuantity) {
		super(name,price,availableQuantity);
		this.author = author;
		this.ISBN = ISBN;
	}

	@Override
	public boolean equals(Object obj) {
		Book b = obj instanceof Book ? (Book)obj : null;
		return b != null && this.ISBN == b.ISBN ? true : false;
	}

	@Override
	public String toString() {
		
		return "\n********Book Details********"
				+"\nID: #"+super.getId()
				+"\nISBN: "+this.ISBN
				+"\nTitle: "+super.getName()
				+"\nPrice: "+super.getPrice()+" DT"
				+"\n----Author---- "+this.author;
	}
	
	public static Book getBook(int id) {
		for(Product p: PersistentData.products) {
			if(p instanceof Book && p.getId() == id) {
				return (Book)p;
			}
		}
		return null;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	
}
