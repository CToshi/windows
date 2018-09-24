package model.cpu;

import model.cpu.CPURegisters.PSW_Type;
import model.cpu.process.ProcessCode;

/**
 * 负责进程指令执行
 *
 * @author 黄芝林
 *
 */
public class InsExecutor {
	private ProcessCode code;
	private CPURegisters registers;
	private int timeLeft;

	public InsExecutor() {
		code = new ProcessCode();
		registers = new CPURegisters();
	}

	public void init(ProcessCode code, CPURegisters registers, int times) {
		this.code = code;
		this.registers = registers;
		timeLeft = times;
	}

	public int getIns() {
		return code.getIns();
	}

	public CPURegisters getRegisters() {
		return registers;
	}

	public void execute() {
		registers = registers.setPSW(PSW_Type.NOTHING);
		timeLeft = Math.max(0, timeLeft - 1);
		int ins = code.getIns();
		if (timeLeft <= 0) {
			registers = registers.setPSW(PSW_Type.TIME_OUT);
		} else {
			code.toNext();
			if (ins < 100) {
				registers = registers.setAX(ins);
			} else if (ins == 100) {
				registers = registers.increase();
			} else if (ins == 101) {
				registers = registers.decreace();
			} else if (111 <= ins && ins <= 139) {
				registers = registers.setPSW(PSW_Type.IO_INTERRUPT);
			} else {
				registers = registers.setPSW(PSW_Type.END);
			}
		}
	}

	public int getTimeLeft() {
		return timeLeft;
	}
}
