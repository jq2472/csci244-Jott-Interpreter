package grammar;
import provided.*;
import java.util.ArrayList;
public class RelOpContainerNode implements ExprNode{
    JottTree Op1;
    JottTree MidOp;
    JottTree Op2;
    public RelOpContainerNode(JottTree a, JottTree b, JottTree c){
        Op1=a;
        MidOp=b;
        Op2=c;
    }
    public static JottTree parseRelOpContainerNode(ArrayList<Token> tokens){
        try{
        JottTree tokena = OperandNode.parseOperandNode(tokens);
        JottTree RelOP = RelOp.parseRelOpNode(tokens);
        JottTree tokenc = OperandNode.parseOperandNode(tokens);
        JottTree returnnode = new MathOpContainerNode(tokena, RelOP, tokenc);
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
        return Op1.convertToJott() + MidOp.convertToJott() + Op2.convertToJott();
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }
    
}