package provided;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Stack;
import tokens.*;

public class JottTokenizer {
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
            boolean quoteread = false;

            System.out.println(linenumber);
            outerloop:
            while((line = jotReader.readLine())!=null)
            {
                System.out.println(linenumber);

                if(line.contains("#"))
                {
                    linenumber++;
                    continue;
                }
                String uniquetoken = "";
                for(int i = 0; i < line.length(); i++)
                {

                    boolean singlecharboolean = line.charAt(i) == ',' || line.charAt(i)=='[' ||line.charAt(i)==']'||line.charAt(i)=='{' || line.charAt(i)=='}'|| line.charAt(i)==';'|| line.charAt(i)==':'||line.charAt(i)=='=';

                    if(i == line.length()-1 && !(singlecharboolean))
                    {
                        uniquetoken += String.valueOf(line.charAt(i));
                    }
                    if(line.charAt(i)=='=' && i == line.length() -1)
                    {
                        uniquetoken +=String.valueOf(line.charAt(i));
                        solvetokenconcat(uniquetoken, tokens, filename, linenumber, stack);
                        continue;
                    }
                    if(uniquetoken.equals("."))
                    {
                        if(i == line.length()-1)
                        {
                        tokens.clear();
                        System.err.println("Invalid syntax");
                        System.err.println("Expecting a float");
                        System.err.println(filename+".jott:"+linenumber);
                        return null;
                        }
                    }
                    if(uniquetoken.equals("!"))
                    {
                        if(i == line.length()-1)
                        {
                            tokens.clear();
                            System.err.println("Invalid syntax");
                            System.err.println("Invalid token \"!\". \"!\" expects following \"=\"");
                            System.err.println(filename+".jott:"+linenumber);
                            return null;
                        }
                        else
                        {
                            if(line.charAt(i+1)=='=')
                            {
                                uniquetoken += String.valueOf(line.charAt(i));
                                continue;
                            }
                            else
                            {
                                tokens.clear();
                                System.err.println("Invalid syntax");
                                System.err.println("Invalid token \"!\". \"!\" expects following \"=\"");
                                System.err.println(filename+".jott:"+linenumber);
                                return null;
                            }
                        }
                        
                    }
                    if(uniquetoken.equals("::"))
                    {//sees / as a funch header for some reason
                        FcHeader fcHeader = new FcHeader(uniquetoken, filename, linenumber);
                        tokens.add(fcHeader);
                        uniquetoken = "";
                        continue;
                    }


                    if(line.charAt(i)==' ' || i == line.length()-1)
                    {
                        if(uniquetoken.isEmpty())
                        {
                            if(singlecharboolean)
                            {
                                solvetokenconcat(String.valueOf(line.charAt(i)), tokens, filename, linenumber, stack);

                            }
                            continue;
                        }

                        if(uniquetoken.equals("=") )
                        {
                            Token assign = new Assign(filename, linenumber);
                            tokens.add(assign);
                            uniquetoken = "";
                            continue;
                        }

                        if(uniquetoken.equals("==") || uniquetoken.equals(">=") || uniquetoken.equals("<=") || uniquetoken.equals("!="))
                        {
                            RelOp relOp = new RelOp(uniquetoken, filename, linenumber);
                            tokens.add(relOp);
                            uniquetoken = "";
                            continue;

                        }


                        if(uniquetoken.equals(">") || uniquetoken.contains("<"))
                        {
                            RelOp relOp = new RelOp(uniquetoken, filename, linenumber);
                            tokens.add(relOp);
                            uniquetoken = "";
                            continue;
                        }
                        if(uniquetoken.contains("\""))
                        {
                            if (quoteread){
                                if(uniquetoken.charAt(0)=='\"' && uniquetoken.charAt(uniquetoken.length()-1)=='\"')
                                {
                                    quoteread = false;
                                    StringToken stringToken = new StringToken(uniquetoken, filename, linenumber);
                                    tokens.add(stringToken);
                                    uniquetoken = "";
                                    continue;
                                }
                            }
                            if (!quoteread){
                                String substring = line.substring(i+1);
                                if (!substring.contains("\"")){
                                    tokens.clear();
                                    System.out.println("Invalid Syntax");
                                    System.out.println("opened quotes \" must be closed on the same line");
                                    System.err.println(filename+".jott:"+linenumber);
                                    return null;
                                }
                                quoteread=true;
                                
                            }
                            if(uniquetoken.charAt(0)=='\"' && uniquetoken.charAt(uniquetoken.length()-1)=='\"')
                            {
                                quoteread = true;
                                StringToken stringToken = new StringToken(uniquetoken, filename, linenumber);
                                tokens.add(stringToken);
                                uniquetoken = "";
                                continue;
                            }

                        }
                        if(Character.isLetter(uniquetoken.charAt(0)))
                        {
                            IdKeyword idKeyword = new IdKeyword(uniquetoken, filename, linenumber);
                            tokens.add(idKeyword);
                            uniquetoken = "";
                            if(i == line.length() -1 && !(Character.isLetter(line.charAt(i))))
                            {
                                solvetokenconcat(String.valueOf(line.charAt(i)), tokens, filename, linenumber, stack);
                            }
                            continue;
                        }

                        if(uniquetoken.equals("."))
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

                    }
                    if(line.charAt(i)=='+'||line.charAt(i)=='-' || line.charAt(i)=='/' || line.charAt(i)=='*')
                    {
                        if(!(uniquetoken.isEmpty()))
                        {
                            solvetokenconcat(uniquetoken, tokens, filename, linenumber, stack);
                            uniquetoken = "";
                            if(i == line.length() - 1)
                            {
                                continue;
                            }

                        }
                        MathOp mathop = new MathOp(String.valueOf(line.charAt(i)), filename, linenumber);
                        tokens.add(mathop);
                        uniquetoken = "";
                        continue;
                    }
                    if(line.charAt(i) == '{')
                    {
                        if(!(uniquetoken.isEmpty()))
                        {
                            solvetokenconcat(uniquetoken, tokens, filename, linenumber, stack);
                            uniquetoken = "";
                        }
                        LBrace lBrace = new LBrace(filename, linenumber);
                        tokens.add(lBrace);
                        stack.push(line.charAt(i));
                        uniquetoken = "";
                        continue;
                    }
                    if(line.charAt(i)== '}')
                    {
                        if(!(uniquetoken.isEmpty()))
                        {
                            solvetokenconcat(uniquetoken, tokens, filename, linenumber, stack);
                            uniquetoken = "";
                        }
                        if(stack.isEmpty())
                        {
                            tokens.clear();
                            System.err.println("Invalid syntax");
                            System.err.println("Incorrect syntax of braces");
                            System.err.println(filename+".jott:"+linenumber);
                            return null;
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
                    if(line.charAt(i)=='[')
                    {
                        if(!(uniquetoken.isEmpty()))
                        {
                            solvetokenconcat(uniquetoken, tokens, filename, linenumber, stack);
                            uniquetoken = "";
                        }

                        LBracket lBracket = new LBracket(filename, linenumber);
                        tokens.add(lBracket);
                        stack.push(line.charAt(i));
                        uniquetoken = "";
                        continue;
                    }
                    if(line.charAt(i)==']')
                    {
                        if(!(uniquetoken.isEmpty()))
                        {
                            solvetokenconcat(uniquetoken, tokens, filename, linenumber, stack);
                            uniquetoken = "";
                        }
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
                    if(line.charAt(i)==';')
                    {
                        if(!(uniquetoken.isEmpty()))
                        {
                            solvetokenconcat(uniquetoken, tokens, filename, linenumber, stack);
                            uniquetoken = "";
                        }
                        Semicolon semicolon = new Semicolon(filename, linenumber);
                        tokens.add(semicolon);
                        uniquetoken = "";
                        continue;
                    }
                    if(line.charAt(i) == ',')
                    {
                        if(!(uniquetoken.isEmpty()))
                        {
                            solvetokenconcat(uniquetoken, tokens, filename, linenumber, stack);
                            uniquetoken = "";
                        }
                        Comma comma = new Comma(filename, linenumber);
                        tokens.add(comma);
                        uniquetoken = "";
                        continue;
                    }
                    if(line.charAt(i)==':')
                    {
                        if(line.charAt(i+1)==':')
                        {
                            uniquetoken += String.valueOf(line.charAt(i));
                            continue;
                        }
                        else
                        {
                            uniquetoken += String.valueOf(line.charAt(i));
                            solvetokenconcat(uniquetoken, tokens, filename, linenumber, stack);
                            uniquetoken = "";
                            continue;
                            
                        }
                        // if(!(uniquetoken.isEmpty()))
                        // {
                        //     if(uniquetoken.contains(":"))
                        //     {
                        //         uniquetoken+=String.valueOf(line.charAt(i));
                        //     }
                        //     solvetokenconcat(uniquetoken, tokens, filename, linenumber, stack);
                        //     uniquetoken = "";
                        // }

                        // else
                        // {
                        //     Colon colon = new Colon(filename, linenumber);
                        //     tokens.add(colon);
                        //     uniquetoken = "";
                        //     continue;
                        // }

                    }


                    if(i == line.length() - 1)
                    {
                        continue;
                    }
                    else{
                        uniquetoken += String.valueOf(line.charAt(i));
                    }



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
    /**
     * solvetokenconcat is a method that is suppose to prevent any wrong concatenation from happening so we don't 
     * get an incorrect token. For example, ::print is incorrect, we want to split those as :: and print.
     * This function gets called several times to ensure we split the tokens up correctly.
     * @param token The current token that is being tested in the switch cases
     * @param tokens Arraylist of tokens that will append a current token.
     * @param filename the name of the file to tokenize; can be relative or absolute path
     * @param linenumber The current linenumber that loop is on.
     * @param stack Used to test for any braces, brackets.
     * @return The arraylist of tokens so they can used further in the program.
     */
    private static ArrayList<Token> solvetokenconcat(String token, ArrayList<Token>tokens, String filename, int linenumber, Stack<Character> stack)
    {
        switch(token)
        {
            case "=":
                Assign assign = new Assign(filename, linenumber);
                tokens.add(assign);
                break;
            case "+":
            case "-":
            case "/":
            case "*":
                MathOp mathOp = new MathOp(token, filename, linenumber);
                tokens.add(mathOp);
                break;
            case "{":
                LBrace lBrace = new LBrace(filename, linenumber);
                tokens.add(lBrace);
                stack.push('[');
                break;
            case "}":
                if(stack.isEmpty())
                {
                    tokens.clear();
                    System.err.println("Invalid syntax");
                    System.err.println("Incorrect syntax of braces");
                    System.err.println(filename+".jott:"+linenumber);

                }
                else
                {
                    RBrace rBrace = new RBrace(filename, linenumber);
                    tokens.add(rBrace);
                    stack.pop();
                }
                break;
            case "[":
                LBracket lBracket = new LBracket(filename, linenumber);
                tokens.add(lBracket);
                stack.push('{');
                break;
            case "]":
                RBracket rBracket = new RBracket(filename, linenumber);
                tokens.add(rBracket);
                stack.pop();
                break;
            case ";":
                Semicolon semicolon = new Semicolon(filename, linenumber);
                tokens.add(semicolon);
                break;
            case ",":
                Comma comma = new Comma(filename, linenumber);
                tokens.add(comma);
                break;
            case ":":
                Colon colon = new Colon(filename, linenumber);
                tokens.add(colon);
                break;
            case "::":
                FcHeader fcHeader = new FcHeader(token, filename, linenumber);
                tokens.add(fcHeader);
                break;
            default:
                if(Character.isLetter(token.charAt(0)))
                {
                    IdKeyword idKeyword = new IdKeyword(token, filename, linenumber);
                    tokens.add(idKeyword);
                }
                else if(token.contains(".") || Character.isDigit(token.charAt(0)))
                {
                    NumberToken numberToken = new NumberToken(token, filename, linenumber);
                    tokens.add(numberToken);
                }
                else if (token.startsWith("\"") && token.endsWith("\"")) {
                    StringToken stringToken = new StringToken(token, filename, linenumber);
                    tokens.add(stringToken);
                }
                else if(token.equals("==") || token.equals(">=") || token.equals("<=") || token.equals("!="))
                {
                    RelOp relOp = new RelOp(token, filename, linenumber);
                    tokens.add(relOp);
                }
                else{
                    System.err.println("Unknown token:" + token);
                }

        }


        return tokens;
    }
}