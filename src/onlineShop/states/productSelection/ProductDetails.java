package onlineShop.states.productSelection;

import input.InputReader;
import onlineShop.data.Cart;
import onlineShop.data.Catalog;
import onlineShop.states.State;
import ui.Menu;

public class ProductDetails implements Runnable {
	private InputReader in;
	private Catalog catalog;
	private Cart cart;
	private String product;
	private Menu<State> menu;
	private State selection;
	
	public ProductDetails(InputReader in, Catalog catalog, Cart cart, String product) throws Exception {
		this.in = in;
		this.catalog = catalog;
		this.cart = cart;
		this.product = product;
		this.makeMenu();
	}
	
	public void makeMenu() throws Exception {
		this.menu = new Menu<State>(this.in, " PRODUCT DETAILS  ");
		this.menu.addText("Name: " + this.product);
		this.menu.addText("Price: " + catalog.getProductPrice(this.product));
		this.menu.addText("In cart: " + cart.getProductCount(this.product));
		this.menu.addText(Menu.ASTERISKS);
		this.menu.addEntry(State.ProductDetails, "ADD TO CART");
		this.menu.addEntry(State.Catalog, "BACK TO CATALOG");
		// #if Search
		this.menu.addEntry(State.ProductSearch, "SEARCH PRODUCT");
		// #endif
		this.menu.addEntry(State.CartContent, "YOUR CART");
	}

	@Override
	public void run() {
		this.selection = this.menu.show();
		try {
			while(this.selection.equals(State.ProductDetails)) {
				this.cart.addProductToCart(this.product);
				this.makeMenu();
				this.selection = this.menu.show();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public State getSelection() {
		return this.selection;
	}
}
