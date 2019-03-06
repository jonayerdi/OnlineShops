package io;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IO {
	private Scanner scanner;
	private PrintStream pstream;
	
	public IO(InputStream istream, OutputStream ostream) {
		this.scanner = new Scanner(istream);
		this.pstream = new PrintStream(ostream);
	}
	
	public String readLine() {
		return this.scanner.nextLine();
	}
	
	public void write(String text) {
		this.pstream.print(text);
		this.pstream.flush();
	}
	
	public void writeLine(String line) {
		this.pstream.println(line);
		this.pstream.flush();
	}
	
	public void close() {
		this.scanner.close();
		this.pstream.close();
	}
}
