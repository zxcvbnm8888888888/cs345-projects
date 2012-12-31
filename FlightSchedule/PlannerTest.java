import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;


public class PlannerTest {

	@Test
	public void testPlanner() {
		Airport a = new Airport("a", "0010");
		Airport b = new Airport("b", "0010");
		Airport c = new Airport("c", "0010");
		
		LinkedList<Airport> l = new LinkedList<Airport>();
		l.add(a);
		l.add(b);
		l.add(c);
		
		Flight ab = new Flight("a", "b", "0000", "0100");
		Flight bc = new Flight("b", "c", "0200", "0300");
		Flight ca = new Flight("c", "a", "0400", "0500");
		
		LinkedList<Flight> f = new LinkedList<Flight>();
		f.add(ab);
		f.add(bc);
		f.add(ca);
		
		Planner p = new Planner(l, f);
		assertEquals(p.getNumAirports(), 3);
		assertEquals(p.getNumFlights(), 3);
		System.out.println("Airports: " + p.getNumAirports() + ", Flights: " + p.getNumFlights());
		
		Itinerary i = p.Schedule("a", "b", "0000");
		i.print();
		
		i = p.Schedule("a", "c", "0000");
		i.print();
		
		i = p.Schedule("b", "c", "0000");
		i.print();
		
		Flight ab1 = new Flight("a", "b", "0005", "0105");
		Flight ab2 = new Flight("a", "b", "0010", "0110");
		Flight ab3 = new Flight("a", "b", "0015", "0115");
		f.add(ab1);
		f.add(ab2);
		f.add(ab3);
		
		p = new Planner(l, f);
		assertEquals(p.getNumFlights(), 6);
		i = p.Schedule("a", "b", "0000");
		i.print();
	}
	
	@Test
	public void testLNYtoLIH() {
		Airport lny = new Airport("LNY", "0115");
		Airport hnl = new Airport("HNL", "0050");
		Airport lih = new Airport("LIH", "0055");
		LinkedList<Airport> airports = new LinkedList<Airport>();
		airports.add(lny);
		airports.add(hnl);
		airports.add(lih);
		
		Flight lny_to_lih = new Flight("LNY", "LIH", "0945", "1302");
		Flight lny_to_hnl = new Flight("LNY", "HNL", "0945", "1015");
		Flight hnl_to_lih = new Flight("HNL", "LIH", "0530", "0607");
		LinkedList<Flight> flights = new LinkedList<Flight>();
		flights.add(lny_to_lih);
		flights.add(lny_to_hnl);
		flights.add(hnl_to_lih);
		
		Planner p = new Planner(airports, flights);
		Itinerary i = p.Schedule("LNY", "LIH", "1347");
		i.print();
	}
	
	@Test
	public void testHawaii() {
		int numAirports = 0, numFlights = 0;
		int numCustomers = 0, numItineraries = 0;
		
		TextInputStream afs = new TextInputStream("testHawaii/Hawaiian-airports.txt");
		TextInputStream ffs = new TextInputStream("testHawaii/Hawaiian-flights-singles.txt");
		TextInputStream sfs = new TextInputStream("testHawaii/Hawaiian-customers100.txt");

		LinkedList<Airport> airportList = new LinkedList<Airport>();
		LinkedList<Flight> flightList = new LinkedList<Flight>();

		while (afs.ready()) {
			String portName = afs.readWord();
			String conTime = afs.readWord();
			Airport port = new Airport(portName, conTime);
			airportList.add(port);
			numAirports++;
		}

		while (ffs.ready()) {
			String src = ffs.readWord();
			String dst = ffs.readWord();
			String deptTime = ffs.readWord();
			String arvlTime = ffs.readWord();
			Flight flt = new Flight(src, dst, deptTime, arvlTime);
			flightList.add(flt);
			numFlights++;
		}
		
		Planner planner = new Planner(airportList, flightList);
		
		assertEquals(planner.getNumAirports(), numAirports);
		assertEquals(planner.getNumFlights(), numFlights);
		
		while (sfs.ready()) {
			String src = sfs.readWord();
			String dst = sfs.readWord();
			String deptTime = sfs.readWord();

			System.out.println(">> Source:" + src + " Destination:" + dst
					+ " Start_Time:" + deptTime);

			Itinerary ticket = planner.Schedule(src, dst, deptTime);
			if (ticket.isFound()) {
				numItineraries++;
				numCustomers++;
				ticket.print();
				System.out.println("Length: " + ticket.getLength());
			}
		}
		
		System.out.println();
		System.out.println("Failed to find a flight schedule for "
				+ (numCustomers - numItineraries) + " out of " + numCustomers
				+ " customers");
	}
}
