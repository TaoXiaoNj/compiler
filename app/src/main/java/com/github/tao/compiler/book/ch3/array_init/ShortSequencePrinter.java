package com.github.tao.compiler.book.ch3.array_init;


import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

@Slf4j
public class ShortSequencePrinter {
	public static void main(String[] args) {
		CharStream input = CharStreams.fromString("{1, 2, {3, {4, 5}}}");
		ArrayInitLexer lexer = new ArrayInitLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ArrayInitParser parser = new ArrayInitParser(tokens);

		// 从 init 规则开始解析
		ParseTree parseTree = parser.init();

		ParseTreeWalker walker = new ParseTreeWalker();
		ShortSequencePrinterListener listener = new ShortSequencePrinterListener();

		// 指定让 walker 调用我们自定义的 Listener 实现类
		log.info("开始遍历");
		walker.walk(listener, parseTree);
		log.info("遍历结束");
	}


	public static class ShortSequencePrinterListener extends ArrayInitBaseListener {
		@Override
		public void enterElement(ArrayInitParser.ElementContext ctx) {
			TerminalNode intNode = ctx.INT();

			// 需要判断 intNode 是否为空
			// 因为 element 规则既可以推导到终结符号 INT，
			// 也可以推导到 init 规则
			if (intNode != null) {
				String intText = intNode.getText();
				log.info("遇到整型字符 {}", intText);
			}
		}
	}
}
