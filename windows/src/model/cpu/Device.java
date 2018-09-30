package model.cpu;

import java.util.ArrayList;

import javafx.util.Pair;
import model.cpu.process.PCB;

public class Device {
	private char device_ID;
	private int remainTime;
	private boolean free;
	private int index_of_usingProcess;
	private PCB pcb;

	public Device(char device_ID, int index) {
		this.device_ID = device_ID;
		this.free = true;
		this.index_of_usingProcess = index;
		remainTime = 0;
	}

	public void run(ArrayList<Pair<Integer, Integer>> usingProcess) {
		if (!isFree()) {
			usingProcess.set(index_of_usingProcess, new Pair<Integer, Integer>(pcb.getID(), --remainTime));
			if (remainTime == 0) {
				DeviceManager.getInstance().release(this);
				DeviceManager.getInstance().occupy(device_ID);
			}
		}
	}

	public int getIndex_of_usingProcess() {
		return index_of_usingProcess;
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
