package tokens;

import provided.*;

public class MathOp extends Token {

    public final static TokenType TOKEN = TokenType.MATH_OP;

    /**
     * Creates MathOp Token
     * @param charStream the stream of necessary scanned characters
     * @param filename the name of the file the token came from
     * @param lineNum the number of the line in the file that the token appears on
     * @return the token
     */
    public MathOp(String charStream, String filename, int lineNumber)
    {
        super(charStream, filename, lineNumber,TOKEN);
    }
}
