//Austin G. Nolting
//Simple arithmatic lexical analyzer
//10.9.2018
//CSCI 4200DB

package lexicalAnalyzer;
import java.io.*;
import java.lang.*;
import java.util.Scanner;



public class LexicalAnalyzer {
//	Global declarations
	public static int lexLen;
	public static String charClass;
	public static String token;
	public static String nextToken;
	public static char[] lexeme = new char[100];
	public static char nextChar;
	public static int index = 0;
	public static String line;
	
	//Character classes
	public static final String LETTER = "0";
	public static final String DIGIT = "1";
	public static final String UNKNOWN = "99";
	
	//Token Codes
	public static final String INT_LIT = "INT_LIT";
	public static final String IDENT = "IDENT";
	public static final String ASSIGN_OP = "ASSIGN_OP";
	public static final String ADD_OP = "ADD_OP";
	public static final String SUB_OP = "SUB_OP";
	public static final String MULT_OP = "MULT_OP";
	public static final String DIV_OP = "DIV_OP";
	public static final String LEFT_PAREN = "LEFT_PAREN";
	public static final String RIGHT_PAREN = "RIGHT_PAREN";
	public static final String EOF = "END_OF_FILE";
	
	//Begin main driver
	public static void main(String[] args) throws IOException
	{
		//opens txt file for processing
		try{
			File input = new File("lexInput.txt");
			BufferedReader br = new BufferedReader(new FileReader(input));
			System.out.println("\nAustin Nolting, CSCI4200-DB, Fall 2018, Lexical Analyzer");
			line = br.readLine();
			while(line != null){			
	        	System.out.println("********************************************************************************");
	        	System.out.println("Input: " + line);
	        	nextLine();
				getChar();
				do { 
					lex();
//					System.out.println("In do while after Lex()");
				} while (nextToken != EOF);
				System.out.println("********************************************************************************");

			line = br.readLine();
			}//end of while loop
			System.out.printf("Next token is: %-15s Next lexeme is ", nextToken);
			System.out.println(lexeme);
			System.out.println("Lexical analysis of the program is complete!");
		}//end of try
		catch (IOException e){
			System.out.println("ERROR - cannot open file");
		} 
		
	}//End main driver
	
	
	//Begin lookup  - a function to lookup operators
	public static String lookup(char ch){
		switch(ch){
		case '(':
			addChar();
			nextToken = LEFT_PAREN;
			break;
		case ')':
			addChar();
			nextToken = RIGHT_PAREN;
			break;
		case '+':
			addChar();
			nextToken = ADD_OP;
			break;
		case '-':
			addChar();
			nextToken = SUB_OP;
			break;
		case '*':
			addChar();
			nextToken = MULT_OP;
			break;
		case '/':
			addChar();
			nextToken = DIV_OP;
			break;
		case '=':
			addChar();
			nextToken = ASSIGN_OP;
			break;
		default:
			addChar();
			nextToken = EOF;
			break;
		}//end switch(ch)
		return nextToken;
	}//end lookup

	//begin addChar method
	public static void addChar(){
		if (lexLen <= 98) {
			lexeme[lexLen++] = nextChar;
			lexeme[lexLen] = 0;
		}//end if statement
		else
			System.out.println("Error: Lexeme is too long");
	}//end addChar method
	
	//begin getChar method
	public static void getChar() throws IOException{
		if (isThere()) {
			if(Character.isLetter(nextChar))
				charClass = LETTER;
			else if(Character.isDigit(nextChar))
				charClass = DIGIT;
			else
				charClass = UNKNOWN;
		}//end if statement
		else
			charClass = EOF;
		
		
	}//end getChar method
	
	//begin getNonBlank method
	public static void getNonBlank() throws IOException {
		while (Character.isWhitespace(nextChar)){
			getChar();
		}//end while statement
	}//end method getNonBlank
	
	//begin lex method
	public static int lex() throws IOException {
		lexeme = new char[100];
		lexLen = 0;
		getNonBlank();
		switch (charClass){
		//parse ident.
		case LETTER:
			addChar();
			getChar();
			while (charClass == LETTER || charClass == DIGIT) {
				addChar();
				getChar();
			}//end while [letter]
			nextToken = IDENT;
			break;
		case DIGIT:
			addChar();
			getChar();
			while (charClass == DIGIT){
				addChar();
				getChar();
			}//end while [digit]
			nextToken = INT_LIT;
			break;
		case UNKNOWN:
			lookup(nextChar);
			getChar();
			break;
		case EOF:
			nextToken = EOF;
				lexeme[0] = 'E';
				lexeme[1] = 'O';
				lexeme[2] = 'F';
				lexeme[3] = 0;
				break;
		}//end switch statement
		if (nextToken != EOF){
			System.out.printf("Next token is: %-15s Next lexeme is ", nextToken);
			System.out.println(lexeme);
		}//end if statement
		return 0;
		
	}//end lex() method
	
	public static void nextLine() {
		index = 0;
		lexeme = new char[100];
	}//end nextLine() method
	
	//Begin method isThere
	public static boolean isThere() throws java.lang.StringIndexOutOfBoundsException{
		try{
			nextChar = line.charAt(index);
			index++;
			return true;
		}
		catch(java.lang.StringIndexOutOfBoundsException e){
			return false;
		}
	}
}// End LexicalAnalyzer


















