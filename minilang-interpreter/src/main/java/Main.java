import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) throws Exception {

		String source = Files.readString(Paths.get("programa.ml"));

		MiniLangLexer lexer = new MiniLangLexer(CharStreams.fromString(source));

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		MiniLangParser parser = new MiniLangParser(tokens);

		ParseTree tree = parser.program();

		try {

			SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
			semanticAnalyzer.visit(tree);

			Interpreter interpreter = new Interpreter();
			interpreter.visit(tree);

			System.out.println("Ejecución finalizada.");

		} catch (RuntimeException e) {

			System.out.println(e.getMessage());

		}

	}
}