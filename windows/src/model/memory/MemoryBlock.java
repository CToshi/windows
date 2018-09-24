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
	private boolean isEmpty;

	/**
	 * 默认为空，即isEmpty为true
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
	 * @return 规定 空串表示空闲区，否则表示占用的进程
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
	 * 判断是否是同个内存区域，忽略是否被占用
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
