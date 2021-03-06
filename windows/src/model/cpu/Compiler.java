package model.cpu;

import java.util.Arrays;
import java.util.LinkedList;

import application.Main;
import model.cpu.process.ProcessCode;

public class Compiler {
	private static int END_CODE = 200;

	/**
	 *
	 * @param instructions 保存了多个指令的字符串，指令间用空格或回车或制表符隔开
	 * @return
	 */
	public static ProcessCode compile(String instructions) {
		String[] inss = instructions.split("[\\s]");
		LinkedList<Integer> list = new LinkedList<>();
		boolean compileError = false;
		for (String ins : inss) {
			if (ins.length() > 0) {
				int res = encode(ins);
				if (res == 255) {
					compileError = true;
					Main.test("fail", ins);
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
	 * x=? --> ?, 且?属于[0,99]
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
				if (result >= 100) {
					result = 255;
				}
			} catch (NumberFormatException e) {
			}
		} else if (code.equals("x++")) {
			result = 100;
		} else if (code.equals("x--")) {
			result = 101;
		} else if (code.startsWith("!") && code.length() == 3) {
			char deviceID = code.charAt(1);
			if('a' <= deviceID && deviceID <= 'z') {
				deviceID -= 'a' + 'A';
			}
			int time = Integer.valueOf(code.substring(2));
			if ('A' <= deviceID && deviceID <= 'C' && time > 0) {
				try {
					result = 110 + (code.charAt(1) - 'A') * 10 + time;
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

	public static String decode(int code) {
		if (0 <= code && code <= 99) {
			return "x=" + String.valueOf(code);
		} else if (code == 100) {
			return "x++";
		} else if (code == 101) {
			return "x--";
		} else if (111 <= code && code <= 139) {
			code -= 110;
			return "!" + (char) ('A' + code / 10) + String.valueOf(code % 10);
		} else if (code == END_CODE) {
			return "end";
		}
		return "";
	}

	public static String getExeFileContent(String txtFileContent) {
		ProcessCode code = compile(txtFileContent);
		if (code == null) {
			return null;
		}
		return code.toString();
	}
}
