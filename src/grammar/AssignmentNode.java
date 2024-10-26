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
            JottTree idnode = IdNode.parseIdNode(tokens);
            Token currToken = tokens.get(0);

            checkTokenType(tokens, TokenType.ASSIGN);
            tokens.remove(0);

            checkIsNotEmpty(tokens);
            // errors if assignment ends abruptly x =;
            currToken = tokens.get(0);

            if (currToken.getTokenType().equals(TokenType.SEMICOLON)) {
                throw new IllegalArgumentException("Error: Expected Expression Node, got Semicolon.");
            }
            // else parse expression
            JottTree expressionNode = ExprNode.parseExprNode(tokens);

            // needs semicolon at the end of the assignment
            checkTokenType(tokens, TokenType.SEMICOLON);
            tokens.remove(0);

            AssignmentNode asgNode = new AssignmentNode(idnode, currToken, expressionNode);
            return asgNode;

        } catch (Exception e) {
            throw new IllegalArgumentException("Error Parsing Assignment Node");
        }
    }

    public String convertToJott() {
        return this.id1.convertToJott() + " " + this.value.getToken() + " " + this.expresnode.convertToJott() + ";";
    }

    public String toString(){
        return convertToJott();
    }

    public boolean validateTree() {//TODO
        return true;
    }

    public void execute() { //TODO
        System.out.println("Executing NumberNode");
    }
}