package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookStoreConnection {
	
	private static Connection connection = null;
	
	public BookStoreConnection() throws ClassNotFoundException, SQLException {
		
		Class.forName(DatabaseConfig.DRIVER);
		connection = DriverManager.getConnection(DatabaseConfig.URL,DatabaseConfig.USER,DatabaseConfig.PASS);
		
	}
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if(BookStoreConnection.connection == null)
			new BookStoreConnection();
		return BookStoreConnection.connection;
	}
	
	public static int executeUpdate(String query) throws ClassNotFoundException, SQLException {
		Connection connection = getConnection();
		Statement statement = connection.createStatement();
		int nb = statement.executeUpdate(query);
		System.out.println(nb+" row(s) affected!");
		return nb;
	}
	
	public static ResultSet executeQuery(String query) throws ClassNotFoundException, SQLException {
		Connection connection = getConnection();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		return rs;
		
	}
	
}
