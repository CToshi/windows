package model.cpu;

public class CPURegisters implements Cloneable {
	public enum PSW_Type {
		NOTHING, END, TIME_OUT, IO_INTERRUPT
	}

	private int AX;
	private int PC;
	private int IR;
	private PSW_Type PSW;

	public CPURegisters() {
		PSW = PSW_Type.NOTHING;
	}

	public int getAX() {
		return AX;
	}

	public int getPC() {
		return PC;
	}

	public int getIR() {
		return IR;
	}

	public PSW_Type getPSW() {
		return PSW;
	}

	@Override
	public CPURegisters clone() {
		CPURegisters result = null;
		try {
			result = (CPURegisters) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return result;
	}

	public CPURegisters setAX(int AX) {
		CPURegisters registers = clone();
		registers.AX = (AX % 256 + 256) % 256;
		return registers;
	}

	public CPURegisters increase() {
		CPURegisters registers = clone();
		registers.setAX(AX + 1);
		return registers;
	}

	public CPURegisters decreace() {
		CPURegisters registers = clone();
		registers.setAX(AX - 1);
		return registers;
	}

	public CPURegisters setPSW(PSW_Type PSW) {
		CPURegisters registers = clone();
		registers.PSW = PSW;
		return registers;
	}

	public CPURegisters setIR(int IR) {
		CPURegisters registers = clone();
		registers.IR = IR;
		return registers;
	}

	public CPURegisters setPC(int PC) {
		CPURegisters registers = clone();
		registers.PC = PC;
		return registers;
	}
}
