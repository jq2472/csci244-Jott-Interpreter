package interpreter;
import java.util.*;

import provided.JottTree;
import provided.Token;

public class CallStack {
    /**
     * stores Stackframe (
     * Local variables,
     * A reference to the calling function (if needed), for each function call.
     * <p>
     * when a function is called, push the new context onto the stack.
     * when the function completes/returns, pop the context from the stack.
     * <p>
     * Use the SymbolTable to fetch FunctionData for the
     * function being called (e.g., its return type, parameters, and body).
     */
    // Making a global state that is accessible by all other classes
    public static CallStack callStack = new CallStack();

    private static Stack<StackFrame> stack = new Stack<>();

    public static void pushContext(StackFrame context) {
        stack.push(context);
    }

    public static StackFrame currentContext() {
        return stack.peek();
    }

    public static void popContext() {
        stack.pop();
    }
}

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
//    @Override
//    public String toString() {
//        StringBuilder result = new StringBuilder("Instruction stack:" + System.lineSeparator());
//        if (this.stack.empty()) {
//            result.append('\t').append("EMPTY").append(System.lineSeparator());
//        } else {
//            int i = 0;
//            for (StackFrame : this.stack) {
//                result.append('\t').append(i++).append(": ").append(instr).append(System.lineSeparator());
//            }
//        }
//        return result.toString();
//    }
//}



