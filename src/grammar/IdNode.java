package grammar;
import static grammar.Helper.*;
import java.util.ArrayList;

import interpreter.SymbolTable;
import provided.*; // checkTokenType(), checkIsNotEmpty()

public class IdNode implements OperandNode {
    private Token idName;

    public Token getIdName() {
        return idName;
    }

    // idName might also just be able to just be a string?
    public IdNode(Token idName) {
        this.idName = idName;
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
        return this.idName.getTokenType() == TokenType.ID_KEYWORD;
    }

    @Override
    public void execute() {
        // needs to be implemented
        System.out.println("Executing IdNode");
    }
    
}
