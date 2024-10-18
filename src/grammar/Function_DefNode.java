package grammar;
import provided.*;
import java.util.ArrayList;
import static grammar.Helper.*;

public class Function_DefNode implements JottTree{
    
    private IdNode Name;
    private JottTree func_def_params;
    private JottTree returntype;
    private JottTree bodyNode;

    public Function_DefNode (IdNode idNode, JottTree funcdefparams, JottTree returntypenode, JottTree body){
        this.Name = idNode;
        this.func_def_params = funcdefparams;
        this.returntype = returntypenode;
        this.bodyNode = body;

    }

    // make another constructor
    /* 
    public static JottTree ParseFunctionDefnode (ArrayList<Token>tokens){
        System.out.println("FuncDefNode Entered");
        checkIsNotEmpty(tokens);
        checkTokenType(tokens, TokenType.ID_KEYWORD);
        tokens.remove(0);
        checkTokenType(tokens, TokenType.ID_KEYWORD);
        Token idToken = tokens.remove(0);
        IdNode idNode = new IdNode(idToken);
        return new Function_DefNode(idNode);
    }
        */
    public static JottTree ParseFunctionDefnode (ArrayList<Token> tokens) throws Exception{
        System.out.println("FuncDefNode Entered");
        checkIsNotEmpty(tokens);
        Token curToken = tokens.get(0);
        if (curToken.getToken().equals("Def")){
            tokens.remove(0);
        }
        else{
            throw new IllegalArgumentException("Expected Def when parsing Func_DefNode");
        }
        IdNode x = IdNode.parseOperandNode(tokens);
        checkTokenType(tokens, TokenType.L_BRACKET);
        tokens.remove(0);
        JottTree params = FunctionDefParamsNode.parseFunctionDefParamsNode(tokens);
        checkTokenType(tokens, TokenType.R_BRACKET);
        tokens.remove(0);
        checkTokenType(tokens, TokenType.COLON);
        tokens.remove(0);
        JottTree returntypecheck = Function_RetNode.parsefunctionRetNode(tokens);
        checkTokenType(tokens, TokenType.L_BRACE);
        tokens.remove(0);
        JottTree f_bodynode = F_BodyNode.parseF_BodyNode(tokens);
        checkTokenType(tokens, TokenType.R_BRACE);
        tokens.remove(0);
        return new Function_DefNode(x , params, returntypecheck, f_bodynode);
    }
    
    // @Override
    public String convertToJott(){
        String j = "Def " + Name.convertToJott() + "[" + func_def_params.convertToJott() + "]:" + returntype.convertToJott() + "{" + bodyNode.convertToJott() + "}";
        return j;
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
