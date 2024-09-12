package tokens;

import provided.*;

public class Comma extends Token {
    
    public final static TokenType TOKEN = TokenType.COMMA;

    /**
     * Creates Colon Token
     * @param filename the name of the file the token came from
     * @param lineNum the number of the line in the file that the token appears on
     * @return the token
     */
    public Comma(String filename, int lineNumber)
    {
        super(",", filename, lineNumber,TOKEN);
    }
}
