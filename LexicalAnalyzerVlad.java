package lexicalAnalyzerPackage;

//Lexical Analyzer

//Vlad Popescu
//CSCI-4200 DB
//Abi Salimi

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class LexicalAnalyzer {

	/* Character classes */
	static final String LETTER = "LETTER";
	static final String DIGIT = "DIGIT";
	static final String UNKNOWN = "UNKNOWN";
	static final String EOF = "END_OF_FILE";

	/* Token codes */
	static String INT_LIT = "INT_LIT";
	static String IDENT = "IDENT";
	static String ASSIGN_OP = "ASSIGN_OP";
	static String ADD_OP = "ADD_OP";
	static String SUB_OP = "SUB_OP";
	static String MULT_OP = "MULT_OP";
	static String DIV_OP = "DIV_OP";
	static String LEFT_PAREN = "LEFT_PAREN";
	static String RIGHT_PAREN = "RIGHT_PAREN";

	/* Global Declarations */
	/* Variables */
	static String charClass;
	static String nextToken;
	static String line;
	static char[] lexeme = new char[100];
	static char nextChar;
	static int count = 0;
	static int lexLen;
	static int token;
	static int index;
	static BufferedReader br;
	static File fl_in;

	// Main driver
	public static void main(String[] args) throws FileNotFoundException, IOException {

		// Open the input data file and process its contents
		fl_in = new File("src/lexInput.txt");
		BufferedReader br = new BufferedReader(new FileReader(fl_in));
		try {
			System.out.println("\nVlad Popescu, CSCI4200-DB, Fall 2018, Lexical Analyzer");
			while ((line = br.readLine()) != null) {
				astk();
				System.out.println("\nInput: " + line);
				nextLine();
				getChar();
				do {
					lex();
				} while (nextToken != EOF);
				System.out.println("********************************************************************************");
				line = br.readLine();
			}
			System.out.println("Next token is:" + " " + nextToken + "    " + "\tNext lexeme is:" + 
					" " + new String(lexeme));
			System.out.println("Lexical analysis of the program is complete!");
		} catch (FileNotFoundException e) {
			System.out.println("Error - File Not Found");
		}
	}

	// Astk method for creating a string of 80 asterisks
	static void astk() {
		for (int i = 0; i < 80; i++) {
			System.out.print("*");
		}
	}

	// nextLine method for resetting the lexeme and index
	public static void nextLine() {
		index = 0;
		lexeme = new char[100];
	}

	// lookup method to lookup operators and return tokens
	static String lookup(char ch) {
		switch (ch) {
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
		}
		return nextToken;
	}

	// addChar function to add nextChar to lexeme
	static void addChar() {
		if (lexLen <= 98) {
			lexeme[lexLen++] = nextChar;
			lexeme[lexLen] = 0;
		} else {
			System.out.println("Error - lexeme is too long");
		}
	}

	// isCar method for identifying whether a character exists on a line and at what
	// index
	public static boolean isChar() throws StringIndexOutOfBoundsException {
		try {
			nextChar = line.charAt(index);
			index++;
			return true;
		} catch (StringIndexOutOfBoundsException e) {
			return false;
		}
	}

	// getChar function to get the next character of input and determine its
	// character class
	static void getChar() throws IOException {
		if (isChar()) {
			if (Character.isLetter(nextChar)) {
				charClass = LETTER;
			} else if (Character.isDigit(nextChar)) {
				charClass = DIGIT;
			} else {
				charClass = UNKNOWN;
			}
		} else {
			charClass = EOF;
		}
	}

	// getNonBlank function to call getChar until it returns a non-whitespace
	// character
	static void getNonBlank() throws IOException {
		while (Character.isWhitespace(nextChar)) {
			getChar();
		}
	}

	// lex - simple lexical analyzer for arithmetic expressions*/
	static int lex() throws IOException {
		lexeme = new char[100];
		lexLen = 0;
		getNonBlank();
		switch (charClass) {
		/* Parse Identifiers */
		case LETTER:
			addChar();
			getChar();
			while (charClass == LETTER || charClass == DIGIT) {
				addChar();
				getChar();
			}
			nextToken = IDENT;
			break;
		/* Parse integer literals */
		case DIGIT:
			do {
				addChar();
				getChar();
			} while (charClass == DIGIT);
			nextToken = INT_LIT;
			break;
		case UNKNOWN:
			lookup(nextChar);
			getChar();
			break;
		/* EOF */
		case EOF:
			nextToken = EOF;
			lexeme[0] = 'E';
			lexeme[1] = 'O';
			lexeme[2] = 'F';
			lexeme[3] = 0;

			break;

		// returns output as long as the end of file is not reached
		}
		if (nextToken != EOF) {
			System.out.println("Next token is:" + " " + nextToken + "    " + "\tNext lexeme is:" + 
					" " + new String(lexeme));
		}
		return 0;
	}
}
