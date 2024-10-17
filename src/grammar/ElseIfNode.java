package grammar;
import static grammar.Helper.*;
import java.util.ArrayList;
import provided.*;

public class ElseIfNode implements JottTree{

    private ArrayList<JottTree>bodyNode;
    private JottTree exprNode;

    public ElseIfNode(ArrayList<JottTree>bodyNode, JottTree exprNode){
        this.bodyNode = bodyNode;
        this.exprNode = exprNode;
    }

    public static ElseIfNode parseElseIfNode(ArrayList<Token> tokens) throws Exception{
        checkIsNotEmpty(tokens);
        checkTokenType(tokens, TokenType.ID_KEYWORD);
        ArrayList<JottTree>bodyTree = new ArrayList<>();

        if(tokens.get(0).getToken().equals("ElseIf")){
            tokens.remove(0);
            checkTokenType(tokens, TokenType.L_BRACKET);
            tokens.remove(0);
            JottTree condition = ExprNode.parseExprNode(tokens);
            checkTokenType(tokens, TokenType.R_BRACKET);
            tokens.remove(0);
            checkTokenType(tokens, TokenType.L_BRACE);
            tokens.remove(0);
            bodyTree = BodyNode.parsebodynode(tokens);
            checkTokenType(tokens, TokenType.R_BRACE);
            return new ElseIfNode(bodyTree, condition);
        }else{
            throw new IllegalArgumentException("Error Parsing ElseIf node, expected ElseIf");
        }
    }

    @Override
    public String convertToJott() {
        if(this.bodyNode != null)
        {
            StringBuilder elseNodeStr = new StringBuilder();
            elseNodeStr.append("ElseIf");
            for (JottTree jottTree : this.bodyNode) {
                elseNodeStr.append(jottTree.convertToJott());
            }
            return elseNodeStr.toString();
        }
        else{
            return "";
        }
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
}
