package assemblyGenerator;

import tokenizer.Token;
import tokenizer.TokenType;

import java.util.List;

public class AssemblyGenerator {
   private String ENDL = System.lineSeparator();
    List<Token> tokens;
    int index;
    public AssemblyGenerator(List<Token> input) {
        this.tokens = input;
    }
    public String generateAssembly() throws GeneratorException{
        StringBuilder builder = new StringBuilder("global _start"+ ENDL +"_start:" + ENDL);
        while (this.index < tokens.size()){
            Token token = tokens.get(this.index);
            if (token.type == TokenType.exit) {
                builder.append(handleExit());
            }
        }
            builder.append(ENDL);
        return builder.toString();
    }

    String handleExit() throws GeneratorException {
        StringBuilder builder = new StringBuilder();
        if (this.index+1 >= tokens.size()) {
            throw new GeneratorException("Exit value required after keyword <exit> but is empty");
        }
        Token num = tokens.get(this.index+1);
        if (num.type != TokenType.int_lit) {
            throw new GeneratorException("Exit value required after keyword <exit> but got: " + num.type);
        }
        if (this.index+2 >= tokens.size() || tokens.get(this.index+2).type != TokenType.semi) {
            throw new GeneratorException("Missed semi-column after exit");
        }
        builder.append("    mov rax, 60").append(ENDL).append("    mov rdi, ").append(num.value).append(ENDL).append("    syscall").append(ENDL);
        this.index = Integer.MAX_VALUE;
        return builder.toString();
    }
}
