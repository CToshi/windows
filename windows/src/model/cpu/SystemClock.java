package model.cpu;

public class SystemClock {
	private static SystemClock clock = new SystemClock();
	private int unit = 1000;
	private long initTime;

	private SystemClock() {
		initTime = System.currentTimeMillis();
	}

	public SystemClock getInstance() {
		return clock;
	}

	public int getClock() {
		return (int) ((System.currentTimeMillis() - initTime) / unit);
	}

	public void setTimeUnit(int unit) {
		this.unit = unit;
	}
}
