package model.cpu.process;

import model.memory.MemoryOccupy;

public class Process implements MemoryOccupy {
	private int length;
	private int[] instructions;
	private int index;

	public Process(int[] instructions) {

	}

	@Override
	public int length() {
		return length;
	}

}
