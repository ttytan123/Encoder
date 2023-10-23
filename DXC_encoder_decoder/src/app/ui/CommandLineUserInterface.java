package app.ui;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.processor.Encoder;

public class CommandLineUserInterface {

	// Hold the encoder object
	protected Encoder encoder;
	
	// Hold scanner object
	private Scanner in;
	
	/**
	 * Constructor for U.I 
	 * Encoder is created here
	 * @param newIn
	 * the inputstream is for testing purposes (so we can swap out a specific set of instructions)
	 */
	public CommandLineUserInterface(InputStream newIn) 
	{
		// Only initalise scanner, because the encoder object will be swapped out at run-time
		this.in = new Scanner(newIn);
	}
	
	/**
	 * To begin the program
	 */
	public void start()
	{
		//Ask to encode or decode
		System.out.println("Press 1 to encode, 2 to decode (will repeat until 1 or 2 is pressed. or 0 to exit)");
		
		//Force to loop until proper choice made,
		int choice = -1;
		while (choice == -1) {choice = this.selection(2);}
		
		if (choice == 0) {return;} //It will go back to main and exit gracefully
		
		//If encode, ask what type of offset wanted
		if (choice == 1)
		{
			//Can be random just don't input number
			System.out.println("Please choose offset (between 0 - 43) or a random offset (do not enter a number)");
			int choice_2 = this.selection(43);
			
			//Shows why we need 2 different constructors
			if (choice_2 == -1)
			{
				this.encoder = new Encoder();
			}
			else
			{
				this.encoder = new Encoder(choice);
			}
			
		}
		
		/*Assume you did want to continue then you have to write a text or ctrl-c to quit*/
		
		System.out.print("Please enter the text you want to encode/decode (one-line only)\n" +
		"Please note that acceptable words are A-Z, 0-9, ()*+,-./ strictly only!\n");
		
		String text;
		String regex = "[^\\sA-Z0-9\\(\\)\\*\\+,\\-\\./]";
		Pattern p = Pattern.compile(regex);
		
		while (true)
		{
			text = this.in.nextLine();
			
			Matcher m = p.matcher(text);
			
			if (m.find()) {System.out.println("Contains unacceptable character, try again"); continue;}
			break;
		}
		// So once we know the text, we need to update the decoder if we are using it
		if (choice == 2)
		{
			this.encoder = new Encoder(text.charAt(0)); //since first letter is offset to decode...
		}
		
		String new_msg;
		
		//Based on the first choice, 
		if (choice == 1)
		{
			new_msg = this.encoder.encode(text);
		}
		else //decode
		{
			new_msg = this.encoder.decode(text);
		}
		
		/* Print result and exit */
		
		System.out.println("This is the final message");
		System.out.println(new_msg);
		
		System.out.println("Exiting now! Run again to decode/encode");
		
		this.in.close(); // got to close scanner
	}
	
	/**
	 * Helper function to return int choice with parameter max no. of chices
	 * @param maxVal
	 * @return choice
	 */
	private int selection(int maxVal)
	{
		String input_string;
		int choice = -1; //just to have a default value, use to indicate empty line
		while (true) //Loop until break
		{
			try
			{
				input_string = this.in.nextLine(); //so whole line is entered
				
				if (input_string.isEmpty()) {return choice;} //if nothing typed and just press enter
				
				// Only should be 1 token
				if (input_string.split(" ").length > 1 ) {System.out.println("Invalid value: too many values"); continue;}
				
				//Parse
				choice = Integer.parseInt(input_string);
				
				// Out of  range
				if (choice < 0 || choice > maxVal) {System.out.println("Out of range"); continue;}
				break;
				
				
			} // Parse failure
			catch (NumberFormatException e){ System.out.println("Invalid value: Not a number"); continue;}
		}
		
		return choice; 
	}
	
	
}
