package grammar;
import static grammar.Helper.*;
import java.util.ArrayList;
import provided.*; // checkTokenType(), checkIsNotEmpty()


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
            e.printStackTrace();
            throw e;
        } catch (Exception e){
            System.err.println("An unexpected error occured in BodyNode" + e.getMessage());
            e.printStackTrace();
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