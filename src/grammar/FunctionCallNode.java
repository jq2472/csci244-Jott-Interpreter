package grammar;

import static grammar.Helper.*;
import java.util.ArrayList;
import provided.*; // checkTokenType(), checkIsNotEmpty()

/**
 * Represents a function call node in the Jott language, which can be expanded to:
 *  :: < id > [ < params > ]
 */
public class FunctionCallNode implements OperandNode {

    // < id >
    private IdNode functionName;
    // [ < params > ]
    private ParamsNode parameters;
    private boolean isbodystmt;

    // The operand interface's parseOperandNode()
    // expects the proper node type to be returned
    public FunctionCallNode(IdNode functionName, ParamsNode parameters) {
        this.functionName = functionName;
        this.parameters = parameters;
        this.isbodystmt = false;
    }
    

    /**
     * Parses a FunctionCallNode from tokens.
     * @param tokens
     * @return The parsed Token representing the function call
     * @throws Exception 
     */
    public static FunctionCallNode parseFuncCallNode(ArrayList<Token> tokens) throws Exception {

        checkIsNotEmpty(tokens);

        // if the first token is an FC_Header, 
        // then validate :: < id > [ < params > ] grammar:
        checkTokenType(tokens, TokenType.FC_HEADER);

        // pop :: fc_header
        tokens.remove(0);

        // expect an ID_KEYWORD < id >
        IdNode functionName = IdNode.parseIdNode(tokens);

        // expect Lbracket [ 
        checkTokenType(tokens, TokenType.L_BRACKET);
        tokens.remove(0);

        // parse parameters, if any
        // * getParameters(tokens) not implemented yet
        ParamsNode parameters = ParamsNode.parseParamsNode(tokens);

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
        functionNodeStr.append("::");
        // <id>
        functionNodeStr.append(this.functionName.convertToJott());
        // [params]
        functionNodeStr.append("[");
        if (this.parameters == null) {
            functionNodeStr.append("");
        } else {
            functionNodeStr.append(this.parameters.convertToJott());
        }
        functionNodeStr.append("]");
        if (isbodystmt) {
            functionNodeStr.append(" ;");
        }

        return String.valueOf(functionNodeStr);
    }
    public void setbodystmttrue(){
        this.isbodystmt = true;
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


    
}
