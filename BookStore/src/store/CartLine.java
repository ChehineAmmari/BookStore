package store;

import stock.Product;

public class CartLine {
	
	private Product product;
	private int quantity;
	
	public CartLine() {}

	public CartLine(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}
	
	public double calculateTotalPrice(int quantity, double price) {
		return price * quantity;
	}

	@Override
	public String toString() {
		return this.product
				+"\nQuantity: "+this.quantity
				+"\n\t=> "+this.calculateTotalPrice(quantity, product.getPrice())+" DT";
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
