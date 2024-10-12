package grammar;
import provided.*;

import java.util.ArrayList;

import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()
public class BooleanNode implements ExprNode{
    private Token booleantoken;
    public BooleanNode(Token boolToken)
    {
        this.booleantoken = boolToken;
    }
    public static BooleanNode parseExprNode(ArrayList<Token> tokens) {
        checkIsNotEmpty(tokens);
        checkTokenType(tokens, TokenType.ID_KEYWORD);

        BooleanNode booleanNode = new BooleanNode(tokens.get(0));
        tokens.remove(0);

        return booleanNode;
    }
    @Override
    public String convertToJott() {
        return ""+this.booleantoken.getToken();
    }
    @Override
    public void execute() {
         // needs to be implemented
         System.out.println("Executing BooleanNode");
    }
    
    @Override
    public boolean validateTree() {
        // needs to be implemented
        return true;
    }
}
