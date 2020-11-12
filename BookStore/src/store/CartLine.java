package store;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.BookStoreConnection;
import humans.Client;
import humans.User;
import stock.Book;

/**
 * @author chehine
 *
 */
public class CartLine {
	
	private static int lastId = 0;
	private Book book;
	private int id, quantity;
	private User user;
	private Order order;
	private boolean purchased;
	
	public CartLine() {}
	
	public CartLine(User user, Book book, int quantity) {
		lastId = this.getLastCartLineId();
		this.user = user;
		this.book = book;
		this.quantity = quantity;
		this.id = ++lastId;
		this.purchased = false;
		this.order = null;
	}
	
	public CartLine(int id, User user, Book book, Order order, int quantity, boolean purchased) {
		this.user = user;
		this.book = book;
		this.quantity = quantity;
		this.id = id;
		this.purchased = purchased;
		this.order = order;
	}

	@Override
	public String toString() {
		return this.book
				+"\nId: #"+this.getId()
				+"\nQuantity: "+this.quantity
				+"\n\t=> "+this.calculateTotalPrice(quantity, book.getPrice())+" DT";
	}
	
	
	/**
	 * Insert a new product line (product,quantity) into the database
	 */
	public void add() {
		String values = "("
				+null+", "
				+this.getQuantity()+", '"
				+this.getBook().getISBN()+"', "
				+this.getUser().getId()+", "
				+null+", "
				+this.isPurchased()
				+ ")";
		String query = "INSERT INTO `cartLines` VALUES "+values;
		try {
			BookStoreConnection.executeUpdate(query);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Can't insert new cart item cart to database");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Delete an existing product line (product,quantity) from the database
	 */
	public void remove() {
		String query = "DELETE FROM `cartLines` WHERE idCartLine="+this.getId()+";";
		try {
			BookStoreConnection.executeUpdate(query);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("This cart item is already removed from the database");
			e.printStackTrace();
		}
	}
	
	/**
	 * Update a product line (product,quantity) in the database
	 */
	public void update() {
		String idOrder;
		if(this.getOrder() == null)
			idOrder = "idOrder="+null;
		else
			idOrder = "idOrder="+this.getOrder().getId();
		String condition = "WHERE idCartLine="+this.getId()+";";
		String query = "UPDATE `cartLines` SET "
				+"quantity="+this.getQuantity()+", "
				+"ISBN='"+this.getBook().getISBN()+"', "
				+"idUser="+this.getUser().getId()+", "
				+idOrder+", "
				+"purchased="+this.isPurchased()+" "
				+condition;
		try {
			BookStoreConnection.executeUpdate(query);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Can't update this cart item");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @param idCartLine int
	 * @return CartLine 
	 * returns the product line (product,quantity)
	 */
	public static CartLine getCartLine(int idCartLine) {
		CartLine cartLine = null;
		String query = "SELECT * FROM `cartLines` WHERE idCartLine="+idCartLine+";";
		try {
			ResultSet rs = BookStoreConnection.executeQuery(query);
			while(rs.next()) {
				cartLine = new CartLine(
							rs.getInt(1),
							User.getUser(rs.getInt(4)),
							Book.getBook(rs.getString(3)),
							Order.getOrder(rs.getInt(5)),
							rs.getInt(2),
							rs.getBoolean(6)
						);			
			}
			return cartLine;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	
	/**
	 * @param client Client
	 * @return ArrayList<CartLine>
	 * returns a collection of product line for a specific user => (Shopping Cart)
	 */
	public static ArrayList<CartLine> getShoppingCart(Client client) {
		ArrayList<CartLine> cartLines = new ArrayList<CartLine>();
		String query = "SELECT * FROM `cartLines` WHERE idUser="+client.getId()+" AND purchased=false;";
		try {
			ResultSet rs = BookStoreConnection.executeQuery(query);
			while(rs.next()) {
				cartLines.add(new CartLine(
							rs.getInt(1),
							User.getUser(rs.getInt(4)),
							Book.getBook(rs.getString(3)),
							Order.getOrder(rs.getInt(5)),
							rs.getInt(2),
							rs.getBoolean(6)
						)
				);
			}
			return cartLines;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	//Used when user wants to know all products that he ordered
	/*public static ArrayList<CartLine> getUserCartLines(Client client) {
		ArrayList<CartLine> cartLines = new ArrayList<CartLine>();
		String query = "SELECT * FROM `cartLines` WHERE idUser="+client.getId()+";";
		try {
			ResultSet rs = BookStoreConnection.executeQuery(query);
			while(rs.next()) {
				cartLines.add(new CartLine(
							rs.getInt(1),
							User.getUser(rs.getInt(4)),
							Book.getBook(rs.getString(3)),
							Order.getOrder(rs.getInt(5)),
							rs.getInt(2),
							rs.getBoolean(6)
						)
				);
			}
			return cartLines;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}	
	}*/
	
	/**
	 * @return double
	 * returns the amount of a product line (product,quantity)
	 */
	public double calculateTotalPrice() {
		return this.getBook().getPrice() * this.getQuantity();
	}	
	
	/**
	 * @param quantity double
	 * @param price double
	 * @return double
	 * returns the total price for a given quantity
	 */
	public double calculateTotalPrice(int quantity, double price) {
		return price * quantity;
	}
	
	/**
	 * @return int
	 * returns the last id in the table of cartLines in the database
	 */
	private int getLastCartLineId() {
		String query = "SELECT COUNT(idCartLine) FROM `cartLines` ;";
		try {
			ResultSet rs = BookStoreConnection.executeQuery(query);
			rs.next();
			return rs.getInt(1);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public Book getBook() {
		return book;
	}

	public void setProduct(Book book) {
		this.book = book;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public User getUser() {
		return user;
	}

	public void setCart(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isPurchased() {
		return purchased;
	}

	public void setPurchased(boolean purchased) {
		this.purchased = purchased;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	
}
