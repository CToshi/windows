package model.cpu;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

public class SystemClock {
	private static SystemClock clock = new SystemClock();
	/**
	 * ϵͳʱ�����ڣ���λ�����룩
	 */
	private int unit = 1000;
	private long initTime;
	private LinkedBlockingQueue<Runnable> runnables;
	private Timer timer;
	private SystemClock() {
		initTime = System.currentTimeMillis();
		runnables = new LinkedBlockingQueue<>();
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				DeviceManager.getInstance().work();
				CPU.getInstance().work();
				for(Runnable runnable:runnables){
					runnable.run();
				}
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
	/**
	 * �رճ���ʱ����timer�߳�
	 */
	public void stop(){
		timer.cancel();
	}
	public void addEvent(Runnable runnable){
		try {
			runnables.put(runnable);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
