// #condition ECoins
package onlineShop.states.checkout.payment;

import io.IO;
import onlineShop.data.PaymentData;
import onlineShop.data.PaymentType;

public class ECoins implements Runnable {
	private IO io;
	private PaymentData paymentData;
	
	public ECoins(IO io) {
		this.io = io;
	}

	@Override
	public void run() {
		this.io.writeLine("Enter E-coin number >> ");
		String number = this.io.readLine();
		this.paymentData = new PaymentData(PaymentType.ECoins, number);
	}
	
	public PaymentData getPaymentData() {
		return this.paymentData;
	}
}
