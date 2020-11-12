package stock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import database.BookStoreConnection;

public class Product {

	private static int lastId = 0;
	private int id, availableQuantity;
	private String name;
	private double price;
	
	public Product() {}
	
	public Product(String name, double price, int availableQuantity) {
		lastId = this.getLastProductId();
		this.id = ++lastId;
		this.availableQuantity = availableQuantity;
		this.name = name;
		this.price = price;
	}

	public Product(int id, String name, double price, Date releaseDate, int availableQuantity) {
		this.id = id;
		this.availableQuantity = availableQuantity;
		this.name = name;
		this.price = price;
	}

	@Override
	public boolean equals(Object obj) {
		Product p = obj instanceof Product ? (Product)obj : null;
		return p != null && this.id == p.id ? true : false;
	}

	@Override
	public String toString() {
		return "\nID: #"+this.id
				+"\nName: "+this.name
				+"\nPrice: "+this.price
				+"\nAvailable Quantity: "+this.availableQuantity;
	}
	
	private int getLastProductId() {
		String query = "SELECT COUNT(idProduct) FROM `products` ;";
		try {
			ResultSet rs = BookStoreConnection.executeQuery(query);
			rs.next();
			return rs.getInt(1);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
