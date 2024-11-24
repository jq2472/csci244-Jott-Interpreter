package grammar;
import static grammar.Helper.checkIsNotEmpty;
import static grammar.Helper.checkTokenType;

import java.lang.StringBuilder;
import java.util.ArrayList;

import provided.*;

public class F_BodyNode implements JottTree {

    private ArrayList<JottTree> var_decList;
    private BodyNode body;

    public F_BodyNode(ArrayList<JottTree> declarations, BodyNode bodynode){
        this.var_decList = declarations;
        this.body = bodynode;

    }

    public static F_BodyNode parseF_BodyNode(ArrayList<Token> tokens) throws Exception{
        checkIsNotEmpty(tokens);
        ArrayList<JottTree> declarations = new ArrayList<>();

        // the body will be any valid Jott code other than defining another function
        if (tokens.get(0).getToken().equals("Def")) {
            throw new IllegalArgumentException("Error: Cannot have nested functions");
        }
        // < f_body > -> < var_dec >* < body >
        // < var_dec > -> < type > < id >;
        // there are any variable declarations, parse them
        while (tokens.get(0).getToken().equals("Double") ||
                tokens.get(0).getToken().equals("Integer") ||
                tokens.get(0).getToken().equals("String") ||
                tokens.get(0).getToken().equals("Boolean")) {
            JottTree declarationToAdd = Var_DecNode.parseVar_DecNode(tokens);
            declarations.add(declarationToAdd);
        }
        // there may be no variable declarations, so it will be an empty list
        if (declarations.size() == 0){
            declarations = new ArrayList<>();
        }
        // otherwise continue parsing the body node
        checkIsNotEmpty(tokens);
        BodyNode bodytosave = BodyNode.parseBodyNode(tokens);
        return new F_BodyNode(declarations, bodytosave);
    }
    @Override
    public String convertToJott() {
        StringBuilder j = new StringBuilder();
        for (JottTree var_dec : this.var_decList) {
            j.append(var_dec.convertToJott());
        }
        j.append(this.body.convertToJott());
        return j.toString();
    }

    @Override
    public boolean validateTree() {
        // validate the children   NEEDS PROOF CHECKING
        // if (this.body.validateTree()){
        //     return true;
        // }
        // return false;
        
        // Validate each Var_DecNode.
        for (JottTree varDec : var_decList) {
            if (varDec == null || !varDec.validateTree()) {
                System.err.println("Error: Invalid variable declaration in function body.");
                return false;
            }
        }
        // Validate if the body is null or not valid
        if (body == null || !body.validateTree()) {
            System.err.println("Error: Invalid body in function body.");
            return false;
        }
        // Otherwise everything is valid.
        return true;
    }

    public Return_StmtNode getReturnNode() {
        return body.getReturnNode();
    }

    @Override
    public Object execute() {
        // TODO Auto-generated method stub
        return "Placeholder in F_bodyNode";
    }

   
    
}
