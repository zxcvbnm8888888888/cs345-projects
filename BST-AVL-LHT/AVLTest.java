import static org.junit.Assert.*;

import org.junit.Test;


public class AVLTest {

	@Test
	public void test() {
		AVL test = new AVL();
		test.insert("10");
		test.insert("20");
		//test.custPrint();
		test.insert("15");
		test.custPrint();
	}
	
	@Test
	public void testSequential() {
		AVL test = new AVL();
		for(int i = 0; i < 100; i++) {
			test.insert("" + i);
		}
		
		for(int i = 0; i < 100; i++) {
			assertTrue(test.find("" + i));
		}
		
	}
}
