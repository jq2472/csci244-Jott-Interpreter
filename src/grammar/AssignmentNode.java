package grammar;
import static grammar.Helper.checkIsNotEmpty;
import static grammar.Helper.checkTokenType;
import java.util.ArrayList;

import provided.*;

import static interpreter.SymbolTable.currentFunction;
import static interpreter.SymbolTable.symbolTable;



public class AssignmentNode implements BodyStmt {
    private IdNode id1;
    private Token value;
    private ExprNode expresnode;

    public AssignmentNode(IdNode idnode, Token value, ExprNode expression) {
        this.id1 = idnode;
        this.value = value;
        this.expresnode = expression;
    }

    public static AssignmentNode parseAssignmentNode(ArrayList<Token>tokens){
        checkIsNotEmpty(tokens);
        try {
            IdNode idnode = IdNode.parseIdNode(tokens);

            // (validating) get <id> to assign before it gets popped off
            String varName = ((IdNode) idnode).getName();
            // what was assigned i.e. Integer i;
            TokenType expectedVarType = ((IdNode) idnode).getType(); // has to cast bc getType isn't in JottTree
            //I believe this line can be removed
            //symbolTable.setVar(currentFunction, varName, expectedVarType);

            Token currToken = tokens.get(0);
            checkTokenType(tokens, TokenType.ASSIGN);
            tokens.remove(0);
            checkIsNotEmpty(tokens);
            // errors if assignment ends abruptly x =;
            currToken = tokens.get(0);
            if (currToken.getTokenType().equals(TokenType.SEMICOLON)) {
                throw new IllegalArgumentException("Error: Expected Expression Node, got Semicolon.");
            }
            // else parse expression
            ExprNode expressionNode = ExprNode.parseExprNode(tokens);

            // needs semicolon at the end of the assignment
            checkTokenType(tokens, TokenType.SEMICOLON);
            tokens.remove(0);

            AssignmentNode asgNode = new AssignmentNode(idnode, currToken, expressionNode);
            return asgNode;

        } catch (Exception e) {
            throw new IllegalArgumentException("Error Parsing Assignment Node");
        }
    }

    public String convertToJott() {
        return this.id1.convertToJott() + " " + "=" + " " + this.expresnode.convertToJott() + ";";
    }

    public String toString(){
        return convertToJott();
    }


    public boolean validateTree() {
        String varName = ((IdNode) this.id1).getName();
        this.id1.validateTree();
        this.expresnode.validateTree();

        //  (validating) make sure the global symbol table instance and current function exists
        if (!symbolTable.hasVar(currentFunction, varName)) {
            System.err.println("Semantic Error: Undefined variable, Not in Symbol Table" + varName);
            return false;
        }
        // expected var type is stored in sym table i.e. symbolTable.setVar(currentFunction, varName, expectedVarType);
        String expectedVarType = (symbolTable.getVar(varName)).getType(); 
        if (!expectedVarType.equals((this.expresnode).getReturnType())) {
            System.err.println("Semantic Error: Type mismatch in assignment to " + varName);
            return false;
        }
        return true;
    }


    public void execute() { 
        System.out.println("Executing NumberNode");
    }

    public String getVariablenameString(){
        return this.id1.getName();
    }

    public String getReturnType(){
        return this.expresnode.getReturnType();
    }

    public Token getidtoken(){
        return this.id1.getToken();
    }
    
}