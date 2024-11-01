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
    public static BooleanNode parseBoolNode(ArrayList<Token> tokens) {
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

    public String toString(){
        return convertToJott();
    }
    
    @Override
    public void execute() {
         // needs to be implemented
         System.out.println("Executing BooleanNode");
    }
    
    @Override
    public boolean validateTree() {
        try{
            String booleanstr = this.booleantoken.getToken();
            if ((booleanstr.equals("True") || booleanstr.equals("False")) && this.booleantoken.getTokenType()
            == TokenType.ID_KEYWORD) {
                return true; // Valid boolean value
            }
            else{
                System.out.println("Invalid booleans: "+ booleanstr + ". Expecting 'True' or 'False'");
                return false;
            }
        }
        catch(Exception e)
        {
            System.out.println("Unexpecting error for validating Boolean node: "+e.getMessage());
            return false;
        }
}
}
