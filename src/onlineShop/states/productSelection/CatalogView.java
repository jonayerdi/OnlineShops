package onlineShop.states.productSelection;

import input.InputReader;
import onlineShop.data.Catalog;
import ui.Menu;
import util.Tuple;

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
		this.menu.addEntry("_cart_content", "YOUR CART");
		this.menu.addEntry("_order_summary", "CHECKOUT");
	}

	@Override
	public void run() {
		this.selection = this.menu.show();
	}
	
	public Tuple<String,String> getSelection() {
		if(this.selection.startsWith("_")) {
			return new Tuple<String, String>(selection, null);
		} else {
			return new Tuple<String, String>("_product_details", selection);
		}
	}
}
