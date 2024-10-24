package grammar;

import static grammar.Helper.*;
import java.util.ArrayList;
import provided.*; // checkTokenType(), checkIsNotEmpty()

/**
 * Represents an expression node in the Jott language, which can be expanded to:
 * < if_stmt > < while_loop > 
 * < asmt > < func_call >
 *     ;
 */
public interface BodyStmt extends JottTree {

    public static final String ERROR_MESSAGE = "Expected: Expression Node";

    /**
     * Determines if the node is an IfStmtNode, AsmtNoede, 
     * and parses the node accordingly.
     * @param tokens
     * @return The proper Node type
     * @throws Exception 
     */
    // < body_stmt > -> < if_stmt > | < while_loop > | < asmt > | < func_call > ;
    public static JottTree parseBodyStmt(ArrayList<Token> tokens) throws Exception {
        checkIsNotEmpty(tokens);
        Token currentToken = tokens.get(0); // Get the first token.
        JottTree parsedStatement;
        switch (currentToken.getTokenType()) {
            case ID_KEYWORD:
                // 'If', 'Elseif', 'Else' statements.
                System.out.println("case ID_KEYWORD");
                if (currentToken.getToken().equals("If") 
                        || currentToken.getToken().equals("Elseif") 
                        || currentToken.getToken().equals("Else")
                        ) 
                        {
                    parsedStatement = IfStatementNode.parseIfStatementNode(tokens);
                }
                // 'While' loops.
                else if (currentToken.getToken().equals("While")) {
                    parsedStatement = While_LoopNode.parsWhile_LoopNode(tokens);
                }
                // try to parse asmt <id >= < expr >;
                else {
                    // throw new IllegalArgumentException("Unexpected token: " + currentToken.getToken());
                    parsedStatement = AssignmentNode.parseAssignmentNode(tokens);
                    checkIsNotEmpty(tokens);
                    checkTokenType(tokens, TokenType.SEMICOLON);
                    tokens.remove(0);
                }
                break;
            case FC_HEADER:
                parsedStatement = FunctionCallNode.parseFuncCallNode(tokens);
                checkIsNotEmpty(tokens);
                checkTokenType(tokens, TokenType.SEMICOLON);
                tokens.remove(0);
                break;
            default:
                throw new IllegalArgumentException(
                        "Unexpected token type in body statement. Got: " + currentToken.getTokenType().toString()
                );
        }
        // After successfully parsing a statement, ensure there's a semicolon.
        //checkIsNotEmpty(tokens); // Ensure tokens are not empty for the semicolon check.
        //checkTokenType(tokens, TokenType.SEMICOLON); // Verify it's a semicolon.
        //tokens.remove(0); // Remove the semicolon.

        return parsedStatement; // Return the parsed statement.
    }

    /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public String convertToJott();

    public String toString();
    /**
     * This will validate that the tree follows the semantic rules of Jott
     * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    public boolean validateTree();  
    






}
