package onlineShop.states;

import input.InputReader;
import onlineShop.data.Cart;
import onlineShop.data.Catalog;
import onlineShop.states.productSelection.CatalogView;
import onlineShop.states.productSelection.ProductDetails;
import onlineShop.states.productSelection.ProductSearch;

public class StateMachine implements Runnable {
	// Input
	private InputReader in;
	// Data
	private Catalog catalog;
	private Cart cart;
	// State
	private State state;
	// Control
	private Boolean running;
	
	public StateMachine(InputReader in, Catalog catalog, Cart cart) {
		this.in = in;
		this.catalog = catalog;
		this.cart = cart;
		this.state = State.Start;
		this.running = true;
	}
	
	private State nextState(String selection) {
		// Transitions
		if(selection.equals("_cancel")) return State.Start;
		if(selection.equals("_cart")) return State.CartContent;
		if(selection.equals("_checkout")) return State.OrderSummary;
		// #if Search
		if(selection.equals("_search")) return State.ProductSearch;
		// #endif
		return State.ProductDetails;
	}
	
	@Override
	public void run()  {
		try {
			this.state = State.Start;
			this.running = true;
			String selection = null;
			while(running) {
				// States
				switch(this.state) {
				case Start:
					this.state = State.Catalog;
					break;
				case Catalog:
					CatalogView catalogView = new CatalogView(this.in, this.catalog);
					catalogView.run();
					selection = catalogView.getSelection();
					break;
				case ProductDetails:
					ProductDetails productDetails = new ProductDetails(this.in, this.catalog, this.cart, selection);
					productDetails.run();
					selection = productDetails.getSelection();
					break;
				// #if Search
				case ProductSearch:
					ProductSearch productSearch = new ProductSearch(this.in, this.catalog);
					productSearch.run();
					selection = productSearch.getSelection();
					break;
				// #endif
				case CartContent:
					
					break;
				case OrderSummary:
					
					break;
				case PaymentChoice:
					
					break;
				// #if BankAccount
				case BankAccount:
					
					break;
				// #endif
				// #if ECoins
				case ECoins:
					
					break;
				// #endif
				// #if CreditCard
				case CreditCard:
					
					break;
				// #endif
				default:
					System.err.println("Invalid State in StateMachine");
					this.state = State.Start;
					break;
				}
				// Transitions
				this.state = this.nextState(selection);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
