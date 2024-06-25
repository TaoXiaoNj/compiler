package com.github.tao.compiler.book.ch3.array_init;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ShortToUnicodeConverterTest {
	private final ShortToUnicodeConverter converter = new ShortToUnicodeConverter();

	private static Stream<Arguments> args() {
		return Stream.of(
				Arguments.of("{1}", "\"\\u0001\""),
				Arguments.of("{1, {{2}}}", "\"\\u0001\\u0002\""),
				Arguments.of("{1, 2, {3}, {4, {5}}}", "\"\\u0001\\u0002\\u0003\\u0004\\u0005\"")
		);
	}


	@ParameterizedTest
	@MethodSource("args")
	void should_convert_to_unicodes(String arrayInitExp, String expectedUnicodeStr) {
		String result = converter.convert(arrayInitExp);
		assertEquals(expectedUnicodeStr, result);
	}
}