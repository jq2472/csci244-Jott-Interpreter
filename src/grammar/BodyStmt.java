package grammar;

import provided.*;

import java.util.ArrayList;

import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()

/**
 * Represents an expression node in the Jott language, which can be expanded to:
 * < if_stmt > < while_loop > 
 * < asmt > < func_call >
 */
public interface BodyStmt extends JottTree {

    public static final String ERROR_MESSAGE = "Expected: Expression Node";

    /**
     * Determines if the node is an IfStmtNode, AsmtNoede, 
     * and parses the node accordingly.
     * @param tokens
     * @return The proper Node type
     */
    public static JottTree parseExprNode(ArrayList<Token> tokens) {
        
       checkIsNotEmpty(tokens);

        Token currentToken = tokens.get(0);

        switch (currentToken.getTokenType()) {
            
            case ID_KEYWORD:
                // check for if statments
                if (currentToken.getToken().equals("If") || currentToken.getToken().equals("Elseif") 
                    || currentToken.getToken().equals("Else")) {
                    return null;//if_stmt.java parser

                } else if (currentToken.getToken().equals("While")){
                    return null;//while_loop parser
                }
            case ASSIGN:
                return AssignmentNode.parseAssignmentNode(tokens);
            case FC_HEADER:
                return null;//func_call
                
            
            default:
                throw new IllegalArgumentException(ERROR_MESSAGE + ", Got: " + currentToken.getTokenType().toString());
        }
    }
    
    /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public String convertToJott();

    /**
     * This will validate that the tree follows the semantic rules of Jott
     * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    public boolean validateTree();  
    






}
