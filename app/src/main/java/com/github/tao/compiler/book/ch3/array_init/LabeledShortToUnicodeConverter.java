package com.github.tao.compiler.book.ch3.array_init;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import java.util.ArrayList;

public class LabeledShortToUnicodeConverter {
	public String convert(String arrInitStmt) {
		CharStream charStream = CharStreams.fromString(arrInitStmt);
		ArrayInitLexer lexer = new ArrayInitLexer(charStream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		LabeledArrayInitParser parser = new LabeledArrayInitParser(tokens);

		ParseTree parseTree = parser.init();
		ParseTreeWalker walker = new ParseTreeWalker();
		LabeledShortToUnicodeListener listener = new LabeledShortToUnicodeListener();

		walker.walk(listener, parseTree);

		return listener.getUnicodeString();
	}



	/**
	 * 与 {@link ShortToUnicodeConverter.ShortToUnicodeListener} 几乎完全一样，
	 * 只是本类扩展了 {@link LabeledArrayInitBaseListener}，因此需要重写 {@link LabeledShortToUnicodeListener#enterIntElement}
	 * 方法。
	 * */
	public static class LabeledShortToUnicodeListener extends LabeledArrayInitBaseListener {
		private final ArrayList<String> uniCodeList = new ArrayList<>();

		public String getUnicodeString() {
			String joined = String.join("", uniCodeList);
			return String.format("\"%s\"", joined);
		}

		@Override
		public void enterIntElement(LabeledArrayInitParser.IntElementContext ctx) {
			String textValue = ctx.INT().getText();
			Integer intValue = Integer.valueOf(textValue);

			String uniCode = String.format("\\u%04x", intValue);

			uniCodeList.add(uniCode);
		}
	}
}
