package grammar;

import provided.*;

import java.util.ArrayList;

import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()

public class StrLitNode implements ExprNode {

    private Token str;

    public StrLitNode(Token token) {
        this.str = token;
    }

    public static StrLitNode parseExprNode(ArrayList<Token> tokens) {
        checkIsNotEmpty(tokens);
        checkTokenType(tokens, TokenType.STRING);

        StrLitNode newStrLitNode = new StrLitNode(tokens.get(0));
        tokens.remove(0);

        return newStrLitNode;
    }

   

    @Override
    public String convertToJott() {
        return "" + this.str.getToken();
    }

    @Override
    public boolean validateTree() {
        // needs to be implemented
        return true;
    }

    @Override
    public void execute() {
        // needs to be implemented
        System.out.println("Executing StrLitNode");
    }

}