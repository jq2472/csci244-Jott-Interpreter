package grammar;

import provided.*;

import java.util.ArrayList;

import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()


public class ProgramNode implements JottTree{

    private ArrayList<JottTree> funcdeflist;
    public ProgramNode(ArrayList<JottTree> funcnodes){
        this.funcdeflist = funcnodes;
    }
    public static ProgramNode parseProgramNode(ArrayList<Token> tokens) throws Exception{
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Tokens list is empty.");
        }
        ArrayList<JottTree> j = new ArrayList<>();
        
        System.out.println("before going into the while loop in parseprogramnode");
        while(!tokens.isEmpty() && tokens.get(0).getToken().equals("Def")){
            System.out.println("ProgramNodeEntered");
            JottTree newfuncdef = Function_DefNode.ParseFunctionDefnode(tokens);
            System.out.println(newfuncdef);

            if (newfuncdef != null) {
                j.add(newfuncdef);
            } else {
                throw new IllegalArgumentException("Failed to parse function definition.");
            }
            
        }
        return new ProgramNode(j);        
    }

    @Override
    public String convertToJott() {
        String toreturn = "";
        for (JottTree funcdefnodes : this.funcdeflist) {
            toreturn = toreturn+ funcdefnodes.convertToJott();
        }
        return toreturn;
    }

    public String toString() {
        return this.convertToJott();
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
