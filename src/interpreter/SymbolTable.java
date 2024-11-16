package interpreter;
import java.util.LinkedHashMap;
import java.util.Map;

import grammar.AssignmentNode;
import grammar.Function_DefNode;
import grammar.Var_DecNode;
import provided.*;

/**
 * The machine's symbol table for handling the mapping of
 * variable names and function names to their associated values.
 */
public class SymbolTable<Token> {
    public static SymbolTable symbolTable = new SymbolTable<>();

    public static String currentFunction; // tracking the currently active function, "turn off/on"

    /** the symbol tables to handle function/variable scope */
    // func -> <func_name, func_info>
    private final Map<String, FunctionData> funcTable;
    // vars -> <var_name, <var_name, var_info>>
    // will know the function scope the variables can be used
    // because it's stored as outermost key
    private final Map<String, Map<String, VariableData>> varTable;

    /**
     * Create an empty symbol table.
     */
    private SymbolTable() {
        // use a LinkedHashMap so that we have O(1) access,
        // but the insertion order is maintained.
        this.funcTable = new LinkedHashMap<>();
        this.varTable = new LinkedHashMap<>();
    }

    /**
     * Set a variable in the symbol table for a specific function scope.
     * @param funcName the name of the function where the variable is declared
     * @param varName the name of the variable
     * @param value the associated value (Token object/tokentype/undecided..)
     */
    public void setVar(String funcName, String varName, Var_DecNode vardatasource) {
        // Ensure the function's variable map exists
        varTable.computeIfAbsent(funcName, k -> new LinkedHashMap<>());
        // Set the variable in the corresponding function's variable map
        varTable.get(funcName).put(varName, VariableData.parseVariabledataData(vardatasource));
    }

    public void setVarVarDec(String funcname, String varName, VariableData vardata){
        varTable.computeIfAbsent(funcname, k -> new LinkedHashMap<>());
        varTable.get(funcname).put(varName, vardata);
    }


    //retrieve a variable within a specific function’s scope
    public VariableData getVar(String funcName, String varName) {
        Map<String, VariableData> funcVars = varTable.get(funcName);
        if (funcVars != null) {
            VariableData vardata = funcVars.get(varName);
            return vardata;

        }
        return null; // variable not found in this function scope
    }

    // overloaded method that defaults to use currentFunction
    public VariableData getVar(String varName) {
        return getVar(currentFunction, varName); // Calls the other method with currentFunction as funcName
    }

    /**
     * Check if a variable exists within a given function scope.
     * @param funcName the name of the function
     * @param varName the name of the variable
     * @return true if the variable exists, false otherwise
     */
    public boolean hasVar(String funcName, String varName) {
        Map<String, VariableData> funcVars = varTable.get(funcName);
        return funcVars != null && funcVars.containsKey(varName);
    }

    /**
     * Set a function name in the symbol table to an associated value.
     * @param funcName the function name
     * @param value the associated value (Token object)
     */
    public void setFunc(String funcName, Function_DefNode funcdefnode) {
        funcTable.put(funcName, FunctionData.parseFunctionData(funcdefnode));
    }
    public void setprebuiltfunc(String funcName, FunctionData f){
        funcTable.put(funcName, f);
    }

    /**
     * Retrieve the associated value of a function name from the table.
     * @param funcName the function name
     * @return the value (Token object), or null if not found
     */
    public FunctionData getFunc(String funcName) {
        return funcTable.get(funcName);
    }

    /**
     * Check if a function name already exists in the symbol table.
     * @param funcName the function name
     * @return true if the function exists, false otherwise
     */
    public boolean hasFunc(String funcName) {
        return funcTable.containsKey(funcName);
    }

    // Method to clear variables after a function finishes execution
    public void clearFunctionScope(String funcName) {
        varTable.remove(funcName);
    }

    /**
     * Returns a string representation of the symbol table, including functions and variables.
     * @return the string representation
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Symbol Table:\n");

        // Append function table entries
        result.append("Functions:\n");
        for (Map.Entry<String, FunctionData> entry : funcTable.entrySet()) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        // Append variable table entries
        result.append("Variables:\n");
        for (Map.Entry<String, Map<String, VariableData>> funcEntry : varTable.entrySet()) {
            result.append("Function: ").append(funcEntry.getKey()).append("\n");
            for (Map.Entry<String, VariableData> varEntry : funcEntry.getValue().entrySet()) {
                result.append("  ").append(varEntry.getKey()).append(": ").append(varEntry.getValue()).append("\n");
            }
        }

        return result.toString();
    }
}