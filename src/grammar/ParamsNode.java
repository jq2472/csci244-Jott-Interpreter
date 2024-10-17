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
        ArrayList<JottTree> paramstouse = new ArrayList<>();
        try {
            JottTree j = ExprNode.parseExprNode(tokens);
            paramstouse.add(j);
        }
        catch(Exception e){
            throw new Exception("Params need to be made up of comma seperated Expressions");
        }
        while (true) {
            Token currToken = tokens.get(0);
            if (currToken.getTokenType() != TokenType.COMMA){
                break;
            }
            else{
                try {
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
        string.append("[ ");
        for (int i = 0; i < params.size(); i++) {
            string.append(params.get(i));
            string.append(", ");
        }
        string.append("]");
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
