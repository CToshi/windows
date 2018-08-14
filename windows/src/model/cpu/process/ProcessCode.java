package model.cpu.process;

import model.cpu.Compiler;
import model.memory.MemoryOccupy;

public class ProcessCode implements MemoryOccupy {
	private int[] instructions;
	private int index;

	public ProcessCode() {
		this(new int[0]);
	}

	public ProcessCode(int[] instructions) {
		this.instructions = instructions;
	}

	@Override
	public int length() {
		return instructions.length;
	}

	/**
	 *
	 * @return ��ǰָ��
	 */
	public int getIns() {
		if (index < instructions.length) {
			return instructions[index];
		}
		return Compiler.getEndCode();
	}

	/**
	 * ת����һ��ָ����䷵��
	 *
	 * @return ��һ��ָ��
	 */
	public int toNext() {
		index++;
		return getIns();
	}

}
