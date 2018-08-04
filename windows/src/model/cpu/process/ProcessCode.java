package model.cpu.process;

import model.cpu.Compiler;
import model.memory.MemoryOccupy;

public class ProcessCode implements MemoryOccupy {
	private int[] instructions;
	private int index;

	public ProcessCode(int[] instructions) {
		this.instructions = instructions;
	}

	@Override
	public int length() {
		return instructions.length;
	}

	public int getNext() {
		if (index < instructions.length) {
			return instructions[index++];
		}
		return Compiler.getEndCode();
	}

}
