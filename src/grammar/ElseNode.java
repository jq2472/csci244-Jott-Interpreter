package grammar;

import provided.*;
import java.util.ArrayList;

import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()
public class ElseNode implements JottTree{
    private JottTree bodyNode;
    public ElseNode(JottTree bodyNode)
    {
        this.bodyNode = bodyNode;
    }
    public static ElseNode parseelsenode(ArrayList<Token>tokens) throws Exception
    {
        checkIsNotEmpty(tokens);
        checkTokenType(tokens, TokenType.ID_KEYWORD);
        
        
        if(tokens.get(0).getToken().equals("Else"))
        {
            tokens.remove(0);

            checkTokenType(tokens, TokenType.L_BRACE);
            tokens.remove(0);

            JottTree bodyTree = BodyNode.parseBodyNode(tokens);

            checkTokenType(tokens, TokenType.R_BRACE);
            tokens.remove(0);

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
            elseNodeStr.append("Else{");
            elseNodeStr.append(this.bodyNode.convertToJott());
            elseNodeStr.append("}");
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
