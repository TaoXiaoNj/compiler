package com.github.tao.compiler.book.ch3.array_init;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import java.util.ArrayList;

public class ShortToUnicodeConverter {
	public String convert(String arrInitStmt) {
		CharStream charStream = CharStreams.fromString(arrInitStmt);
		ArrayInitLexer lexer = new ArrayInitLexer(charStream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ArrayInitParser parser = new ArrayInitParser(tokens);

		ParseTree parseTree = parser.init();
		ParseTreeWalker walker = new ParseTreeWalker();
		ShortToUnicodeListener listener = new ShortToUnicodeListener();

		walker.walk(listener, parseTree);

		return listener.getUnicodeString();
	}



	/**
	 * 将把
	 *    <pre>{@code {1, 2, {3}, {4, {5}}}}</pre>
	 *
	 * <p>
	 * 这样的数组初始化语句，转换成 <p>
	 *
	 *  <pre>
	 *    "&#92;u0001&#92;u0002&#92;u0003&#92;u0004&#92;u0005"
	 *  </pre>
	 *
	 * 这样的字符串。
	 * */
	public static class ShortToUnicodeListener extends ArrayInitBaseListener {
		private final ArrayList<String> uniCodeList = new ArrayList<>();


		public String getUnicodeString() {
			String joined = String.join("", uniCodeList);
			return String.format("\"%s\"", joined);
		}


		@Override
		public void enterElement(ArrayInitParser.ElementContext ctx) {
			if (ctx.INT() == null) {
				return;
			}

			String textValue = ctx.INT().getText();
			Integer intValue = Integer.valueOf(textValue);

			// "\\u%04x" 表示以4位长、前部补零的unicode形式输出 intVal
			String uniCode = String.format("\\u%04x", intValue);

			uniCodeList.add(uniCode);
		}
	}
}
