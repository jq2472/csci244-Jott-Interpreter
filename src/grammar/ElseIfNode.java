package grammar;
import provided.*;

import static grammar.Helper.*;

import java.util.ArrayList;

public class ElseIfNode implements JottTree, ExprNode{

    private ArrayList<JottTree>bodyNode;

    public ElseIfNode(ArrayList<JottTree>bodyNode)
    {
        this.bodyNode = bodyNode;
    }


    public ElseIfNode ElseIfNode(ArrayList<Token>tokens) throws Exception{
        checkIsNotEmpty(tokens);
        checkTokenType(tokens, TokenType.ID_KEYWORD);
        ArrayList<JottTree>bodyTree = new ArrayList<>();

        if(tokens.get(0).getToken().equals("elseif")){
            tokens.remove(0);
            bodyTree = BodyNode.parsebodynode(tokens);
            return new ElseIfNode(bodyTree);
        }else{
            return new ElseIfNode(null);
        }

    }

    @Override
    public String convertToJott() {
        if(this.bodyNode != null)
        {
            StringBuilder elseNodeStr = new StringBuilder();
            elseNodeStr.append("elseif");
            for (JottTree jottTree : this.bodyNode) {
                elseNodeStr.append(jottTree.convertToJott());
            }
            return elseNodeStr.toString();
        }
        else{
            return "";
        }
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
