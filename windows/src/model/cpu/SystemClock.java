package model.cpu;

import java.util.Timer;
import java.util.TimerTask;

public class SystemClock {
	private static SystemClock clock = new SystemClock();
	private int unit = 1000;
	private long initTime;

	private SystemClock() {
		initTime = System.currentTimeMillis();
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				DeviceManager.getInstance().work();
				CPU.getInstance().handle();
			}
		}, 0, unit);
	}

	public static SystemClock getInstance() {
		return clock;
	}

	public int getClock() {
		return (int) ((System.currentTimeMillis() - initTime) / unit);
	}

	public int getTimeUnit() {
		return unit;
	}
	// public void setTimeUnit(int unit) {
	// this.unit = unit;
	// }
}
