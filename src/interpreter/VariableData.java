package interpreter;

import grammar.Var_DecNode;
import provided.*;

public class VariableData{ 
    protected Token tokeninfo;
    protected String name;
    protected String type;
    public Object value;
    
    private VariableData(Token intoken, String inname, String intype){
        this.tokeninfo = intoken;
        this.name = inname;
        this.type = intype;
        this.value = null;
    }

    public void setvalue(Object inval){
        this.value = inval;
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
    public Object getValue(){
        return this.value;
    }

    public static VariableData parseVariabledataData(Var_DecNode j){
        String name = j.getVariablenameString();
        String type = j.getReturnType();
        Token t  = j.getidtoken();
        return new VariableData(t, name, type);
    } 

    public static VariableData parseVariabledatafromFDefParams(Token inname, String typesString){
        String name = inname.getToken();
        String type = typesString;
        Token t = inname;
        return new VariableData(t, name, type);
    }
}