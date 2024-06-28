package com.github.tao.compiler.book.ch4.arith_expr;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ArithExprResolverTest {
	private final ArithExprResolver resolver = new ArithExprResolver();

	private static Stream<Arguments> args() {
		return Stream.of(
				Arguments.of("193\n", 193),
				Arguments.of("1+2\n", 3),
				Arguments.of("1+3*2\n", 7),
				Arguments.of("(1+2)*3\n", 9),
				Arguments.of("(6+6)/(12-10)\n", 6),
				Arguments.of("5+0-(12%10-1)\n", 4)
		);
	}

	@ParameterizedTest
	@MethodSource("args")
	void should_work(String strProg, int expected) {
		assertEquals(expected, resolver.resolve(strProg));
	}
}