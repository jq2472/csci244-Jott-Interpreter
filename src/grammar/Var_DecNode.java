package grammar;
import static grammar.Helper.*;
import static interpreter.SymbolTable.currentFunction;
import static interpreter.SymbolTable.symbolTable;

import java.util.ArrayList;
import provided.*; // checkTokenType(), checkIsNotEmpty()
import interpreter.*; // symboltable


public class Var_DecNode implements JottTree{
    private TypeNode typeNode;
    private IdNode idNode;

    public TypeNode getTypeNode() {
        return typeNode;
    }

    public IdNode getIdNode() {
        return idNode;
    }

    public Var_DecNode(TypeNode typeNode, IdNode idNode)
    {
        this.typeNode = typeNode;
        this.idNode = idNode;
    }

    public String getName(){
        return this.idNode.getName();
    }

    public static Var_DecNode parseVar_DecNode(ArrayList<Token>tokens)
    {
        //        < var_dec > -> < type > < id >;
        TypeNode typeNode = TypeNode.parseTypeNode(tokens); // will check empty, etc. there
        IdNode idNode = IdNode.parseIdNode(tokens);
        // parse semicolon
        checkIsNotEmpty(tokens);
        checkTokenType(tokens, TokenType.SEMICOLON);
        tokens.remove(0);

        Var_DecNode var_DecNode = new Var_DecNode(typeNode, idNode);
        if (SymbolTable.symbolTable.hasVar(currentFunction, idNode.getName()) || SymbolTable.symbolTable.hasFunc(idNode.getName())) {
            print_err("Duplicate entry in Symbol table During variable declaration", idNode.getToken());
        }
        SymbolTable.symbolTable.setVar(currentFunction, idNode.getName(), var_DecNode);
        return var_DecNode;

    }
    @Override
    public String convertToJott() {
//        < var_dec > -> < type > < id >;
        StringBuilder typeNodeStr = new StringBuilder();
        typeNodeStr.append(this.typeNode.convertToJott());
        typeNodeStr.append(" ");
        typeNodeStr.append(this.idNode.convertToJott());
        typeNodeStr.append(";");

        return typeNodeStr.toString();
    }

    @Override
    public boolean validateTree() {
        this.typeNode.validateTree();
        this.idNode.validateTree();
        String varName = this.idNode.getName(); // doesn't return a string.
        // keeping as Token bc the object has the name, line number, type etc.

        // check if the variable name already exists in the symbol table?
        // not actually sure if
        // int x = 5
        // int x = 4 will be an error or if it will override
        if (symbolTable.hasVar(currentFunction, varName)) {
            return true;
        }
        // add the variable to the symbol table with an initial value
        Token token = this.idNode.getToken();
        print_err("Variable name already exists in the symbol table", token);
        return false;
    }

    @Override
    public void execute() {
        // needs to be implemented
        System.out.println("Var_DecNode");
    }

    public String getVariablenameString(){
        return this.idNode.getName();
    }
    public String getReturnType(){
        return this.typeNode.convertToJott();
    }
    public Token getidtoken(){
        return this.idNode.getToken();
    }


}
