package grammar;
import static grammar.Helper.*;
import static interpreter.SymbolTable.symbolTable;

import java.util.ArrayList;

import interpreter.*;
import provided.*;

public class Function_DefNode implements JottTree{
    
    private IdNode Name;
    private FunctionDefParamsNode func_def_params;
    private Function_RetNode returntype;
    private F_BodyNode bodyNode;

    public Function_DefNode (IdNode idNode, FunctionDefParamsNode funcdefparams, Function_RetNode returntypenode, F_BodyNode body){
        this.Name = idNode;
        this.func_def_params = funcdefparams;
        this.returntype = returntypenode;
        this.bodyNode = body;
    }


    /***
     * Def name[ varName:varType, ... ]:returnType{
     * ... body ...
     * Return ...;
     * }
     * @param tokens
     * @return
     * @throws Exception
     */
    public static Function_DefNode ParseFunctionDefnode (ArrayList<Token> tokens) throws Exception{
        {
            try {
                checkIsNotEmpty(tokens);
        
                // parses def
                Token curToken = tokens.get(0);
                if (curToken.getToken().equals("Def")) {
                    tokens.remove(0);
                } else {
                    throw new IllegalArgumentException("Expected Def when parsing Func_DefNode");
                }
        
                // function name
                checkTokenType(tokens, TokenType.ID_KEYWORD);
                IdNode funcname = IdNode.parseIdNode(tokens);
                SymbolTable.symbolTable.currentFunction = funcname.getName();
                checkTokenType(tokens, TokenType.L_BRACKET);
                tokens.remove(0);
        
                // varName:varType, ...
                FunctionDefParamsNode params = FunctionDefParamsNode.parseFunctionDefParamsNode(tokens);
                if (tokens.isEmpty()) {
                    throw new IllegalArgumentException("Expected right bracket after function parameters");
                }
                
                checkTokenType(tokens, TokenType.R_BRACKET);
                tokens.remove(0);
                
                checkTokenType(tokens, TokenType.COLON);
                tokens.remove(0);
                
                // Def <id >[ func_def_params ]: < function_return >
                Function_RetNode returntypecheck = Function_RetNode.parsefunctionRetNode(tokens);
                if (tokens.isEmpty()) {
                    throw new IllegalArgumentException("Expected left brace after return type");
                }
                
                checkTokenType(tokens, TokenType.L_BRACE);
                tokens.remove(0);
        
                // { < f_body >}
                F_BodyNode f_bodynode = F_BodyNode.parseF_BodyNode(tokens);
                if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.R_BRACE) {
                    throw new IllegalArgumentException("Expected right brace after function body");
                }
                checkTokenType(tokens, TokenType.R_BRACE);
                tokens.remove(0);
                Function_DefNode node = new Function_DefNode(funcname, params, returntypecheck, f_bodynode);

                if (SymbolTable.symbolTable.hasFunc(funcname.getName())) {
                    print_err("Duplicate entry in Symbol table During variable declaration", funcname.getToken());
                }
                SymbolTable.symbolTable.setFunc(node.getnametoken().getToken(), node);
                params.addtosymboltable();
                return node;
        
            } catch (Exception e) {
                System.out.println("Caught exception: " + e.getMessage());
                // Handle the exception as needed
                return null; // Or handle it in another way
            }
        }
    }
    
    // @Override
    public String convertToJott(){
        String j = "Def " + this.Name.convertToJott() + "[" + this.func_def_params.convertToJott() + "]:" + this.returntype.convertToJott() + "{" + this.bodyNode.convertToJott() + "}";
        return j;
    }

    @Override
    public boolean validateTree() {
        SymbolTable.currentFunction = this.Name.getName();
        if (!symbolTable.hasFunc(this.Name.getName())){
            print_err("Function Not in Symbol Table", getnametoken()); // needs approval
            return false;
        }
        if (!Name.validateTree()) {
            return false;
        }
        if (!func_def_params.validateTree()){
            return false;
        }
        if (!returntype.validateTree()) {
            return false;
        }
        if (!bodyNode.validateTree()) {
            return false;
        }
        if (this.bodyNode.getReturnNode() != null){
            return this.bodyNode.getReturnNode().validateTree();
        }
        return true;
    }

    // // @Override
    // public Object execute() { // Needs Approval
    //     System.out.println("in function_defnode execute");
    //     SymbolTable.currentFunction = this.Name.getName();
    //     if (!SymbolTable.symbolTable.hasFunc(this.Name.getName())){
    //         print_err("Function Not in Symbol Table", getnametoken()); 
    //         return false;
    //     }else{
    //         return func_def_params.execute();
    //     }

    // }

    @Override
    public Object execute() {
        //System.out.println("Executing function: " + this.Name.getName());
        SymbolTable.currentFunction = this.Name.getName();
        
        func_def_params.execute(); 

        Object result = bodyNode.execute();
        //System.out.println("Function " + this.Name.getName() + " executed, result: " + result);

        return result;
    }

    public ArrayList<String> getparamstrings(){
        if (this.func_def_params.getParamStrings().isEmpty()) {
            return new ArrayList<>();
        }
        else{
            return this.func_def_params.getParamStrings();
        }
    }
    public Token getnametoken(){
        return this.Name.getToken();
    }

    public String getReturnType(){
        return this.returntype.getreturntype();
    }

    public JottTree getBody() {
        return this.bodyNode;
    }
}
