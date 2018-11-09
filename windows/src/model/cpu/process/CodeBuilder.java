package model.cpu.process;

import java.util.Random;

public class CodeBuilder {
	private enum CodeType {
		Assignment, Increase, Decrease, IO
	}

	/**
	 *
	 * @return 随机指令，包括赋值，自增，自减，设备占用
	 */
	private static String getRandomCode() {
		Random random = new Random();
		CodeType[] codeTypes = CodeType.values();
		CodeType type = codeTypes[random.nextInt(codeTypes.length)];
		String result = "";
		switch (type) {
		case Assignment:
			result = "x=" + Integer.valueOf(random.nextInt(100)).toString();
			break;
		case Increase:
			result = "x++";
			break;
		case Decrease:
			result = "x--";
			break;
		case IO:
			char device = "ABC".charAt(random.nextInt(3));
			char time = (char) (random.nextInt(9) + '1');
			result = "!" + device + time;
			break;
		}

		return result;
	}

	public static String getRandomCodes() {
		int length = new Random().nextInt(10);
		String result = "";
		for (int i = 0; i < length; i++) {
			if (i != 0){
				result += " ";
			}
			result += getRandomCode();
		}
		return result;
	}
}
