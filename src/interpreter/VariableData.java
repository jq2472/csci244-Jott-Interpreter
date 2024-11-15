package interpreter;

public class VariableData{ 
    protected String name;
    protected String type;
    
    public VariableData(String inname, String intype){
        this.name = inname;
        this.type = intype;
    }

    public String getName(){
        return this.name;
    }
    public String getType(){
        return this.type;
    }
}