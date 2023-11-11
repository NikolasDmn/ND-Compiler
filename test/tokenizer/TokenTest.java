package tokenizer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TokenTest {

    @Test
    public void testTokenWithOnlyType() {
        Token token = new Token(TokenType.exit);
        assertEquals(TokenType.exit, token.type, "Token type should be 'exit'.");
        assertNull(token.value, "Token value should be null for tokens without a value.");
    }

    @Test
    public void testTokenWithTypeAndValue() {
        String testValue = "123";
        Token token = new Token(TokenType.int_lit, testValue);
        assertEquals(TokenType.int_lit, token.type, "Token type should be 'int_lit'.");
        assertEquals(testValue, token.value, "Token value should match the provided value.");
    }

    @Test
    public void testTokenWithNullValue() {
        Token token = new Token(TokenType.float_lit, null);
        assertEquals(TokenType.float_lit, token.type, "Token type should be 'float_lit'.");
        assertNull(token.value, "Token value should be null when explicitly set to null.");
    }

    @Test
    public void testTokenWithEmptyStringValue() {
        Token token = new Token(TokenType._return, "");
        assertEquals(TokenType._return, token.type, "Token type should be '_return'.");
        assertEquals("", token.value, "Token value should be an empty string.");
    }
}
