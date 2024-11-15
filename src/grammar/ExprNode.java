package grammar;

import static grammar.Helper.*;
import java.util.ArrayList;
import provided.*; // checkTokenType(), checkIsNotEmpty()

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
    public static ExprNode parseExprNode(ArrayList<Token> tokens) {
        
       checkIsNotEmpty(tokens);

        try{
           

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
                // < operand >
                // < operand > < relop > < operand > |
                //  < operand > < mathop > < operand >
                // so assume parsing operand if not string/bool anyways
                ExprNode left = OperandNode.parseOperandNode(tokens);

                t = tokens.get(0);

                // just the <expr> on its own
                if(!(t.getTokenType().equals(TokenType.MATH_OP)
                        || t.getTokenType().equals(TokenType.REL_OP))){
                    return left;
                }

                Token operator = t;
                tokens.remove(0);
                OperandNode right = OperandNode.parseOperandNode(tokens);

                return new BinaryOpNode(left, operator, right);
            }
        }          
        catch (Exception e) {
            throw new IllegalArgumentException(ERROR_MESSAGE + ", Got: " + tokens.get(0).getTokenType().toString());
        }
    }

    public Token getToken();

    public String getReturnType();
}