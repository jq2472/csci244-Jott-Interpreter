package grammar;
import static grammar.Helper.*;
import java.util.ArrayList;

import interpreter.SymbolTable;
import interpreter.VariableData;
import provided.*; // checkTokenType(), checkIsNotEmpty()

public class IdNode implements OperandNode {
    private Token idName;

    // idName might also just be able to just be a string?
    public IdNode(Token idName) {
        this.idName = idName;
    }

    public String getName() {
        return this.idName.getToken();
    }

    public TokenType getType(){
        return this.idName.getTokenType();
    }

    @Override
    public Token getToken() {
        return idName;
    }

    /**
     * Parses an IDNode when Operand Interface is called.
     * IDNode must start with a lowercase letter.
     * @param tokens
     * @return
     */
    public static IdNode parseIdNode(ArrayList<Token> tokens){
        checkIsNotEmpty(tokens);
        checkTokenType(tokens, TokenType.ID_KEYWORD);
        if (!Character.isLowerCase(tokens.get(0).getToken().charAt(0))){ 
            throw new IllegalArgumentException(
                ERROR_MESSAGE + 
                "ID_KEYWORD does not start with a lowercase letter");
        } else {
            IdNode newIdNode =  new IdNode(tokens.get(0));
            tokens.remove(0);

            return newIdNode;
        }
    }
    /**
     * @return idNode's name using getToken.
     */
    @Override
    public String convertToJott() {
        return "" + this.idName.getToken();  
    }

    public String toString(){
        return convertToJott();
    }

    @Override
    public boolean validateTree() {
        //If the symbol table doesnt have this variable as either a Variable or function we have problems
        if (!SymbolTable.symbolTable.hasVar(SymbolTable.currentFunction, this.getName()) && !SymbolTable.symbolTable.hasFunc(this.getName())) {
            print_err("IDNode not in symbol table", idName);
            return false;
        }
        return true;
    }

    @Override
    public void execute() {
        // needs to be implemented
        System.out.println("Executing IdNode");
    }

    @Override
    public String getReturnType() {
        if (SymbolTable.symbolTable.hasVar(SymbolTable.currentFunction, this.getName())) {
            VariableData vardata = SymbolTable.symbolTable.getVar(SymbolTable.currentFunction, this.getName());
            return vardata.getType();
        }
        System.err.println("Id node thats not in SymbolTable");
        return "Error";
    }
}
