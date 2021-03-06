package model.cpu;

public class CPURegisters implements Cloneable {
	public enum PSW_Type {
		NOTHING, END, TIME_OUT, IO_INTERRUPT
	}

	private int AX;
	private PSW_Type PSW;

	public CPURegisters() {
		PSW = PSW_Type.NOTHING;
	}

	public int getAX() {
		return AX;
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
		return this.setAX(AX + 1);
	}

	public CPURegisters decreace() {
		return this.setAX(AX - 1);
	}

	public CPURegisters setPSW(PSW_Type PSW) {
		CPURegisters registers = clone();
		registers.PSW = PSW;
		return registers;
	}

}
