package grammar;
import provided.*;
import java.util.ArrayList;

import interpreter.SymbolTable;

import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()
public class Function_RetNode implements JottTree{
    private TypeNode typeNode;
    private Token voidtoken;
    public Function_RetNode(TypeNode typeNode)
    {
        this.typeNode = typeNode;
        this.voidtoken = null;
    }
    public Function_RetNode(Token voidToken)
    {
        this.typeNode = null;
        this.voidtoken = voidToken;
    }
    
    public static Function_RetNode parsefunctionRetNode(ArrayList<Token>tokens)
    {
        try {
            checkIsNotEmpty(tokens);
            checkTokenType(tokens, TokenType.ID_KEYWORD);
            
            Token currToken = tokens.get(0);
            if (currToken.getToken().equals("Void")) {
                tokens.remove(0);
                return new Function_RetNode(currToken);
            } else if (currToken.getToken().equals("Double") || currToken.getToken().equals("String") 
                    || currToken.getToken().equals("Integer") || currToken.getToken().equals("Boolean")) {
                return new Function_RetNode(TypeNode.parseTypeNode(tokens));
            } else {
                System.err.println("Syntax Error");
                throw new IllegalArgumentException(ERROR_MESSAGE + ", Got ID_KEYWORD, but not one of the Keywords for node\n" + currToken.getFilename() + ":" + currToken.getLineNum());
            }
        } catch (IllegalArgumentException iae) {
            System.err.println("Illegal Argument: " + iae.getMessage());
            throw iae;  // Re-throw the exception after logging
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            throw e;  // Re-throw the exception after logging
        }
    
    }
    @Override
    public String convertToJott() {
        String returnedString = "";
        if(this.typeNode == null)
        {
            returnedString =this.voidtoken.getToken();
        }
        else
        {
            returnedString = this.typeNode.convertToJott();
        }
        return returnedString;
    }

    @Override
    public boolean validateTree() {
        if (this.typeNode != null) {
            return this.typeNode.validateTree();
        } 
        else if (this.voidtoken != null) {
            
            return this.voidtoken.getToken().equals("Void");
        }
        return false; // Neither typeNode nor voidtoken is valid
    }

    @Override
    public void execute() {
        System.out.println("Function_RetNode");
    }
    
}
