package onlineShop.states.productSelection;

import input.InputReader;
import onlineShop.data.Cart;
import onlineShop.data.Catalog;
import ui.Menu;

public class ProductDetails implements Runnable {
	private Menu menu;
	private String selection;
	
	public ProductDetails(InputReader in, Catalog catalog, Cart cart, String product) throws Exception {
		// Init catalog menu
		this.menu = new Menu(in, " PRODUCT DETAILS ");
		this.menu.addText("Name: " + product);
		this.menu.addText("Price: " + catalog.getProductPrice(product));
		this.menu.addText("In cart: " + cart.getProductCount(product));
		this.menu.addText(Menu.ASTERISKS);
		this.menu.addEntry("_catalog", "BACK TO CATALOG");
		// #if Search
		this.menu.addEntry("_search", "SEARCH PRODUCT");
		// #endif
		this.menu.addEntry("_cart", "YOUR CART");
		this.menu.addEntry("_checkout", "CHECKOUT");
	}

	@Override
	public void run() {
		this.selection = this.menu.show();
	}
	
	public String getSelection() {
		return selection;
	}
}
