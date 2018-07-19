package model.memory;

/**
 * 内存块，存起始位置和长度
 *
 * @author 黄芝林
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
	 * @return 规定 空串表示空闲区，否则表示占用的进程
	 */
	public String getMessage() {
		return message;
	}
}
