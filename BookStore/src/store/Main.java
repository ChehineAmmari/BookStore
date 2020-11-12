package store;

import java.sql.Date;
import java.util.Scanner;

import humans.Admin;
import humans.Client;
import humans.User;
import stock.Book;
import utilities.Display;

public class Main {
	
	static Scanner sc = new Scanner(System.in);
	
	private static void addUser() {
		System.out.print("Give a username for the new client: ");
		String username = sc.next();
		System.out.println();
		System.out.print("Give a password for the new client: ");
		String password = sc.next();
		System.out.println();
		
		Client client = new Client(username,password);
		client.add();
		System.out.println("Client added successfully");
	}
	
	private static void addBook() {
		System.out.print("Type the book's ISBN: ");
		String ISBN = sc.next();
		System.out.println();
		
		System.out.print("Type the book's title: ");
		String title = sc.next();
		System.out.println();
		
		System.out.print("Type the book's price: ");
		double price = sc.nextDouble();
		System.out.println();
		
		System.out.print("Type the book's author: ");
		String authorName = sc.next();
		System.out.println();
		
		System.out.print("Type the release date(AAAA-MM-DD): ");
		String dateString = sc.next();
		Date releaseDate = Date.valueOf(dateString);
		System.out.println();
		
		Book book = new Book(ISBN,title,price,authorName,releaseDate);
		book.add();
		System.out.println("Book added successfully");
	}
	
	private static void updateBook() {
		System.out.println("Type the ISBN of the book to update");
		String ISBN = sc.next();
		
		Book book = Book.getBook(ISBN);
		System.out.print("Type the book's title: ");
		String title = sc.next();
		book.setTitle(title);
		System.out.println();
		
		System.out.print("Type the book's price: ");
		double price = sc.nextDouble();
		book.setPrice(price);
		System.out.println();
		
		System.out.print("Type the book's author: ");
		String authorName = sc.next();
		book.setAuthorName(authorName);
		System.out.println();
		
		System.out.print("Type the release date(AAAA-MM-DD): ");
		String dateString = sc.next();
		Date releaseDate = Date.valueOf(dateString);
		book.setReleaseDate(releaseDate);
		System.out.println();
		
		book.update();
	}
	
	private static void deleteBook() {
		System.out.println("Type the ISBN of the book to delete");
		String ISBN = sc.next();
		
		Book book = Book.getBook(ISBN);
		book.remove();
	}
	
	private static void addToCart(Client client) {
		System.out.println("Type the book's ISBN");
		String ISBN = sc.next();
		System.out.println();
		
		System.out.println("Enter the quantity: ");
		int quantity = sc.nextInt();
		System.out.println();
		
		
		Book book = Book.getBook(ISBN);
		
		CartLine item = new CartLine(client,book,quantity);
		item.add();
	}
	
	private static void updateCartItem(Client client) {
		System.out.println("*************Shopping Cart content*************");
		for(CartLine cl: CartLine.getShoppingCart(client)) {
			System.out.println(cl);
		}
		
		System.out.print("Type the item id: ");
		int idCartLine = sc.nextInt();
		System.out.println();
				
		System.out.print("Enter the new quantity");
		int quantity = sc.nextInt();
		System.out.println();
		
		CartLine item = CartLine.getCartLine(idCartLine);
		item.setQuantity(quantity);
		
		item.update();
	}
	
	private static void deleteCartItem(Client client) {
		System.out.println("*************Shopping Cart content*************");
		for(CartLine cl: CartLine.getShoppingCart(client)) {
			System.out.println(cl);
		}
		
		System.out.print("Type the item id: ");
		int idCartLine = sc.nextInt();
		System.out.println();
		
		CartLine item = CartLine.getCartLine(idCartLine);
		item.remove();
	}
	
	private static void purchase(Client client) {
		if(!CartLine.getShoppingCart(client).isEmpty()) {
			Order order = new Order(client);
			order.add();
			order.purchase();
		}
	}
	
	private static void adminMenu(boolean isAdmin) {
		int choice;
		admin: while(isAdmin) {
			System.out.println(Display.AdminMenu);
			do {
				System.out.print("-> ");
				choice = sc.nextInt();			
			} while(choice < 1 && choice > 6);
			switch(choice) {
				case 1:
					Main.addUser();
					break;
				case 2:
					Book.showBooks();
					break;
				case 3:
					Main.addBook();
					break;
				case 4:
					Main.updateBook();
					break;
				case 5:
					Main.deleteBook();
					break;
				case 6: 
					Order.showOrders();
					break;
					
				case 7:
					break admin;
			}
		}
	}
	
	private static void clientMenu(boolean isClient, Client client) {
		int choice;
		client: while(isClient) {
			System.out.println(Display.ClientMenu);
			do {
				System.out.print("-> ");
				choice = sc.nextInt();			
			} while(choice < 1 && choice > 4);
			
			switch(choice) {
				case 1:
					Book.showBooks();
					break;	
				case 2: 
					double sum = 0;
					for(CartLine cl: CartLine.getShoppingCart(client)) {
						System.out.println(cl);
						sum += cl.calculateTotalPrice();
					}
					System.out.println("############## Total price: "+sum+" ##############");
					break;
				case 3:							
					Main.addToCart(client);
					break;
				case 4:
					Main.updateCartItem(client);
					break;
				case 5:
					Main.deleteCartItem(client);
					break;
				case 6:
					for(Order o: Order.getOrders(client) ) {
						System.out.println(o);
					}
					break;	
				case 7: 
					Main.purchase(client);							
					break;
				case 8:
					break client;
			}
		}
	}

	public static void main(String[] args) {
		
		try {			
			String username, password;
			int choice;
			boolean isAdmin = false;
			boolean isClient = false;
			User user;
			Client client = null;
			
			System.out.println(Display.welcome);
			main : while(true) {
				System.out.println(Display.mainMenu);
				do {
					System.out.print("-> ");
					choice = sc.nextInt();			
				} while(choice < 1 && choice > 2);
				
				switch(choice) {
					case 1: 
						System.out.println(Display.loginMenu);
						System.out.print("Username: ");
						username = sc.next();
						System.out.println();
						System.out.print("Password: ");
						password = sc.next();
						System.out.println();
						
						user = new User(username,password).login();
						if(user == null) {
							System.out.println("Invalid username/password");
							continue main;
						}
						if(user instanceof Admin) {
							System.out.println("Admin");
							isAdmin = true;
							isClient = false;
						} else {
							System.out.println("Client");
							isAdmin = false;
							isClient = true;
							client = (Client)user;
						}
						break;
						
					case 2:
						System.exit(0);		
				}
							
				Main.adminMenu(isAdmin);
				
				Main.clientMenu(isClient, client);
				
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Unknown error");
		}	
	}
}
