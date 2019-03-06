// #condition Search
package onlineShop.states.productSelection;

import java.util.HashSet;
import java.util.Set;

import input.InputReader;
import onlineShop.data.Catalog;
import ui.Menu;
import util.Tuple;

public class ProductSearch implements Runnable {
	private InputReader in;
	private Catalog catalog;
	private Menu menu;
	private String selection;
	
	public ProductSearch(InputReader in, Catalog catalog) throws Exception {
		this.in = in;
		this.catalog = catalog;
		// Init catalog menu
		this.menu = new Menu(in, " SEARCH PRODUCT ");
		this.menu.addEntry("_search", "ENTER SEARCH TERM");
		this.menu.addEntry("_catalog", "BACK TO CATALOG");
		this.menu.addEntry("_cart_content", "YOUR CART");
		this.menu.addEntry("_order_summary", "CHECKOUT");
	}
	
	private Set<String> searchByProductName(String query) {
		Set<String> results = new HashSet<String>();
		for(String item : this.catalog.getProductNames()) {
			if(item.contains(query)) {
				results.add(item);
			}
		}
		return results;
	}
	
	private Menu createMenuFromSearchResults(Set<String> products) {
		Menu resultsMenu = new Menu(this.in, " SEARCH RESULTS ");
		try {
			for(String product : products) {
				this.menu.addEntry(product, product);
			}
			this.menu.addText(Menu.ASTERISKS);
			resultsMenu.addEntry("_search", "ENTER SEARCH TERM");
			resultsMenu.addEntry("_catalog", "BACK TO CATALOG");
			resultsMenu.addEntry("_cart_content", "YOUR CART");
			resultsMenu.addEntry("_order_summary", "CHECKOUT");
		} catch(Exception e) { }
		return resultsMenu;
	}

	@Override
	public void run() {
		this.selection = this.menu.show();
		while(this.selection.equals("_search")) {
			System.out.print("Enter search query >> ");
			String query = this.in.nextLine();
			Menu resultsMenu = this.createMenuFromSearchResults(this.searchByProductName(query));
			this.selection = resultsMenu.show();
		}
	}
	
	public Tuple<String,String> getSelection() {
		if(this.selection.startsWith("_")) {
			return new Tuple<String, String>(selection, null);
		} else {
			return new Tuple<String, String>("_product_details", selection);
		}
	}
}
