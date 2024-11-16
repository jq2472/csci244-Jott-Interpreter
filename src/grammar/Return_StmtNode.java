package grammar;

import static grammar.Helper.*;
import static interpreter.SymbolTable.currentFunction;

import java.util.ArrayList;

import interpreter.FunctionData;
import interpreter.SymbolTable;
import provided.*; // checkTokenType(), checkIsNotEmpty()
public class Return_StmtNode implements JottTree{

    private ExprNode jottTreeexpr;
    public Return_StmtNode(ExprNode jottTreeexpr)
    {
        this.jottTreeexpr = jottTreeexpr;
    }
    public static Return_StmtNode parseReturn_StmtNode(ArrayList<Token>tokens) throws Exception
    {
        checkIsNotEmpty(tokens);
        checkTokenType(tokens, TokenType.ID_KEYWORD);
        Token currToken = tokens.get(0);
        if(currToken.getToken().equals("Return"))
        {
            tokens.remove(0);
            ExprNode exprTree = ExprNode.parseExprNode(tokens);
            currToken = tokens.get(0);
            if (currToken.getTokenType() != TokenType.SEMICOLON) {
                
                throw new Exception("Syntax Error\nExpected ';' after return expression\n"+currToken.getFilename()+":"+currToken.getLineNum());
            }
            tokens.remove(0);
            return new Return_StmtNode(exprTree);
        }
        else{
            return new Return_StmtNode(null);
        }
    }
    @Override
    public String convertToJott() {
        if(this.jottTreeexpr != null)
        {
            StringBuilder returnStmtNodeStr = new StringBuilder();
            returnStmtNodeStr.append("Return ");
            returnStmtNodeStr.append(this.jottTreeexpr.convertToJott());
            returnStmtNodeStr.append(" ; ");
            return returnStmtNodeStr.toString();
        }
        else{
            return "";
        }
       
    }

    @Override
    public boolean validateTree() {
        // check the type and validate children
        // Possible that jottTreeexpr might check if it is an instance of BinaryOpNode
        
        if (this.jottTreeexpr != null){
            if (!this.jottTreeexpr.validateTree()){
                return false;
            }
            //Checks that this statement returns what the function claims it should.
            FunctionData j = SymbolTable.symbolTable.getFunc(currentFunction);
            if (!this.jottTreeexpr.getReturnType().equals(j.getReturns()) && !j.getReturns().equals("Any")) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void execute() {
         // needs to be implemented
         System.out.println("Return_StmtNode");
    }
    
}
