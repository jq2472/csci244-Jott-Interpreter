package tokens;
import provided.*;

public class Semicolon extends Token{

    public final static TokenType TOKEN = TokenType.SEMICOLON;

    public Semicolon(String filename, int lineNum) {
        super(";", filename, lineNum, TOKEN);
    }
    
}
