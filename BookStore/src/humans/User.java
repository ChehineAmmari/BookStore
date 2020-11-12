package humans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.BookStoreConnection;

public class User {
	
	private static int lastId = 0;
	private int id;
	private String username, password;
	
	public User() {}
	
	public User(String username, String password) {
		this.id = ++lastId;
		this.username = username;
		this.password = password;
	}

	public User(int id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}
	
	public User login() {
		User user = null;
		String query = "SELECT * FROM `users` WHERE username='"+this.getUsername()+"' and password='"+this.getPassword()+"';";
		try {
			ResultSet rs = BookStoreConnection.executeQuery(query);
			while(rs.next()) {
				if(rs.getString(4).equals("admin"))
					user = new Admin(
								rs.getInt(1),
								rs.getString(2),
								rs.getString(3)
							);
				else
					user = new Client(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3)
						);
			}
			return user;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String toString() {
		return "\nId: #"+this.id+" - "+this.username;
	}

	@Override
	public boolean equals(Object obj) {
		boolean b = false;
		if(obj instanceof User) {
			User p = (User)obj;
			b = this.username.equals(p.username) && this.password.equals(p.password) ? true : false; 
		}
		return b;
	}
	
	public void add() {
		String values = "("
				+null+", '"
				+this.getUsername()+"', '"
				+this.getPassword()+"', "
				+"'client')";
		String query = "INSERT INTO `users` VALUES "+values;
		try {
			BookStoreConnection.executeUpdate(query);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Can't insert new user to database");
			e.printStackTrace();
		}
	}
	
	public void remove() {
		String query = "DELETE FROM `users` WHERE idUser="+this.getId()+";";
		try {
			BookStoreConnection.executeUpdate(query);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("This user is already removed from the database");
			e.printStackTrace();
		}
	}
	
	public void update() {
		String condition = "WHERE idUser="+this.getId()+";";
		String query = "UPDATE `users` SET "
				+"username='"+this.getUsername()+"', "
				+"author='"+this.getPassword()+"', "
				+condition;
		try {
			BookStoreConnection.executeUpdate(query);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Can't update this user");
			e.printStackTrace();
		}
	}
	
	public static User getUser(int idUser) {
		User user = null;
		String query = "SELECT * FROM `users` WHERE idUser="+idUser+";";
		try {
			ResultSet rs = BookStoreConnection.executeQuery(query);
			while(rs.next()) {
				user = new User(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3)
						);
						
			}
			return user;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	public static ArrayList<User> getUsers() {
		ArrayList<User> users = new ArrayList<User>();
		String query = "SELECT * FROM `users` ;";
		try {
			ResultSet rs = BookStoreConnection.executeQuery(query);
			while(rs.next()) {
				users.add(
						new User(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3)
						)
				);		
			}
			return users;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return users;
		}
	}
	
	public static void showUsers() {
		for(User u: User.getUsers()) {
			System.out.println(u);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
