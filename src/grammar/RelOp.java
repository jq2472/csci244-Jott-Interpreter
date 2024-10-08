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
        checkTokenType(tokens, TokenType.MATH_OP);
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
        return true;
    }

    @Override
    public void execute(){
        System.out.println("executing RelOp");
    }
}
