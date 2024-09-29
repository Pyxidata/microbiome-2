package scripts.data;

public class Sample {
	public String name;
	public String genome;
	public int[] schedules;
	public long prevScheduledSpawnTime;
	
	public Sample(String name, String genome, int[] schedules) {
		this.name = name;
		this.genome = genome;
		this.schedules = schedules;
		prevScheduledSpawnTime = 0;
	}
}
