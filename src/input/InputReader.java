package input;

import java.io.InputStream;
import java.util.Scanner;

public class InputReader {
	private Scanner scanner;
	
	public InputReader(InputStream istream) {
		this.scanner = new Scanner(istream);
	}
	
	public String nextLine() {
		return this.scanner.nextLine();
	}
	
	public void close() {
		this.scanner.close();
	}
}
