package onlineShop.states.productSelection;

import input.InputReader;
import onlineShop.data.Catalog;
import ui.Menu;

public class CatalogView implements Runnable {
	private Menu menu;
	private String selection;
	
	public CatalogView(InputReader in, Catalog catalog) throws Exception {
		// Init catalog menu
		this.menu = new Menu(in, " CATALOG ");
		for(String product : catalog.getProductNames()) {
			this.menu.addEntry(product, product);
		}
		this.menu.addText(Menu.ASTERISKS);
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
