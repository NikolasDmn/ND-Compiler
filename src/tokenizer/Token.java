package tokenizer;

public class Token {
    public TokenType type;
    public String value = null;
    public Token(TokenType token) {
        this.type = token;
    }
    public Token(TokenType token, String value) {
        this.type = token;
        this.value = value;
    }
}
