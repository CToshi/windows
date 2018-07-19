package model.cpu;

public class CPURegisters implements Cloneable {
	private enum PSW_TYPE {
		NOTHING, END, TIME_OUT, IO_INTERRUPT
	}

	private int AX;
	private int PC;
	private int IR;
	private PSW_TYPE PSW;

	public int getAX() {
		return AX;
	}

	public int getPC() {
		return PC;
	}

	public int getIR() {
		return IR;
	}

	public PSW_TYPE getPSW() {
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
}
