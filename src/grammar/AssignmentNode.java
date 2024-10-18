package grammar;
import static grammar.Helper.checkIsNotEmpty;
import static grammar.Helper.checkTokenType;
import java.util.ArrayList;
import provided.*;


public class AssignmentNode implements BodyStmt{
    private JottTree id1;
    private Token value;
    private JottTree expresnode;


    public AssignmentNode(JottTree idnode, Token value, JottTree expression) {
        this.id1 = idnode;
        this.value = value;
        this.expresnode = expression;
    }
    
    public static AssignmentNode parseAssignmentNode(ArrayList<Token>tokens){
        checkIsNotEmpty(tokens);
        try {
            JottTree idnode = IdNode.parseOperandNode(tokens);
            Token currToken = tokens.get(0);
            checkTokenType(tokens, TokenType.ASSIGN);
            tokens.remove(0);
            Token y = currToken;
            JottTree expressionNode = ExprNode.parseExprNode(tokens);
            AssignmentNode asgNode = new AssignmentNode(idnode, y, expressionNode);
            currToken = tokens.get(0);
            checkTokenType(tokens, TokenType.SEMICOLON);
            tokens.remove(0);
            return asgNode;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error Parsing Assignment Node");
        }
    }

    public String convertToJott() {
        return this.id1.convertToJott() + " " + this.value.getToken() + " " + this.expresnode.convertToJott() + ";";
    }

    public boolean validateTree() {//TODO
        return true;
    }

    public void execute() { //TODO
        System.out.println("Executing NumberNode");
    }


    
}

