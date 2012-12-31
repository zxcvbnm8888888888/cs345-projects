import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Planner {
	
	private HashMap<String, Airport> airports = new HashMap<String, Airport>();
	private HashSet<Flight> flights = new HashSet<Flight>();
	private HashMap<String, HashSet<Flight>> graph = new HashMap<String, HashSet<Flight>>();

	public Planner(LinkedList<Airport> portList, LinkedList<Flight> fltList) {
		for(Airport a : portList) {
			airports.put(a.getID(), a);
			graph.put(a.getID(), new HashSet<Flight>());
		}
		
		for(Flight f : fltList) {
			flights.add(f);
			graph.get(f.getStart()).add(f);
		}
	}
	
	/**
	 * @return The number of Airports contained in this Planner
	 */
	public int getNumAirports() {
		return airports.size();
	}
	
	/**
	 * @return The number of Flights contained in this Planner
	 */
	public int getNumFlights() {
		return flights.size();
	}

	/**
	 * Schedule a series of flights from a starting airport to an ending airport. Prefer earliest arrival time.
	 * @param start The 3-character ID of the starting airport
	 * @param end The 3-character ID of the ending airport
	 * @param departure A 4-digit string 
	 * @return an Itinerary object containing a list of flights that will get you from point a to b
	 */
	public Itinerary Schedule(String start, String end, String departure) {
		HashSet<Airport> optimized = new HashSet<Airport>();
		HashMap<Airport, Itinerary> distances = new HashMap<Airport, Itinerary>();
		
		Airport startingPort = airports.get(start);
		if(startingPort == null) {
			//System.out.println("Can't find starting port: " + start);
			return new Itinerary(false);
		}
		Airport endingPort = airports.get(end);
		if(endingPort == null) {
			//System.out.println("Can't find ending port: " + end);
			return new Itinerary(false);
		}
		//System.out.println("Attempting to find a route from " + startingPort.getID() + " to " + endingPort.getID());
		
		distances.put(startingPort, new Itinerary(departure));
		
		Airport currentPort = startingPort;
		Itinerary currentRoute;
		int length;
		int min = 0;
		
		// While there exists some unvisited vertex
		while(optimized.size() < airports.size()) {
			
			// Pick out the next minimally-weighted path
			for(Airport a : airports.values()) {
				// If the airport has already been visited, continue
				if(optimized.contains(a))
					continue;
				
				//System.out.println("Looking at airport " + a.getID());
				
				currentRoute = distances.get(a);
				// The airport has some path, but that path has not been taken yet
				if(currentRoute != null) {
					//System.out.println("Found a path to " + a.getID());
					length = currentRoute.getLength();

					// The airport has a shorter path than any other airport we've looked at
					if(length < min || min == -1) {
						currentPort = a;
						min = length;
						if(min == -1) {
							//return new Itinerary(false);
							//System.out.println("The length of a route is -1 -- " +
							//		"there is an illegal flight somewhere in the itinerary");
						}
					}
				}
			}
			
			if(optimized.contains(currentPort)) {
				//System.out.println("Something went wrong, or we can't reach any more vertices.");
				return new Itinerary(false);
			}
			
			// Add the airport to the set of airports that have a shortest path
			optimized.add(currentPort);
			//System.out.println("Found a shortest path to " + currentPort.getID());
			
			// If the minimally-weighted path goes to the destination, end the algorithm
			if(currentPort.equals(endingPort)) {
				//System.out.println("Found path to airport: " + endingPort.getID());
				return distances.get(currentPort);
			}
			
			// Follow all flights outward from the current airport
			for(Flight f : graph.get(currentPort.getID())) {
				// As long as that flight goes to an airport that doesn't already have a shortest path
				if(optimized.contains(airports.get(f.getEnd())))
					continue;
				
				currentRoute = distances.get(currentPort);
				Itinerary potentialRoute = new Itinerary(currentRoute);
				potentialRoute.add(f, airports);
				
				// If we've never been to the airport before, add the route to the set of routes
				if(!distances.containsKey(airports.get(f.getEnd())))
					distances.put(airports.get(f.getEnd()), potentialRoute);
				// If this route arrives earlier than the old route, replace the old route
				else if(potentialRoute.getLength() < distances.get(airports.get(f.getEnd())).getLength())
					distances.put(airports.get(f.getEnd()), potentialRoute);
			}
			
			min = -1;
		}
		
		//System.out.println("Failed to find a route ? If we found some route it will be printed here:");
		if(distances.containsKey(end))
			distances.get(end).print();
		return new Itinerary(false);
	}

}
