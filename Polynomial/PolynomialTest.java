import static org.junit.Assert.*;
import org.junit.Test;

public class PolynomialTest {

	@Test
	public void testAdd() {
		Polynomial poly = new Polynomial(1,1);
		assertTrue(poly.poly.containsKey(1));
		assertTrue(poly.poly.containsValue(1));
		assertEquals(1, (int)poly.poly.get(1));
		Polynomial poly2 = new Polynomial(2,2);
		poly.add(poly2);
		assertTrue(poly.poly.containsKey(1));
		assertTrue(poly.poly.containsValue(1));
		assertTrue(poly.poly.containsKey(2));
		assertTrue(poly.poly.containsValue(2));
		assertEquals(2, (int)poly.poly.get(2));
		poly2 = new Polynomial(5, 10);
		poly.add(poly2);
		assertTrue(poly.poly.containsKey(10));
		assertTrue(poly.poly.containsValue(5));
		assertEquals(5, (int)poly.poly.get(10));
		poly2 = new Polynomial(3, 1);
		poly.add(poly2);
		assertTrue(poly.poly.containsKey(1));
		assertTrue(poly.poly.containsValue(4));
		assertEquals(4, (int)poly.poly.get(1));
	}
	
	@Test
	public void testSub() {
		Polynomial poly = new Polynomial(1,1);
		assertTrue(poly.poly.containsKey(1));
		assertTrue(poly.poly.containsValue(1));
		assertEquals(1, (int)poly.poly.get(1));
		Polynomial poly2 = new Polynomial(2,2);
		poly.sub(poly2);
		assertTrue(poly.poly.containsKey(1));
		assertTrue(poly.poly.containsValue(1));
		assertTrue(poly.poly.containsKey(2));
		assertTrue(poly.poly.containsValue(-2));
		assertEquals(-2, (int)poly.poly.get(2));
		poly2 = new Polynomial(5, 10);
		poly.sub(poly2);
		assertTrue(poly.poly.containsKey(10));
		assertTrue(poly.poly.containsValue(-5));
		assertEquals(-5, (int)poly.poly.get(10));
		poly2 = new Polynomial(3, 1);
		poly.sub(poly2);
		assertTrue(poly.poly.containsKey(1));
		assertTrue(poly.poly.containsValue(-2));
		assertEquals(-2, (int)poly.poly.get(1));
	}
	
	@Test
	public void testPrint() {
		Polynomial poly = new Polynomial(1,1);
		poly.print();
		poly.add(new Polynomial(2,2));
		poly.print();
		poly.add(new Polynomial(10,20));
		poly.print();
		poly.add(new Polynomial(-10,-20));
		poly.print();
		poly.add(new Polynomial(-10,-20));
		poly.print();
	}
}
