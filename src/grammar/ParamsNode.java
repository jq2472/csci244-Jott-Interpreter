package grammar;
import static grammar.Helper.checkIsNotEmpty;
import static grammar.Helper.print_err;

import java.util.ArrayList;

import interpreter.FunctionData;
import interpreter.SymbolTable;
import provided.*;
import java.lang.StringBuilder;

public class ParamsNode implements JottTree {
    /**
     * < params_t > -> ,< expr
     */
    private ArrayList<ExprNode> params;
    private Token funcname;
    public ParamsNode(ArrayList<ExprNode> parameters){
        this.params = parameters;
    }

    public void setFuncName(Token funcnameinput){
        this.funcname = funcnameinput;
    }
    public static ParamsNode parseParamsNode(ArrayList<Token> tokens) throws Exception {
        checkIsNotEmpty(tokens);
        Token currToken = tokens.get(0);

        // return empty ParamsNode if the next token is a closing bracket
        if (currToken.getTokenType().equals(TokenType.R_BRACKET)) {
            return new ParamsNode(new ArrayList<ExprNode>());
        }

        ArrayList<ExprNode> paramstouse = new ArrayList<>();

        // try to parse the first expression
        try {
            ExprNode j = ExprNode.parseExprNode(tokens);
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
                ExprNode j = ExprNode.parseExprNode(tokens);
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
        ArrayList<String> types = new ArrayList<>();
        for (ExprNode paramtypelist: params){
            types.add(paramtypelist.getReturnType());
        }
        // validate each parameter
        for (JottTree param: params){
            if (!param.validateTree()){
                return false;
            }
        }
        FunctionData func = SymbolTable.symbolTable.getFunc(this.funcname.getToken());
        if ( func != null && !func.getParams().contains("Any")){
            if (types.size()!=func.getParams().size()) {
                print_err("Number of Parameters does not match", this.funcname);
                return false;
            }
            for (int i = 0; i < types.size(); i++) {
                if (!types.get(i).equals(func.getParams().get(i))) {
                    print_err("Params do not match function from symbol table", funcname);
                }
            }
        }
        return true;
    }
    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}
