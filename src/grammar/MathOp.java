package grammar;
import java.util.ArrayList;
import provided.*;
import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()
public class MathOp implements ExprNode{

    private Token mathoptoken;

    public MathOp(Token token)
    {
        this.mathoptoken = token;
    }
    /**
     * 
     * @param tokens list of tokens from what we tokenized. 
     * @return the current first element in the tokens in this scenario a MathOp node containing MathOp token.
     */
    public static MathOp parseMathOpNode(ArrayList<Token> tokens) {
        checkIsNotEmpty(tokens);
        checkTokenType(tokens, TokenType.MATH_OP);
        MathOp mathoptoken = new MathOp(tokens.get(0));
        tokens.remove(0);
        return mathoptoken;
    }

    @Override
    public String convertToJott() {
        return "" + this.mathoptoken.getToken();
    }
    @Override
    public boolean validateTree() {
        return this.mathoptoken.getTokenType() == TokenType.MATH_OP;
    }
    @Override
    public void execute() {
        System.out.println("Executing MathOp");
    }
    @Override
    public Token getToken() {
        return this.getToken();
    }

    

    
    
}
