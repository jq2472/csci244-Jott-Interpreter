package grammar;

import static grammar.Helper.*;
import static interpreter.SymbolTable.currentFunction;
import static interpreter.SymbolTable.symbolTable;

import java.util.ArrayList;

import interpreter.FunctionData;
import interpreter.SymbolTable;
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

        parameters.setFuncName(functionName.getToken());

        return new FunctionCallNode(functionName, parameters);

    }

    // when convertToJott is called the output should be:
    // :: < id > [ < params > ]
    // i.e. ":: print[ 5 ]"
    @Override
    public String convertToJott() {
        StringBuilder functionNodeStr = new StringBuilder();

        functionNodeStr.append("::");
        functionNodeStr.append(this.functionName.convertToJott());
        functionNodeStr.append("[");
        if (this.parameters != null) {
            functionNodeStr.append(this.parameters.convertToJott());
        }
        functionNodeStr.append("]");
        if (isbodystmt) {
            functionNodeStr.append(" ;");
        }
        return functionNodeStr.toString();
    }

    public void setbodystmttrue(){
        this.isbodystmt = true;
    }

    @Override
    public boolean validateTree() {
        // ::foo[ y ]; // invalid if foo is expecting a non-integer
        //::foo[]; // invalid if foo expects params
        if (!SymbolTable.symbolTable.hasFunc(functionName.getName())) {
            return false;
        }
        if(!this.functionName.validateTree())
        {
            return false;
        }
        if(!this.parameters.validateTree())
        {
            return false;
        }
        return true;

    }

    @Override
    public void execute() {
        // needs to be implemented
        System.out.println("Executing FunctionCallNode");
    }


    @Override
    public Token getToken() {
        return this.functionName.getToken();
    }


    @Override
    public String getReturnType() {
        FunctionData j = SymbolTable.symbolTable.getFunc(this.functionName.getName());
        return j.getReturns();
    }


    
}
