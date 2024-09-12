package tokens;

import provided.*;

public class RelOp extends Token {
    public final static TokenType TOKEN = TokenType.REL_OP;

    /**
     * Creates RelOp Token
     * @param filename the name of the file the token came from
     * @param lineNum the number of the line in the file that the token appears on
     * @return the token
     */
    public RelOp(String filename, int lineNumber)
    {
        super("<>", filename, lineNumber,TOKEN);
    }
    
}
