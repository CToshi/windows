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
	private char deviceID;
	private int deviceTime;

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
		// timeLeft--;
		int ins = code.getIns();

		code.toNext();
		if (ins < 100) {
			registers = registers.setAX(ins);
		} else if (ins == 100) {
			registers = registers.increase();
		} else if (ins == 101) {
			registers = registers.decreace();
		} else if (111 <= ins && ins <= 139) {
			registers = registers.setPSW(PSW_Type.IO_INTERRUPT);
			String inst = Compiler.decode(ins);
			deviceID = inst.charAt(1);
			deviceTime = Integer.valueOf(inst.substring(2));
		} else {
			registers = registers.setPSW(PSW_Type.END);
		}
		if (timeLeft <= 0) {
			registers = registers.setPSW(PSW_Type.TIME_OUT);
		}
	}

	public int getTimeLeft() {
		return timeLeft;
	}
	public char getDeviceID(){
		return deviceID;
	}
	public int getDeviceTime() {
		return deviceTime;
	}
}
