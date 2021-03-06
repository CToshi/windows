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
		return Math.max(20, instructions.length * 5);
	}

	/**
	 *
	 * @return 当前指令
	 */
	public int getIns() {
		if (index < instructions.length) {
			return instructions[index];
		}
		return Compiler.getEndCode();
	}

	/**
	 * 转到下一条指令并将其返回
	 *
	 * @return 下一条指令
	 */
	public int toNext() {
		index++;
		return getIns();
	}


	@Override
	public String toString() {
		String result = "";
		for(int inst:instructions){
			result += Compiler.decode(inst) + '\n';
		}
		return result;
	}
}
