import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Test;


public class ItineraryTest {

	@Test
	public void testItinerary() {
		Airport a = new Airport("a", "0010");
		Airport b = new Airport("b", "0010");
		Airport c = new Airport("c", "0010");
		
		HashMap<String, Airport> airports = new HashMap<String, Airport>();
		airports.put(a.getID(), a);
		airports.put(b.getID(), b);
		airports.put(c.getID(), c);
		
		Flight ab = new Flight("a", "b", "0000", "0100");
		Flight bc = new Flight("b", "c", "0200", "0300");
		Flight ca = new Flight("c", "a", "0400", "0500");
		
		LinkedList<Flight> flights = new LinkedList<Flight>();
		flights.add(ab);
		flights.add(bc);
		flights.add(ca);
		
		Itinerary i = new Itinerary("0000");
		assertEquals(i.getLength(), 0);
		i.add(ab, airports);
		assertEquals(i.getLength(), 60);
		i.add(bc, airports);
		assertEquals(i.getLength(), 180);
		i.add(ca, airports);
		assertEquals(i.getLength(), 60*5);
		
		i.print();
		
		i.add(new Flight("a", "b", "0000", "0001"), airports);
		assertEquals(1441, i.getLength());
		
		i = new Itinerary("0000");
		i.add(new Flight("a", "b", "0000", "0010"), airports);
		assertEquals(i.getLength(), 10);
	}
	
	@Test
	public void testGetLength() {
		Airport a = new Airport("a", "0010");
		Airport b = new Airport("b", "0010");
		Airport c = new Airport("c", "0010");
		
		HashMap<String, Airport> airports = new HashMap<String, Airport>();
		airports.put(a.getID(), a);
		airports.put(b.getID(), b);
		airports.put(c.getID(), c);
		
		Flight ab = new Flight("a", "b", "0000", "0100");
		Flight bc = new Flight("b", "c", "0200", "0300");
		Flight ca = new Flight("c", "a", "0400", "0500");
		
		LinkedList<Flight> flights = new LinkedList<Flight>();
		flights.add(ab);
		flights.add(bc);
		flights.add(ca);
		
		Itinerary i = new Itinerary("0000");
		i.add(bc, airports);
		assertEquals(i.getLength(), 3*60);
	}
	
	@Test
	public void testGetLengthWithNextDayFlight() {
		Airport jhm = new Airport("JHM", "0105");
		Airport hnl = new Airport("HNL", "0050");
		Airport ito = new Airport("ITO", "0110");
		LinkedList<Airport> airports = new LinkedList<Airport>();
		airports.add(jhm);
		airports.add(hnl);
		airports.add(ito);
		
		HashMap<String,Airport> airportMap = new HashMap<String,Airport>();
		airportMap.put("JHM", jhm);
		airportMap.put("HNL", hnl);
		airportMap.put("ITO", ito);
		
		Flight jhm_to_hnl = new Flight("JHM", "HNL", "0758", "0830");
		Flight jhm_to_ito = new Flight("JHM", "ITO", "0758", "1038");
		Flight hnl_to_ito = new Flight("HNL", "ITO", "0525", "0616");
		LinkedList<Flight> flights = new LinkedList<Flight>();
		flights.add(jhm_to_hnl);
		flights.add(jhm_to_ito);
		flights.add(hnl_to_ito);
		
		Itinerary oneFlight = new Itinerary("0719");
		oneFlight.add(jhm_to_ito, airportMap);
		assertEquals(oneFlight.getLength(), (10*60 + 38) - (07*60 + 19));
		
		Itinerary twoFlights = new Itinerary("0719");
		twoFlights.add(jhm_to_hnl, airportMap);
		twoFlights.add(hnl_to_ito, airportMap);
		assertEquals(twoFlights.getLength(), (24*60) - (07*60 + 19) + (06*60 + 16));
	}
	
	@Test
	public void testKOAtoMKK() {
		Airport koa = new Airport("KOA", "0045");
		Airport mkk = new Airport("MKK", "0035");
		Airport ogg = new Airport("OGG", "0030");
		HashMap<String, Airport> airports = new HashMap<String, Airport>();
		airports.put("KOA", koa);
		airports.put("MKK", mkk);
		airports.put("OGG", ogg);
		
		Flight koa_to_mkk = new Flight("KOA", "MKK", "0628", "0850");
		Flight koa_to_ogg = new Flight("KOA", "OGG", "1107", "1136");
		Flight ogg_to_mkk = new Flight("OGG", "MKK", "1730", "1755");
 		
		Itinerary i = new Itinerary("0828");
		i.add(koa_to_mkk, airports);
		assertEquals((24*60 - (8*60 + 28)) + (8*60 + 50), i.getLength());
		
		System.out.println(ogg_to_mkk.getDeparture());
		System.out.println(koa_to_ogg.getArrival());
		
		assertEquals((17*60 + 30) - (11*60 + 36), ogg_to_mkk.getDeparture() - koa_to_ogg.getArrival());
		
		Itinerary i2 = new Itinerary("0828");
		i2.add(koa_to_ogg, airports);
		i2.add(ogg_to_mkk, airports);
		assertEquals((17*60 + 55) - (8*60 + 28), i2.getLength());
	}
	
