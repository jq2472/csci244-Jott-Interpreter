package grammar;
import provided.*;
import java.util.ArrayList;

import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()
public class While_LoopNode implements JottTree{

    private JottTree exprNode;
    private ArrayList<JottTree>bodyNode;

    public While_LoopNode(JottTree exprNode, ArrayList<JottTree>bodyNode)
    {
        this.exprNode = exprNode;
        this.bodyNode = bodyNode;
    }
    public static While_LoopNode parsWhile_LoopNode(ArrayList<Token>tokens) throws Exception
    {
        checkIsNotEmpty(tokens);
        checkTokenType(tokens, TokenType.ID_KEYWORD);
        
        if(tokens.get(0).getToken().equals("While"))
        {
            tokens.remove(0);
        }
        else{
            throw new IllegalArgumentException("While keyword not in there. Illegal Argument.");
        }
        checkTokenType(tokens, TokenType.L_BRACKET);
        tokens.remove(0);
        JottTree exprNode = ExprNode.parseExprNode(tokens);
        checkTokenType(tokens, TokenType.R_BRACKET);
        tokens.remove(0);
        checkTokenType(tokens, TokenType.L_BRACE);
        tokens.remove(0);
        ArrayList<JottTree>bodyNode = BodyNode.parsebodynode(tokens);
        checkTokenType(tokens, TokenType.R_BRACE);
        tokens.remove(0);
        return new While_LoopNode(exprNode, bodyNode);
    }
    @Override
    public String convertToJott() {
        StringBuilder whileloopNodeStr = new StringBuilder();
        whileloopNodeStr.append("While");
        whileloopNodeStr.append("[");
        whileloopNodeStr.append(this.exprNode.convertToJott());
        whileloopNodeStr.append("]");
        whileloopNodeStr.append("{");
        for (JottTree jottTree : this.bodyNode) {
            whileloopNodeStr.append(jottTree.convertToJott());
        }
        whileloopNodeStr.append("}");
        return whileloopNodeStr.toString();

    }

    @Override
    public boolean validateTree() {
        return true;
    }

    @Override
    public void execute() {
        System.out.println("While_LoopNode");
    }
    
}
