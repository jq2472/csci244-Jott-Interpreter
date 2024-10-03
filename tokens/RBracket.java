package tokens;

import provided.*;

public class RBracket extends Token{
    public final static TokenType TOKEN = TokenType.R_BRACKET;

    /**
     * Creates RBracket Token
     * @param filename the name of the file the token came from
     * @param lineNum the number of the line in the file that the token appears on
     * @return the token
     */
    public RBracket(String filename, int lineNumber)
    {
        super("]", filename, lineNumber,TOKEN);
    }
    
}
