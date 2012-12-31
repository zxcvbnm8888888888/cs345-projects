public class Airport {

	private String id;
	private int minTime;
	
	public Airport(String port, String connectTime) {
		id = port;
		minTime = Integer.parseInt(connectTime.substring(0,2))*60 + Integer.parseInt(connectTime.substring(2,4));
	}

	public void print() {
		System.out.println(id + " " + minTime);
	}

	public String getID() {
		return id;
	}
	
	/**
	 * @return The minimum time between connections in minutes.
	 */
	public int getMinTime() {
		return minTime;
	}

}
