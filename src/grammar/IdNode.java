package grammar;
import java.util.ArrayList;
import provided.*;
import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()

public class IdNode implements OperandNode {

    private Token idName;

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
    public static IdNode parseOperandNode(ArrayList<Token> tokens){
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

    @Override
    public String convertToJott() {
        return "" + this.idName.getToken();  
    }

    @Override
    public boolean validateTree() {
        // needs to be implemented
        return true;
    }

    @Override
    public void execute() {
        // needs to be implemented
        System.out.println("Executing IdNode");
    }
    
}