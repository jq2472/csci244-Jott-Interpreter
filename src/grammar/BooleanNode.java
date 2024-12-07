package grammar;
import provided.*;

import java.util.ArrayList;

import interpreter.SymbolTable;

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
    public Object execute() {
        //System.out.println("in BooleanNode execute");
        
        if (this.booleantoken.getToken().equals("True")){return true;}
        else {return false;}
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
                print_err("Error in boolean node ", booleantoken);
                return false;
            }
        }
        catch(Exception e)
        {
            print_err("Unknown Error in Boolean Node", booleantoken);
            return false;
        }

    }
    
    @Override
    public Token getToken() {
        return booleantoken;
    }
    @Override
    public String getReturnType() {
        return "Boolean";
    }

    
       
}
