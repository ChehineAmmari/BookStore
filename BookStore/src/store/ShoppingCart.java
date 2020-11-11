package store;

import java.util.ArrayList;

import utilities.PersistentData;

public class ShoppingCart {

	private static int lastId = 0;
	private int id;
	private ArrayList<CartLine> items = null;
	
	public ShoppingCart() {
		this.id = ++lastId;
		this.items = new ArrayList<CartLine>();
	}

	@Override
	public String toString() {
		String s = "\n###################Shopping Cart Content###################";
		for(CartLine item: items) {
			s += "\n"+item;
		}
		s += "\n######################################";
		return s;
	}

	public void addItem(CartLine item) {
		this.items.add(item);
	}
	
	public void add() {
		PersistentData.carts.add(this);
	}
	
	public void delete() {
		PersistentData.carts.remove(this);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<CartLine> getItems() {
		return items;
	}

	public void setItems(ArrayList<CartLine> items) {
		this.items = items;
	}
	
}
