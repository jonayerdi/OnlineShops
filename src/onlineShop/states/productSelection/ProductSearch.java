// #condition Search
package onlineShop.states.productSelection;

import java.util.ArrayList;
import java.util.List;

import io.IO;
import onlineShop.data.Catalog;
import onlineShop.states.State;
import ui.Menu;
import util.Tuple;

public class ProductSearch implements Runnable {
	private IO io;
	private Catalog catalog;
	private Menu<Tuple<State,String>> menu;
	private Tuple<State,String> selection;
	
	public ProductSearch(IO io, Catalog catalog) throws Exception {
		this.io = io;
		this.catalog = catalog;
		this.menu = new Menu<Tuple<State,String>>(io, " SEARCH PRODUCT ");
		this.menu.addEntry(new Tuple<State,String>(State.ProductSearch, null), "ENTER SEARCH TERM");
		this.menu.addEntry(new Tuple<State,String>(State.Catalog, null), "BACK TO CATALOG");
		this.menu.addEntry(new Tuple<State,String>(State.CartContent, null), "YOUR CART");
	}
	
	private List<String> searchByProductName(String query) {
		List<String> results = new ArrayList<String>();
		for(String item : this.catalog.getProductNames()) {
			if(item.contains(query)) {
				results.add(item);
			}
		}
		return results;
	}
	
	private Menu<Tuple<State,String>> createMenuFromSearchResults(List<String> products) {
		Menu<Tuple<State,String>> resultsMenu = new Menu<Tuple<State,String>>(this.io, " SEARCH RESULTS ");
		try {
			for(String product : products) {
				resultsMenu.addEntry(new Tuple<State,String>(State.ProductDetails, product), product);
			}
			resultsMenu.addText(Menu.ASTERISKS);
			resultsMenu.addEntry(new Tuple<State,String>(State.ProductSearch, null), "ENTER SEARCH TERM");
			resultsMenu.addEntry(new Tuple<State,String>(State.Catalog, null), "BACK TO CATALOG");
			resultsMenu.addEntry(new Tuple<State,String>(State.CartContent, null), "YOUR CART");
		} catch(Exception e) { }
		return resultsMenu;
	}

	@Override
	public void run() {
		this.selection = this.menu.show();
		while(this.selection.a.equals(State.ProductSearch)) {
			System.out.print("Enter search query >> ");
			String query = this.io.readLine();
			Menu<Tuple<State,String>> resultsMenu = this.createMenuFromSearchResults(this.searchByProductName(query));
			this.selection = resultsMenu.show();
		}
	}
	
	public State getNextState() {
		return this.selection.a;
	}
	
	public String getSelectedProduct() {
		return this.selection.b;
	}
}
