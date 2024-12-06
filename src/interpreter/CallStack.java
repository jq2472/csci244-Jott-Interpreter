package interpreter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Stack;

import provided.JottTree;
import provided.Token;

public class CallStack {
    // Making a global state that is accessible by all other classes
    public static CallStack callStack = new CallStack();

    // create a new stack
        // function data is ~stack frame which CAN store
            // Local variables
            // Arguments passed into the method
            // Information about the caller's stack frame
            // The return address—what the program should do after
            // the function returns (i.e.: where it should "return to").
            // This is usually somewhere in the middle of the caller's code.
    // most of this is in FunctionData class which is stored in the symboltable:
//    public FunctionData(Token intoken, String inname, ArrayList<String> types, String returntype){
//        this.idnode = intoken;
//        this.name = inname;
//        this.params = types;
//        this.returns = returntype;
//    }
    // SO can retrieve function data from the symboltable, so the call stack can
    // store Jottree objects and not FunctionData
    private final Stack<JottTree> stack;

//    private final

    /**
     * constructor
     */
    public CallStack() {
        this.stack = new Stack<>();

    }

    // push jottree item so it is executable, but should also store the return?
    public void push(JottTree jottItem){
        this.stack.push(jottItem);
    }

    public JottTree pop() {
        if (this.stack.isEmpty()) {
            throw new IllegalStateException("Cannot pop from an empty stack.");
        }
        return this.stack.pop();
    }


    public JottTree peek() {
        if (this.stack.isEmpty()) {
            throw new IllegalStateException("Cannot peek on an empty stack.");
        }
        return this.stack.peek();
    }


    /**
     * Returns a string representation of the instruction set in the format:
     *     If empty:
     *     Instruction set:
     *          EMPTY
     *
     *     If not empty:
     *     Instruction set:
     * 	        0: {first-value}
     * 	        1: {second-value}
     * 	        ...
     * 	        N: {last-value}
     * </pre>
     *
     * @return the string described here.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Instruction stack:" + System.lineSeparator());
        if (this.stack.empty()) {
            result.append('\t').append("EMPTY").append(System.lineSeparator());
        } else {
            int i = 0;
            for (JottTree instr : this.stack) {
                result.append('\t').append(i++).append(": ").append(instr).append(System.lineSeparator());
            }
        }
        return result.toString();
    }



}
