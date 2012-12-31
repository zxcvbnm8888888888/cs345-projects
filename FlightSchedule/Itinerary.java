import java.util.HashMap;
import java.util.LinkedList;

public class Itinerary {

	private LinkedList<Flight> flights;
	private int departure;
	private boolean found = true;
	private int length = 0;
	
	public Itinerary(String departure) {
		this.departure = (Integer.parseInt(departure.substring(0,2))*60 + Integer.parseInt(departure.substring(2,4))) % 1440;
		flights = new LinkedList<Flight>();
	}

	@SuppressWarnings("unchecked")
	public Itinerary(Itinerary toDuplicate) {
		this.departure = toDuplicate.getDeparture();
		flights = (LinkedList<Flight>) toDuplicate.getFlights().clone();
		length = toDuplicate.getLength();
	}
	
	public Itinerary(boolean found) {
		flights = new LinkedList<Flight>();
		this.found = found;
	}

	/**
	 * Add a flight to the current itinerary.
	 */
	public void add(Flight f, HashMap<String, Airport> airports) {
		if(flights.size() == 0) {
			flights.add(f);
			if(f.getDeparture() < departure)
				length += 24*60;
			length += f.getTime() + (f.getDeparture() - departure);
			return;
		}
		
		Flight prev = flights.getLast();
		flights.add(f);

		int currentTime = prev.getArrival();
		int minTime = airports.get(prev.getEnd()).getMinTime();

		// Add the wait time and the flight time to the length
		length += f.getTime() + (f.getDeparture() - currentTime);

		// If there is not enough wait time to take the flight today, have to wait a day to take it
		if ((f.getDeparture() - currentTime) < minTime) {
			length += 24 * 60;
			if (f.getDeparture() < currentTime)
				if (((f.getDeparture() + 24 * 60) - currentTime) < minTime)
					length += 24 * 60;
		}

		// If the flight left before you got to the airport, have to wait a day to take it
		else if (f.getDeparture() < currentTime)
			length += 24 * 60;
		
		return;
	}
	
	public boolean isFound() {
		return found;
	}
	
	public LinkedList<Flight> getFlights() {
		return flights;
	}
	
	/**
	 * @return Departure time in minutes. 1440 minutes in a day.
	 */
	public int getDeparture() {
		return departure;
	}
	
	public int getArrival() {
		if(flights.size() == 0)
			return departure;
		return flights.getLast().getArrival();
	}
	
	/**
	 * @return The total length of the trip.
	 */
	public int getLength() {
		return length;
	}

	public void print() {
		if(flights == null || found == false) {
			System.out.println("No Flight Schedule Found.");
			return;
		}
		for(Flight f : flights) {
			String depart = String.format("%02d%02d", f.getDeparture()/60, f.getDeparture()%60);
			String arrive = String.format("%02d%02d", f.getArrival()/60, f.getArrival()%60);
			System.out.print("[" + f.getStart() + "->" + f.getEnd()
					+ ":" + depart + "->" + arrive + "]");
		}
		System.out.println();
	}
	
	public String toString() {
		String result = "";
		for(Flight f : flights) {
			String depart = String.format("%02d%02d", f.getDeparture()/60, f.getDeparture()%60);
			String arrive = String.format("%02d%02d", f.getArrival()/60, f.getArrival()%60);
			result += "[" + depart + "->" + arrive
					+ ":" + f.getStart() + "->" + f.getEnd() + "]";
		}
		return result;
	}
	
	public Flight getLastFlight() {
		return flights.getLast();
	}
}
