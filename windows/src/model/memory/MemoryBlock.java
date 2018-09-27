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
	private boolean isEmpty;

	/**
	 * Ĭ��Ϊ�գ���isEmptyΪtrue
	 *
	 * @param startPosition
	 * @param length
	 */
	public MemoryBlock(int startPosition, int length) {
		this(startPosition, length, true);
	}

	public MemoryBlock(int startPosition, int length, boolean isEmpty) {
		this.startPosition = startPosition;
		this.length = length;
		this.isEmpty = isEmpty;
		// this(startPosition, length, "");
	}

	public MemoryBlock(MemoryBlock block, boolean isEmpty) {
		this(block.getStartPosition(), block.getLength(), isEmpty);
	}
	// public MemoryBlock(int startPosition, int length, String msg) {
	// this.startPosition = startPosition;
	// this.length = length;
	// this.message = msg;
	// }

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

	// public void setEmpty(boolean isEmpty) {
	// this.isEmpty = isEmpty;
	// }

	public boolean isEmpty() {
		return isEmpty;
	}

	/**
	 * �ж��Ƿ���ͬ���ڴ����򣬺����Ƿ�ռ��
	 *
	 * @param block
	 * @return
	 */
	public boolean equals(MemoryBlock block) {
		return block.getLength() == this.getLength() && block.getStartPosition() == this.getStartPosition();
	}

	public MemoryBlock add(int length) {
		return new MemoryBlock(getStartPosition(), getLength() + length, isEmpty);
	}
}
