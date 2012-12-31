import static org.junit.Assert.*;

import org.junit.Test;


public class FlightTest {

	@Test
	public void testFlightTime() {
		Flight f;
		f = new Flight("ABC", "DEF", "0000", "0010");
		assertEquals(f.getTime(), 10);
		
		f = new Flight("ABC", "DEF", "0000", "0020");
		assertEquals(f.getTime(), 20);
		
		f = new Flight("ABC", "DEF", "0000", "0000");
		assertEquals(f.getTime(), 0);
		
		f = new Flight("ABC", "DEF", "0000", "0100");
		assertEquals(f.getTime(), 60);
		
		f = new Flight("ABC", "DEF", "0100", "0100");
		assertEquals(f.getTime(), 0);
		
		f = new Flight("ABC", "DEF", "0000", "0200");
		assertEquals(f.getTime(), 120);
		
		f = new Flight("ABC", "DEF", "0000", "1000");
		assertEquals(f.getTime(), 600);
		
		f = new Flight("ABC", "DEF", "1000", "1100");
		assertEquals(f.getTime(), 60);
		
		f = new Flight("ABC", "DEF", "2330", "2400");
		assertEquals(f.getTime(), 30);
		
		f = new Flight("ABC", "DEF", "2300", "0100");
		assertEquals(f.getTime(), 120);
		
		f = new Flight("ABC", "DEF", "2400", "0005");
		assertEquals(f.getTime(), 5);
		
		f = new Flight("ABC", "DEF", "2410", "0015");
		assertEquals(f.getTime(), 5);
		
		f = new Flight("ABC", "DEF", "2430", "0050");
		assertEquals(f.getTime(), 20);
		
		f = new Flight("ABC", "DEF", "2400", "0130");
		assertEquals(f.getTime(), 90);
		
		f = new Flight("ABC", "DEF", "2300", "2500");
		assertEquals(f.getTime(), 120);
	}

}
