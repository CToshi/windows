package model.cpu;

import model.cpu.process.PCB;

public class Device {
	private char device_ID;
	private int remainTime;
	private boolean free;
	private PCB pcb;

	public Device(char device_ID) {
		this.device_ID = device_ID;
		this.free = true;
		remainTime = 0;
	}

	public void run() {
		if (!isFree()) {
			remainTime--;
			if (remainTime == 0) {
				DeviceManager.getInstance().release(this);
				DeviceManager.getInstance().occupy(device_ID);
			}
		}
	}
	
	public PCB getPcb() {
		return pcb;
	}

	public void setPcb(PCB pcb) {
		this.pcb = pcb;
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
