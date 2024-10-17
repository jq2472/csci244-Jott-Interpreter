package grammar;
import provided.*;
import java.util.ArrayList;

import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()
public class TypeNode implements JottTree{
    private Token typenodetype;

    private static String ERROR_MESSAGE = "Error Parsing Type Node";

    public TypeNode(Token typeString)
    {
        typenodetype = typeString;
    }
    public static TypeNode parseTypeNode(ArrayList<Token> tokens){
        checkIsNotEmpty(tokens);
        checkTokenType(tokens, TokenType.ID_KEYWORD);
        
        TypeNode typenode = new TypeNode(tokens.get(0));
        if (tokens.get(0).getToken().equals("Double")){
            
            tokens.remove(0);
            
        }
        else if (tokens.get(0).getToken().equals("String")){
            
            tokens.remove(0);
            
        }
        else if (tokens.get(0).getToken().equals("Boolean")){
            
            tokens.remove(0);
        }
        else if (tokens.get(0).getToken().equals("Integer")){
            
            tokens.remove(0);
        }
        else{
            throw new IllegalArgumentException("Syntax Error\n"+ERROR_MESSAGE + ", Got ID_KEYWORD, but not one of the Keywords for node\n"+tokens.get(0).getFilename()+":"+tokens.get(0).getLineNum());
            
        }
        return typenode;
        
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