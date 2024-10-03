package tokens;

import provided.*;

public class LBracket extends Token {
    public final static TokenType TOKEN = TokenType.L_BRACKET;

    /**
     * Creates LBracket Token
     * @param filename the name of the file the token came from
     * @param lineNum the number of the line in the file that the token appears on
     * @return the token
     */
    public LBracket(String filename, int lineNumber)
    {
        super("[", filename, lineNumber,TOKEN);
    }
}
