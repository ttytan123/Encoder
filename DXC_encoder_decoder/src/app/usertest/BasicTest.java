package app.usertest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
//import java.io.FileDescriptor;
//import java.io.FileOutputStream;
import java.io.PrintStream;
//import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.processor.Encoder;
import app.ui.CommandLineUserInterface;

/**
 * To test encode/decode functionality
 * and sample a run via running start
 */
class BasicTest {

	Encoder testEncoder;
	
	String example_1;
	
	String example_2;
	
	String example_3;
	
	
	@BeforeEach
	void setUp() throws Exception {
		
		example_1 = "HELLO WORLD"; 
		
		example_2 = "0123456789()*+,-./";
		
		example_3 = "BGDKKN VNQKC";
	}

	@Test
	void testEncode() {
		
		
		//include the samples given in the handout for shift 1 and 5 for hello world 
		
		testEncoder = new Encoder(1);
		
		String encode_result_1 = testEncoder.encode(this.example_1);
		
		assertEquals(encode_result_1, "BGDKKN VNQKC");
			
		testEncoder = new Encoder(5);
		
		String encode_result_2 = testEncoder.encode(this.example_1);
		
		assertEquals(encode_result_2, "FC/GGJ RJMG.");
		
		/*-----------Others ------------------------------*/
		
		testEncoder = new Encoder(43);
		
		String encode_result_3 = testEncoder.encode(this.example_1);
		
		assertEquals(encode_result_3, "/IFMMP XPSME");
		
		testEncoder = new Encoder(0);
		
		String encode_result_4 = testEncoder.encode(this.example_1);
		
		assertEquals(encode_result_4, "AHELLO WORLD");
		
		testEncoder = new Encoder(1);
		
		String encode_result_5 = testEncoder.encode(this.example_2);
		
		assertEquals(encode_result_5, "BZ0123456789()*+,-.");
		
	}
	
	@Test
	void testDecode() {
		
		//include the samples given in the handout for shift 1 and 5 for hello world 
		
		testEncoder = new Encoder(1);
		
		String encode_result_1 = testEncoder.decode(this.example_3);
		
		assertEquals(encode_result_1, "HELLO WORLD");
			
		testEncoder = new Encoder(5);
		
		this.example_3 = "FC/GGJ RJMG.";
		
		String encode_result_2 = testEncoder.decode(this.example_3);
		
		assertEquals(encode_result_2, "HELLO WORLD");
		
		/*-----------Others ------------------------------*/
		
		testEncoder = new Encoder(43);
		
		this.example_3 = "/IFMMP XPSME";
		
		String encode_result_3 = testEncoder.decode(this.example_3);
		
		assertEquals(encode_result_3, "HELLO WORLD");
		
		testEncoder = new Encoder(0);
		
		this.example_3 = "AHELLO WORLD";
		
		String encode_result_4 = testEncoder.decode(this.example_3);
		
		assertEquals(encode_result_4, "HELLO WORLD");
		
		testEncoder = new Encoder(1);
		
		this.example_3 = "BZ0123456789()*+,-.";
		
		String encode_result_5 = testEncoder.decode(this.example_3);
		
		assertEquals(encode_result_5, "0123456789()*+,-./");
		
		
	}
	
	@Test
	void testUI() {
		
		//Relative primitive way of testing by swapping system in to predetermined string
		
		String testInput = "1\n1\nHELLO WORLD";
		System.setIn(new ByteArrayInputStream(testInput.getBytes()));
		
		ByteArrayOutputStream testOut = new ByteArrayOutputStream();
		
		System.setOut(new PrintStream(testOut));
		
		CommandLineUserInterface testUI = new CommandLineUserInterface(System.in);
		testUI.start();
		
		String result = testOut.toString();
		
		String[] result_array = result.split("\n");
		
		assertEquals(result_array[result_array.length-2], "BGDKKN VNQKC");
		
		/*If values to restore system out */
		
		//System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
		
		//System.out.println(result_array[result_array.length-2]);
		
		
		String testInput_2 = "2\nFC/GGJ RJMG.";
		System.setIn(new ByteArrayInputStream(testInput_2.getBytes()));
		
		CommandLineUserInterface testUI_2 = new CommandLineUserInterface(System.in);
		testUI_2.start();
		
		String result_2 = testOut.toString();
		
		String[] result_array_2 = result_2.split("\n");
		
		assertEquals(result_array_2[result_array_2.length-2], "HELLO WORLD");
		
		//System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
		
		//System.out.println(result_array_2[result_array_2.length-2]);
		
		
		String testInput_3 = "1\n1\n0123456789()*+,-./";
		System.setIn(new ByteArrayInputStream(testInput_3.getBytes()));
		
		CommandLineUserInterface testUI_3 = new CommandLineUserInterface(System.in);
		testUI_3.start();
		
		String result_3 = testOut.toString();
		
		String[] result_array_3 = result_3.split("\n");
		
		assertEquals(result_array_3[result_array_3.length-2], "BZ0123456789()*+,-.");
		
		
		/*To show nonsense output will be ignored...*/
		
		String testInput_4 = "2\n!@#!@#\\123123\nBZ0123456789()*+,-."; //have some random gibberish
		System.setIn(new ByteArrayInputStream(testInput_4.getBytes()));
		
		CommandLineUserInterface testUI_4 = new CommandLineUserInterface(System.in);
		testUI_4.start();
		
		String result_4 = testOut.toString();
		
		String[] result_array_4 = result_4.split("\n");
		
		assertEquals(result_array_4[result_array_4.length-2], this.example_2);
		
		
		
	}

}
