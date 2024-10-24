package grammar;

import provided.*;

import java.util.ArrayList;
import static grammar.Helper.checkIsNotEmpty;
import static grammar.Helper.checkTokenType;

public class BinaryOpNode implements JottTree {

    private JottTree left;
    private JottTree operator; // RelopNode or MathopNode
    private JottTree right;

    public BinaryOpNode(JottTree left, JottTree operator, JottTree right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public String convertToJott() {
        // Return the binary operation as a string, e.g., "left + right" or "left == right"
        return left.convertToJott() + " " + operator.convertToJott() + " " + right.convertToJott();
    }

    @Override
    public boolean validateTree() {
        return true;
    }

    @Override
    public void execute() {
    }
}
