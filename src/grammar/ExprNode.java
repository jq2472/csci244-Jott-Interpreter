package grammar;

import provided.*;

import java.util.ArrayList;

import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()

/**
 * Represents an expression node in the Jott language, which can be expanded to:
 * < operand > 
 * < operand > < relop > < operand >
 * < operand > < mathop > < operand > 
 * < string_literal > 
 * < bool >
 */
public interface ExprNode extends JottTree {

    public static final String ERROR_MESSAGE = "Expected: Expression Node";

    /**
     * Determines if the node is an OperandNode, RelopNode, MathopNode, StringNode, or BoolNode
     * and parses the node accordingly.
     * @param tokens
     * @return The proper Node type
     */
    public static JottTree parseExprNode(ArrayList<Token> tokens) {
        
       checkIsNotEmpty(tokens);

        Token currentToken = tokens.get(0);

        switch (currentToken.getTokenType()) {
            // note: each case still checks again for empty/valid token type
            // might need to remove those additional checks in the future
            // why not just remove now? 
            // -> see < body_stmt > grammar rules doesn't call <operand> directly
            // -> see < body_stmt > grammar rules doesn't call <operand> directly
            case ID_KEYWORD:
                // check if boolean
                if (currentToken.getToken().equals("true") || currentToken.getToken().equals("false")) {
                    // parse a boolean node

                } else {
                    return IdNode.parseOperandNode(tokens);
                }
            case FC_HEADER:
                return FunctionCallNode.parseOperandNode(tokens);
            case NUMBER:
                return NumberNode.parseOperandNode(tokens);
            case STRING:
                return StrLitNode.parseExprNode(tokens);
            case REL_OP:
                
            case MATH_OP:
                return MathOp.parseMathOpNode(tokens);
                
            
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
