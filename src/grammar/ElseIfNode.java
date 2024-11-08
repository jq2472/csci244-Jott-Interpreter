package grammar;
import static grammar.Helper.*;
import java.util.ArrayList;
import provided.*;

public class ElseIfNode implements JottTree{

    private JottTree bodyNode;
    private JottTree exprNode;

    public ElseIfNode(JottTree bodyNode, JottTree exprNode){
        this.bodyNode = bodyNode;
        this.exprNode = exprNode;
    }

    public static ElseIfNode parseElseIfNode(ArrayList<Token> tokens) throws Exception{
        try {
            checkIsNotEmpty(tokens);
            checkTokenType(tokens, TokenType.ID_KEYWORD);
            
            if (tokens.get(0).getToken().equals("ElseIf")) {
                tokens.remove(0);
                checkTokenType(tokens, TokenType.L_BRACKET);
                tokens.remove(0);
                JottTree condition = ExprNode.parseExprNode(tokens);
                checkTokenType(tokens, TokenType.R_BRACKET);
                tokens.remove(0);
                checkTokenType(tokens, TokenType.L_BRACE);
                tokens.remove(0);
                JottTree bodyTree = BodyNode.parseBodyNode(tokens);
                checkTokenType(tokens, TokenType.R_BRACE);
                tokens.remove(0);
                return new ElseIfNode(bodyTree, condition);
            } else {
                throw new IllegalArgumentException("Error Parsing ElseIf node, expected ElseIf");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // Handle or rethrow the exception as necessary
            return null;
        }
    }

    @Override
    public String convertToJott() {
        if(this.bodyNode != null)
        {
            StringBuilder elseNodeStr = new StringBuilder();
            elseNodeStr.append("ElseIf");
            elseNodeStr.append("[");
            elseNodeStr.append(this.exprNode.convertToJott());
            elseNodeStr.append("]");
            elseNodeStr.append("{");
            elseNodeStr.append(this.bodyNode.convertToJott());
            elseNodeStr.append("}");
            return elseNodeStr.toString();
        }
        else{
            return "";
        }
    }

    @Override
    public boolean validateTree() {
        // validate bodynode and expressionNode
        if (this.bodyNode == null){
            return bodyNode.validateTree();
        }if (this.exprNode == null){
            return exprNode.validateTree();
        }
        return false;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
}
