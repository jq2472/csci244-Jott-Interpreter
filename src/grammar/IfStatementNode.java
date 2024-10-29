package grammar;
import static grammar.Helper.checkIsNotEmpty;
import java.util.ArrayList;
import provided.*;
public class IfStatementNode implements BodyStmt{
    private JottTree condition;
    private JottTree body;
    private ArrayList<JottTree> elsenodes;
    private JottTree finalelsenode;

//    public IfStatementNode(JottTree cond, JottTree bodylist, ArrayList<JottTree> elseiflist, JottTree finalelse){
//        this.condition = cond;
//        this.body = bodylist;
//        if (!elseiflist.isEmpty()){
//            this.elsenodes = elseiflist;
//        }
//        else {this.elsenodes = null;}
//        if (finalelse !=null){
//            this.finalelsenode = finalelse;
//        }
//    }
    public IfStatementNode(JottTree cond, JottTree bodylist, ArrayList<JottTree> elseiflist, JottTree finalelse) {
        this.condition = cond;
        this.body = bodylist;
        this.elsenodes = (elseiflist != null) ? elseiflist : new ArrayList<>(); // Ensure elsenodes is always initialized
        this.finalelsenode = finalelse;
    }

    public static IfStatementNode parseIfStatementNode(ArrayList<Token> tokens){
        checkIsNotEmpty(tokens);
        try {
            Token currentToken = tokens.get(0);
            if (!currentToken.getToken().equals("If")){
                throw new IllegalArgumentException("Error parsing If statement, expected If, not "+ currentToken.getToken());  
            }                                          
            tokens.remove(0);
            currentToken = tokens.get(0);
            if (!currentToken.getTokenType().equals(TokenType.L_BRACKET)){
                throw new IllegalArgumentException("Error parsing If statement at condition, should be an expression in brackets");
            }
            tokens.remove(0);
            JottTree cond = ExprNode.parseExprNode(tokens);
            currentToken = tokens.get(0);
            if (!currentToken.getTokenType().equals(TokenType.R_BRACKET)){
                throw new IllegalArgumentException("Error parsing If statement at condition, should be an expression in brackets, missing closing bracket");
            }
            tokens.remove(0);
            currentToken = tokens.get(0);
            if (!currentToken.getTokenType().equals(TokenType.L_BRACE)){ 
                throw new IllegalArgumentException("Error Parsing If statement, after condition should be body node in braces");
            }
            tokens.remove(0);
            JottTree body = BodyNode.parseBodyNode(tokens);
            currentToken = tokens.get(0);
            if (currentToken.getTokenType().equals(TokenType.R_BRACE)) {
                tokens.remove(0);
            }
            else
            {
                throw new IllegalArgumentException("Error Parsing If statement, after body should be closing brace");
            }
            
            currentToken = tokens.get(0);
            ArrayList<JottTree> elseifs = new ArrayList<>();
            while (currentToken.getToken().equals("ElseIf")){
                ElseIfNode j = ElseIfNode.parseElseIfNode(tokens);
                elseifs.add(j);
            }
            currentToken = tokens.get(0);
            if (currentToken.getToken().equals("Else")){
                JottTree finalelse = ElseNode.parseelsenode(tokens);
                return new IfStatementNode(cond, body, elseifs, finalelse);
            }
            return new IfStatementNode(cond, body, elseifs, null);
            
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
        String j  = "If" + "[" + this.condition.convertToJott() + "]" + "{";
        j = j+ this.body.convertToJott();
        j = j+"}";
        if (this.elsenodes.size()>0) {   
            for (JottTree elseifs : this.elsenodes){
                j = j + elseifs.convertToJott();
            }
        }
        if (this.finalelsenode!=null) {
            j = j+this.finalelsenode.convertToJott();
        }
        return j;
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }
    
}
