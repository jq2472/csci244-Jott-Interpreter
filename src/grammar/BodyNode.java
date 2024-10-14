package grammar;
import provided.*;
import java.util.ArrayList;

import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()

public interface BodyNode extends JottTree{
    
    
    public static ArrayList<JottTree> parsebodynode(ArrayList<Token>tokens) throws Exception
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
        return bodyTree;
    
    }
    @Override
    public String convertToJott();

    
    public boolean validateTree();
    @Override
    public void execute();
    
}
