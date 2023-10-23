package app;

import app.ui.CommandLineUserInterface;

/**
 * Main class to hold main method
 * 
 */
public class Main {

	public static void main(String[] args) {
		// Create the u.i object
		CommandLineUserInterface ui = new CommandLineUserInterface(System.in);
		
		//Run the application
		ui.start();
		
	}

}
