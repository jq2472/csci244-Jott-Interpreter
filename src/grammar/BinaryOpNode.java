package grammar;

import static grammar.Helper.print_err;
import provided.*;

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
            print_err("Error in left or right half of Binary Op Node", operator);
            return false;
        }
        if (!left.getReturnType().equals(right.getReturnType())){
            print_err("Type Mismatch in Binary Op Node", operator);
            return false;
        }
        return true;
    }

    @Override
    public Object execute() {
        //System.out.println("in BinaryOpNode execute");
        switch (operator.getToken()) {
            case "+":
                return ((double)left.execute() + (double)right.execute());
            case "-":
                return ((double)left.execute() - (double)right.execute());
            case "*":
                return ((double)left.execute() * (double)right.execute());
            case "/":
                if((double)right.execute() == 0){
                    System.out.println("Error divided bt zero");
                    return null;
                }
                return ((double)left.execute() / (double)right.execute());
            case "<":
                return ((double)left.execute() < (double)right.execute());
            case "<=":
                return ((double)left.execute() <= (double)right.execute());
            case ">":
                return ((double)left.execute() > (double)right.execute());
            case ">=":
                return ((double)left.execute() >= (double)right.execute());
            case "==":
                return (left.execute() == right.execute());
            default:
                System.out.println("Potential Error In BinaryOp Execution, did not return one of the intended nodes.");
                return false;
        }
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
        if (this.left.getReturnType().equals(this.right.getReturnType()) || this.left.getReturnType().equals("Any") || this.right.getReturnType().equals("Any")){
            if (this.left.getReturnType().equals("Integer") || this.left.getReturnType().equals("Double")) {
                return this.left.getReturnType();
            }
        }
        return "Error";
    }

  
}
