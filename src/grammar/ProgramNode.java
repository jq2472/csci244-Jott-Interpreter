package grammar;

import static grammar.Helper.print_err;

import java.util.ArrayList;

import interpreter.FunctionData;
import interpreter.SymbolTable;
import provided.*;


public class ProgramNode implements JottTree{

    private ArrayList<Function_DefNode> funcdeflist;
    public ProgramNode(ArrayList<Function_DefNode> funcnodes){
        this.funcdeflist = funcnodes;
    }
    public static ProgramNode parseProgramNode(ArrayList<Token> tokens) throws Exception{
        try{
            addbuiltinfunctions();
            if (!tokens.isEmpty() && !tokens.get(0).getToken().equals("Def")){
                throw new Exception("You cannot have statements outside a function");
            }
            if (tokens.isEmpty()) {
                throw new IllegalArgumentException("Tokens list is empty.");
            }
            ArrayList<Function_DefNode> j = new ArrayList<>();
            while(!tokens.isEmpty() && tokens.get(0).getToken().equals("Def")){

                Function_DefNode newfuncdef = Function_DefNode.ParseFunctionDefnode(tokens);


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
        if (!SymbolTable.symbolTable.hasFunc("main")) {
            System.err.println("Missing Main Function");
            return false;
        }
        for(JottTree functiondef: this.funcdeflist)
        {
            if(!functiondef.validateTree())
            {
                return false;
            }
        }
        
        return true;
    }

    @Override
    public Object execute() {
        // TODO Auto-generated method stub
        return "Placeholder in ProgramNode";
    }

    private static void addbuiltinfunctions(){
        ArrayList<String> j = new ArrayList<>();
        j.add("String");
        j.add("String");
        FunctionData f1 = FunctionData.prebuiltFunctionData("concat", j, "String");
        SymbolTable.symbolTable.setprebuiltfunc("concat", f1);
        ArrayList<String> h  = new ArrayList<>();
        h.add("String");
        FunctionData f2 = FunctionData.prebuiltFunctionData("length", h, "Integer");
        SymbolTable.symbolTable.setprebuiltfunc("length", f2);
        ArrayList<String> g = new ArrayList<>();
        g.add("Any");
        FunctionData f3 = FunctionData.prebuiltFunctionData("print", g, "Void");
        SymbolTable.symbolTable.setprebuiltfunc("print", f3);
    }

}
