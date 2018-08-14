package model.memory;

/**
 *
 * @author 黄芝林
 *
 */
public class Memory {
	private static Memory memory = new Memory();

	public static Memory getInstance() {
		return memory;
	}

	private Memory() {

	}

	/**
	 * 分配内存，大小由occupy的length()确定
	 *
	 * @param occupy
	 * @return 成功时，返回占用内存的起始位置和大小，失败返回null
	 */
	public MemoryBlock allocate(MemoryOccupy occupy) {
		return null;
	}

	/**
	 * 释放一个已占用的内存块
	 *
	 * @param block
	 */
	public void release(MemoryBlock block) {

	}

	/**
	 *
	 * @return 返回内存占用情况
	 */
	public MemoryBlock[] listStatus() {
		return null;
	}
}
