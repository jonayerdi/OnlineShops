package onlineShop.states;

import input.InputReader;
import onlineShop.data.Cart;
import onlineShop.data.Catalog;
import onlineShop.states.cartContent.CartContent;
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
	// State: Tuple containing selected state and selected product
	private Tuple<State,String> state;
	// Control
	private Boolean running;
	
	public StateMachine(InputReader in, Catalog catalog, Cart cart) {
		this.in = in;
		this.catalog = catalog;
		this.cart = cart;
		this.state = new Tuple<State,String>(State.Start, null);
		this.running = true;
	}
	
	@Override
	public void run()  {
		try {
			this.state = new Tuple<State,String>(State.Start, null);
			this.running = true;
			while(running) {
				// States
				switch(this.state.a) {
				case Start:
					this.state.a = State.Catalog;
					break;
				case Catalog:
					CatalogView catalogView = new CatalogView(this.in, this.catalog);
					catalogView.run();
					this.state = catalogView.getSelection();
					break;
				case ProductDetails:
					ProductDetails productDetails = new ProductDetails(this.in, this.catalog, this.cart, this.state.b);
					productDetails.run();
					this.state.a = productDetails.getSelection();
					break;
				// #if Search
				case ProductSearch:
					ProductSearch productSearch = new ProductSearch(this.in, this.catalog);
					productSearch.run();
					this.state = productSearch.getSelection();
					break;
				// #endif
				case CartContent:
					CartContent cartContent = new CartContent(this.in, this.cart);
					cartContent.run();
					this.state.a = cartContent.getSelection();
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
				case PaymentValidation:
					
					break;
				default:
					System.err.println("Invalid State in StateMachine");
					this.state.a = State.Start;
					break;
				}
				// Transitions
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
