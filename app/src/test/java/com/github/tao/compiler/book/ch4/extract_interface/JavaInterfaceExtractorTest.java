package com.github.tao.compiler.book.ch4.extract_interface;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class JavaInterfaceExtractorTest {
	private final JavaInterfaceExtractor extractor = new JavaInterfaceExtractor();

	@Test
	void should_extract_work() {
		String input = """
				package com.github.haha;
				    
				import a.b.c.Hello;
				import static x.y.z.*;
				    
				public class Driver {
				    public void drive(/* 无参数 */) { }
				    
				    protected int[] park(
				        String place,  // 地段
				        double price   // 停车价格
				    ) {
				        log.info("停车");
				        return new int[10];
				    }
				    
				    List<Map<String, Object>>[] getHous() {
				        log.info("查询停车时长);
				        return List.of();
				    }
				}""";

		String result = extractor.extract(input);

		log.info("============================\n{}", result);
		log.info("============================");
	}
}