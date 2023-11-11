package tokenizer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Tokenizer {
    private List<Token> tokens;
    private int index = 0;
    private String input = "";

    public Tokenizer(String input) {
        this.input = input;
    }

    public List<Token> tokenize()  throws TokenException{
        this.tokens = new ArrayList<>();
        while (peek() != null) {
            if (Character.isLetter(peek())) {
                handleKeyword();
            }
            else if (Character.isDigit(peek())) {
                handleNumber();
            }else if (peek() == ';') {
                consume();
                tokens.add(new Token(TokenType.semi));
            } else if (Character.isSpaceChar(peek())) {
                consume();
            } else {
                consume();
//                throw new TokenException("Error parsing character: " + peek());
            }
        }
        return tokens;
    }

    private void handleNumber() {
        boolean isInt = true;
        StringBuilder valueBuilder = new StringBuilder();
        while (peek() != null) {
            if (peek() == '_') {
                consume();
            } else if (peek() == '.') {
                isInt = false;
                valueBuilder.append(consume());
            } else if (Character.isDigit(peek())) {
                valueBuilder.append(consume());
            } else {
                break;
            }
        }
        TokenType tokenType = isInt? TokenType.int_lit: TokenType.float_lit;
        tokens.add(new Token(tokenType, valueBuilder.toString()));
    }

    void handleKeyword() throws TokenException {
        StringBuilder builder = new StringBuilder();
        while (peek() != null && Character.isLetterOrDigit(peek())) {
            builder.append(consume());
        }
        String keyword = builder.toString();
        switch (keyword) {
            case "exit":
                tokens.add(new Token(TokenType.exit));
                break;
            case "return":
                tokens.add(new Token(TokenType._return));
                break;
            default:
                throw new TokenException("Invalid keyword: " + keyword);
        }
    }
    Character peek() {
        return peek(0);
    }
    Character peek(int ahead) {
        if (index+ahead >= input.length()) {
            return null;
        }
        return input.charAt(index+ahead);
    }
    Character consume() {
        return index < input.length() ? input.charAt(index++) : null;
    }
}
