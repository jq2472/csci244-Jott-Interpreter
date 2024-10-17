package grammar;
import static grammar.Helper.checkIsNotEmpty;
import java.util.ArrayList;
import provided.*;
public class IfStatementNode implements BodyStmt{
    private JottTree condition;
    private ArrayList<JottTree> body;
    private ArrayList<JottTree> elsenodes;
    private JottTree finalelsenode; 
    public IfStatementNode(JottTree cond, ArrayList<JottTree> bodylist, ArrayList<JottTree> elseiflist, JottTree finalelse){
        condition = cond;
        body = bodylist;
        if (!elseiflist.isEmpty()){
            elsenodes = elseiflist;
        }
        else {elsenodes = null;}
        if (finalelse !=null){
            finalelsenode = finalelse;
        }
    }
    public static IfStatementNode parseIfStatementNode(ArrayList<Token> tokens){
        checkIsNotEmpty(tokens);
        try {
            Token currentToken = tokens.get(0);
            if (currentToken.getToken().equals("If")){
                tokens.remove(0);
                currentToken = tokens.get(0);
                if (currentToken.getTokenType().equals(TokenType.L_BRACKET)){
                    tokens.remove(0);
                    JottTree cond = ExprNode.parseExprNode(tokens);
                    currentToken = tokens.get(0);
                    if (currentToken.getTokenType().equals(TokenType.R_BRACKET)){
                        tokens.remove(0);
                        currentToken = tokens.get(0);
                        if (currentToken.getTokenType().equals(TokenType.L_BRACE)){
                            tokens.remove(0);
                            ArrayList<JottTree> body = BodyNode.parsebodynode(tokens);
                            currentToken = tokens.get(0);
                            if (currentToken.getTokenType().equals(TokenType.R_BRACE)) {
                                tokens.remove(0);
                                currentToken = tokens.get(0);
                                ArrayList<JottTree> elseifs = new ArrayList<>();
                                while (currentToken.getToken().equals("ElseIf")){
                                    ElseIfNode j = ElseIfNode.parseElseIfNode(tokens);
                                    elseifs.add(j);
                                }
                                currentToken = tokens.get(0);
                                if (currentToken.equals("Else")){
                                    JottTree finalelse = ElseNode.parseelsenode(tokens);
                                    return new IfStatementNode(cond, body, elseifs, finalelse);
                                }
                                return new IfStatementNode(cond, body, elseifs, null);
                            }
                            else{throw new IllegalArgumentException("Error Parsing If statement, after body should be closing brace");}
                        }
                        else{ throw new IllegalArgumentException("Error Parsing If statement, after condition should be body node in braces");}
                    }
                    else{
                        throw new IllegalArgumentException("Error parsing If statement at condition, should be an expression in brackets, missing closing bracket");
                    }
                }
                else{
                    throw new IllegalArgumentException("Error parsing If statement at condition, should be an expression in brackets");
                }
            }
            else {
                throw new IllegalArgumentException("Error parsing If statement, expected If, not "+ currentToken.getToken());
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e + " Error parsing If Statement");
        }

    }


    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    @Override
    public String convertToJott() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToJott' in ifstatementnode");
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }
    
}
