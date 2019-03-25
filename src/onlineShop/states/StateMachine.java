package onlineShop.states;

import io.IO;
import onlineShop.data.Cart;
import onlineShop.data.Catalog;
import onlineShop.data.PaymentData;
import onlineShop.states.cartContent.CartContent;
import onlineShop.states.checkout.OrderSummary;
import onlineShop.states.checkout.payment.PaymentChoice;
import onlineShop.states.checkout.payment.PaymentValidation;
import onlineShop.states.productSelection.CatalogView;
import onlineShop.states.productSelection.ProductDetails;
//#if Search
import onlineShop.states.productSelection.ProductSearch;
//#endif
//#if BankAccount
import onlineShop.states.checkout.payment.BankAccount;
//#endif
//#if CreditCard
import onlineShop.states.checkout.payment.CreditCard;
//#endif
//#if ECoins
import onlineShop.states.checkout.payment.ECoins;
//#endif

public class StateMachine implements Runnable {
	// Input
	private IO io;
	// Data
	private Catalog catalog;
	private Cart cart;
	// Temporal data, only used to pass data from some states to others
	private String selectedProduct;
	private PaymentData paymentData;
	// Current state
	private State state;
	// Control
	private Boolean running;
	
	public StateMachine(IO io, Catalog catalog, Cart cart) {
		this.io = io;
		this.catalog = catalog;
		this.cart = cart;
		this.state = State.Start;
		this.running = true;
	}
	
	@Override
	public void run()  {
		try {
			this.state = State.Start;
			this.running = true;
			while(running) {
				// States
				switch(this.state) {
				case Start:
					this.state = State.Catalog;
					break;
				case Catalog:
					CatalogView catalogView = new CatalogView(this.io, this.catalog);
					catalogView.run();
					this.state = catalogView.getNextState();
					this.selectedProduct = catalogView.getSelectedProduct();
					break;
				case ProductDetails:
					ProductDetails productDetails = new ProductDetails(this.io, this.catalog, this.cart, this.selectedProduct);
					productDetails.run();
					this.state = productDetails.getNextState();
					break;
				// #if Search
				case ProductSearch:
					ProductSearch productSearch = new ProductSearch(this.io, this.catalog);
					productSearch.run();
					this.state = productSearch.getNextState();
					this.selectedProduct = productSearch.getSelectedProduct();
					break;
				// #endif
				case CartContent:
					CartContent cartContent = new CartContent(this.io, this.cart);
					cartContent.run();
					this.state = cartContent.getNextState();
					break;
				case OrderSummary:
					OrderSummary orderSummary = new OrderSummary(this.io, this.catalog, this.cart);
					orderSummary.run();
					this.state = orderSummary.getNextState();
					break;
				case PaymentChoice:
					PaymentChoice paymentChoice = new PaymentChoice(this.io);
					paymentChoice.run();
					this.state = paymentChoice.getNextState();
					break;
				// #if BankAccount
				case BankAccount:
					BankAccount bankAccount = new BankAccount(this.io);
					bankAccount.run();
					this.paymentData = bankAccount.getPaymentData();
					this.state = State.PaymentValidation;
					break;
				// #endif
				// #if ECoins
				case ECoins:
					ECoins eCoins = new ECoins(this.io);
					eCoins.run();
					this.paymentData = eCoins.getPaymentData();
					this.state = State.PaymentValidation;
					break;
				// #endif
				// #if CreditCard
				case CreditCard:
					CreditCard creditCard = new CreditCard(this.io);
					creditCard.run();
					this.paymentData = creditCard.getPaymentData();
					this.state = State.PaymentValidation;
					break;
				// #endif
				case PaymentValidation:
					PaymentValidation paymentValidation = new PaymentValidation(this.io, this.paymentData, this.cart);
					paymentValidation.run();
					this.state = paymentValidation.getNextState();
					break;
				case End:
					this.running = false;
					break;
				default:
					this.io.writeLine("Invalid State in StateMachine");
					this.state = State.Start;
					break;
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
