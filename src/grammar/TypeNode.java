package grammar;
import provided.*;
import java.util.ArrayList;

import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()
public class TypeNode implements JottTree{
    
    private Token typenodetype;

    private static String ERROR_MESSAGE = "Error Parsing Type Node";

    public TypeNode(Token typeString)
    {
        this.typenodetype = typeString;
    }
    public static TypeNode parseTypeNode(ArrayList<Token> tokens){
        checkIsNotEmpty(tokens);
        Token currentToken = tokens.get(0);
        
        String tokenValue = currentToken.getToken();
        try
        {
            if (tokenValue.equals("Double") || tokenValue.equals("String") ||
            tokenValue.equals("Boolean") || tokenValue.equals("Integer")) {
        
            // Remove the token from the list
            tokens.remove(0);
            
            // Token typeToken = tokens.remove(0);
            // return new TypeNode(typeToken);
        } else {
            System.out.println("ID KEYWORD: " + TokenType.ID_KEYWORD);
            throw new IllegalArgumentException("Syntax Error\n" + ERROR_MESSAGE +
                ", Got ID_KEYWORD, but not one of the expected types: " +
                "Double, String, Boolean, Integer\n" + 
                tokens.get(0).getFilename() + ":" + tokens.get(0).getLineNum());
            }
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
        return new TypeNode(currentToken);
        

        // checkTokenType(tokens, TokenType.ID_KEYWORD);

        // TypeNode typenode = new TypeNode(tokens.get(0));
        // if (tokens.get(0).getToken().equals("Double")){
            
        //     tokens.remove(0);
            
        // }
        // else if (tokens.get(0).getToken().equals("String")){
            
        //     tokens.remove(0);
            
        // }
        // else if (tokens.get(0).getToken().equals("Boolean")){
            
        //     tokens.remove(0);
        // }
        // else if (tokens.get(0).getToken().equals("Integer")){
            
        //     tokens.remove(0);
        // }
        // else{
        //     throw new IllegalArgumentException("Syntax Error\n"+ERROR_MESSAGE + ", Got ID_KEYWORD, but not one of the Keywords for node\n"+tokens.get(0).getFilename()+":"+tokens.get(0).getLineNum());
            
        // }
        
        // return typenode;
        
    }
    @Override
    public String convertToJott() {
        return "" + this.typenodetype.getToken();  
    }

    @Override
    public boolean validateTree() {
        // needs to be implemented phase 3
        return true;
    }

    @Override
    public void execute() {
        // needs to be implemented phase 3
        System.out.println("Type Node");
    }
}