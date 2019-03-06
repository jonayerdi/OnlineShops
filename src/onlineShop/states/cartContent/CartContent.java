package onlineShop.states.cartContent;

import io.IO;
import onlineShop.data.Cart;
import onlineShop.states.State;
import ui.Menu;
import util.Tuple;

public class CartContent implements Runnable {
	private IO io;
	private Cart cart;
	private Menu<Tuple<State,String>> menu;
	private Tuple<State,String> selection;
	
	public CartContent(IO io, Cart cart) throws Exception {
		this.io = io;
		this.cart = cart;
		this.makeMenu();
	}
	
	public void makeMenu() throws Exception {
		this.menu = new Menu<Tuple<State,String>>(this.io, " YOUR CART  ");
		this.menu.addText("Select product to remove:");
		for(String product : cart.getProductNames()) {
			this.menu.addEntry(new Tuple<State,String>(State.ProductDetails, product), product + " (" + cart.getProductCount(product) + ")");
		}
		this.menu.addText(Menu.ASTERISKS);
		this.menu.addEntry(new Tuple<State,String>(State.Catalog, null), "BACK TO CATALOG");
		this.menu.addEntry(new Tuple<State,String>(State.OrderSummary, null), "CHECKOUT");
	}
	
	@Override
	public void run() {
		this.selection = this.menu.show();
		try {
			while(this.selection.a.equals(State.ProductDetails)) {
				this.cart.removeProductFromCart(this.selection.b);
				this.makeMenu();
				this.selection = this.menu.show();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public State getSelection() {
		return selection.a;
	}
}
