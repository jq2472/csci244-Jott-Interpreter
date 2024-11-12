package grammar;
import static grammar.Helper.checkIsNotEmpty;
import java.util.ArrayList;
import provided.*;
import java.lang.StringBuilder;

public class ParamsNode implements JottTree {
    /**
     * < params_t > -> ,< expr
     */
    private ArrayList<JottTree> params;
    public ParamsNode(ArrayList<JottTree> parameters){
        this.params = parameters;
    }
    public static ParamsNode parseParamsNode(ArrayList<Token> tokens) throws Exception {
        checkIsNotEmpty(tokens);
        Token currToken = tokens.get(0);

        // return empty ParamsNode if the next token is a closing bracket
        if (currToken.getTokenType().equals(TokenType.R_BRACKET)) {
            return new ParamsNode(new ArrayList<JottTree>());
        }

        ArrayList<JottTree> paramstouse = new ArrayList<>();

        // try to parse the first expression
        try {
            JottTree j = ExprNode.parseExprNode(tokens);
            paramstouse.add(j);
        } catch (Exception e) {
            throw new Exception("Params need to be made up of comma-separated Expressions");
        }

        // contineu parsing for additional parameters
        while (true) {
            currToken = tokens.get(0);
            if (currToken.getTokenType() != TokenType.COMMA) {
                break;
            }
            tokens.remove(0); // Remove the comma
            try {
                JottTree j = ExprNode.parseExprNode(tokens);
                paramstouse.add(j);
            } catch (Exception e) {
                throw new Exception("Params need to be made up of comma-separated Expressions");
            }
        }

        return new ParamsNode(paramstouse);
    }

    @Override
    public String convertToJott() {
        StringBuilder string = new StringBuilder();
        if (this.params != null && !this.params.isEmpty()) {
            for (int i = 0; i < this.params.size() - 1; i++) {
                string.append(this.params.get(i).convertToJott());
                string.append(", ");
            }
            string.append(this.params.get(this.params.size() - 1).convertToJott()); // Last item without comma
        }
        return string.toString();
    }

    @Override
    public boolean validateTree() {
        // validate each parameter
        return false;
    }
    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}
