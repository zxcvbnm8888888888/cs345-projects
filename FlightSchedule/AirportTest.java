import static org.junit.Assert.*;

import org.junit.Test;


public class AirportTest {

	@Test
	public void testAirport() {
		Airport a = new Airport("a", "0000");
		assertTrue(a.getID().equals("a"));
		assertEquals(a.getMinTime(), 0);
		
		Airport b = new Airport("b", "0010");
		assertEquals(b.getMinTime(), 10);
		
		Airport c = new Airport("c", "0060");
		assertEquals(c.getMinTime(), 60);
		
		Airport d = new Airport("d", "0130");
		assertEquals(d.getMinTime(), 90);
	}

}
