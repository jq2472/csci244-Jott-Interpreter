package tokens;

import provided.*;

public class NumberToken extends Token {

    public final static TokenType TOKEN = TokenType.NUMBER;

    /**
     * Creates Number Token
     * @param charStream the stream of necessary scanned characters
     * @param filename the name of the file the token came from
     * @param lineNum the number of the line in the file that the token appears on
     * @return the token
     */
    public NumberToken(String charStream, String filename, int lineNumber)
    {
        super(charStream, filename, lineNumber,TOKEN);
    }
}
