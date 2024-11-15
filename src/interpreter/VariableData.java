package interpreter;

import grammar.AssignmentNode;
import provided.*;

public class VariableData{ 
    protected Token tokeninfo;
    protected String name;
    protected String type;
    
    private VariableData(Token intoken, String inname, String intype){
        this.tokeninfo = intoken;
        this.name = inname;
        this.type = intype;
    }

    public String getName(){
        return this.name;
    }
    public String getType(){
        return this.type;
    }
    public Token getToken(){
        return this.tokeninfo;
    }

    public static VariableData parseVariabledata(AssignmentNode j){
        String name = j.getVariablenameString();
        String type = j.getReturnType();
        Token t  = j.getidtoken();
        return new VariableData(t, name, type);
    } 
}