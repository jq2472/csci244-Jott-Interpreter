package interpreter;
import java.util.ArrayList;

public class FunctionData{ 
    protected String name;
    protected ArrayList<String> params;
    protected String returns;


    public FunctionData(String inname, ArrayList<String> types, String returntype){
        this.name = inname;
        this.params = types;
        this.returns = returntype;
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
} 