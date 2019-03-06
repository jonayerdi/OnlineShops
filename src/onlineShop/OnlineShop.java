package onlineShop;

import io.IO;
import onlineShop.data.Cart;
import onlineShop.data.Catalog;
import onlineShop.states.StateMachine;

public class OnlineShop {

	public static void main(String[] args) throws Exception {
		// Init IO channel
		IO io = new IO(System.in, System.out);
		// Init data
		Catalog catalog = new Catalog();
		Cart cart = new Cart();
		// Init state machine
		StateMachine stateMachine = new StateMachine(io, catalog, cart);
		// Run state machine
		stateMachine.run();
		// Cleanup
		io.close();
	}

}
