package onlineShop.states;

import input.InputReader;
import onlineShop.data.Cart;
import onlineShop.data.Catalog;
import onlineShop.states.productSelection.CatalogView;
import onlineShop.states.productSelection.ProductDetails;
import onlineShop.states.productSelection.ProductSearch;
import util.Tuple;

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
		if(selection.equals("_start")) return State.Start;
		if(selection.equals("_catalog")) return State.Catalog;
		if(selection.equals("_product_details")) return State.ProductDetails;
		// #if Search
		if(selection.equals("_search")) return State.ProductSearch;
		// #endif
		if(selection.equals("_cart_content")) return State.CartContent;
		if(selection.equals("_order_summary")) return State.OrderSummary;
		if(selection.equals("_payment_choice")) return State.PaymentChoice;
		// #if BankAccount
		if(selection.equals("_bank_account")) return State.BankAccount;
		// #endif
		// #if ECoins
		if(selection.equals("_ecoins")) return State.ECoins;
		// #endif
		// #if CreditCard
		if(selection.equals("_credit_card")) return State.CreditCard;
		// #endif
		return State.Invalid;
	}
	
	@Override
	public void run()  {
		try {
			this.state = State.Start;
			this.running = true;
			// Tuple containing selected state and selected product
			Tuple<String,String> selection = new Tuple<String, String>("_start", null);
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
					ProductDetails productDetails = new ProductDetails(this.in, this.catalog, this.cart, selection.b);
					productDetails.run();
					selection.a = productDetails.getSelection();
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
				this.state = this.nextState(selection.a);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
