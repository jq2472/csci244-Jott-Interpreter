package grammar;

import provided.*;

import java.util.ArrayList;
import static grammar.Helper.checkIsNotEmpty;
import static grammar.Helper.checkTokenType;

public class BinaryOpNode implements ExprNode {

    private ExprNode left;
    private Token operator; // RelopNode or MathopNode
    private ExprNode right;

    public BinaryOpNode(ExprNode left, Token operator, ExprNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public String convertToJott() {
        // Return the binary operation as a string, e.g., "left + right" or "left == right"
        return left.convertToJott() + " " + operator.getToken() + " " + right.convertToJott();
    }

    @Override
    public boolean validateTree() {
        // validate left, right, where type left = type right
        if (!left.validateTree() || !right.validateTree()){
            return false;
        }
        return true;
    }

    @Override
    public void execute() {
    }

    @Override
    public Token getToken() {
        return this.operator;
    }

    @Override
    public String getReturnType() {
        if (this.operator.getTokenType().equals(TokenType.REL_OP)) {
            return "Boolean";
        }
        if (this.left.getReturnType().equals(this.right.getReturnType())){
            if (this.left.getReturnType().equals("Int") || this.left.getReturnType().equals("Float")) {
                return this.left.getReturnType();
            }
        }
        return "Error";
    }

  
}
