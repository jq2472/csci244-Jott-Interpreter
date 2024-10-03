package grammar;

import java.util.ArrayList;
import provided.*;
import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()

/**
 * Represents a function call node in the Jott language, which can be expanded to:
 *  :: < id > [ < params > ]
 */
public class FunctionCallNode implements OperandNode {

    // < id >
    private IdNode functionName;
    // [ < params > ]
    private ArrayList<ExprNode> parameters;

    // The operand interface's parseOperandNode()
    // expects the proper node type to be returned
    public FunctionCallNode(IdNode functionName, ArrayList<ExprNode> parameters) {
        this.functionName = functionName;
        this.parameters = parameters;
    }
    

    /**
     * Parses a FunctionCallNode from tokens.
     * @param tokens
     * @return The parsed Token representing the function call
     */
    public static FunctionCallNode parseOperandNode(ArrayList<Token> tokens) {

        checkIsNotEmpty(tokens);

        // if the first token is an FC_Header, 
        // then validate :: < id > [ < params > ] grammar:
        checkTokenType(tokens, TokenType.FC_HEADER);

        // pop :: fc_header
        tokens.remove(0);

        // expect an ID_KEYWORD < id >
        IdNode functionName = IdNode.parseOperandNode(tokens);

        // expect Lbracket [ 
        checkTokenType(tokens, TokenType.L_BRACKET);
        tokens.remove(0);

        // parse parameters, if any
        // * getParameters(tokens) not implemented yet
        ArrayList<ExprNode> parameters = getParameters(tokens);

        // now expect a right bracket ] if tokens is not empty
        checkTokenType(tokens, TokenType.R_BRACKET);
        tokens.remove(0);

        return new FunctionCallNode(functionName, parameters);

    }

    // when convertToJott is called the output should be:
    // :: < id > [ < params > ]
    // i.e. ":: print[ 5 ]"
    @Override
    public String convertToJott() {
        StringBuilder functionNodeStr = new StringBuilder();

        // fc header
        functionNodeStr.append(":: ");
        // <id>
        functionNodeStr.append(functionName.convertToJott());
        // [params]
        functionNodeStr.append(" [ ");
        if (parameters == null) {
            functionNodeStr.append(" ");
        } else {
            for (ExprNode parameter : parameters) {
                functionNodeStr.append(parameter.convertToJott());
            }
        }
        functionNodeStr.append(" ]");
        return functionNodeStr.toString();
    }

    @Override
    public boolean validateTree() {
        // needs to be implemented
        return true;
    }

    @Override
    public void execute() {
        // needs to be implemented
        System.out.println("Executing FunctionCallNode");
    }

    
    /**
     * Use ParamNode class that implements ExprNode to get parameters
     * @param tokens
     * @return ArrayList<ExprNode> parameters
     */
    public static ArrayList<ExprNode> getParameters(ArrayList<Token> tokens) {
        // Collect the function parameters
        while (checkIsNotEmpty(tokens) && tokens.get(0).getTokenType() != TokenType.R_BRACKET) {
            // param node not implemented yet: < params > -> < expr > < params_t >⋆ | ε
            // Token parameters = ParamNode.parseExprNode(tokens);  
            // return array list of parameters;
            ArrayList<ExprNode> parameters = new ArrayList<ExprNode>();
            System.out.println("Collecting parameters");
            parameters.add(null);
        }
        return null;
    }

    
}
