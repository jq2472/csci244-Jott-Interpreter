package grammar;
import provided.*;
import java.util.ArrayList;

import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()


public class BodyNode implements JottTree {

    private ArrayList<JottTree> bodystatementArrayList;
    private JottTree returnnode;

    public BodyNode(ArrayList<JottTree> bodystmts, JottTree returnstatement) {
        this.bodystatementArrayList = bodystmts;
        this.returnnode = returnstatement;

    }

    /**
     * Parses a BodyNode from tokens.
     * <body> -> <body_stmt>* <return_stmt>
     * @param tokens The list of tokens to parse
     * @return The parsed BodyNode
     * @throws Exception If parsing fails
     */
    public static BodyNode parseBodyNode(ArrayList<Token> tokens) throws Exception {
        
        try{
            checkIsNotEmpty(tokens); // Ensure tokens are not empty at the start.

            ArrayList<JottTree> bodystmts = new ArrayList<>();

            // Parse all body statements until 'Return' or 'R_BRACE' is found.
            while (!tokens.isEmpty() &&
                    !tokens.get(0).getTokenType().equals(TokenType.R_BRACE) &&
                    !tokens.get(0).getToken().equals("Return")) {
                JottTree bodystmt = BodyStmt.parseBodyStmt(tokens); // Parse each body statement
                bodystmts.add(bodystmt); // Add to the list
            }

            JottTree returnStmt = null; // Default to no return statement.

            if (tokens.isEmpty() || tokens.get(0).getTokenType().equals(TokenType.R_BRACE)) {
                System.out.println("FOUND RBRACE!!! ");
            }

            // Check if the next token is a 'Return' statement.
            
            if (!tokens.isEmpty() && tokens.get(0).getToken().equals("Return")) {
                returnStmt = Return_StmtNode.parseReturn_StmtNode(tokens); // Parse return statement
            }

            // Return the parsed BodyNode.
            return new BodyNode(bodystmts, returnStmt);
        
        } catch (IllegalArgumentException e){
            System.err.println("IllegalArgumentException in BodyNode: " + e.getMessage());
            throw e;
        } catch (Exception e){
            System.err.println("An unexpected error occured in BodyNode" + e.getMessage());
            throw e;
        }
    }

    /**
     * Converts the body node to a Jott code string representation.
     * @return A string representing the Jott code of this tree.
     */
    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();
        for (JottTree stmt : this.bodystatementArrayList) {
            sb.append(stmt.convertToJott());
            sb.append("\n"); // can rmv
        }
        // convert the return statement if it exists.
        if (returnnode != null) {
            sb.append(this.returnnode.convertToJott());
        }
        return sb.toString();
    }

    public String toString() {
        return " " + this.convertToJott();
    }

    /**
     * This will validate that the tree follows the semantic rules of Jott
     * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    public boolean validateTree(){
        return true;
    }

    /**
     * This will execute the Jott code represented by this JottTree node.
     */
    public void execute(){
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