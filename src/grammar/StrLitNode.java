package grammar;

import provided.*;

import java.util.ArrayList;

import interpreter.SymbolTable;

import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()

public class StrLitNode implements ExprNode {

    private Token str;

    public StrLitNode(Token token) {
        this.str = token;
    }

    public static StrLitNode parseStrLitNode(ArrayList<Token> tokens) {
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

    public String toString(){
        return convertToJott();
    }

    @Override
    public boolean validateTree() {
        if (this.str.getTokenType() != TokenType.STRING) {
            System.err.println("Error: Invalid token type for StrlitNode. Expected STRING_LITERAL, got: " 
                               + this.str.getTokenType());
            return false;
        }
    
        // Check if the literal value is enclosed in quotes
        String stringvalue = this.str.getToken();
        boolean isValid = stringvalue.startsWith("\"") && stringvalue.endsWith("\"");
    
        // If isValid is False.
        if (!isValid) {
            System.err.println("Error: String literal not enclosed in quotes: " + stringvalue);
            return isValid; 
        }
        
        return isValid;
    }

    @Override
    public void execute() {
        // needs to be implemented in phase 3
        System.out.println("Executing StrLitNode");
    }

}
