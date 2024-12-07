package grammar;
import static grammar.Helper.*;
import java.util.ArrayList;
import provided.*;

public class ElseIfNode implements JottTree{

    private JottTree bodyNode;
    private ExprNode exprNode;

    public ElseIfNode(JottTree bodyNode, ExprNode exprNode){
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
                ExprNode condition = ExprNode.parseExprNode(tokens);
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
        // if (this.bodyNode == null){
        //     return bodyNode.validateTree();
        // }if (this.exprNode == null){
        //     return exprNode.validateTree();
        // }
        // return false;
        
        // Check if there is an exprNode, if there, check if the exprnode is valid.
        if (exprNode == null || !exprNode.validateTree()) {
            System.err.println("Error: Invalid or missing condition in ElseIf statement.");
            return false;
        }
        // Check if the exprNode is a BooleanNode or BinaryOpNode, for the expression condidion.
        if (!exprNode.getReturnType().equals("Boolean")) {
            System.err.println("Error: ElseIf condition must evaluate to a boolean");
            return false;
        }
        // Check if the body is not empty, and see if it is valid.
        if (bodyNode == null || !bodyNode.validateTree()) {
            System.err.println("Error: Invalid or missing body in ElseIf statement.");
            return false;
        }
        return true;
    }

    @Override
    public Object execute() {
        //System.out.println("in ElseIfNode execute");
        if ((Boolean)this.exprNode.execute()) {
            this.bodyNode.execute();
            return "Branch Taken";
        }
        else{
            return "Branch Not Taken";
        }
    }

    
}
