package provided;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Stack;
import tokens.Assign;
import tokens.Colon;
import tokens.Comma;
import tokens.FcHeader;
import tokens.IdKeyword;
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

    public static void main(String[] args) {
        System.out.println("About to try to parse try 3");
        ArrayList<Token> t = tokenize("tokenizerTestCases\\number.jott");
        for(int i = 0; i < t.size(); i++)
        {
            System.out.println(t.get(i).getToken());
        }
    }

	/**
     * Takes in a filename and tokenizes that file into Tokens
     * based on the rules of the Jott Language
     * @param filename the name of the file to tokenize; can be relative or absolute path
     * @return an ArrayList of Jott Tokens
     */
    public static ArrayList<Token> tokenize(String filename){
        System.out.println("Parsing started");
		ArrayList<Token>tokens = new ArrayList<>();
        Stack<Character> stack = new Stack<>();
        System.out.println("About to enter reading, no print statement in the function after this one works");
        try(BufferedReader jotReader = new BufferedReader(new FileReader(filename)))
        {
            String line;
            System.out.println(jotReader);
            int linenumber = 1;
            String uniquetoken = "";
            System.out.println(linenumber);
            while((line = jotReader.readLine())!=null)
            {
                System.out.println(linenumber);

                if(line.contains("#"))
                {
                    linenumber++;
                    continue;
                }
                
                for(int i = 0; i < line.length(); i++)
                {
                   
                    
                    if(i == line.length()-1)
                    {
                        uniquetoken += String.valueOf(line.charAt(i));
                    }
                    
                    if(uniquetoken.equals("!"))
                    {
                        tokens.clear();
                        System.err.println("Invalid syntax");
                        System.err.println("Invalid token \"!\". \"!\" expects following \"=\"");
                        System.err.println(filename+".jott:"+linenumber);
                        break;
                    }
                    if(uniquetoken.equals("::") ||i == line.length()-1)
                    {
                        FcHeader fcHeader = new FcHeader(uniquetoken, filename, linenumber);
                        tokens.add(fcHeader);
                        uniquetoken = "";
                        continue;
                    }
                    if(line.charAt(i)==':'||i == line.length()-1)
                    {
                        Colon colon = new Colon(filename, linenumber);
                        tokens.add(colon);
                        uniquetoken = "";
                        continue;
                    }

                    if(line.charAt(i)==' ' || i == line.length()-1)
                    {   
                        
                        if(uniquetoken.contains("="))
                        {
                            
                            if(uniquetoken.equals("==") || uniquetoken.equals(">=") || uniquetoken.equals("<=") || uniquetoken.equals("!=") || i == line.length()-1)
                            {
                                RelOp relOp = new RelOp(uniquetoken, filename, linenumber);
                                tokens.add(relOp);
                                uniquetoken = "";
                                continue;
                                
                            }
                            
                        }
                        if(uniquetoken.equals(">") || uniquetoken.contains("<") || i == line.length()-1)
                        {
                            RelOp relOp = new RelOp(uniquetoken, filename, linenumber);
                            tokens.add(relOp);
                            uniquetoken = "";
                            continue;
                        }
                        if(uniquetoken.contains("\"") || i == line.length()-1)
                        {
                            if(uniquetoken.charAt(0)=='\"' && uniquetoken.charAt(uniquetoken.length()-1)=='\"')
                            {
                                StringToken stringToken = new StringToken(uniquetoken, filename, linenumber);
                                tokens.add(stringToken);
                                uniquetoken = "";
                                continue;
                            }
                            else
                            {
                                System.err.println("Incorrect syntax with a string");
                            }
                        }
                        if(Character.isLetter(uniquetoken.charAt(0)) || i == line.length()-1)
                        {
                            IdKeyword idKeyword = new IdKeyword(uniquetoken, filename, linenumber);
                            tokens.add(idKeyword);
                            uniquetoken = "";
                            continue;
                        }
                        
                        if(uniquetoken.equals(".") || i == line.length()-1)
                        {   
                            System.err.println("Incorrect syntax for number");
                        }
                        if(uniquetoken.contains(".") || Character.isDigit(uniquetoken.charAt(0)))
                        {
                            if(Character.isDigit(uniquetoken.charAt(0)) || uniquetoken.charAt(0)=='.' || i == line.length()-1)
                            {
                                NumberToken numbertoken = new NumberToken(uniquetoken, filename, linenumber);
                                tokens.add(numbertoken);
                                uniquetoken = "";
                                continue;
                            }
                        }
                        if(uniquetoken.isEmpty())
                        {
                            continue;
                        }
                    }
                    if(line.charAt(i)=='=' && uniquetoken.isEmpty())
                    {
                        Token assign = new Assign(filename, linenumber);
                        tokens.add(assign);
                        uniquetoken = "";
                        continue;
                    }
                    if(line.charAt(i)=='+'||line.charAt(i)=='-' || line.charAt(i)=='/' || line.charAt(i)=='*' || i == line.length()-1)
                    {
                        MathOp mathop = new MathOp(String.valueOf(line.charAt(i)), filename, linenumber);
                        tokens.add(mathop);
                        uniquetoken = "";
                        continue;
                    }
                    if(line.charAt(i) == '[' || i == line.length()-1)
                    {
                        LBrace lBrace = new LBrace(filename, linenumber);
                        tokens.add(lBrace);
                        stack.push(line.charAt(i));
                        uniquetoken = "";
                        continue;
                    }
                    if(line.charAt(i)== ']' || i == line.length()-1)
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
                            continue;
                        }
                    }
                    if(line.charAt(i)=='{' || i == line.length()-1)
                    {

                        LBracket lBracket = new LBracket(filename, linenumber);
                        tokens.add(lBracket);
                        stack.push(line.charAt(i));
                        uniquetoken = "";
                    }
                    if(line.charAt(i)=='}' || i == line.length()-1)
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
                            continue;
                        }
                    }
                    if(line.charAt(i)==';' || i == line.length()-1)
                    {
                        Semicolon semicolon = new Semicolon(filename, linenumber);
                        tokens.add(semicolon);
                        uniquetoken = "";
                        continue;
                    }
                    if(line.charAt(i) == ',' || i == line.length()-1)
                    {
                        Comma comma = new Comma(filename, linenumber);
                        tokens.add(comma);
                        uniquetoken = "";
                        continue;
                    }
                    
                    uniquetoken += String.valueOf(line.charAt(i));
                    

                    
                }
                
                linenumber++;
                
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return tokens;

	}
}