package tokens;

import provided.*;

public class RBrace extends Token {

    public final static TokenType TOKEN = TokenType.R_BRACE;

    /**
     * Creates RBrace Token
     * @param filename the name of the file the token came from
     * @param lineNum the number of the line in the file that the token appears on
     * @return the token
     */
    public RBrace(String filename, int lineNumber)
    {
        super("}", filename, lineNumber,TOKEN);
    }
    
}
