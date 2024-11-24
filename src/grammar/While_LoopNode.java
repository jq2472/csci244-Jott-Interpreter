package grammar;
import provided.*;
import java.util.ArrayList;

import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()
public class While_LoopNode implements BodyStmt{

    private ExprNode exprNode;
    private BodyNode bodyNode;

    public While_LoopNode(ExprNode exprNode, BodyNode bodyNode)
    {
        this.exprNode = exprNode;
        this.bodyNode = bodyNode;
    }
    public static While_LoopNode parsWhile_LoopNode(ArrayList<Token>tokens) throws Exception
    {
        checkIsNotEmpty(tokens);
        checkTokenType(tokens, TokenType.ID_KEYWORD);
        
        try{
            if(tokens.get(0).getToken().equals("While"))
            {
                tokens.remove(0);
            }
            else{
                throw new IllegalArgumentException("While keyword not in there. Illegal Argument.");
            }
        }
        catch(IllegalArgumentException iae)
        {
            System.out.println(iae.getMessage());
        }
        checkTokenType(tokens, TokenType.L_BRACKET);
        tokens.remove(0);
        ExprNode exprNode = ExprNode.parseExprNode(tokens);
        checkTokenType(tokens, TokenType.R_BRACKET);
        tokens.remove(0);
        checkTokenType(tokens, TokenType.L_BRACE);
        tokens.remove(0);
        BodyNode bodyNode = BodyNode.parseBodyNode(tokens);
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
        whileloopNodeStr.append(this.bodyNode.convertToJott());
        whileloopNodeStr.append("}");
        return whileloopNodeStr.toString();

    }

    //make sure cond. is valid, make sure body valid, make sure cond. returns bool
    @Override
    public boolean validateTree() {
        this.exprNode.validateTree();
        this.bodyNode.validateTree();

        if(!this.exprNode.getReturnType().equals("Boolean")){
            System.err.println("Condition in while Loop does not evaluate to BooleanNode");
            return false;
        }

        return true;
    }

    @Override
    public Object execute() {
        while (!(boolean)this.exprNode.execute()) {
            this.bodyNode.execute();
        }
        return "WhileLoop Completed";
    }
  
    
}
