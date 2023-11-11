package assemblyGenerator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tokenizer.Token;
import tokenizer.TokenType;
import java.util.Arrays;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

public class AssemblyGeneratorTest {

    private AssemblyGenerator generator;

    @BeforeEach
    public void setup() {
        // Initialize with an empty list before each test
        generator = new AssemblyGenerator(Collections.emptyList());
    }

    @Test
    public void testHandleExitWithValidInput() throws GeneratorException {
        generator.tokens = Arrays.asList(
                new Token(TokenType.exit),
                new Token(TokenType.int_lit, "123"),
                new Token(TokenType.semi)
        );
        generator.index = 0;
        String expectedOutput = "    ldc 123" + System.lineSeparator() +
                "    invokestatic java/lang/System/exit(I)V" + System.lineSeparator();
        assertEquals(expectedOutput, generator.handleExit(), "Assembler code for exit command should match expected output.");
    }

    @Test
    public void testHandleExitWithMissingValue() {
        generator.tokens = Collections.singletonList(new Token(TokenType.exit));
        generator.index = 0;
        assertThrows(GeneratorException.class, generator::handleExit, "Should throw GeneratorException due to missing exit value.");
    }

    @Test
    public void testHandleExitWithIncorrectTokenType() {
        generator.tokens = Arrays.asList(
                new Token(TokenType.exit),
                new Token(TokenType.float_lit, "123.45")
        );
        generator.index = 0;
        assertThrows(GeneratorException.class, generator::handleExit, "Should throw GeneratorException due to incorrect token type.");
    }

    @Test
    public void testHandleExitWithMissingSemicolon() {
        generator.tokens = Arrays.asList(
                new Token(TokenType.exit),
                new Token(TokenType.int_lit, "123")
        );
        generator.index = 0;
        assertThrows(GeneratorException.class, generator::handleExit, "Should throw GeneratorException due to missing semicolon.");
    }
}
