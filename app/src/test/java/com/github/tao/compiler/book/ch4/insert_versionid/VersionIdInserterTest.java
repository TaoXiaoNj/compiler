package com.github.tao.compiler.book.ch4.insert_versionid;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VersionIdInserterTest {
	@Test
	void should_work() {
		String input = """
				package com.github.haha;
				    
				public class Driver {
				    public void drive(/* 无参数 */) { }
				    
				    protected f[] park() {}
				}""";

		String expected = """
				package com.github.haha;
				    
				public class Driver {
						public static final long serialVersionID = 1L;
						
				    public void drive(/* 无参数 */) { }
				    
				    protected f[] park() {}
				}""";

		VersionIdInserter inserter = new VersionIdInserter();
		String result = inserter.insert(input);

		System.out.println(result);

		assertEquals(expected, result);
	}
}