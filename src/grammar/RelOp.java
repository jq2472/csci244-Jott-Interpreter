package grammar;
import java.util.ArrayList;
import provided.*;
import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()

public class RelOp implements ExprNode{

    private Token RelOpToken;

    public RelOp(Token token){
        this.RelOpToken = token;
    }
    
    public static RelOp parseRelOpNode(ArrayList<Token> tokens){
        checkIsNotEmpty(tokens);
        checkTokenType(tokens, TokenType.REL_OP);
        RelOp RelOpToken = new RelOp(tokens.get(0));
        tokens.remove(0);
        return RelOpToken;
    }

    @Override
    public String convertToJott(){
        return "" + this.RelOpToken.getToken();
    }

    @Override
    public boolean validateTree(){
        // checking if token is RelOp
        if (this.RelOpToken.getTokenType().equals(TokenType.REL_OP)){
            return true;
        }
        return false;
    }

    @Override
    public Object execute(){
        return "Placeholder in relop";
    }

    @Override
    public Token getToken() {
        return null;
    }

    @Override
    public String getReturnType() {
        return "RelOpNode";
    }
}
