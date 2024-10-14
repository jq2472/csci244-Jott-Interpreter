package grammar;
import provided.*;
import java.util.ArrayList;

import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()

public class BodyNode implements JottTree{
    private ArrayList<JottTree> elements;
    public BodyNode(ArrayList<JottTree>elements)
    {
        this.elements = elements;
    }
    public BodyNode parsebodynode(ArrayList<Token>tokens) throws Exception
    {
        checkIsNotEmpty(tokens);
        ArrayList<JottTree> bodyTree = new ArrayList<>();
        Token currToken = tokens.get(0);
        while (currToken.getTokenType() == TokenType.ID_KEYWORD || currToken.getTokenType() == TokenType.ASSIGN || currToken.getTokenType() == TokenType.FC_HEADER)
        {
            JottTree bodyStmtTree = BodyStmt.parseBodyStmt(tokens); // Parse each body statement
            bodyTree.add(bodyStmtTree);
            tokens.remove(0);
            currToken = tokens.get(0);
        }
        JottTree returnStmtTree = Return_StmtNode.parseReturn_StmtNode(tokens); // Parse the return statement
        if (returnStmtTree == null) {
            throw new Exception("Expected return statement but none found.");
        }
        bodyTree.add(returnStmtTree);
        return new BodyNode(bodyTree);
    
    }
    @Override
    public String convertToJott() {
        String finalstr = "";
        for(JottTree element: this.elements)
        {
            finalstr += element.convertToJott();
        }
        return finalstr;
    }

    @Override
    public boolean validateTree() {
        return true;
    }

    @Override
    public void execute() {
        System.out.println("Body Node");
    }
    
}
