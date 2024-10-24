package grammar;

import static grammar.Helper.*;
import java.util.ArrayList;
import provided.*;

/**
 * represents the function definition parameters in the Jott language.
 * ie: Def foo[ x:Integer, y:Double ]:String
 *
 * < func_def_params > -> < id >: < type > < function_def_params_t >*
 * < function_def_params_t > -> , < id >: < type >
 */
public class FunctionDefParamsNode implements JottTree {

    private ArrayList<ParameterNode> params;

    public FunctionDefParamsNode(ArrayList<ParameterNode> params) {
        this.params = params;
    }

    /**
     * Parses function definition parameters from tokens.
     * @param tokens list of tokens to parse.
     * @return a new FunctionDefParamsNode.
     */
    public static FunctionDefParamsNode parseFunctionDefParamsNode(ArrayList<Token> tokens) throws Exception {
        checkIsNotEmpty(tokens); // will error if true
        ArrayList<ParameterNode> parameters = new ArrayList<>();
        
        // can have no parameters i.e. Def main[]:Void
        if (tokens.get(0).getTokenType().equals(TokenType.R_BRACKET)){
            // return empty array
            return new FunctionDefParamsNode(parameters);
        } else {
        do {
            parameters.add(parseSingleParam(tokens));
            
            // if a comma is present, pop it to continue parsing
            if (!tokens.isEmpty() && tokens.get(0).getToken().equals(",")) {
                tokens.remove(0); // Remove comma
            }
            else{ break; }
        } while (!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.ID_KEYWORD);
        return new FunctionDefParamsNode(parameters);
        }
    }

    /**
     * parses a single parameter in the form < id >: < type >.
     * @param tokens List of tokens to parse.
     * @return A parsed ParameterNode.
     */
    private static ParameterNode parseSingleParam(ArrayList<Token> tokens) throws Exception {
        // < id >
        checkTokenType(tokens, TokenType.ID_KEYWORD);
        IdNode paramName = IdNode.parseIdNode(tokens);
        tokens.remove(0); // Remove the ID keyword
        // :
        checkTokenType(tokens, TokenType.COLON);
        tokens.remove(0); // Remove the colon
        // < type >.
        checkIsNotEmpty(tokens);
        
        TypeNode type = TypeNode.parseTypeNode(tokens);

        return new ParameterNode(paramName, type);
    }

    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.params.size(); i++) {
            sb.append(this.params.get(i).convertToJott());
            if (i < this.params.size() - 1) sb.append(", ");
        }
        return sb.toString();
    }

    @Override
    public boolean validateTree() {
        return true;
    }

    @Override
    public void execute() {
        // implement
    }
}

/**
 * Represents a single parameter in the function definition, e.g., `x: Integer`.
 */
class ParameterNode implements JottTree {
    private final IdNode paramName;
    private final TypeNode type;
    public ParameterNode(IdNode paramName, TypeNode type) {
        this.paramName = paramName;
        this.type = type;
    }
    @Override
    public String convertToJott() {
        return paramName.convertToJott() + ": " + type.convertToJott();
    }
    @Override
    public boolean validateTree() {
        return true;
    }

    @Override
    public void execute() {
        // implement
    }
}

