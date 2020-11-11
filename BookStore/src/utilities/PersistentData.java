package utilities;

import java.util.ArrayList;

import humans.Admin;
import humans.Author;
import humans.Client;
import humans.User;
import stock.Book;
import stock.Product;
import store.CartLine;
import store.Order;
import store.ShoppingCart;

public class PersistentData {
	
	public static ArrayList<User> users = new ArrayList<User>();
	public static ArrayList<Product> products = new ArrayList<Product>();
	public static ArrayList<Order> orders = new ArrayList<Order>();
	public static ArrayList<ShoppingCart> carts = new ArrayList<ShoppingCart>();
	public static ArrayList<Author> authors = new ArrayList<Author>();
	
	private Admin admin = new Admin("chehine","0000");
	
	private Client client1 = new Client("John","000000");
	private Client client2 = new Client("Joe","0000");
	
	private Author rob = new Author(111111,"Rob","Pike‏","11111111");
	private Book book1 = new Book("9780133133417","The Practice of Programming",150,rob,10);
	private Book book2 = new Book("9780139376818","The Unix Programming Environment",2,rob,10);
	
	public PersistentData() {
			
		users.add(admin);
		users.add(client1);
		users.add(client2);
		
		authors.add(rob);
		
		book1.add();
		book2.add();
		
		ShoppingCart cart1 = new ShoppingCart();
		ShoppingCart cart2 = new ShoppingCart();
		carts.add(cart1);
		carts.add(cart2);
		client1.setCurrentCart(cart1);
		client2.setCurrentCart(cart2);
		
		CartLine items1 = new CartLine(book1,2);
		cart1.addItem(items1);
		CartLine items2 = new CartLine(book2,5);
		cart1.addItem(items2);
		
		
		
	}
	
}
