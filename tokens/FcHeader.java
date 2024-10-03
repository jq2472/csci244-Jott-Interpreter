package tokens;

import provided.Token;
import provided.TokenType;

public class FcHeader extends Token {
    
    public final static TokenType TOKEN = TokenType.FC_HEADER;

    /**
     * Creates FcHeader Token
     * @param charStream the stream of necessary scanned characters
     * @param filename the name of the file the token came from
     * @param lineNum the number of the line in the file that the token appears on
     * @return the token
     */
    public FcHeader(String charStream, String filename, int lineNumber)
    {
        super(charStream, filename, lineNumber,TOKEN);
    }
}
