package com.github.tao.compiler.book.ch4.extract_interface;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import java.util.ArrayList;

public class JavaInterfaceExtractor {
	public String extract(String classText) {
		CharStream chars = CharStreams.fromString(classText);
		JavaLexer lexer = new JavaLexer(chars);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		JavaParser parser = new JavaParser(tokens);

		ParseTree parseTree = parser.compilationUnit();
		ParseTreeWalker walker = new ParseTreeWalker();
		InterfaceExtractListener listener = new InterfaceExtractListener(tokens);

		walker.walk(listener, parseTree);

		return listener.getInterfaceDeclarationText();
	}


	@RequiredArgsConstructor
	public static class InterfaceExtractListener extends JavaBaseListener {
		private final TokenStream tokenStream;

		private final ArrayList<String> snippets = new ArrayList<>();


		public String getInterfaceDeclarationText() {
			return String.join("\n", snippets);
		}

		/**
		 * 注意 tokenStream.getText(ctx) 与 ctx.getText() 是不同的<p>
		 * tokenStream.getText(ctx) 会返回与该规则节点对应的原始文本，包括分隔符<p>
		 * 而 ctx.getText() 则是把该规则节点对应的所有 token 文本直接返回，不包含分隔符<p>
		 * 例如，对于 "import static  x.y.z.*;" 这一句，<p>
		 * 前者会返回 "import static  x.y.z.*;"，空格的个数都是原始的<p>
		 * 而后者会返回 "importstaticx.y.z.*;"，空格丢失了<p>
		 * */
		@Override
		public void enterPackageDeclaration(JavaParser.PackageDeclarationContext ctx) {
			snippets.add(tokenStream.getText(ctx));
		}


		@Override
		public void enterImportDeclaration(JavaParser.ImportDeclarationContext ctx) {
			snippets.add(tokenStream.getText(ctx));
		}
	}
}
