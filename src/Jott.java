import interpreter.SymbolTable;
import provided.JottParser;
import provided.JottTokenizer;
import provided.JottTree;
import provided.Token;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static interpreter.SymbolTable.symbolTable;

public class Jott {
    /***
     * From Jott tester file
     * @param tokens
     * @return
     */
    private static String tokenListString(ArrayList<Token> tokens){
        StringBuilder sb = new StringBuilder();
        for (Token t: tokens) {
            sb.append(t.getToken());
            sb.append(":");
            sb.append(t.getTokenType().toString());
            sb.append(" ");
        }
        return sb.toString();
    }

    /**
     * We are starting to run the tokenizer using a certain jott file. 
     * We will print out the list of tokens based on its and its TokenType for confirmation that the tokenizer 
     * ran and got all the appropriately tokens.
     */
    public static void test_tokenizer(){
        
        String filepath = "tokenizerTestCases\\phase1Example.jott";
        ArrayList<Token> t = JottTokenizer.tokenize(filepath);
        // print tokens
        for(int i = 0; i < t.size(); i++)
        {
            System.out.println(t.get(i).getToken()+" "+ t.get(i).getTokenType());
        }
    }

    /**
     * The main method.  Machine instructions can either be specified from standard input
     * (no command line), or from a file (only argument on command line).  From
     * here the machine assembles the instructions and then executes them.
     * javac Jott.java
     * java Jott funcNotDefined.jott
     *
     * @param args command line argument (optional)
     * @throws FileNotFoundException if the machine file is not found
     */
    public static void main(String[] args) {
//        String path = "parserTestCases/";
        try {
            if (args.length != 1) {
                System.out.println("Usage: java Jott [filename.jott]");
                System.exit(1);
            }
//            String filePath = path + args[0];
            String filePath = args[0];

            // read and display original Jott code
            String originalJottCode = new String(
                    Files.readAllBytes(Paths.get(filePath)));
            System.out.println("Original Jott Code:\n" + originalJottCode + "\n");

            // run tokenizer, get tokens
            ArrayList<Token> tokens = JottTokenizer.tokenize(filePath);
            if (tokens == null) {
                System.err.println("Expected a list of tokens, but got null");
                return;
            }

            // run parser, get Jotttree
            ArrayList<Token> cpyTokens = new ArrayList<>(tokens);
            JottTree parsedTokens = JottParser.parse(cpyTokens);
            if (parsedTokens == null) {
                System.err.println("Expected a JottTree, but got null");
            } else {
                System.out.println("Parsed JottTree:\n" + parsedTokens);
            }

            // validate tree
            symbolTable = SymbolTable.symbolTable;
        
            if (!parsedTokens.validateTree()) {
                System.err.println("The Jott code has semantic errors.");
            }
            // iterate through the symbols in the symbol table and just do .validate() 

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
