package tokenizer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TokenizerTest {

    private Tokenizer tokenizer;

    @BeforeEach
    public void setUp() {
        tokenizer = new Tokenizer("hello");
    }

    @Test
    public void testTokenizeWithKeywords() throws TokenException {
        Tokenizer tokenizer = new Tokenizer("exit return");
        List<Token> tokens = tokenizer.tokenize();
        assertEquals(2, tokens.size(), "There should be two tokens.");
        assertEquals(TokenType.exit, tokens.get(0).type, "First token should be 'exit'.");
        assertEquals(TokenType._return, tokens.get(1).type, "Second token should be 'return'.");
    }

    @Test
    public void testTokenizeWithNumbers() throws TokenException {
        Tokenizer tokenizer = new Tokenizer("123 45.67");
        List<Token> tokens = tokenizer.tokenize();
        assertEquals(2, tokens.size(), "There should be two tokens.");
        assertEquals(TokenType.int_lit, tokens.get(0).type, "First token should be an integer literal.");
        assertEquals("123", tokens.get(0).value, "First token's value should be '123'.");
        assertEquals(TokenType.float_lit, tokens.get(1).type, "Second token should be a float literal.");
        assertEquals("45.67", tokens.get(1).value, "Second token's value should be '45.67'.");
    }

    @Test
    public void testTokenizeWithSemicolons() throws TokenException {
        Tokenizer tokenizer = new Tokenizer(";");
        List<Token> tokens = tokenizer.tokenize();
        assertEquals(1, tokens.size(), "There should be one token.");
        assertEquals(TokenType.semi, tokens.get(0).type, "Token should be a semicolon.");
    }

    @Test
    public void testTokenizeWithInvalidInput() {
        Tokenizer tokenizer = new Tokenizer("123 abc");
        assertThrows(TokenException.class, tokenizer::tokenize, "Tokenizer should throw TokenException for invalid input.");
    }

    @Test
    public void testTokenizeEmptyInput() throws TokenException {
        Tokenizer tokenizer = new Tokenizer("");
        List<Token> tokens = tokenizer.tokenize();
        assertTrue(tokens.isEmpty(), "Token list should be empty for empty input.");
    }

    @Test
    public void testPeak() {
        assertEquals('h', tokenizer.peek(), "Peak should return the character at the index.");
    }

    @Test
    public void testPeakWithAhead() {
        assertEquals('l', tokenizer.peek(2), "Peak with ahead should return the character at the specified position ahead.");
    }

    @Test
    public void testPeakBeyondLength() {
        Tokenizer longTokenizer = new Tokenizer("hi");
        longTokenizer.consume(); // Advances index to 1
        assertNull(longTokenizer.peek(2), "Peak beyond the length of the string should return null.");
    }

    @Test
    public void testConsume() {
        assertEquals('h', tokenizer.consume(), "Consume should return the current character and advance the index.");
        assertEquals('e', tokenizer.consume(), "Consume should now return the next character.");
    }

    @Test
    public void testConsumeAtEndOfString() {
        Tokenizer shortTokenizer = new Tokenizer("a");
        shortTokenizer.consume(); // Consumes 'a', index is now at the end of the string
        assertNull(shortTokenizer.consume(), "Consume at the end of the string should return null");
    }
}
