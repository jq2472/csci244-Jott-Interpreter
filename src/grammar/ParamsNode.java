package grammar;
import static grammar.Helper.checkIsNotEmpty;
import java.util.ArrayList;
import provided.*;
import java.lang.StringBuilder;

public class ParamsNode implements JottTree {
    private ArrayList<JottTree> params;
    public ParamsNode(ArrayList<JottTree> parameters){
        this.params = parameters;
    }
    public static ParamsNode parseParamsNode(ArrayList<Token>tokens) throws Exception
    {
        checkIsNotEmpty(tokens);
        Token currToken = tokens.get(0);
        if (currToken.getTokenType().equals(TokenType.R_BRACKET)){
            return new ParamsNode(new ArrayList<JottTree>());
        }

        ArrayList<JottTree> paramstouse = new ArrayList<>();
        try {
            JottTree j = ExprNode.parseExprNode(tokens);
            paramstouse.add(j);
        }
        catch(Exception e){
            throw new Exception("Params need to be made up of comma seperated Expressions");
        }
        while (true) {
            currToken = tokens.get(0);
            if (currToken.getTokenType() != TokenType.COMMA){
                break;
            }
            else{
                try {
                    tokens.remove(0);
                    JottTree j = ExprNode.parseExprNode(tokens);
                    paramstouse.add(j);
                }
                catch(Exception e){
                    throw new Exception("Params need to be made up of comma seperated Expressions");
            }
        }
        }
        ParamsNode paramNode = new ParamsNode(paramstouse);
        return paramNode;
        
    }
    @Override
    public String convertToJott() {
        StringBuilder string = new StringBuilder();
        if (!params.isEmpty()){
        for (int i = 0; i < params.size()-1; i++) {
            string.append(params.get(i));
            string.append(", ");
        }
        string.append(params.getLast());
        }
        return string.toString();
    }
    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }
    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}
