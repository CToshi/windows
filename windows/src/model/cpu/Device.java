package model.cpu;

public class Device {
	private char device_ID;
	private int remainTime;
	private boolean free;

	public Device(char device_ID) {
		this.device_ID = device_ID;
		this.free = true;
		remainTime = 0;
	}

	public char getDevice_ID() {
		return device_ID;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public int getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(int remainTime) {
		this.remainTime = remainTime;
	}
}
