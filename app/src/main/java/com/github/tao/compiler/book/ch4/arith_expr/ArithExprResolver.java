package com.github.tao.compiler.book.ch4.arith_expr;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class ArithExprResolver {
	public int resolve(String statement) {
		CharStream chars = CharStreams.fromString(statement);
		ArithExprLexer lexer = new ArithExprLexer(chars);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ArithExprParser parser = new ArithExprParser(tokens);

		ParseTree parseTree = parser.stmt();
		ArithExprVisitor visitor = new ArithExprVisitor();

		return visitor.visit(parseTree);
	}


	/**
	 * Visitor 实现，在访问每个规则节点时，它都可以得到一个返回值。
	 * */
	public static class ArithExprVisitor extends ArithExprBaseVisitor<Integer> {
		@Override
		public Integer visitStmt(ArithExprParser.StmtContext ctx) {
			if (ctx.expr() == null) {
				return null;
			}

			return visitExpr(ctx.expr());
		}


		@Override
		public Integer visitExpr(ArithExprParser.ExprContext ctx) {
			// 针对规则 expr: INT
			if (ctx.INT() != null) {
				return Integer.valueOf(ctx.INT().getText());
			}

			// 针对规则 expr: '(' expr ')'
			if (ctx.op == null) {
				return visitExpr(ctx.expr().getFirst());
			}

			// 现在，我们知道肯定是 + - * / % 这 5 种运算符的情况
			int left = visitExpr(ctx.expr().getFirst());
			int right = visitExpr(ctx.expr().getLast());

			return switch (ctx.op.getType()) {
				case ArithExprParser.ADD -> left + right;
				case ArithExprParser.SUB -> left - right;
				case ArithExprParser.MUL -> left * right;
				case ArithExprParser.DIV -> left / right;
				case ArithExprParser.MOD -> left % right;
				default -> throw new IllegalArgumentException("不支持的运算符 " + ctx.op.getText());
			};
		}
	}
}
