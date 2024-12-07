package interpreter;
import java.util.*;

import grammar.ExprNode;
import grammar.Function_DefNode;
import provided.JottTree;
import provided.Token;

public class builtInFuncs {
    
    public static void builtInPrint(ArrayList<ExprNode> paramValues){
        System.out.println(paramValues.get(0).execute().toString());
    }

    public static String builtInConcat(ArrayList<ExprNode> paramValues){
        String concatStr = paramValues.get(0).execute() + "" + paramValues.get(1).execute();
        return concatStr;
    }
}
