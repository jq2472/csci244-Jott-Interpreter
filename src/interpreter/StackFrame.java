package interpreter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import provided.JottTree;


public class StackFrame {
    private Map<String, Object> localVariables = new HashMap<>();
    private FunctionData functionData;

    // Constructor to initialize with FunctionData
    public StackFrame(FunctionData funcData) {
        this.functionData = funcData;
    }

    public void setVariable(String name, Object value) {
        localVariables.put(name, value);
    }

    public Object getVariable(String name) {
        return localVariables.get(name);
    }

    public void setSymbolTableVariables(){
        ArrayList<String> strings = this.functionData.getParamNames();
        for (String name : strings) {
            VariableData j = SymbolTable.symbolTable.getVar(SymbolTable.currentFunction, name);
            if (localVariables.get(name) instanceof JottTree) {
                JottTree node = (JottTree) localVariables.get(name);
                j.setvalue(node.execute());
            }
            else{
            j.setvalue(localVariables.get(name));
            }
        }
    }

    public FunctionData getFunctionData() {
        return functionData;
    }
}
