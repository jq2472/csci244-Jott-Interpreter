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
import tokens.Colon;
import tokens.FcHeader;
import tokens.LBrace;
import tokens.LBracket;
import tokens.MathOp;
import tokens.NumberToken;
import tokens.RBrace;
import tokens.RBracket;
import tokens.RelOp;
import tokens.Semicolon;
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
        Stack<Character> stack = new Stack<>();
        try(BufferedReader jotReader = new BufferedReader(new FileReader(filename)))
        {
            String line;
            int linenumber = 1;
            while((line = jotReader.readLine())!=null)
            {

                if(line.contains("#"))
                {
                    linenumber++;
                    continue;
                }
                String uniquetoken = "";
                for(int i = 0; i < line.length(); i++)
                {
                    if(uniquetoken.equals("::"))
                    {
                        FcHeader fcHeader = new FcHeader(uniquetoken, filename, linenumber);
                        tokens.add(fcHeader);
                        uniquetoken = "";
                    }
                    if(uniquetoken.equals(":"))
                    {
                        Colon colon = new Colon(filename, linenumber);
                        tokens.add(colon);
                        uniquetoken = "";
                    }

                    if(line.charAt(i)==' ')
                    {
                        
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
                                RelOp relOp = new RelOp(uniquetoken, filename, linenumber);
                                tokens.add(relOp);
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
                            RelOp relOp = new RelOp(uniquetoken, filename, linenumber);
                            tokens.add(relOp);
                            uniquetoken = "";
                        }
                        if(uniquetoken.contains("\""))
                        {
                            if(uniquetoken.charAt(0)=='\"' && uniquetoken.charAt(uniquetoken.length()-1)=='\"')
                            {
                                StringToken stringToken = new StringToken(uniquetoken, filename, linenumber);
                                tokens.add(stringToken);
                                uniquetoken = "";
                            }
                            else
                            {
                                System.err.println("Incorrect syntax with a string");
                            }
                        }
                        if(uniquetoken.equals("+")||uniquetoken.equals("-") || uniquetoken.equals("/") || uniquetoken.equals("*"))
                        {
                            MathOp mathop = new MathOp(uniquetoken, filename, linenumber);
                            tokens.add(mathop);
                            uniquetoken = "";
                        }
                        if(uniquetoken.equals("."))
                        {   
                            System.err.println("Incorrect syntax for number");
                        }
                        if(Character.isDigit(uniquetoken.charAt(0)))
                        {
                            NumberToken numbertoken = new NumberToken(uniquetoken, filename, linenumber);
                            tokens.add(numbertoken);
                            uniquetoken = "";
                        }
                        if(uniquetoken.isEmpty())
                        {
                            continue;
                        }
                    }
                    if(line.charAt(i) == '[')
                    {
                        LBrace lBrace = new LBrace(filename, linenumber);
                        tokens.add(lBrace);
                        stack.push(line.charAt(i));
                        uniquetoken = "";
                    }
                    if(line.charAt(i)== ']')
                    {
                        if(stack.isEmpty())
                        {
                            System.err.println("Incorrect syntax of braces");
                        }
                        else
                        {
                            RBrace rBrace = new RBrace(filename, linenumber);
                            tokens.add(rBrace);
                            stack.pop();
                            uniquetoken = "";
                        }
                    }
                    if(line.charAt(i)=='{')
                    {

                        LBracket lBracket = new LBracket(filename, linenumber);
                        tokens.add(lBracket);
                        stack.push(line.charAt(i));
                        uniquetoken = "";
                    }
                    if(line.charAt(i)=='}')
                    {
                        if(stack.isEmpty())
                        {
                            System.err.println("Incorrect syntax of braces");
                        }
                        else
                        {
                            RBracket rBracket = new RBracket(filename, linenumber);
                            tokens.add(rBracket);
                            stack.pop();
                            uniquetoken = "";
                        }
                    }
                    if(line.charAt(i)==';')
                    {
                        Semicolon semicolon = new Semicolon(filename, linenumber);
                        tokens.add(semicolon);
                        uniquetoken = "";
                    }

                    
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