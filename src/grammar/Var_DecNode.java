package grammar;
import provided.*;
import java.util.ArrayList;

import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()
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
        TypeNode typeNode = TypeNode.parseTypeNode(tokens);
        IdNode idNode = IdNode.parseOperandNode(tokens);
        Var_DecNode var_DecNode = new Var_DecNode(typeNode, idNode);
        return var_DecNode;
        

    }
    @Override
    public String convertToJott() {
        return this.typeNode.convertToJott()+""+this.idNode.convertToJott();
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
