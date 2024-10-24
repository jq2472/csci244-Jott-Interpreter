package grammar;
import static grammar.Helper.*;
import java.util.ArrayList;
import provided.*; // checkTokenType(), checkIsNotEmpty()
public class Var_DecNode implements JottTree{
    private TypeNode typeNode;
    private IdNode idNode;
    public Var_DecNode(TypeNode typeNode, IdNode idNode)
    {
        this.typeNode = typeNode;
        this.idNode = idNode;
    }
    public static Var_DecNode parseVar_DecNode(ArrayList<Token>tokens)
    {
        //        < var_dec > -> < type > < id >;
        TypeNode typeNode = TypeNode.parseTypeNode(tokens); // will check empty, etc. there
        IdNode idNode = IdNode.parseIdNode(tokens);
        // parse semicolon
        checkIsNotEmpty(tokens);
        checkTokenType(tokens, TokenType.SEMICOLON);
        tokens.remove(0);

        Var_DecNode var_DecNode = new Var_DecNode(typeNode, idNode);
        return var_DecNode;
        

    }
    @Override
    public String convertToJott() {
//        < var_dec > -> < type > < id >;
        StringBuilder typeNodeStr = new StringBuilder();
        typeNodeStr.append(this.typeNode.convertToJott());
        typeNodeStr.append(" ");
        typeNodeStr.append(this.idNode.convertToJott());
        typeNodeStr.append(";");

        return typeNodeStr.toString();
    }

    @Override
    public boolean validateTree() {
        // needs to be implemented
        return true;
    }

    @Override
    public void execute() {
        // needs to be implemented
        System.out.println("Var_DecNode");
    }
    

}
