package unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class JUnit5ExampleTest {
	@Test
	void test_shouldPass(){
		assertEquals(2, 2, "2 should be equal 2...");
	}
}