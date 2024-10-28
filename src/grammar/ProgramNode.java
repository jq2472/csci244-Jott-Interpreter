package grammar;

import java.util.ArrayList;
import provided.*;


public class ProgramNode implements JottTree{

    private ArrayList<JottTree> funcdeflist;
    public ProgramNode(ArrayList<JottTree> funcnodes){
        this.funcdeflist = funcnodes;
    }
    public static ProgramNode parseProgramNode(ArrayList<Token> tokens) throws Exception{
        try{
            if (!tokens.isEmpty() && !tokens.get(0).getToken().equals("Def")){
                throw new Exception("You cannot have statements outside a function");
            }
            if (tokens.isEmpty()) {
                throw new IllegalArgumentException("Tokens list is empty.");
            }
            ArrayList<JottTree> j = new ArrayList<>();
            while(!tokens.isEmpty() && tokens.get(0).getToken().equals("Def")){

                JottTree newfuncdef = Function_DefNode.ParseFunctionDefnode(tokens);


                if (newfuncdef != null) {
                    j.add(newfuncdef);
                } else {
                    throw new IllegalArgumentException("Failed to parse function definition.");
                }

            }
            return new ProgramNode(j);
        } catch (IllegalArgumentException e){
            System.err.println("Error: IllegalArgumentException " + e.getMessage());
            return null;
        } catch (Exception e){
            System.err.println("An unexpected error occurred: " + e.getMessage());
            return null;
        }
        // if (tokens.isEmpty()) {
        //     return new ProgramNode(j);
        // }
        // else{
        //     throw new IllegalArgumentException("Error parsing Program Node");
        // }
        
        // return new ProgramNode(j);        
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
