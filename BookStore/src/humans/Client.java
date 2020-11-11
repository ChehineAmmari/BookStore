package humans;

import java.util.ArrayList;

import store.Order;
import store.ShoppingCart;

public class Client extends User {
	
	private ShoppingCart currentCart = null;
	private ArrayList<Order> orders = null;

	public Client() {}
	
	public Client(String username, String password) {
		super(username,password);
		this.currentCart = new ShoppingCart();
		this.orders = new ArrayList<Order>();
	}
	
	@Override
	public String toString() {
		String s = super.toString();
		for(Order o: this.orders) {
			s += "\n"+o;
		}
		s += this.currentCart.toString();
		return s;
	}
	
	public void addOrder(Order order) {
		this.orders.add(order);
	}

	public ShoppingCart getCurrentCart() {
		return currentCart;
	}

	public void setCurrentCart(ShoppingCart currentCart) {
		this.currentCart = currentCart;
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
	
}
