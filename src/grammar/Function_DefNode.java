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

    /***
     * Def name[ varName:varType, ... ]:returnType{
     * ... body ...
     * Return ...;
     * }
     * @param tokens
     * @return
     * @throws Exception
     */
    public static JottTree ParseFunctionDefnode (ArrayList<Token> tokens) throws Exception{
        checkIsNotEmpty(tokens);
        // parses def
        // System.out.println("Current tokens: " + tokens); 
        Token curToken = tokens.get(0);
        
        if (curToken.getToken().equals("Def")){
            tokens.remove(0);
        }
        else{
            throw new IllegalArgumentException("Expected Def when parsing Func_DefNode");
        }
        // function name
        // checkTokenType takes in an ArrayList of tokens, but only checks the first token
        checkTokenType(tokens, TokenType.ID_KEYWORD);
        IdNode x = IdNode.parseOperandNode(tokens);
        System.out.println("Function Name: " + x);

        checkTokenType(tokens, TokenType.L_BRACKET);
        tokens.remove(0);

        // varName:varType, ...
        JottTree params = FunctionDefParamsNode.parseFunctionDefParamsNode(tokens);
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expected right bracket after function parameters");
        }
        checkTokenType(tokens, TokenType.R_BRACKET);
        tokens.remove(0);

        checkTokenType(tokens, TokenType.COLON);
        tokens.remove(0);

        // Def <id >[ func_def_params ]: < function_return >
        JottTree returntypecheck = Function_RetNode.parsefunctionRetNode(tokens);
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expected left brace after return type");
        }
        checkTokenType(tokens, TokenType.L_BRACE);
        tokens.remove(0);
        // { < f_body >}
        JottTree f_bodynode = F_BodyNode.parseF_BodyNode(tokens);
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expected right brace after function body");
        }
    
        checkTokenType(tokens, TokenType.R_BRACE);
        tokens.remove(0);

        return new Function_DefNode(x , params, returntypecheck, f_bodynode);
    }
    
    // @Override
    public String convertToJott(){
        String j = "Def " + this.Name.convertToJott() + "[" + this.func_def_params.convertToJott() + "]:" + this.returntype.convertToJott() + "{" + this.bodyNode.convertToJott() + "}";
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
