public class Flight {

	private String start;
	private String end;
	private int time; // Represent as minutes (1440 minutes in a day)
	private int departure;
	private int arrival;

	public Flight(String src, String dest, String stime, String dtime) {
		start = src;
		end = dest;
		departure = (Integer.parseInt(stime.substring(0, 2)) * 60 
				+ Integer.parseInt(stime.substring(2, 4))) % 1440;
		arrival = (Integer.parseInt(dtime.substring(0, 2)) * 60 
				+ Integer.parseInt(dtime.substring(2, 4))) % 1440;

		time = arrival - departure;
		if (departure > arrival)
			time += 24 * 60;

	}

	public void print() {
		System.out.println(start + " " + end + " " + " " + departure + " "
				+ arrival);
	}

	/**
	 * @return The string ID of the departure airport
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @return The string ID of the arrival airport
	 */
	public String getEnd() {
		return end;
	}

	/**
	 * @return The time of day in minutes that the flight leaves the ground.
	 */
	public int getDeparture() {
		return departure;
	}

	/**
	 * @return The total number of minutes the flight will take. There are 1440
	 *         minutes in a day.
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @return The time of day in minutes that the flight touches down.
	 */
	public int getArrival() {
		return arrival;
	}
}
