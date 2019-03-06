package onlineShop.states.productSelection;

import input.InputReader;
import onlineShop.data.Cart;
import onlineShop.data.Catalog;
import onlineShop.states.State;
import ui.Menu;

public class ProductDetails implements Runnable {
	private Menu<State> menu;
	private State selection;
	
	public ProductDetails(InputReader in, Catalog catalog, Cart cart, String product) throws Exception {
		this.menu = new Menu<State>(in, " PRODUCT DETAILS ");
		this.menu.addText("Name: " + product);
		this.menu.addText("Price: " + catalog.getProductPrice(product));
		this.menu.addText("In cart: " + cart.getProductCount(product));
		this.menu.addText(Menu.ASTERISKS);
		this.menu.addEntry(State.Catalog, "BACK TO CATALOG");
		// #if Search
		this.menu.addEntry(State.ProductSearch, "SEARCH PRODUCT");
		// #endif
		this.menu.addEntry(State.CartContent, "YOUR CART");
	}

	@Override
	public void run() {
		this.selection = this.menu.show();
	}
	
	public State getSelection() {
		return this.selection;
	}
}
