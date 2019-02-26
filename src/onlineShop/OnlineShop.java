package onlineShop;

import input.InputReader;
import onlineShop.data.Cart;
import onlineShop.data.Catalog;
import onlineShop.states.StateMachine;

public class OnlineShop {

	public static void main(String[] args) throws Exception {
		// Init input
		InputReader in = new InputReader(System.in);
		// Init data
		Catalog catalog = new Catalog();
		Cart cart = new Cart();
		// Init state machine
		StateMachine stateMachine = new StateMachine(in, catalog, cart);
		// Run state machine
		stateMachine.run();
		// Cleanup
		in.close();
	}

}
