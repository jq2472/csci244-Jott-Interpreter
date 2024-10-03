package grammar;

import java.util.ArrayList;
import provided.*;
import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()

public class NumberNode implements OperandNode {

    private Token value;

    public NumberNode(Token value) {
        this.value = value;
    }

    /**
     * Parses a NumberNode from tokens.
     * @param tokens
     * @return The parsed Token representing the number
     */
    public static NumberNode parseOperandNode(ArrayList<Token> tokens) {
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
        return "" + this.value.getToken();
    }

    @Override
    public boolean validateTree() {
        // needs to be implemented
        return true;
    }

    @Override
    public void execute() {
        // needs to be implemented
        System.out.println("Executing NumberNode");
    }
}