	@Test
	public void testJHMtoMKK() {
		Airport jhm = new Airport("JHM", "0105");
		Airport ito = new Airport("ITO", "0110");
		Airport hnl = new Airport("HNL", "0050");
		Airport mkk = new Airport("MKK", "0035");
		HashMap<String, Airport> airports = new HashMap<String, Airport>();
		airports.put("MKK", mkk);
		airports.put("JHM", jhm);
		airports.put("HNL", hnl);
		airports.put("ITO", ito);
		
		Flight jhm_to_ito = new Flight("JHM", "ITO", "0758", "1038");
		Flight ito_to_mkk = new Flight("ITO", "MKK", "1108", "1430");
		
		//Flight jhm_to_nhl = new Flight("JHM", "NHL", "0758", "0830");
		//Flight nhl_to_mkk = new Flight("NHL", "MKK", "0530", "0555");
		
		assertEquals(11*60 + 8, ito_to_mkk.getDeparture());
		assertEquals(10*60 + 38, jhm_to_ito.getArrival());
		assertEquals((11*60 + 8) - (10*60 + 38), ito_to_mkk.getDeparture() - jhm_to_ito.getArrival());
		
		Itinerary i = new Itinerary("0538");
		i.add(jhm_to_ito, airports);
		i.add(ito_to_mkk, airports);
		assertEquals((24*60-(5*60+38) + (14*60+30)), i.getLength());
	}
	
	@Test
	public void testDUTtoSEA() {
		Airport dut = new Airport("DUT", "0126");
		Airport anc = new Airport("ANC", "0123");
		Airport sea = new Airport("SEA", "0040");
		HashMap<String, Airport> airports = new HashMap<String, Airport>();
		airports.put("DUT", dut);
		airports.put("ANC", anc);
		airports.put("SEA", sea);
		
		Flight dut_to_anc = new Flight("DUT", "ANC", "2045", "2345");
		Flight anc_to_sea = new Flight("ANC", "SEA", "0020", "0435");
		assertTrue(anc_to_sea.getDeparture() < dut_to_anc.getArrival());
		assertTrue(anc_to_sea.getDeparture()+24*60 - dut_to_anc.getArrival() < anc.getMinTime());
		
		Itinerary itin = new Itinerary("2045");
		itin.add(dut_to_anc, airports);
		assertEquals(itin.getLength(), dut_to_anc.getTime());
		itin.add(anc_to_sea, airports);
		//System.out.println(itin.getLength());
		//assertEquals(itin.getLength(airports), (3*60 + 15) + (4*60 + 35));
		assertEquals(itin.getLength(), (3*60 + 15) + (24*60) + (4*60 + 35));
	}
	
	@Test
	public void testFlightDepartureBeforeTripDeparture() {
		Airport dut = new Airport("DUT", "0126");
		Airport anc = new Airport("ANC", "0123");
		Airport sea = new Airport("SEA", "0040");
		HashMap<String, Airport> airports = new HashMap<String, Airport>();
		airports.put("DUT", dut);
		airports.put("ANC", anc);
		airports.put("SEA", sea);
		
		Flight dut_to_anc = new Flight("DUT", "ANC", "2045", "2345");
		
		Itinerary i = new Itinerary("2145");
		assertEquals(0, i.getLength());
		i.add(dut_to_anc, airports);
		assertEquals((26*60), i.getLength());
	}
	
	@Test
	public void testLNYtoLIH() {
		Airport lny = new Airport("LNY", "0115");
		Airport hnl = new Airport("HNL", "0050");
		Airport lih = new Airport("LIH", "0055");
		HashMap<String, Airport> airports = new HashMap<String, Airport>();
		airports.put("LNY", lny);
		airports.put("HNL", hnl);
		airports.put("LIH", lih);
		
		Flight lny_to_lih = new Flight("LNY", "LIH", "0945", "1302");
		
		Itinerary i1 = new Itinerary("1347");
		i1.add(lny_to_lih, airports);
		assertEquals((24*60 - 45), i1.getLength());
		
		Flight lny_to_hnl = new Flight("LNY", "HNL", "0945", "1015");
		Flight hnl_to_lih = new Flight("HNL", "LIH", "0530", "0607");
		Itinerary i2 = new Itinerary("1347");
		i2.add(lny_to_hnl, airports);
		assertEquals(24*60 - (13*60 + 47) + (10*60 + 15), i2.getLength());
		i2.add(hnl_to_lih, airports);
		assertEquals(24*60 - (13*60 + 47) + 24*60 + (6*60 + 7), i2.getLength());
		assertTrue(i1.getLength() < i2.getLength());
	}

}
