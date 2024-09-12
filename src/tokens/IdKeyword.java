package tokens;

import provided.*;

public class IdKeyword extends Token {

    public final static TokenType TOKEN = TokenType.ID_KEYWORD;
    /**
     * Creates IdKeyword Token
     * @param charStream the stream of necessary scanned characters
     * @param filename the name of the file the token came from
     * @param lineNum the number of the line in the file that the token appears on
     * @return the token
     */
    public IdKeyword(String charStream, String filename, int lineNumber)
    {
        super(charStream, filename, lineNumber,TOKEN);
    }
    
}
