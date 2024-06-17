package com.github.tao.compiler.book.ch3.array_init;

import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

@Slf4j
public class ParserTreeRenderer {
	public static void main(String[] args) {
		CharStream input = CharStreams.fromString("{1, 2, {3, 4}}");
		ArrayInitLexer lexer = new ArrayInitLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ArrayInitParser parser = new ArrayInitParser(tokens);

		ParseTree tree = parser.init();

		log.info("渲染parse tree: {}", tree.toStringTree(parser));
	}
}
