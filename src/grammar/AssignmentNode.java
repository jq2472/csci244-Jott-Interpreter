package grammar;
import static grammar.Helper.checkIsNotEmpty;
import static grammar.Helper.checkTokenType;

import java.util.ArrayList;

import provided.Token;
import provided.TokenType;


public class AssignmentNode implements BodyStmt{
    private Token value;

    public AssignmentNode(Token value) {
        this.value = value;
    }
    
    public static AssignmentNode parseAssignmentNode(ArrayList<Token>tokens){
        checkIsNotEmpty(tokens);
        checkTokenType(tokens, TokenType.ASSIGN);
        AssignmentNode asgNode = new AssignmentNode(tokens.get(0));
        return asgNode;

    }

    public String convertToJott() {
        return "" + this.value.getToken();
    }

    public boolean validateTree() {//TODO
        return true;
    }

    public void execute() { //TODO
        System.out.println("Executing NumberNode");
    }


    
}

