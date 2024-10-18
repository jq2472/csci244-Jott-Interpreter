package grammar;
import provided.*;
import java.util.ArrayList;
import static grammar.Helper.*;

public class Function_DefNode implements JottTree{
    
    private IdNode IDNode;

    public Function_DefNode (IdNode IDNode){
        this.IDNode = IDNode;
    }

    // make another constructor

    public static JottTree ParseFunctionDefnode (ArrayList<Token>tokens){
        checkIsNotEmpty(tokens);
        checkTokenType(tokens, TokenType.ID_KEYWORD);
        tokens.remove(0);
        checkTokenType(tokens, TokenType.ID_KEYWORD);
        Token idToken = tokens.remove(0);
        IdNode idNode = new IdNode(idToken);
        return new Function_DefNode(idNode);
    }
    
    // @Override
    public String convertToJott(){
        return "Def " + IDNode.convertToJott() + "();";
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
