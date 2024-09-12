package tokens;
import provided.*;


public class StringToken extends Token {
    
    public final static TokenType TOKEN = TokenType.STRING;

    /**
     * Creates String Token
     * @param filename the name of the file the token came from
     * @param lineNum the number of the line in the file that the token appears on
     * @return the token
     */
    public StringToken(String token, String filename, int lineNumber)
    {
        super(token, filename, lineNumber, TOKEN);
    }

    //test
}
