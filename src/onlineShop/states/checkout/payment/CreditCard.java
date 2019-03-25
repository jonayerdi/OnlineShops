// #condition CreditCard
package onlineShop.states.checkout.payment;

import io.IO;
import onlineShop.data.PaymentData;
import onlineShop.data.PaymentType;

public class CreditCard implements Runnable {
	private IO io;
	private PaymentData paymentData;
	
	public CreditCard(IO io) {
		this.io = io;
	}

	@Override
	public void run() {
		this.io.writeLine("Enter credit card number >> ");
		String number = this.io.readLine();
		this.paymentData = new PaymentData(PaymentType.CreditCard, number);
	}
	
	public PaymentData getPaymentData() {
		return this.paymentData;
	}
}
