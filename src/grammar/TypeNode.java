package grammar;
import provided.*;

public class TypeNode implements JottTree{
    public string typenodetype;

    private static String ERROR_MESSAGE = "Error Parsing Type Node"

    public static parseTypeNode(ArrayList<Token> tokens){
        if (tokens.empty()){throw new IllegalArgumentException(ERROR_MESSAGE + ", Got Empty List");}

        Token examToken = tokens[0]
        tokens.remove[0];

        if (examToken.type != ID_KEYWORD){throw new IllegalArgumentException(ERROR_MESSAGE+", Got: " + examToken.getTokenType().toString)}

       
        if (currentToken.string.equals("Double")){
            typenodetype = "Double";
            return tokens;
        }
        if (currentToken.string.equals("String")){
            typenodetype = "String";
            return tokens;
        }
        if (currentToken.string.equals("Boolean")){
            typenodetype = "Boolean";
            return tokens;
        }
        if (currentToken.string.equals("Integer")){
            typenodetype = "Integer";
            return tokens;
        }
        else{
            throw new IllegalArgumentException(ERROR_MESSAGE + ", Got ID_KEYWORD, but not one of the Keywords for this node");
        }
    }
    @Override
    public String convertToJott() {
        return "" + this.idName.typenodetype;  
    }

    @Override
    public boolean validateTree() {
        // needs to be implemented
        return true;
    }

    @Override
    public void execute() {
        // needs to be implemented
        System.out.println("Type Node");
    }
}