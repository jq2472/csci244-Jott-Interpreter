package provided;

/**
 * This class is responsible for tokenizing Jott code.
 *
 * @author
 **/

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class JottTokenizer {
	// fields used during tokenizing
	private final String filename;
	private int lineNum;
	private Scanner scan;
	private String nextLine;
	private int lineLength;
	private int charIndex;
	private boolean isTokenizeError;


	/**
	 * Constructor method of JottTokenizer class
	 * */
	JottTokenizer(String filename) {
		this.filename = filename;
		this.lineNum = 0;
		File file = new File(filename);
		try {
			this.scan = new Scanner(file);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.nextLine = "";
		this.lineLength = 0;
		this.charIndex = 0;
		this.isTokenizeError = false;
	}


	/**
	 * Helper method for simplifying Token creation
	 * @param token String input representing the token
	 * @param tt TokenType of token
	 * @return Token
	 */
	private Token createToken(String token, TokenType tt) {
		return new Token(token, this.filename, this.lineNum, tt);
	}


	/**
	 * Helper method for sending a syntax error encountered during tokenization to the console
	 * @param errToken String "token" causing the syntax error
	 */
	private void printErrToken(String expected, String errToken) {
		System.err.printf("Syntax Error:\n\tExpected %s, got \"%s\"\n\t%s:%d\n\n",expected, errToken, this.filename, this.lineNum);
		this.isTokenizeError = true;
	}


	/**
	 * Method for creation of token beginning with '='
	 * Assumes that the '=' character was already encountered during tokenization before method call
	 * @return Either '=' ASSIGN or '==' REL_OP Token
	 */
	private Token getEqualsSignToken() {
		int j = charIndex + 1;
		if (j < lineLength) {
			char lookAhead = nextLine.charAt(j);
			if (lookAhead == '=') {
				charIndex++;
				return createToken("==", TokenType.REL_OP);
			}
		}
		return createToken("=", TokenType.ASSIGN);
	}


	/**
	 * Method for creation of token beginning with '<' or '>'
	 * Assumes that the '<' or '>' character was already encountered during tokenization before method call
	 * @param angleBracket Either '<' or '>' depending on which character was encountered to create the token
	 * @return '<', '>', '<=', or '>=' REL_OP Token
	 */
	private Token getAngleBracketToken(String angleBracket) {
		int j = charIndex + 1;
		if (j < lineLength) {
			char lookAhead = nextLine.charAt(charIndex + 1);
			if (lookAhead == '=') {
				charIndex++;
				return createToken(angleBracket + "=", TokenType.REL_OP);
			}
		}
		return createToken(angleBracket, TokenType.REL_OP);
	}


	/**
	 * Method for creation of token beginning with a digit
	 * Assumes that the first digit character was already encountered during tokenization before method call
	 * @param firstNumber String representation of the first digit character found to start off the token
	 * @return NUMBER Token in format of X X* {. | none} X* where X is a digit
	 */
	private Token getNumberToken(String firstNumber) {
		StringBuilder newTok = new StringBuilder(firstNumber);
		int j = charIndex + 1;
		if (j >= lineLength)
		{
			return createToken(newTok.toString(), TokenType.NUMBER);
		}
		char lookAhead = nextLine.charAt(j);
		while (isDigit(lookAhead)) {
			newTok.append(lookAhead);
			j++;
			if (j >= lineLength)
			{
				break;
			}
			lookAhead = nextLine.charAt(j);
		}
		if (lookAhead == '.') {
			newTok.append(".");
			j++;
			if (j < lineLength)
			{
				lookAhead = nextLine.charAt(j);
			}
			while (isDigit(lookAhead)) {
				newTok.append(lookAhead);
				j++;
				if (j >= lineLength)
				{
					break;
				}
				lookAhead = nextLine.charAt(j);
			}
		}
		charIndex += newTok.length() - 1;
		return createToken(newTok.toString(), TokenType.NUMBER);
	}


	/**
	 * Method for creation of token beginning with '.'
	 * Assumes that the '.' character was already encountered during tokenization before method call
	 * @return Null if token is invalid else NUMBER token in format . X X* where X is a digit
	 */
	private Token getPeriodToken() {
		int j = charIndex + 1;
		char lookAhead;
		if (j >= lineLength || !isDigit(lookAhead = nextLine.charAt(j)))
		{
			printErrToken("Valid Number", ".");
			return null;
		}
		StringBuilder newTok = new StringBuilder(".");
		while (isDigit(lookAhead)) {
			newTok.append(lookAhead);
			j++;
			if (j >= lineLength)
			{
				break;
			}
			lookAhead = nextLine.charAt(j);
		}
		charIndex += newTok.length()-1;
		return createToken(newTok.toString(), TokenType.NUMBER);
	}


	/**
	 * Method for creation of token beginning with '!'
	 * Assumes that the '!' character was already encountered during tokenization before method call
	 * @return NULL if token is invalid else '!=' REL_OP Token
	 */
	private Token getExclamationToken() {
		int j = charIndex + 1;
		if (j >= lineLength || nextLine.charAt(j) != '=')
		{
			printErrToken("\"!=\"", "!");
			return null;
		}
		charIndex++;
		return createToken("!=", TokenType.REL_OP);
	}


	/**
	 * Method for creation of token beginning with a letter
	 * Assumes that the letter character was already encountered during tokenization before method call
	 * @param firstLetter String representation of the first letter character found to start off the token
	 * @return ID_KEYWORD Token with a string of letters and digits, beginning with a letter
	 */
	private Token getIdKeywordToken(String firstLetter) {
		String newTok = firstLetter;
		int j = charIndex + 1;
		while (j < lineLength) {
			char lookAhead = nextLine.charAt(j);
			if (isLetter(lookAhead) || isDigit(lookAhead)) {
				newTok += lookAhead;
				j++;
			}
			else {
				break;
			}
		}
		charIndex += newTok.length()-1;
		return createToken(newTok, TokenType.ID_KEYWORD);
	}


	/**
	 * Method for creation of token beginning with '"'
	 * Assumes that the '"' character was already encountered during tokenization before method call
	 * @return Null if token is invalid else a STRING token with a string of letters, digits, and ' ' beginning and
	 *         ending with '"' characters
	 */
	private Token getStringToken() {
		StringBuilder newTok = new StringBuilder("\"");
		int j = charIndex + 1;
		while (j < lineLength) {
			char lookAhead = nextLine.charAt(j);
			if (lookAhead == '"') {
				newTok.append("\"");
				charIndex += newTok.length()-1;
				return createToken(newTok.toString(), TokenType.STRING);
			}
			else if (!isLetter(lookAhead) && !isDigit(lookAhead) && lookAhead != ' ') {
				break;
			}
			newTok.append(lookAhead);
			j++;
		}
		printErrToken("Valid String",newTok.toString());
		return null;
	}

    private Token getColonorFCToken()
    {
        int j = charIndex + 1;
		if (j < lineLength) {
			char lookAhead = nextLine.charAt(j);
			if (lookAhead == ':') {
				charIndex++;
				return createToken("::", TokenType.FC_HEADER);
			}
            else{
                return createToken(":", TokenType.COLON);
            }
		}
		return createToken(":", TokenType.COLON);
    }
	/**
	 * Method for tokenizing entire file for the Tokenizer and adding tokens to list
	 * @return Null if a syntax error was found during tokenization else an ArrayList of valid tokens
	 */
	private ArrayList<Token> getTokens() {
		ArrayList<Token> outList = new ArrayList<>();
		while (scan.hasNextLine() && !isTokenizeError) {
			this.nextLine = scan.nextLine();
			this.lineNum++;
			this.lineLength = nextLine.length();
			for (charIndex = 0; charIndex < lineLength && !isTokenizeError; charIndex++) {
				char nextChar = nextLine.charAt(charIndex);
				Token newToken = null;
				if (nextChar == '#')
				{
					break;
				}
				else if (isDigit(nextChar)) {
					newToken = getNumberToken("" + nextChar);
				}
				else if (isLetter(nextChar)) {
					newToken = getIdKeywordToken("" + nextChar);
				}
				else {
					switch (nextChar) {
						case ',' -> newToken = createToken(",", TokenType.COMMA);
						case ']' -> newToken = createToken("]", TokenType.R_BRACKET);
						case '[' -> newToken = createToken("[", TokenType.L_BRACKET);
						case '{' -> newToken = createToken("{", TokenType.L_BRACE);
						case '}' -> newToken = createToken("}", TokenType.R_BRACE);
						case '=' -> newToken = getEqualsSignToken();
						case '>', '<' -> newToken = getAngleBracketToken("" + nextChar);
						case '/', '+', '-', '*' -> newToken = createToken("" + nextChar, TokenType.MATH_OP);
						case ';' -> newToken = createToken(";", TokenType.SEMICOLON);
						case '.' -> newToken = getPeriodToken();
						case ':' -> newToken = getColonorFCToken();
						case '!' -> newToken = getExclamationToken();
						case '"' -> newToken = getStringToken();
					}
				}

				if (newToken != null) {
					outList.add(newToken);
				}
			}
		}
		this.scan.close();
		if (this.isTokenizeError) {
			return null;
		}
		return outList;
	}

	/**
     * Takes in a filename and tokenizes that file into Tokens
     * based on the rules of the Jott Language
     * @param filename the name of the file to tokenize; can be relative or absolute path
     * @return an ArrayList of Jott Tokens
     */
    public static ArrayList<Token> tokenize(String filename){
		JottTokenizer tokenizer = new JottTokenizer(filename);
		return tokenizer.getTokens();
	}


	/**
	 * Helper method for determining whether a character is a digit
	 * @return TRUE if character is a digit else FALSE
	 */
	private static boolean isDigit(char c) {
		return switch (c) {
			case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> true;
			default -> false;
		};
	}


	/**
	 * Helper method for determining whether a character is a letter
	 * @return TRUE if character is a letter else FALSE
	 */
	private static boolean isLetter(char c) {
		return switch (c) {
			case 'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e', 'F', 'f' -> true;
			case 'G', 'g', 'H', 'h', 'I', 'i', 'J', 'j', 'K', 'k', 'L', 'l' -> true;
			case 'M', 'm', 'N', 'n', 'O', 'o', 'P', 'p', 'Q', 'q', 'R', 'r' -> true;
			case 'S', 's', 'T', 't', 'U', 'u', 'V', 'v', 'W', 'w', 'X', 'x' -> true;
			case 'Y', 'y', 'Z', 'z' -> true;
			default -> false;
		};
	}
}