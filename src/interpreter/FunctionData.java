package interpreter;
import java.util.ArrayList;

import grammar.Function_DefNode;
import provided.Token;

public class FunctionData{ 
    protected String name;
    protected ArrayList<String> params;
    protected String returns;
    protected Token idnode;


    public FunctionData(Token intoken, String inname, ArrayList<String> types, String returntype){
        this.idnode = intoken;
        this.name = inname;
        this.params = types;
        this.returns = returntype;
    }

    public Token getidNode(){
        return this.idnode;
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<String> getParams(){
        return this.params;
    }

    public String getReturns(){
        return this.returns;
    }

    public static FunctionData parseFunctionData(Function_DefNode node){
        ArrayList<String> toparams = node.getparamstrings();
        Token idnodetoken = node.getnametoken();
        String toname = idnodetoken.getToken();
        String returntype = node.getReturnType();
        return new FunctionData(idnodetoken, toname, toparams, returntype);
    }

    public static FunctionData prebuiltFunctionData(String inname, ArrayList<String> types, String returntype){
        return new FunctionData(null, inname, types, returntype);
    }
} 