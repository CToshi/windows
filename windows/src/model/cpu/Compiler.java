package model.cpu;

import java.util.Arrays;
import java.util.LinkedList;

public class Compiler {
	public static int[] compile(String instructions) {
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
		int[] results = null;
		if (!compileError) {
			results = Arrays.stream(list.toArray(new Integer[0])).mapToInt(Integer::valueOf).toArray();
		}
		return results;
	}

	/**
	 * �������
	 * x=? --> ?,
	 * x++ --> 100,
	 * x-- --> 101,
	 * !?? --> x����[111, 139]��x%10 != 0, ����ʮλ��ʾ�豸����λ��ʾʱ��,
	 * end --> 200,
	 * ����ʧ�� --> 255,
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
		} else if (code.equals("end")){
			result = 200;
		}
		return result;
	}
}
