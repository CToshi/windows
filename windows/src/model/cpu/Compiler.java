package model.cpu;

import java.util.Arrays;
import java.util.LinkedList;

import model.cpu.process.ProcessCode;

public class Compiler {
	private static int END_CODE = 200;

	public static ProcessCode compile(String instructions) {
		String[] inss = instructions.split("[\\s]");
		LinkedList<Integer> list = new LinkedList<>();
		boolean compileError = false;
		for (String ins : inss) {
			if (ins.length() > 0) {
				int res = encode(ins);
				if (res == 255) {
					compileError = true;
					break;
				}
				list.add(res);
			}
		}
		ProcessCode code = null;
		if (!compileError) {
			code = new ProcessCode(Arrays.stream(list.toArray(new Integer[0])).mapToInt(Integer::valueOf).toArray());
		}
		return code;
	}

	/**
	 * 编码规则：
	 * x=? --> ?,
	 * x++ --> 100,
	 * x-- --> 101,
	 * !?? --> x属于[111, 139]且x%10 != 0, 其中十位表示设备，个位表示时间,
	 * end --> END_CODE,
	 * 编码失败 --> 255,
	 *
	 * @param code
	 * @return
	 */
	private static int encode(String code) {
		int result = 255;
		if (code.startsWith("x=")) {
			try {
				result = Integer.valueOf(code.substring(2));
			} catch (NumberFormatException e) {
			}
		} else if (code.equals("x++")) {
			result = 100;
		} else if (code.equals("x--")) {
			result = 101;
		} else if (code.startsWith("!") && code.length() == 3) {
			if ('A' <= code.charAt(1) && code.charAt(1) <= 'B') {
				try {
					result = 100 + code.charAt(1) - 'A' + Integer.valueOf(code.substring(2));
				} catch (NumberFormatException e) {
				}
			}
		} else if (code.equals("end")) {
			result = END_CODE;
		}
		return result;
	}

	public static int getEndCode() {
		return END_CODE;
	}
}
