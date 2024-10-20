package grammar;
import java.util.ArrayList;
import provided.*;
import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()
/**
 * Represents an operand node in the Jott language, which can be expanded to:
 * < id >
 * < num >
 * < func_call >
 * - < num >
 * Handles methods for parsing, converting to Jott, validating, and executing
 * each node's respective grammar rules.
 */
public interface OperandNode extends JottTree  {

    public static final String ERROR_MESSAGE = "Expected: Operand Node";
    
    /**
     * Determines if the node is an IDNode, NumberNode, or FunctionCallNode
     * and parses the node accordingly.
     * @param tokens
     * @return The proper Node type
     * @throws Exception 
     */
    public static JottTree parseOperandNode(ArrayList<Token> tokens) throws Exception {
        
        checkIsNotEmpty(tokens);

        Token currentToken = tokens.get(0);
        

        switch (currentToken.getTokenType()) {
            // note: each case still checks again for empty/valid token type
            // might need to remove those additional checks in the future
            // why not just remove now? 
            // -> see < body_stmt > grammar rules doesn't call <operand> directly
            case ID_KEYWORD:
                return IdNode.parseOperandNode(tokens);
            case FC_HEADER:
                return FunctionCallNode.parseFuncCallNode(tokens);
            case NUMBER:
                return NumberNode.parseOperandNode(tokens);
            case MATH_OP:
                if(currentToken.equals("-")){
                    try {
                        tokens.remove(0);
                        Token nextToken = tokens.get(0);
                        if (nextToken.getTokenType().equals(TokenType.NUMBER)) {
                            NumberNode j  = NumberNode.parseOperandNode(tokens);
                            j.negative();
                            return j;
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Error Parsing Math_Op in OperandNode, no number following");
                    }
                }
            
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
	
	/**
	 * This will execute the Jott code represented by this JottTree node.
	 */
	public void execute();

}
