package com.github.tao.compiler.book.ch4.insert_versionid;

import com.github.tao.compiler.book.ch4.extract_interface.JavaBaseListener;
import com.github.tao.compiler.book.ch4.extract_interface.JavaLexer;
import com.github.tao.compiler.book.ch4.extract_interface.JavaParser;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;


/**
 * 为 class 定义自动插入 {@literal "public static final long serialVersionID = 1L;"} 这行语句
 * */
public class VersionIdInserter {
	public String insert(String classFileText) {
		CodePointCharStream chars = CharStreams.fromString(classFileText);
		JavaLexer lexer = new JavaLexer(chars);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		JavaParser parser = new JavaParser(tokens);
		ParseTree parseTree = parser.compilationUnit();

		TokenStreamRewriter tokenStreamRewriter = new TokenStreamRewriter(tokens);
		VersionIdInsertListener listener = new VersionIdInsertListener(tokenStreamRewriter);

		ParseTreeWalker walker = new ParseTreeWalker();
		walker.walk(listener, parseTree);

		return listener.getRewrittenText();
	}


	@RequiredArgsConstructor
	public static class VersionIdInsertListener extends JavaBaseListener {
		private final TokenStreamRewriter tokenStreamRewriter;


		public String getRewrittenText() {
			return tokenStreamRewriter.getText();
		}


		/**
		 * 回顾下 classBody 的文法
		 * <pre>
		 *   {@code
		 *      classBody
		 *            : '{' classBodyDeclaration* '}'
		 *            ;
		 *   }
		 * </pre>
		 * */
		@Override
		public void enterClassBody(JavaParser.ClassBodyContext ctx) {
			String field = "\n\t\tpublic static final long serialVersionID = 1L;\n";
			tokenStreamRewriter.insertAfter(ctx.getStart(), field);
		}
	}
}
