import provided.JottTokenizer;
import provided.Token;

import java.io.FileNotFoundException;
import java.util.ArrayList;
public class Jott {

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
     *
     * @param args command line argument (optional)
     * @throws FileNotFoundException if the machine file is not found
     */
    public static void main(String[] args) throws FileNotFoundException {
        
;        // determine input source
//        Scanner input = null;
//        if (args.length == 0) {
//            // no cmd line
//        } else if (args.length == 1){
//            // filename
//            input = new Scanner(new File(args[0]));
//        } else {
//            System.out.println("Usage: java Jott [filename.jott");
//            System.exit(1);
//        }
//        input.close();
        test_tokenizer();
    }
}
