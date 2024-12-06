package grammar;
import java.util.ArrayList;
import provided.*;
import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()
public class MathOp implements ExprNode{

    private Token mathoptoken;
    private ExprNode left;
    private ExprNode right;

    public MathOp(Token token)
    {
        this.mathoptoken = token;
        this.left = left;
        this.right = right;
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
    public Object execute() { // needs verification
        System.out.println("in MathOp execute");
        Object leftValue = left.execute();
        Object rightValue = right.execute();
        if (!(leftValue instanceof Number) || !(rightValue instanceof Number)){
            System.err.println("Operands must be numeric");
        }
        double left = ((Number) leftValue).doubleValue();
        double right = ((Number) rightValue).doubleValue();
        switch (this.mathoptoken.getToken()) {
            case "+":
                return left + right;
            case "-":
                return left - right;
            case "*":
                return left * right;
            case "/":
                if (right == 0) {
                    System.err.println("error: division by zero");
                }
                return left / right;
            default:
                throw new UnsupportedOperationException("unsupported math operator: " + this.mathoptoken.getToken());
        }
    }
    @Override
    public Token getToken() {
        return null;
    }

    @Override
    public String getReturnType() {
        return "MathOP";
    }
    

    
    
}
