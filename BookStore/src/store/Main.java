package store;

import java.util.Scanner;

import humans.Admin;
import humans.Author;
import humans.Client;
import humans.User;
import stock.Book;
import stock.Product;
import utilities.Display;
import utilities.PersistentData;

public class Main {
	
	static Scanner sc = new Scanner(System.in);
	
	private static Author addAuthor() {
		System.out.print("Type the author's CIN: ");
		int cin = sc.nextInt();
		System.out.println();
		
		System.out.print("Type the author's first name: ");
		String firstName = sc.next();
		System.out.println();
		
		System.out.print("Type the author's last name: ");
		String lastName = sc.next();
		System.out.println();
		
		System.out.print("Type the author's phone number: ");
		String phone = sc.next();
		System.out.println();
		
		Author author = new Author(cin,firstName,lastName,phone);
		author.add();
		return author;
	}

	public static void main(String[] args) {
		
		try {
			//initializing sonme data
			new PersistentData();
			
			String username, password;
			int choice,idBook,quantity;
			boolean isAdmin = false;
			boolean isClient = false;
			User user;
			Client client = null;
			ShoppingCart currentCart = null;
			Order order = null;
			
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
							
				admin: while(isAdmin) {
					System.out.println(Display.AdminMenu);
					do {
						System.out.print("-> ");
						choice = sc.nextInt();			
					} while(choice < 1 && choice > 6);
					
					switch(choice) {
						case 1:
							System.out.print("Give a username for the new client: ");
							username = sc.next();
							System.out.println();
							System.out.print("Give a password for the new client: ");
							password = sc.next();
							System.out.println();
							
							client = new Client(username,password);
							client.add();
							System.out.println("Client added successfully");
							break;
						case 2:
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
							System.out.println("\n1) New author\n2) Existing author");
							int authorChoice;
							do {
								System.out.print("-> ");
								authorChoice = sc.nextInt();			
							} while(authorChoice < 1 && authorChoice > 2);
							System.out.println();
							int cin = 0;
							switch(authorChoice) {
								case 1:
									cin = Main.addAuthor().getCin();
									break;
								case 2:
									System.out.print("Type the author'c CIN: ");
									cin = sc.nextInt();
									System.out.println();
							}
							
							System.out.print("Type the available quantity: ");
							int availableQuantity = sc.nextInt();
							System.out.println();
							
							Book book = new Book(ISBN,title,price,Author.getAuthor(cin),availableQuantity);
							book.add();
							System.out.println("Book added successfully");
							break;
							
						case 3:
							Main.addAuthor();
							break;
							
						case 4:
							Product.showAll();
							break;
							
						case 5: 
							Order.showAll();
							break;
							
						case 6:
							break admin;
					}
				}
				
				client: while(isClient) {
					System.out.println(Display.ClientMenu);
					do {
						System.out.print("-> ");
						choice = sc.nextInt();			
					} while(choice < 1 && choice > 4);
					
					switch(choice) {
						case 1:
							Product.showAll();
							break;
						case 2:
							currentCart = client.getCurrentCart();
							
							System.out.println("Type the book's ID");
							idBook = sc.nextInt();
							System.out.println();
							Book book = Book.getBook(idBook);
							
							System.out.println("Enter the quantity: ");
							quantity = sc.nextInt();
							System.out.println();
							
							CartLine item = new CartLine(book,quantity);
							currentCart.addItem(item);
							break;
							
						case 3: 
							currentCart = client.getCurrentCart();
							System.out.println(currentCart);
							break;
							
						case 4:
							for(Order o: client.getOrders()) {
								System.out.println(o);
							}
							break;	
							
						case 5: 
							order = new Order(client.getCurrentCart());
							order.add();
							client.addOrder(order);
							client.setCurrentCart(new ShoppingCart());
							break;
							
						case 6:
							break client;
					}
					
				}
			}
		} catch(Exception e) {
			System.out.println("Unknown error");
		}
		
		

	}

}
