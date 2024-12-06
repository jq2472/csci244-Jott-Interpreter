package interpreter;
import java.util.ArrayList;

import grammar.Function_DefNode;
import provided.JottTree;
import provided.Token;

public class FunctionData{ 
    protected String name;
    protected ArrayList<String> params;
    protected String returns;
    protected Token idnode;
    protected JottTree body; // to get computed results of function calls

    public FunctionData(Token intoken, String inname, ArrayList<String> types, String returntype){
        this.idnode = intoken;
        this.name = inname;
        this.params = types;
        this.returns = returntype;
    }

    public JottTree getBody() {
        return this.body;
    }

    public void setBody(JottTree body) {
        this.body = body;
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

    // parsing FunctionData from a function_fefnode
    public static FunctionData parseFunctionData(Function_DefNode node){
        ArrayList<String> toparams = node.getparamstrings();
        Token idnodetoken = node.getnametoken();
        String toname = idnodetoken.getToken();
        String returntype = node.getReturnType();

        // create the FunctionData and set its body
        FunctionData functionData = new FunctionData(idnodetoken, toname, toparams, returntype);
        functionData.setBody(node.getBody()); // Assume Function_DefNode provides a method to get the body
        return functionData;
//        return new FunctionData(idnodetoken, toname, toparams, returntype);
    }

    public static FunctionData prebuiltFunctionData(String inname, ArrayList<String> types, String returntype){
        return new FunctionData(null, inname, types, returntype);
    }

}