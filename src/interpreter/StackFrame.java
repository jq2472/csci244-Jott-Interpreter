package interpreter;
import java.util.HashMap;
import java.util.Map;

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

    public FunctionData getFunctionData() {
        return functionData;
    }
}
