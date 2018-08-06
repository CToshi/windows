package model.cpu.process;

import model.cpu.CPURegisters;
import model.memory.MemoryBlock;

public class PCB {
	private int id;
	private STATE state;
	private MemoryBlock memoryBlock;

	public enum STATE {
		RUNNING, READY, BLOCKED,
	};

	public PCB(int id, CPURegisters registers, MemoryBlock memoryBlock) {
		this.id = id;
		this.state = STATE.READY;
		this.registers = registers;
		this.memoryBlock = memoryBlock;
	}

	private CPURegisters registers;

	public int getID() {
		return id;
	}

	public CPURegisters getRegisters() {
		return registers;
	}

	public STATE getState() {
		return state;
	}

	public MemoryBlock getMemoryBlock() {
		return memoryBlock;
	}
}
