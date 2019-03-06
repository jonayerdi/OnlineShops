package onlineShop.states;

import input.InputReader;
import onlineShop.data.Cart;
import onlineShop.data.Catalog;
import onlineShop.states.cartContent.CartContent;
import onlineShop.states.checkout.OrderSummary;
import onlineShop.states.checkout.payment.PaymentChoice;
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
					OrderSummary orderSummary = new OrderSummary(this.in, this.catalog, this.cart);
					orderSummary.run();
					this.state.a = orderSummary.getSelection();
					break;
				case PaymentChoice:
					PaymentChoice paymentChoice = new PaymentChoice(this.in);
					paymentChoice.run();
					this.state.a = paymentChoice.getSelection();
					break;
				// #if BankAccount
				case BankAccount:
					System.out.println("Enter account number >> ");
					this.in.nextLine();
					this.state.a = State.PaymentValidation;
					break;
				// #endif
				// #if ECoins
				case ECoins:
					System.out.print("Enter E-coin whatever >> ");
					this.in.nextLine();
					this.state.a = State.PaymentValidation;
					break;
				// #endif
				// #if CreditCard
				case CreditCard:
					System.out.print("Enter credit card number >> ");
					this.in.nextLine();
					this.state.a = State.PaymentValidation;
					break;
				// #endif
				case PaymentValidation:
					System.out.println("Order processed :)");
					this.cart.clear();
					this.state.a = State.End;
					break;
				case End:
					this.running = false;
					break;
				default:
					System.err.println("Invalid State in StateMachine");
					this.state.a = State.Start;
					break;
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
