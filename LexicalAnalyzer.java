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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

//Enumerate character classes along with setter and getter for retrieval
//Return int
enum CharacterClass{
  LETTER(0), 
  DIGIT(1), 
  UNKNOWN(99),
  EOF(-1);
  
  private int value;
  
  private CharacterClass(int value){
      this.value = value;
  }
  public int getValue(){
      return value;
  }
}

//Enumerate token codes along with getter and setter for retrieval
//Return string
enum TokenCodes{
  INT_LIT("INT_LIT"), 
  IDENT("IDENT"), 
  ASSIGN_OP("ASSIGN_OP"), 
  ADD_OP("ADD_OP"),
  SUB_OP("SUB_OP"), 
  MULT_OP("MULT_OP"), 
  DIV_OP("DIV_OP"), 
  LEFT_PAREN("LEFT_PAREN"),
  RIGHT_PAREN("RIGHT_PAREN"), 
  EOF("END_OF_FILE");
  
  private String value;
  
  private TokenCodes(String value){
      this.value = value;
  }
  public String getValue(){
      return value;
  }
}

public class LexicalAnalyzer {

  /* Global Declarations */
  /* Variables */
  static CharacterClass charClass;
  static String lexeme;
  static char nextChar;
  static int count = 0;
  static int lexLen;
  static int token;
  static TokenCodes nextToken;
  static BufferedReader br;
  static File fl_in;
  
  
  //Main driver
  public static void main(String[] args) throws FileNotFoundException, IOException {
  	
  	// Open the input data file and process its contents 
  	fl_in = new File("src/sample.txt");
  	
      try{ 
          br = new BufferedReader(new FileReader(fl_in));
          
          System.out.println("Vlad P. Student, CSCI4200-DB, Fall 2018, Lexical Analyzer");
          
          try (BufferedReader br = new BufferedReader(new FileReader(fl_in))) {
       	   String line = null;
       	   while ((line = br.readLine()) != null) {
       		   getChar();
       		   astk();
       	       System.out.println("\nInput: " + line);
       	       //Scanner scan = new Scanner(new BufferedReader(new FileReader(fl_in)));
       	       while(nextToken != TokenCodes.EOF) {
       	    		   lex();
       	    		   System.out.println("Next token is:" + " " + nextToken.getValue() + "    " + "\tNext lexeme is:" + " " + new String(lexeme));
       	       }
       	       System.out.println("");
       	   }
       	  br.close();
       	}
          System.out.println("Lexical analysis of the program is complete!");
      }catch (FileNotFoundException e){
          System.out.println("Error - File Not Found");
      }catch (IOException e){
          System.out.println("Error - IO Exception");
      }
      
  }
  
  //Astk method for creating a string of 80 asterisks
  static void astk() {
  	for (int i = 0; i<=80; i++) {
  		System.out.print("*");
  	}
  }
  
  //lookup method to lookup operators and return tokens
  static void lookup(char ch){
      switch(ch){
          case '(':
              addChar();
              nextToken = TokenCodes.LEFT_PAREN;
              break;
          case ')':
              addChar();
              nextToken = TokenCodes.RIGHT_PAREN;
              break;
          case '+':
              addChar();
              nextToken = TokenCodes.ADD_OP;
              break;
          case '-':
              addChar();
              nextToken = TokenCodes.SUB_OP;
              break;
          case '*':
              addChar();
              nextToken = TokenCodes.MULT_OP;
              break;
          case '/':
              addChar();
              nextToken = TokenCodes.DIV_OP;
              break;
          case '=':
              addChar();
              nextToken = TokenCodes.ASSIGN_OP;
              break;
          default:
              addChar();
              nextToken = TokenCodes.EOF;
              lexeme = "EOF";
              break;
      }
      
  }
  
  //addChar function to add nextChar to lexeme 
  static void addChar(){
      if(lexLen <= 98){
      	lexeme=lexeme+ nextChar;
      	lexLen++;
      }else{
          System.out.println("Error - lexeme is too long");
      }
  }
  
  //getChar function to get the next character of input and determine its character class
  static void getChar(){
      try {
          nextChar = (char)br.read();
          if(nextChar != 0){
              if(Character.isLetter(nextChar)){
                  charClass = CharacterClass.LETTER;
              }else if(Character.isDigit(nextChar)){
                  charClass = CharacterClass.DIGIT;
              }else{
                  charClass = CharacterClass.UNKNOWN;
              }
          }else{
              charClass = CharacterClass.EOF;
          }
      } catch (IOException ex) {
          Logger.getLogger(LexicalAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
  
  //getNonBlank function to call getChar until it returns a non-whitespace character
  static void getNonBlank(){
      while(Character.isWhitespace(nextChar)){
          getChar();
      }
  }
  
  //lex - simple lexical analyzer for arithmetic expressions*/ 
  static void lex(){
      lexLen = 0;
      lexeme = "";
      getNonBlank();
      switch(charClass){
          /* Parse Identifiers */
          case LETTER:
              addChar();
              getChar();
              while(charClass == CharacterClass.LETTER ||
                      charClass == CharacterClass.DIGIT){
                  addChar();
                  getChar();
              }
              nextToken = TokenCodes.IDENT;
              break;
          /* Parse integer literals */
          case DIGIT:
              do{
                  addChar();
                  getChar();
              }
              while (charClass == CharacterClass.DIGIT);
              nextToken = TokenCodes.INT_LIT;
              break;
          case UNKNOWN:
              lookup(nextChar);
              getChar();
              break;
          /* EOF */
          case EOF:
          	nextToken = TokenCodes.EOF;
          	lexeme = "EOF";
              break;
              
      }
  }
}