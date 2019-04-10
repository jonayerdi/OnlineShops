package onlineShop.states.checkout.payment;

import io.IO;
import onlineShop.data.Cart;
import onlineShop.data.PaymentData;
import onlineShop.states.State;

public class PaymentValidation implements Runnable {
	private IO io;
	private PaymentData paymentData;
	private State nextState;
	private Cart cart;
	
	public PaymentValidation(IO io, PaymentData paymentData, Cart cart) {
		this.io = io;
		this.paymentData = paymentData;
		this.cart = cart;
	}

	@Override
	public void run() {
		if(this.paymentData.number.equals("")) {
			this.io.writeLine("Invalid payment data");
			this.nextState = State.OrderSummary;
		} else {
			this.io.writeLine("Order processed :)");
			this.cart.clear();
			this.nextState = State.End;
		}
	}
	
	public State getNextState() {
		return this.nextState;
	}

}
