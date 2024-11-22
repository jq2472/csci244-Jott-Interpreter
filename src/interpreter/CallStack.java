package interpreter;
import java.util.LinkedHashMap;
import java.util.Stack;

import provided.JottTree;

public class CallStack {
    // Making a global state that is accessible by all other classes
    public static CallStack callStack = new CallStack();

    // create a new stack
    private final Stack<JottTree> stack;
    /**
     * constructor
     */
    private CallStack() {
        this.stack = new Stack<>();
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
