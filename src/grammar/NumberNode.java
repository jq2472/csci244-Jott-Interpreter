package grammar;

import static grammar.Helper.*;
import java.util.ArrayList;
import provided.*; // checkTokenType(), checkIsNotEmpty()

public class NumberNode implements OperandNode {

    private Token value;
    private boolean negative;

    public NumberNode(Token value) {
        this.value = value;
        this.negative = false;
    }
    public void negative(){
        this.negative = true;
    }

    /**
     * Parses a NumberNode from tokens.
     * @param tokens
     * @return The parsed Token representing the number
     */
    public static NumberNode parseNumberNode(ArrayList<Token> tokens) {
        checkIsNotEmpty(tokens);
        checkTokenType(tokens, TokenType.NUMBER);

        NumberNode newNumNode = new NumberNode(tokens.get(0));
        tokens.remove(0);

        return newNumNode;

    }
    /**
     * @return the NumberNode instance to a string. We get the token out of NumberNode
     */
    @Override
    public String convertToJott() {
        if (this.negative) {
            return "-" + this.value.getToken();
        }
        return "" + this.value.getToken();
    }

    public String toString(){
        return convertToJott();
    }

    @Override
    public boolean validateTree() {
        if (value.getTokenType() != TokenType.NUMBER) {
            System.out.println("Invalid token type for NumberNode: " + value.getToken());
            return false;
        }
        try {
            Double.parseDouble(toString());
        } 
        catch (NumberFormatException e) {
            System.out.println("Incorrect formatting for number "+ toString());
        }
        return true;

    }

    @Override
    public void execute() {
        // needs to be implemented
        System.out.println("Executing NumberNode");
    }
}
