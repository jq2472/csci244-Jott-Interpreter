package grammar;
import provided.*;
import java.util.ArrayList;

import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()


public class BodyNode implements JottTree{

    private ArrayList<JottTree> bodystatementArrayList;
    private JottTree returnnode;

    public BodyNode(ArrayList<JottTree> bodystmts, JottTree returnstatement){
        this.bodystatementArrayList = bodystmts;
        this.returnnode = returnstatement;

    }

    public static BodyNode parseBodyNode(ArrayList<Token>tokens) throws Exception{
        System.out.println("Entering BodyNode Parsing");
        checkIsNotEmpty(tokens);
        ArrayList<JottTree> bodystmts = new ArrayList<>();
        while (!tokens.get(0).getTokenType().equals(TokenType.R_BRACE) && !tokens.get(0).getToken().equals("Return")) {
            JottTree bodystmttoadd = BodyStmt.parseBodyStmt(tokens);
            bodystmts.add(bodystmttoadd);
        }
        if (tokens.get(0).getToken().equals("Return")){
            JottTree j = Return_StmtNode.parseReturn_StmtNode(tokens);
            return new BodyNode(bodystmts, j);
        }
        return new BodyNode(bodystmts, null);

    }

    @Override
    public String convertToJott() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToJott'");
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
/* 
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
*/