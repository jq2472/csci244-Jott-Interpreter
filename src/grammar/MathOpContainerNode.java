package grammar;
import provided.*;
import java.util.ArrayList;
public class MathOpContainerNode implements ExprNode{
    JottTree Op1;
    JottTree MidOp;
    JottTree Op2;
    public MathOpContainerNode(JottTree a, JottTree b, JottTree c){
        this.Op1=a;
        this.MidOp=b;
        this.Op2=c;
    }
    public static JottTree parseMathOpContainerNode(ArrayList<Token> tokens){
        try{
        JottTree tokena = OperandNode.parseOperandNode(tokens);
        JottTree mathOP = MathOp.parseMathOpNode(tokens);
        JottTree tokenc = OperandNode.parseOperandNode(tokens);
        JottTree returnnode = new MathOpContainerNode(tokena, mathOP, tokenc);
        return returnnode;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while parsing MathOpContainerNode, expected Operand, MathOp, Operand");
        }
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    @Override
    public String convertToJott() {
        // TODO Auto-generated method stub
        return this.Op1.convertToJott() + this.MidOp.convertToJott() + this.Op2.convertToJott();
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }
    
}
