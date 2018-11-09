package model.cpu.process;

import model.cpu.CPURegisters;
import model.memory.MemoryBlock;

/**
 *
 * ÄÚ´æ¿ØÖÆ¿é
 * @author »ÆÖ¥ÁÖ
 *
 */
public class PCB {
	private int id;
	private MemoryBlock memoryBlock;
	private CPURegisters registers;
	private ProcessCode code;

	public PCB(int id, MemoryBlock memoryBlock, ProcessCode code) {
		this.id = id;
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

	public MemoryBlock getMemoryBlock() {
		return memoryBlock;
	}

	public ProcessCode getCode() {
		return code;
	}

	public void setRegisters(CPURegisters registers) {
		this.registers = registers;
	}
}
