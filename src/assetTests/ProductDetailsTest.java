package assetTests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import io.IO;
import onlineShop.data.Cart;
import onlineShop.data.Catalog;
import onlineShop.states.productSelection.ProductDetails;

public class ProductDetailsTest {
	private PrintStream in;
	private InputStream out;
	private IO io;
	private ProductDetails productDetails;
	
	private Catalog catalog;
	private Cart cart;
	
	@Rule
	public Timeout globalTimeout = Timeout.seconds(3); // 3 seconds max per method tested

	@Before
	public void setUp() throws Exception {
		// Setup input/output streams
		PipedInputStream piin = new PipedInputStream();
		PipedOutputStream poin = new PipedOutputStream(piin);
		PipedInputStream piout = new PipedInputStream();
		PipedOutputStream poout = new PipedOutputStream(piout);
		this.io = new IO(piin, poout);
		this.in = new PrintStream(poin);
		this.out = piout;
		// Init Catalog and Cart
		this.catalog = new Catalog();
		this.cart = new Cart();
		// Init ProductDetails
		this.productDetails = new ProductDetails(io, this.catalog, this.cart, "Banana");
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	/* Product details should look like the following */
	/// Name: Banana
	/// Price: 0.45
	/// In cart: 0
	
	/* Menu should look like the following */
	/// 1. ADD TO CART
	/// 2. BACK TO CATALOG
	/// #if Search
	/// 3. SEARCH PRODUCT
	/// 4. YOUR CART
	/// #else
	///	3. YOUR CART

	@Test
	public void testProductDataDisplay() throws Exception {
		// Setup inputs
		this.in.println("2"); // 2. BACK TO CATALOG
		// Run ProductDetails
		this.productDetails.run();
		this.io.close();
		List<String> lines =
			      new BufferedReader(new InputStreamReader(this.out,
			          StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
		// Check output
		boolean namePresent = false, pricePresent = false, inCartPresent = false;
		for(String line : lines) {
			if(line.startsWith("Name: ")) {
				assertFalse(namePresent);
				namePresent = true;
				assertEquals("Name: Banana", line);
			} else if(line.startsWith("Price: ")) {
				assertFalse(pricePresent);
				pricePresent = true;
				assertEquals("Price: 0.45", line);
			} else if(line.startsWith("In cart: ")) {
				assertFalse(inCartPresent);
				inCartPresent = true;
				assertEquals("In cart: 0", line);
			}
		}
		assertTrue(namePresent);
		assertTrue(pricePresent);
		assertTrue(inCartPresent);
	}
	
	@Test
	public void testCommonTransitions() throws Exception {
		// Setup inputs
		this.in.println("2"); // BACK TO CATALOG
		// Run ProductDetails
		this.productDetails.run();
		this.io.close();
		List<String> lines =
			      new BufferedReader(new InputStreamReader(this.out,
			          StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
		// Check output
		boolean addToCartPresent = false, backToCatalogPresent = false, yourCartPresent = false;
		for(String line : lines) {
			if(line.endsWith("ADD TO CART")) {
				assertFalse(addToCartPresent);
				addToCartPresent = true;
			} else if(line.endsWith("BACK TO CATALOG")) {
				assertFalse(backToCatalogPresent);
				backToCatalogPresent = true;
			} else if(line.endsWith("YOUR CART")) {
				assertFalse(yourCartPresent);
				yourCartPresent = true;
			}
		}
		assertTrue(addToCartPresent);
		assertTrue(backToCatalogPresent);
		assertTrue(yourCartPresent);
	}
	
	
	@Test
	public void testSearchTransition() throws Exception {
		// Setup inputs
		this.in.println("2"); // BACK TO CATALOG
		// Run ProductDetails
		this.productDetails.run();
		this.io.close();
		List<String> lines =
			      new BufferedReader(new InputStreamReader(this.out,
			          StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
		// Check output
		boolean searchProductPresent = false;
		for(String line : lines) {
			if(line.endsWith("SEARCH PRODUCT")) {
				assertFalse(searchProductPresent);
				searchProductPresent = true;
			}
		}
		// #if Search
		assertTrue(searchProductPresent);
		// #else
//@		assertFalse(searchProductPresent);
		// #endif
	}
}
