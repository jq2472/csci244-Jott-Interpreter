package tokens;
import provided.*;

public class Semicolon extends Token{

    public final static TokenType TOKEN = TokenType.SEMICOLON;

    public Semicolon(String token, String filename, int lineNum, TokenType TOKEN) {
        super(";", filename, lineNum, TOKEN);
    }
    
}
