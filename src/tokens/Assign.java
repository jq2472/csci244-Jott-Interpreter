package tokens;
import provided.*;

public class Assign extends Token {

    public final static TokenType TOKEN = TokenType.ASSIGN;
    
    private Token assign;

    /**
     * Creates Assign Token
     * @param filename the name of the file the token came from
     * @param lineNum the number of the line in the file that the token appears on
     * @return the token
     */
    public Assign(String filename, int lineNumber)
    {
        super("=", filename, lineNumber,TOKEN);
    }
}


