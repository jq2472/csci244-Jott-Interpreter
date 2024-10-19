package grammar;
import java.util.ArrayList;
import provided.Token;
import provided.TokenType;

/**
 * Helper class for the JottParser:
 * 
 * if token type: 
 * checkTokenType(ArrayList<Token> tokens, TokenType expectedType)
 * 
 * if a list of tokens is empty:
 * checkIsNotEmpty(ArrayList<Token> tokens)
 * 
 */

public class Helper {

    public static final String ERROR_MESSAGE = "Parsing Error: ";

    /**
     * Helper method to check if the next token is of the expected type.
     * @param tokens the list of tokens to check
     * @param expectedType the expected token type
     * @param errorMessage the error message to throw if the check fails
     */
    public static void checkTokenType(ArrayList<Token> tokens, TokenType expectedType) {
        checkIsNotEmpty(tokens);
        if (tokens.get(0).getTokenType() != expectedType) {
            System.err.println("Syntax Error\n" +ERROR_MESSAGE + "expected " + expectedType + " but got " + tokens.get(0).getTokenType() + "\n" +tokens.get(0).getFilename() + ":" +tokens.get(0).getLineNum());
        }
    }

    /**
     * Helper method to check if the list of tokens is empty.
     * @param tokens
     * @return true if the list of tokens is not empty
     */
    public static boolean checkIsNotEmpty(ArrayList<Token> tokens) {
        if (tokens.size() == 0) {
            throw new IllegalArgumentException(ERROR_MESSAGE + "list of tokens is empty");
        }
        return true;
    }
    
}
