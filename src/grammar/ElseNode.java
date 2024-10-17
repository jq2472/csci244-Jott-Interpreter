package grammar;

import provided.*;
import java.util.ArrayList;

import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()
public class ElseNode implements JottTree{
    private ArrayList<JottTree>bodyNode;
    public ElseNode(ArrayList<JottTree>bodyNode)
    {
        this.bodyNode = bodyNode;
    }
    public static ElseNode parseelsenode(ArrayList<Token>tokens) throws Exception
    {
        checkIsNotEmpty(tokens);
        checkTokenType(tokens, TokenType.ID_KEYWORD);
        ArrayList<JottTree> bodyTree = new ArrayList<>();
        
        if(tokens.get(0).getToken().equals("Else"))
        {
            tokens.remove(0);
            bodyTree = BodyNode.parsebodynode(tokens);
            return new ElseNode(bodyTree);
        }
        else
        {
            return new ElseNode(null);
        }
    }
    @Override
    public String convertToJott() {
        if(this.bodyNode != null)
        {
            StringBuilder elseNodeStr = new StringBuilder();
            elseNodeStr.append("Else");
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
        return true;
    }
    @Override
    public void execute() {
        System.out.println("ElseNode");
    }
}
