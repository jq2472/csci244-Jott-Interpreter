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

        // Token currentToken = tokens.get(0);
        // Token nextToken = tokens.get(1);
        try{
            // switch (currentToken.getTokenType()) {
            //     // note: each case still checks again for empty/valid token type
            //     // might need to remove those additional checks in the future
            //     // why not just remove now? 
            //     // -> see < body_stmt > grammar rules doesn't call <operand> directly
            //     // -> see < body_stmt > grammar rules doesn't call <operand> directly
            //     case ID_KEYWORD:
            //         // check if boolean
            //         if (currentToken.getToken().equals("True") || currentToken.getToken().equals("False")) {
            //             // parse a boolean node
            //             return BooleanNode.parseExprNode(tokens);

            //         } else {
            //             return IdNode.parseOperandNode(tokens);
            //         }
            //     case FC_HEADER:
            //         return FunctionCallNode.parseFuncCallNode(tokens);
            //     case STRING:
            //         return StrLitNode.parseExprNode(tokens);
            //     default:
            //         if (nextToken.getTokenType().equals(TokenType.MATH_OP)){
            //             return parsemultistepJottTree(tokens, nextToken.getTokenType());
            //         }
            //         else if (nextToken.getTokenType().equals(TokenType.REL_OP)){

            //         }
            //         return OperandNode.parseOperandNode(tokens);
                    
            //     }

            Token t = tokens.get(0);

            //String Literal Node
            if(t.getTokenType().equals(TokenType.STRING)){
                return StrLitNode.parseStrLitNode(tokens);
            }

            //Boolean Node
            else if(t.getTokenType() == TokenType.ID_KEYWORD && (t.getToken().equals("True") || t.getToken().equals("False"))){
                return BooleanNode.parseBoolNode(tokens);
            }

            //Operand or Binary op
            else{
                JottTree left = OperandNode.parseOperandNode(tokens);

                t = tokens.get(0);

                if(!t.getTokenType().equals(TokenType.MATH_OP)){
                    return left;
                }

                Token operator = t;
                tokens.remove(0);
                JottTree right = OperandNode.parseOperandNode(tokens);

                return new BinaryOpNode(left, operator, right);
            }
        }          
        catch (Exception e) {
            throw new IllegalArgumentException(ERROR_MESSAGE + ", Got: " + tokens.get(0).getTokenType().toString());
        }
    }

    // private static JottTree parsemultistepJottTree(ArrayList<Token> tokens, TokenType j){
        
    //     try {
    //         if (j.equals(TokenType.MATH_OP)){
    //             return MathOpContainerNode.parseMathOpContainerNode(tokens);
    //         }
    //         else if (j.equals(TokenType.REL_OP)) {
    //             return RelOpContainerNode.parseRelOpContainerNode(tokens);
    //         }
    //         else {throw new IllegalArgumentException(ERROR_MESSAGE + ", error in parsing a math or relop expression");}
    //     } catch (Exception e) {
    //         throw new IllegalArgumentException(ERROR_MESSAGE + ", error in parsing a math or relop expression");
    //     }
    // }

    
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
    







