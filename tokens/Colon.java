package tokens;
import provided.*;

public class Colon extends Token {

    public final static TokenType TOKEN = TokenType.COLON;

    /**
     * Creates Colon Token
     * @param filename the name of the file the token came from
     * @param lineNum the number of the line in the file that the token appears on
     * @return the token
     */
    public Colon(String filename, int lineNumber)
    {
        super(":", filename, lineNumber,TOKEN);
    }
    
}

