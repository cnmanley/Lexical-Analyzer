#include <stdio.h>

#include <ctype.h>

/* Global declarations */

/* Variables */

int charClass;

char lexeme[100];

char nextChar;

int lexLen;

int token;

int nextToken;

FILE *in_fp, *fopen();

/* Function declarations */

void addChar();

void getChar();

void getNonBlank();

int lex();

/* Character classes */

#define LETTER 0

#define DIGIT 1

#define UNKNOWN 99

/* Token codes */

#define INT_LIT 10

#define IDENT 11

#define ASSIGN_OP 20

#define ADD_OP 21

#define SUB_OP 22

#define MULT_OP 23

#define DIV_OP 24

#define LEFT_PAREN 25

#define RIGHT_PAREN 26

#define SEMI_COLON 199

#define COMMA 91

#define LEFT_BRACKET 32

#define RIGHT_BRACKET 33

#define FOR_CODE 34

#define INT_CODE 35

#define RETURN 36

#define USING 37

#define INCLUDE 38

#define OPERATOR 39

#define INCREMENT 40

#define ASSIGN_MULT_OP 41

#define LESS_THAN_OP 42

#define LESS_THAN_EQUAL_OP 43

#define SHIFT_LEFT 44

#define GREATER_THAN_OP 45

#define GREATER_THAN_EQUAL_OP 46

#define SHIFT_RIGHT 47

#define COLON 67

#define COMMENT_CODE 68

#define BACKSLASH 69

#define QUOTE 70

/******************************************************/

/* main driver */

int main()

{

/* Open the input data file and process its contents */

if ((in_fp = fopen("front.txt", "r")) == NULL)

printf("ERROR - cannot open front.in \n");

else

{

getChar();

do

{

lex();

} while (nextToken != EOF);

}

return 0;

}

/*****************************************************/

/* lookup - a function to lookup operators and parentheses

and return the token */

int lookup(char ch)

{

switch (ch)

{

case '(':

addChar();

nextToken = LEFT_PAREN;

break;

case ')':

addChar();

nextToken = RIGHT_PAREN;

break;

// case '+':

// addChar();

// nextToken = ADD_OP;

// break;

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

case ';':

addChar();

nextToken = SEMI_COLON;

break;

case ',':

addChar();

nextToken = COMMA;

break;

case '{':

addChar();

nextToken = LEFT_BRACKET;

break;

case '}':

addChar();

nextToken = RIGHT_BRACKET;

break;

case ':':

addChar();

nextToken = COLON;

break;

case '"':

addChar();

nextToken = QUOTE;

break;

/* Here is the part we had to add*/
case '=':
addChar();
nextToken = ADD_OP;
break;

case '#':

addChar();

nextToken = COMMENT_CODE;

break;

case '\\':

addChar();

nextToken = BACKSLASH;

break;

default:

addChar();

nextToken = EOF;

break;

}

return nextToken;

}

/*****************************************************/

/* addChar - a function to add nextChar to lexeme */

void addChar()

{

if (lexLen <= 98)

{

lexeme[lexLen++] = nextChar;

lexeme[lexLen] = 0;

}

else

printf("Error - lexeme is too long \n");

}

/*****************************************************/

/* getChar - a function to get the next character of

input and determine its character class */

void getChar()

{

if ((nextChar = getc(in_fp)) != EOF)

{

if (isalpha(nextChar))

charClass = LETTER;

else if (isdigit(nextChar))

charClass = DIGIT;

else

charClass = UNKNOWN;

}

else

charClass = EOF;

}

/*****************************************************/

/* getNonBlank - a function to call getChar until it

returns a non-whitespace character */

void getNonBlank()

{

while (isspace(nextChar))

getChar();

}

/*****************************************************/

/* lex - a simple lexical analyzer for arithmetic

expressions */

int lex()

{

int decimal = 0;

int lexLen = 0;

getNonBlank();

switch (charClass)

{

//Parse for keywords + identifers

case LETTER:

addChar();

getChar();

while (charClass == LETTER || charClass == DIGIT)

{

addChar();

getChar();

}

//KEYWORD: for

if (lexeme[0] == 'f' && lexeme[1] == 'o' && lexeme[2] == 'r')

{

nextToken = FOR_CODE;

break;

}

//KEYWORD: int

else if (lexeme[0] == 'i' && lexeme[1] == 'n' && lexeme[2] == 't')

{

nextToken = INT_CODE;

break;

}

//KEYWORD: return

else if (lexeme[0] == 'r' && lexeme[1] == 'e' && lexeme[2] == 't' && lexeme[3] == 'u' && lexeme[4] == 'r' && lexeme[5] == 'n')

{

nextToken = RETURN;

break;

}

//KEYWORD: using

else if (lexeme[0] == 'u' && lexeme[1] == 's' && lexeme[2] == 'i' && lexeme[3] == 'n' && lexeme[4] == 'g')

{

nextToken = USING;

break;

}

//KEYWORD: include

else if (lexeme[0] == 'i' && lexeme[1] == 'n' && lexeme[2] == 'c' && lexeme[3] == 'l' && lexeme[4] == 'u' && lexeme[5] == 'd' && lexeme[6] == 'e')

{

nextToken = INCLUDE;

break;

}

else

{

nextToken = IDENT;

break;

}

//Parse integers literals

case DIGIT:

addChar();

getChar();

while (charClass == DIGIT)

{

addChar();

getChar();

}

nextToken = INT_LIT;

break;

//Parse thru operators.

case OPERATOR:

addChar();

getChar();

while (charClass == OPERATOR)

{

addChar();

getChar();

}

//OPERATOR: INCREMENT (++)

if (lexeme[0] == '+' && lexeme[1] == '+')

{

nextToken = INCREMENT;

break;

}

//OPERATOR.ASSIGN: *=

else if (lexeme[0] == '*' && lexeme[1] == '=')

{

nextToken = ASSIGN_MULT_OP;

break;

}

//OPERATORS WITH <

else if (lexeme[0] == '>')

{

if (lexeme[1] == '\0')

{

nextToken = LESS_THAN_OP;

break;

}

else if (lexeme[1] == '=')

{

nextToken = LESS_THAN_EQUAL_OP;

break;

}

else if (lexeme[1] == '>')

{

nextToken = SHIFT_LEFT;

break;

}

else

{

}

}

//OPERATES WITH <

else if (lexeme[0] == '<')

{

if (lexeme[1] == '\0')

{

nextToken = GREATER_THAN_OP;

break;

}

else if (lexeme[1] == '=')

{

nextToken = GREATER_THAN_EQUAL_OP;

break;

}

else if (lexeme[1] == '<')

{

nextToken = SHIFT_RIGHT;

break;

}

else

{

}

}

/* Parentheses and operators */

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

} /* End of switch */

printf("Next token is: %d, Next lexeme is %s\n",

nextToken, lexeme);

return nextToken;

} /* End of function lex */''