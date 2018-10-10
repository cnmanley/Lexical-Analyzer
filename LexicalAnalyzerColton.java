package lexicalAnalyzerPackage;

import java.io.*;
import java.lang.*;

public class LexicalAnalyzer {
    //Declarations
    public static String charClass;
    public static char[] lexeme = new char[100];
    public static char nextChar;
    public static int lexLen;
    public static String nextToken;
    public static String line;
    public static int index = 0;



    //token codes
    public static final String int_Lit = "INT_LIT";
    public static final String ident = "IDENT";
    public static final String assign_Op = "ASSIGN_OP";
    public static final String add_Op = "ADD_OP";
    public static final String sub_Op = "SUB_OP";
    public static final String mult_Op = "MULT_OP";
    public static final String div_Op = "DIV_OP";
    public static final String left_Paren = "LEFT_PAREN";
    public static final String right_Paren = "RIGHT_PAREN";
    public static final String EOF = "END_OF_FILE";

    //Char Classes
    public static final String LETTER = "0";
    public static final String DIGIT = "1";
    public static final String UNKNOWN = "99";

    //Functions

    //Main Driver
    public static void main(String[] args) throws IOException {
        try {

            File input = new File("C:\\Users\\cnm07\\Documents\\College\\Fall 2018\\Prog Lang\\Lexical Analyzer\\src\\lexicalAnalyzerPackage\\lexInput.txt");
            BufferedReader br = new BufferedReader(new FileReader(input));
            System.out.println("\nColton Manley, CSCI4200-DB, Fall 2018, Lexical Analyzer");
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
            System.out.printf("NExt token is: %-15s Next Lexeme is ", nextToken);
            System.out.println(lexeme);
            System.out.println("Lexical analysis is complete.");

    } catch (IOException ex) {
        System.out.println(
                "Unable to open file");
    }
    }
    public static void nextLine() {
        index = 0;
        lexeme = new char[100];
    }
    public static boolean isPresent() throws java.lang.StringIndexOutOfBoundsException {
        try {
            nextChar = line.charAt(index);
            index++;
            return true;
        } catch (java.lang.StringIndexOutOfBoundsException e) {
            return false;
        }
    }
    //function to lookup operators and parentheses and return the token
    public static void lookUp(char ch){
        switch (ch) {
            case '(':
                addChar();
                nextToken = left_Paren;
                break;
            case ')' :
                addChar();
                nextToken = right_Paren;
                break;
            case '+' :
                addChar();
                nextToken = add_Op;
                break;
            case '-' :
                addChar();
                nextToken = sub_Op;
                break;
            case '*' :
                addChar();
                nextToken = mult_Op;
                break;
            case '/' :
                addChar();
                nextToken = div_Op;
                break;
            case '=' :
                addChar();
                nextToken = assign_Op;
                break;
            default:
                addChar();
                nextToken = EOF;
                break;
        }
    }

    //function to add nextChar to lexeme
    public static void addChar(){
        if (lexLen <= 98) {
            lexeme[lexLen++] = nextChar;
            lexeme[lexLen] = 0;
        }
        else {
            System.out.println("Error - lexeme is too long \n");
        }

    }
    //Function to get characters
    public static void getChar() throws IOException {
        if (isPresent()) {
            if (Character.isLetter(nextChar)) {
                charClass = LETTER;
            }
            else if (Character.isDigit(nextChar)) {
                charClass = DIGIT;

            }
            else {
                charClass = UNKNOWN;

            }

        }
        else {
            charClass = EOF;
        }

    }
//Gets nonblank chars
    public static void getNonBlank() throws IOException {
        while (Character.isWhitespace(nextChar)) {
            getChar();
        }
    }
//Main lex function
    public static int lex() throws IOException {
        lexeme = new char[100];
        lexLen = 0;
        getNonBlank();
        switch (charClass) {
            case LETTER :
                addChar();
                getChar();
                while (charClass == LETTER || charClass == DIGIT) {
                    addChar();
                    getChar();
                }
                nextToken = ident;
                break;
            case DIGIT:
                addChar();
                getChar();
                while (charClass == DIGIT) {
                    addChar();
                    getChar();
                }
                nextToken = int_Lit;
                break;
            case UNKNOWN:
                lookUp(nextChar);
                getChar();
                break;
            case EOF:
                nextToken = EOF;
                lexeme[0] = 'E';
                lexeme[1] = 'O';
                lexeme[2] = 'F';
                lexeme[3] = 0;
                break;
        }
        if (nextToken != EOF) {
            System.out.printf("Next token is: %-15s Next lexeme is ", nextToken);
            System.out.println(lexeme);
        }
        return 0;

    }



}
