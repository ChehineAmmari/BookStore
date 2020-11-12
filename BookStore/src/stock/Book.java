package stock;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.BookStoreConnection;

public class Book extends Product {
	
	private String ISBN,title,authorName;
	private Date releaseDate;
	double price;
	
	public Book() {}
	
	public Book(String ISBN, String title, double price, String author, Date releaseDate) {
		this.authorName = author;
		this.title = title;
		this.ISBN = ISBN;
		this.releaseDate = releaseDate;
		this.price = price;
	}

	@Override
	public boolean equals(Object obj) {
		Book b = obj instanceof Book ? (Book)obj : null;
		return b != null && this.ISBN == b.ISBN ? true : false;
	}

	@Override
	public String toString() {
		
		return "\n+++++ Book Details +++++"
				+"\nISBN: "+this.ISBN
				+"\nTitle: "+this.getTitle()
				+"\nPrice: "+this.getPrice()+" DT"
				+"\nAuthor: "+this.getAuthorName();
	}
	
	public void add() {
		String values = "('"
				+this.getISBN()+"', '"
				+this.getTitle()+"', '"
				+this.getAuthorName()+"', '"
				+this.getReleaseDate()+"', "
				+this.getPrice()
				+ ")";
		String query = "INSERT INTO `books` VALUES "+values;
		try {
			BookStoreConnection.executeUpdate(query);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Can't insert new book to database");
			e.printStackTrace();
		}
	}
	
	public void remove() {
		String query = "DELETE FROM `books` WHERE ISBN='"+this.getISBN()+"';";
		try {
			BookStoreConnection.executeUpdate(query);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("This book is already removed from the database");
			e.printStackTrace();
		}
	}
	
	public void update() {
		String condition = "WHERE ISBN='"+this.getISBN()+"';";
		String query = "UPDATE `books` SET "
				+"title='"+this.getTitle()+"', "
				+"author='"+this.getAuthorName()+"', "
				+"releaseDate='"+this.getReleaseDate()+"', "
				+"price="+this.getPrice()+" "
				+condition;
		
		try {
			BookStoreConnection.executeUpdate(query);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Can't update this book");
			e.printStackTrace();
		}
	}
	
	public static Book getBook(String ISBN) {
		Book book = null;
		String query = "SELECT * FROM `books` WHERE ISBN='"+ISBN+"';";
		try {
			ResultSet rs = BookStoreConnection.executeQuery(query);
			while(rs.next()) {
				book = new Book(
							rs.getString(1),
							rs.getString(2),
							rs.getDouble(5),
							rs.getString(3),
							rs.getDate(4)
						);
						
			}
			return book;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	public static ArrayList<Book> getBooks() {
		ArrayList<Book> books = new ArrayList<Book>();
		String query = "SELECT * FROM `books` ;";
		try {
			ResultSet rs = BookStoreConnection.executeQuery(query);
			while(rs.next()) {
				books.add(
						new Book(
							rs.getString(1),
							rs.getString(2),
							rs.getDouble(5),
							rs.getString(3),
							rs.getDate(4)
						)
				);		
			}
			return books;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return books;
		}
	}
	
	public static void showBooks() {
		for(Book b: Book.getBooks()) {
			System.out.println(b);
		}
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	
}
