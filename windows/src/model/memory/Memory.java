package model.memory;

/**
 *
 * @author ��֥��
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
	 * �����ڴ棬��С��occupy��length()ȷ��
	 *
	 * @param occupy
	 * @return �ɹ�ʱ������ռ���ڴ����ʼλ�úʹ�С��ʧ�ܷ���null
	 */
	public MemoryBlock allocate(MemoryOccupy occupy) {
		return null;
	}

	/**
	 * �ͷ�һ����ռ�õ��ڴ��
	 *
	 * @param block
	 */
	public void release(MemoryBlock block) {

	}

	/**
	 *
	 * @return �����ڴ�ռ�����
	 */
	public MemoryBlock[] listStatus() {
		return null;
	}
}
