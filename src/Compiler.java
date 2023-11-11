import assemblyGenerator.AssemblyGenerator;
import assemblyGenerator.GeneratorException;
import tokenizer.Token;
import tokenizer.TokenException;
import tokenizer.Tokenizer;

import java.io.IOException;
import java.util.List;

public class Compiler {
    public static void main(String[] args) {
        if (args.length < 1 || !args[0].endsWith(".nd")) {
            System.err.println("Error: Missing required arguments.");
            System.err.println("Usage: java NDumb <input_file_path.nd>");
            System.exit(1); // Exit with a non-zero status to indicate an error
        }
        try {
            String fileContents = FileManager.read(args[0]);
            Tokenizer tokenizer = new Tokenizer(fileContents);
            List<Token> tokens = tokenizer.tokenize();
            AssemblyGenerator asmGen = new AssemblyGenerator(tokens);
            String asmCode = asmGen.generateAssembly();
            FileManager.write("./exec.asm", asmCode);
        }
        catch (IOException | TokenException | GeneratorException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

}
