package onlineShop.states.checkout;

import io.IO;
import onlineShop.data.Cart;
import onlineShop.data.Catalog;
import onlineShop.states.State;
import ui.Menu;

public class OrderSummary implements Runnable {
	private Menu<State> menu;
	private State selection;
	
	public OrderSummary(IO io, Catalog catalog, Cart cart) throws Exception {
		double total = 0.0;
		this.menu = new Menu<State>(io, " ORDER SUMMARY ");
		for(String product : cart.getProductNames()) {
			int count = cart.getProductCount(product);
			double price = catalog.getProductPrice(product);
			double productTotal = price * count;
			this.menu.addText(product + " (" + count + ")\t" + price + " €/unit\t" + productTotal + " € total");
			total += productTotal;
		}
		this.menu.addText("TOTAL: " + total + " €");
		this.menu.addText(Menu.ASTERISKS);
		this.menu.addEntry(State.PaymentChoice, "PROCEED TO PAYMENT");
		this.menu.addEntry(State.Start, "CANCEL ORDER");
	}
	
	@Override
	public void run() {
		this.selection = this.menu.show();
	}
	
	public State getSelection() {
		return selection;
	}
}
