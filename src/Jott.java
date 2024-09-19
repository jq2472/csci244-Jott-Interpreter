import provided.JottTokenizer;
import provided.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Jott {

    public static void test_tokenizer(){
        System.out.println("About to try to parse try 3");
        String filepath = "tokenizerTestCases/phase1Example.jott";
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
        // determine input source
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
