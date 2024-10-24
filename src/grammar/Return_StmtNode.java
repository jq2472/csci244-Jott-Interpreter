package grammar;

import static grammar.Helper.*;
import java.util.ArrayList;
import provided.*; // checkTokenType(), checkIsNotEmpty()
public class Return_StmtNode implements JottTree{

    private JottTree jottTreeexpr;
    public Return_StmtNode(JottTree jottTreeexpr)
    {
        this.jottTreeexpr = jottTreeexpr;
    }
    public static Return_StmtNode parseReturn_StmtNode(ArrayList<Token>tokens) throws Exception
    {
        checkIsNotEmpty(tokens);
        checkTokenType(tokens, TokenType.ID_KEYWORD);
        Token currToken = tokens.get(0);
        if(currToken.getToken().equals("Return"))
        {
            tokens.remove(0);
            JottTree exprTree = ExprNode.parseExprNode(tokens);
            currToken = tokens.get(0);
            if (currToken.getTokenType() != TokenType.SEMICOLON) {
                
                throw new Exception("Syntax Error\nExpected ';' after return expression\n"+currToken.getFilename()+":"+currToken.getLineNum());
            }
            tokens.remove(0);
            return new Return_StmtNode(exprTree);
        }
        else{
            return new Return_StmtNode(null);
        }
    }
    @Override
    public String convertToJott() {
        if(this.jottTreeexpr != null)
        {
            StringBuilder returnStmtNodeStr = new StringBuilder();
            returnStmtNodeStr.append("Return");
            returnStmtNodeStr.append(this.jottTreeexpr.convertToJott());
            returnStmtNodeStr.append(" ;");
            return returnStmtNodeStr.toString();
        }
        else{
            return "";
        }
       
    }

    @Override
    public boolean validateTree() {
        // needs to be implemented
        return true;
    }

    @Override
    public void execute() {
         // needs to be implemented
         System.out.println("Return_StmtNode");
    }
    
}
