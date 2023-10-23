package app.processor;

import java.util.*;

/**
 * 
 * Public class for encoder/decoder
 *
 */
public class Encoder {
	
	//store the ref table as a char array, where offset is index
	private char[] refTable = new char[44];
	
	//HashMap to get offset given a char 
	private HashMap<Character, Integer> inverseRefTable = new HashMap<>();
	
	//To store offset 
	private int offset;
	
	//Random variable to get a random offset
	private Random rand = new Random();
	
	/**
	 * Constructor for encoder
	 */
	public Encoder()
	{
		// We initialize the reference table and the inverse reference Map when object
		// is created 
		this.offset = this.rand.nextInt(44) ;
		for (int i = 0; i < 44; i++)
		{
			if (i <= 25) //A-Z
			{
				refTable[i] = (char) (i+65);
				inverseRefTable.put((char) (i+65), i);
			}
			else if (i >= 26 && i <= 35) //0-9
			{
				//refTable.put(i, (char) (i-26 + 48));
				refTable[i] = (char) (i-26+48);
				inverseRefTable.put((char)(i-26+48), i );
			}
			else //Remaining special characters
			{
				//refTable.put(i, (char) (i- 35 + 40));
				refTable[i] = (char) (i-36+40);
				inverseRefTable.put((char)(i-36+40), i);
			}
			
		}
	  
	}
	/**
	 * Value constructor if certain offset is wanted
	 * @param offset
	 */
	public Encoder(int offset)
	{
		this(); //call base constructor
		
		//override offset
		this.offset = offset;
		
	}
	/**
	 * Char constructor if certain offset is wanted(decoder use)
	 * @param offset
	 */
	public Encoder(char offset)
	{
		this();
		this.offset = this.inverseRefTable.get(offset);
	}
	
	
	/**
	 * Required encode function
	 * @param plainText
	 * @return encodedText
	 */
	public String encode(String plainText)
	{
		
		// convert msg to char array
		char[] msg_array = plainText.toCharArray();
		
		// use string builder to create new string
		StringBuilder result = new StringBuilder();
		
		// Append the offset char in beginning based on initial ref table
		result.append(refTable[this.offset]);
		
		// Loop through each character in message
		for (char c: msg_array)
		{
			// Ignore and append whitespacce
			if (Character.isWhitespace(c))
			{
				result.append(c);
				continue; // go to next char
			}
			
			// Find the initial index value
			int index_no = inverseRefTable.get(c);
			// because we want to shift down, we minus the offset and add the length of ref table
			// to ensure that our val is positive and do a modulo to ensure it is within 0-43
			int new_index = (index_no - this.offset + 44) % 44;
			char offset_char = refTable[new_index]; //Once value is found, just use ref table to get new char
			
			
			result.append(offset_char); // append to string builder
		}
		
		return result.toString(); //return new result
	}
	
	/**
	 * Required decode function
	 * @param encodedText
	 * @return plainText
	 */
	public String decode(String encodedText)
	{
		// Similar to encode put msg to char[] array
		char[] msg_array = encodedText.toCharArray();
		
		StringBuilder result = new StringBuilder(); 
		
		//Ignore the offset character
		for (int i = 1; i < msg_array.length; i++)
		{	//Since not using foreach got to retrieve the char 1 by 1
			char c = msg_array[i];
			
			if (Character.isWhitespace(c))
			{
				result.append(c);
				continue;
			}
			
			//So we first get the index without any offset
			int index_no = inverseRefTable.get(c);
			// Then reverse by adding offset and doing modulo again 
			int old_index = (index_no + this.offset) % 44;
			char org_char = refTable[old_index]; //find old char
			result.append(org_char);
		}
		
		return result.toString(); 
	}

	/* Not used getters, but in case want to find ref table and offset value
	 * The inverse ref table shouldn't be known, so we don't reveal it
	 */
	
	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}
	/**
	 * @return the refTable
	 */
	public char[] getRefTable() {
		return refTable;
	}
	
	
}
