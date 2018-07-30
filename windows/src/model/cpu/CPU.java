package model.cpu;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import model.cpu.process.PCB;

public class CPU {
	private static CPU cpu = new CPU();
	private static int MAX_NUMBE_OF_PROCESS = 10;
	private CPURegisters registers;
	private ArrayList<LinkedBlockingQueue<PCB>> processQueues;

	private enum QUEUE_TYPE {
		BLANK, READY, BLOCKED, WATING
	}

	private CPU() {
		processQueues = new ArrayList<>(QUEUE_TYPE.values().length);
		for (QUEUE_TYPE type : QUEUE_TYPE.values()) {
			if (type == QUEUE_TYPE.WATING) {
				processQueues.add(new LinkedBlockingQueue<>());
			} else {
				processQueues.add(new LinkedBlockingQueue<>(MAX_NUMBE_OF_PROCESS));
			}
		}
	}

	public static CPU getInstance() {
		return cpu;
	}

	public boolean create(int[] instructions) {
		if(instructions == null || instructions.length == 0){
			return false;
		}

		return true;
	}

	public boolean create(String instructions) {
		return create(Compiler.compile(instructions));
	}
}
