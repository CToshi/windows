package model.memory;

/**
 * �ڴ�飬����ʼλ�úͳ���
 *
 * @author ��֥��
 *
 */
public class MemoryBlock {
	private int startPosition;
	private int length;
	private String message;

	public MemoryBlock(int startPosition, int length, String msg) {
		this.startPosition = startPosition;
		this.length = length;
		this.message = msg;
	}

	public int getStartPosition() {
		return startPosition;

	}

	public int getLength() {
		return length;
	}

	/**
	 *
	 * @return �涨 �մ���ʾ�������������ʾռ�õĽ���
	 */
	public String getMessage() {
		return message;
	}
}
