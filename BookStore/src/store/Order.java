package store;

import utilities.PersistentData;

public class Order {

	private static int lastId = 0;
	private int id;
	private ShoppingCart cart;
	
	public Order() {}
	
	public Order(ShoppingCart cart) {
		this.id = ++lastId;
		this.cart = cart;
	}
	
	public Order(int id, ShoppingCart cart) {
		this.id = id;
		this.cart = cart;
	}

	@Override
	public boolean equals(Object obj) {
		Order o = obj instanceof Order ? (Order)obj : null;
		return o != null && this.id == o.id ? true: false;
	}

	@Override
	public String toString() {
		return "\nOrder ID: #"+this.id
				+"\n----Cart Content----\n"+this.cart;
	}
	
	public void add() {
		PersistentData.orders.add(this);
	}
	
	public void delete() {
		PersistentData.orders.remove(this);
	}
	
	public static void showAll() {
		for(Order o: PersistentData.orders) {
			System.out.println(o);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ShoppingCart getCart() {
		return cart;
	}

	public void setCart(ShoppingCart cart) {
		this.cart = cart;
	}
	
}
