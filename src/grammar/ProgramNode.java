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
        ArrayList<JottTree> j = new ArrayList<>();
        System.out.println(tokens);
        System.out.println(tokens.get(0).toString());
        while(!tokens.isEmpty() && tokens.get(0).getToken().equals("Def")){
            System.out.println("ProgramNodeEntered");
            JottTree newfuncdef = Function_DefNode.ParseFunctionDefnode(tokens);
            System.out.println(newfuncdef);
            j.add(newfuncdef);
        }
        if (tokens.isEmpty()) {
            return new ProgramNode(j);
        }
        else{
            throw new IllegalArgumentException("Error parsing Program Node");
        }
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
