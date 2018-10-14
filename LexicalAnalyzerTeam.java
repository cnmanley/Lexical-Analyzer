//Lexical Analyzer
//Austin Nolting, Vlad Popescu, Colton Manley, Ashna Momin, Patrick Owen
//CSCI-4200 DB
//Abi Salimi



import java.io.*;

public class LexicalAnalyzerTeam{
	
	//For Global Declarations, Character Classes, and Token Codes we used a combination of Colton and Austin's code
	
	//Global Declarations 
	public static int lexLen;
	public static String charClass;
	public static String token;
	public static String nextToken;
	public static char[] lexeme = new char[100];
	public static char nextChar;
	public static int index = 0;
	public static String line;
	
	//Character Classes
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
	
	
	//Main Driver
	//Code from Colton M.
	//Main Driver Class
	public static void main(String[] args) throws IOException {
        try {

            File input = new File("lexInput.txt");
            BufferedReader br = new BufferedReader(new FileReader(input));
            System.out.println("\nTeam 2, CSCI4200-DB, Fall 2018, Lexical Analyzer");
            line = br.readLine();
            while (line != null) {
                System.out.println("*******************************************************************************");
                System.out.println("Input: " + line);
                nextLine();
                    getChar();
                    do {
                        lex();

                    } while (nextToken != EOF);
                    System.out.println("*******************************************************************************");

                line = br.readLine();
            }
            System.out.printf("Next token is: %-15s Next Lexeme is ", nextToken);
            System.out.println(lexeme);
            System.out.println("Lexical analysis is complete.");

    } catch (IOException ex) {
        System.out.println(
                "Unable to open file");
    }
    }//End Main Driver
	

	//Code from Austin N.
	//Lookup Class
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
		}//End Lookup
	

	//Code from Patrick O.
	//addChar Class
		public static void addChar(){
			if (lexLen <= 98) {
				    lexeme[lexLen++] = nextChar;
				    lexeme[lexLen] = 0;
				}
			else
				System.out.println("Error - lexeme is too long \n");

		}//End addChar() 
	
	//Code from Colton M.
	//End nextLine()
	    public static void nextLine() {
	        index = 0;
	        lexeme = new char[100];
	    }//End nextLine()
	    
    //Code from Vlad P.
    //getChar()
	    static void getChar() throws IOException {
	        if (isPresent()) {
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
	      }//End getChar()
	    
	//Code from Austin N.
	//getNonBlank method
		public static void getNonBlank() throws IOException {
			while (Character.isWhitespace(nextChar)){
				getChar();
			}//end while statement
		}//end method getNonBlank
	
	//Code from Colton M.
	//isPresent() method
	    public static boolean isPresent() throws java.lang.StringIndexOutOfBoundsException {
	        try {
	            nextChar = line.charAt(index);
	            index++;
	            return true;
	        } catch (java.lang.StringIndexOutOfBoundsException e) {
	            return false;
	        }
	    }//end isPresent() method
	
	//Code from Vlad P.
	//lex() method
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
	
	
	
	
	
	
	
	
	
	
	
	
}//End LexicalTeamAnalyzer