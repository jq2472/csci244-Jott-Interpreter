package grammar;
import static grammar.Helper.checkIsNotEmpty;
import java.lang.StringBuilder;
import java.util.ArrayList;

import provided.*;

public class F_BodyNode implements JottTree {

    private ArrayList<JottTree> var_decList;
    private JottTree body;
    public F_BodyNode(ArrayList<JottTree> declarations, JottTree bodynode){
        var_decList = declarations;
        body = bodynode;
    }

    public static F_BodyNode parseF_BodyNode(ArrayList<Token> tokens) throws Exception{
        checkIsNotEmpty(tokens);
        System.out.println("Entered Parsing F_Body");
        ArrayList<JottTree> declarations = new ArrayList<>();
        while (tokens.get(0).getToken().equals("Double")||tokens.get(0).getToken().equals("Integer")||tokens.get(0).getToken().equals("String")||tokens.get(0).getToken().equals("Boolean")) {
            JottTree declarationtoadd = Var_DecNode.parseVar_DecNode(tokens);
            declarations.add(declarationtoadd);
        }
        checkIsNotEmpty(tokens);
        JottTree bodytosave = BodyNode.parseBodyNode(tokens);
        return new F_BodyNode(declarations, bodytosave);
    }
    @Override
    public String convertToJott() {
        StringBuilder j = new StringBuilder();
        // TODO Auto-generated method stub
        for (JottTree var_dec : var_decList) {
            j.append(var_dec.convertToJott());
        }
        j.append(body.convertToJott());
        return j.toString();
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
}
