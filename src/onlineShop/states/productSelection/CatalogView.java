package onlineShop.states.productSelection;

import input.InputReader;
import onlineShop.data.Catalog;
import onlineShop.states.State;
import ui.Menu;
import util.Tuple;

public class CatalogView implements Runnable {
	private Menu<Tuple<State,String>> menu;
	private Tuple<State,String> selection;
	
	public CatalogView(InputReader in, Catalog catalog) throws Exception {
		this.menu = new Menu<Tuple<State,String>>(in, " CATALOG  ");
		for(String product : catalog.getProductNames()) {
			this.menu.addEntry(new Tuple<State,String>(State.ProductDetails, product), product);
		}
		this.menu.addText(Menu.ASTERISKS);
		// #if Search
		this.menu.addEntry(new Tuple<State,String>(State.ProductSearch, null), "SEARCH PRODUCT");
		// #endif
		this.menu.addEntry(new Tuple<State,String>(State.CartContent, null), "YOUR CART");
	}

	@Override
	public void run() {
		this.selection = this.menu.show();
	}
	
	public Tuple<State,String> getSelection() {
		return this.selection;
	}
}
