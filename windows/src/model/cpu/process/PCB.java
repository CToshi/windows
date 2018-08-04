package model.cpu.process;

import model.cpu.CPURegisters;
import model.memory.MemoryBlock;

public class PCB {
	private int id;
	private STATE state;
	private MemoryBlock memoryBlock;
	private CPURegisters registers;
	private ProcessCode code;

	public enum STATE {
		RUNNING, READY, BLOCKED,
	};

	public PCB(int id, MemoryBlock memoryBlock, ProcessCode code) {
		this.id = id;
		this.state = STATE.READY;
		this.registers = new CPURegisters();
		this.memoryBlock = memoryBlock;
		this.code = code;
	}

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

	public ProcessCode getCode() {
		return code;
	}
}
