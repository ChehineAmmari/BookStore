package store;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.BookStoreConnection;
import humans.Client;
import humans.User;
/*import humans.User;
import utilities.PersistentData;*/

public class Order {

	private static int lastId = 0;
	private int id;
	private User user;
	private Date orderDate;
	
	public Order() {}
	
	public Order(User user) {
		lastId = this.getLastOrderId();
		this.id = ++lastId;
		this.user = user;
	}
	
	public Order(int id, User user) {
		this.id = id;
		this.user = user;
	}
	
	public Order(int id, User user, Date orderDate) {
		this.id = id;
		this.user = user;
		this.orderDate = orderDate;
	}

	@Override
	public boolean equals(Object obj) {
		Order o = obj instanceof Order ? (Order)obj : null;
		return o != null && this.id == o.id ? true: false;
	}

	@Override
	public String toString() {
		return "\nOrder ID: #"+this.id;
	}
	
	public void add() {
		String values = "("
				+null+", '"
				+new Date(System.currentTimeMillis())+"', "
				+this.getUser().getId()+""
				+ ")";
		String query = "INSERT INTO `orders` VALUES "+values;
		try {
			BookStoreConnection.executeUpdate(query);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Can't insert new order to database");
			e.printStackTrace();
		}
	}
	
	public void remove() {
		String query = "DELETE FROM `orders` WHERE idOrder="+this.getId()+";";
		try {
			BookStoreConnection.executeUpdate(query);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("This order is already removed from the database");
			e.printStackTrace();
		}
	}
	
	public void update() {
		String condition = "WHERE idOrder="+this.getId()+";";
		String query = "UPDATE `orders` SET "
				+"idCart="+this.getUser().getId()+", "
				+"orderDate='"+this.getOrderDate()+"' "
				+condition;
		try {
			BookStoreConnection.executeUpdate(query);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Can't update this order");
			e.printStackTrace();
		}
	}
	
	public void purchase() {
		String condition = "WHERE idUser="+this.getUser().getId()+" "
				+"AND purchased="+false
				+";";
		String query = "UPDATE `cartLines` SET "
				+"purchased="+true+" , "
				+"idOrder='"+this.getId()+"' "
				+condition;
		try {
			BookStoreConnection.executeUpdate(query);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Can't purchase this order");
			e.printStackTrace();
		}
	}
	
	public static Order getOrder(int idOrder) {
		Order order = null;
		String query = "SELECT * FROM `orders` WHERE idOrder="+idOrder+";";
		try {
			ResultSet rs = BookStoreConnection.executeQuery(query);
			while(rs.next()) {
				order = new Order(
							rs.getInt(1),
							User.getUser(rs.getInt(3)),
							rs.getDate(2)
						);			
			}
			return order;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	public static ArrayList<Order> getOrders(Client client) {
		ArrayList<Order> orders = new ArrayList<Order>();
		String query = "SELECT * FROM `orders` WHERE idUser="+client.getId()+";";
		try {
			ResultSet rs = BookStoreConnection.executeQuery(query);
			while(rs.next()) {
				orders.add(
						new Order(
							rs.getInt(1),
							User.getUser(rs.getInt(3)),
							rs.getDate(2)
						)
				);			
			}
			return orders;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	public static ArrayList<Order> getOrders() {
		ArrayList<Order> orders = new ArrayList<Order>();
		String query = "SELECT * FROM `orders` ;";
		try {
			ResultSet rs = BookStoreConnection.executeQuery(query);
			while(rs.next()) {
				orders.add(
						new Order(
							rs.getInt(1),
							User.getUser(rs.getInt(3)),
							rs.getDate(2)
						)
				);		
			}
			return orders;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return orders;
		}
	}
	
	public static void showOrders() {
		for(Order o: Order.getOrders()) {
			System.out.println(o);
		}
	}
	
	private int getLastOrderId() {
		String query = "SELECT COUNT(idOrder) FROM `orders` ;";
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

	public User getUser() {
		return user;
	}

	public void setCart(User user) {
		this.user = user;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	
	
}
