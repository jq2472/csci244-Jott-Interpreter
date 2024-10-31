package grammar;

import provided.*;

import java.util.ArrayList;

import interpreter.SymbolTable;

import static grammar.Helper.*; // checkTokenType(), checkIsNotEmpty()

public class StrLitNode implements ExprNode {

    private Token str;

    public StrLitNode(Token token) {
        this.str = token;
    }

    public static StrLitNode parseStrLitNode(ArrayList<Token> tokens) {
        checkIsNotEmpty(tokens);
        checkTokenType(tokens, TokenType.STRING);

        StrLitNode newStrLitNode = new StrLitNode(tokens.get(0));
        tokens.remove(0);

        return newStrLitNode;
    }

   

    @Override
    public String convertToJott() {
        return "" + this.str.getToken();
    }

    public String toString(){
        return convertToJott();
    }

    @Override
    public boolean validateTree() {
        return SymbolTable.symbolTable.has(str);
    }

    @Override
    public void execute() {
        // needs to be implemented in phase 3
        System.out.println("Executing StrLitNode");
    }

}
