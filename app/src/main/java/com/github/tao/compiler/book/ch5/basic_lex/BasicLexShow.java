package com.github.tao.compiler.book.ch5.basic_lex;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * 运行方法：
 *
 * <br><br>
 *
 * 先构建出 uber jar:
 * <pre>
 *   {@code ./gradlew clean build shadowJar}
 * </pre>
 *
 * <br>
 * <br>
 *
 * 然后，在普通的 terminal 内运行：
 * <pre>
 *   {@code java -cp app/build/libs/app-all.jar com.github.tao.compiler.book.ch5.basic_lex.BasicLexShow}
 * </pre>
 *
 * <br><br>
 * 不要在一些特别的终端（例如 IDEA 自带的终端）中运行，否则 {@link #readLines} 可能会异常。
 */
@Slf4j
public class BasicLexShow {
	public static void main(String[] args) {
		BasicLexShow show = new BasicLexShow();

		while(true) {
			System.out.println("⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜");
			String input = show.readLines();
			show.run(input);
			System.out.println("⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜\n");
		}
	}


	private void run(String input) {
		System.out.println("↓ ↓ ↓ ↓ 以下为输入的文本 ↓ ↓ ↓ ↓ ");
		System.out.println(input);
		System.out.println("↑ ↑ ↑ ↑ 以上为输入的文本 ↑ ↑ ↑ ↑ ");

		CharStream chars = CharStreams.fromString(input);
		BasicLexLexer lexer = new BasicLexLexer(chars);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		BasicLexParser parser = new BasicLexParser(tokens);
		ParseTree parseTree = parser.expr();

		ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
		MyBasicLexListener listener = new MyBasicLexListener();
		parseTreeWalker.walk(listener, parseTree);
	}


	@SneakyThrows
	private String readLines() {
		System.out.println(">>>>>>> 请输入 (按下 `CTRL+D` 表示输入结束，按下 `CTRL+C` 结束程序运行):");

		StringBuilder sb = new StringBuilder();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			try {
				String line = scanner.nextLine();
				sb.append(line + System.lineSeparator());
			} catch (NoSuchElementException ex) {
				break;
			}
		}

		// 当在新行遇到 CTRL + D 后，sb 会被插入一个 new line
		// 这里我们需要把它去掉
		if (!sb.isEmpty()) {
			sb.deleteCharAt(sb.length() - 1);
		}

		return sb.toString();
	}



	@Slf4j
	public static class MyBasicLexListener extends BasicLexBaseListener {
		@Override
		public void enterExpr(BasicLexParser.ExprContext ctx) {
			String strText = ctx.STRING().getText();
			System.out.println("识别出的 STRING 词法的 token 为\n" + strText);
		}


	}
}
