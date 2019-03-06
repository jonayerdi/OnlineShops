package onlineShop.states.cartContent;

import input.InputReader;
import onlineShop.data.Cart;
import onlineShop.states.State;
import ui.Menu;
import util.Tuple;

public class CartContent implements Runnable {
	private InputReader in;
	private Cart cart;
	private Menu<Tuple<State,String>> menu;
	private Tuple<State,String> selection;
	
	public CartContent(InputReader in, Cart cart) throws Exception {
		this.in = in;
		this.cart = cart;
		this.makeMenu();
	}
	
	public void makeMenu() throws Exception {
		this.menu = new Menu<Tuple<State,String>>(this.in, " YOUR CART ");
		this.menu.addText("Select product to remove");
		this.menu.addText(Menu.ASTERISKS);
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
