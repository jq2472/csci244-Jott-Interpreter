package provided;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author 
 **/

import java.util.ArrayList;
import java.util.Stack;

import tokens.Assign;
import tokens.FcHeader;
import tokens.LBrace;
import tokens.LBracket;
import tokens.MathOp;
import tokens.NumberToken;
import tokens.RBrace;
import tokens.RBracket;
import tokens.RelOp;
import tokens.StringToken;

public class JottTokenizer {

	/**
     * Takes in a filename and tokenizes that file into Tokens
     * based on the rules of the Jott Language
     * @param filename the name of the file to tokenize; can be relative or absolute path
     * @return an ArrayList of Jott Tokens
     */
    public static ArrayList<Token> tokenize(String filename){
		ArrayList<Token>tokens = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        try(BufferedReader jotReader = new BufferedReader(new FileReader(filename)))
        {
            String line;
            int linenumber = 1;
            while((line = jotReader.readLine())!=null)
            {

                String uniquetoken = "";
                for(int i = 0; i < line.length(); i++)
                {

                    if(line.charAt(i)==' ')
                    {
                        if(uniquetoken.contains("#"))
                        {
                            uniquetoken+=String.valueOf(line.charAt(i));
                        }
                        if(uniquetoken.contains("="))
                        {
                            if(uniquetoken.length()==1)
                            {
                                Token assign = new Assign(filename, linenumber);
                                tokens.add(assign);
                                uniquetoken = "";
                            }
                            if(uniquetoken.equals("==") || uniquetoken.equals(">=") || uniquetoken.equals("<="))
                            {
                                RelOp relOp = new RelOp();
                                relOp.setrelop(uniquetoken);
                                Token token = new Token(relOp.getrelop(), filename, linenumber, TokenType.REL_OP);
                                tokens.add(token);
                                uniquetoken = "";
                            }
                            if(uniquetoken.charAt(1)=='!')
                            {
                                System.err.println("Incorrect syntax with not equals");
                            }
                            else{
                                
                                uniquetoken = "";
                            }
                        }
                        if(uniquetoken.equals(">") || uniquetoken.contains("<"))
                        {
                            RelOp relOp = new RelOp();
                            relOp.setrelop(uniquetoken);
                            Token token = new Token(relOp.getrelop(), filename, linenumber, TokenType.REL_OP);
                            tokens.add(token);
                            uniquetoken = "";
                        }
                        if(uniquetoken.contains("\""))
                        {
                            if(uniquetoken.charAt(0)=='\"' && uniquetoken.charAt(uniquetoken.length()-1)=='\"')
                            {
                                StringToken stringToken = new StringToken();
                                stringToken.setstringtoken(uniquetoken);
                                Token token = new Token(stringToken.getstringtoken(), filename, linenumber, TokenType.STRING);
                                tokens.add(token);
                                uniquetoken = "";
                            }
                            else
                            {
                                System.err.println("Incorrect syntax with a string");
                            }
                        }
                        if(uniquetoken.equals("+")||uniquetoken.equals("-") || uniquetoken.equals("/") || uniquetoken.equals("*"))
                        {
                            MathOp mathop = new MathOp();
                            mathop.setmathop(uniquetoken);
                            Token token = new Token(mathop.getmathop(), filename, linenumber, TokenType.MATH_OP);
                            tokens.add(token);
                            uniquetoken = "";
                        }
                        if(uniquetoken.equals("["))
                        {
                            LBrace lBrace = new LBrace();
                            lBrace.setlbrace(uniquetoken);
                            Token token = new Token(lBrace.getlbrace(), filename, linenumber, TokenType.L_BRACE);
                            tokens.add(token);
                            stack.push(uniquetoken);
                            uniquetoken = "";
                        }
                        if(uniquetoken.equals("]"))
                        {
                            if(stack.isEmpty())
                            {
                                System.err.println("Incorrect syntax of braces");
                            }
                            else
                            {
                                RBrace rBrace = new RBrace();
                                rBrace.setrbrace(uniquetoken);
                                Token token = new Token(uniquetoken, filename, linenumber, TokenType.R_BRACE);
                                tokens.add(token);
                                stack.pop();
                                uniquetoken = "";
                            }
                        }
                        if(uniquetoken.equals("{"))
                        {
                            LBracket lBracket = new LBracket();
                            lBracket.setlbracket(uniquetoken);
                            Token token = new Token(lBracket.getlbracket(), filename, linenumber, TokenType.L_BRACKET);
                            tokens.add(token);
                            stack.push(uniquetoken);
                            uniquetoken = "";
                        }
                        if(uniquetoken.equals("}"))
                        {
                            if(stack.isEmpty())
                            {
                                System.err.println("Incorrect syntax of braces");
                            }
                            else
                            {
                                RBracket rBracket = new RBracket();
                                rBracket.setrbracket(uniquetoken);
                                Token token = new Token(rBracket.getrbracket(), filename, linenumber, TokenType.R_BRACKET);
                                tokens.add(token);
                                stack.pop();
                                uniquetoken = "";
                            }
                        }
                        if(uniquetoken.equals("."))
                        {   
                            System.err.println("Incorrect syntax for number");
                        }
                        if(Character.isDigit(uniquetoken.charAt(0)))
                        {
                            NumberToken numbertoken = new NumberToken();
                            numbertoken.setstrnumber(uniquetoken);
                            Token token = new Token(numbertoken.getstrnumber(), filename, linenumber, TokenType.NUMBER);
                            tokens.add(token);
                            uniquetoken = "";
                        }
                    }

                    
                }
                if(uniquetoken.contains("#"))
                {
                    FcHeader fcHeader = new FcHeader();
                    fcHeader.setfcheader(uniquetoken);
                    Token token = new Token(fcHeader.getfcheader(), filename, linenumber, TokenType.FC_HEADER);
                    tokens.add(token);

                }
                linenumber++;
                
            }
        }
        catch(Exception e)
        {

        }
        return tokens;

	}
}