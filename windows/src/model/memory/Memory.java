package model.memory;

import java.util.LinkedList;

import application.Main;

/**
 *
 * @author ��֥��
 *
 */
public class Memory {
	private static Memory memory;
	private LinkedList<MemoryBlock> blocks;

	public static Memory getInstance() {
		return memory;
	}

	/**
	 * �ڴ��С����λ���ֽڣ�
	 */
	private static int MEMORY_SIZE = 512;
	static{
		memory = new Memory();
	}

	private Memory() {
		blocks = new LinkedList<>();
		blocks.add(new MemoryBlock(0, MEMORY_SIZE));
	}

	/**
	 * �����ڴ棬��С��occupy��length()ȷ��
	 *
	 * @param occupy
	 * @return �ɹ�ʱ������ռ���ڴ����ʼλ�úʹ�С��ʧ�ܷ���null
	 */
	public MemoryBlock allocate(MemoryOccupy occupy) {
		MemoryBlock result = null;
		for (int i = 0; i < blocks.size(); i++) {
			MemoryBlock block = blocks.get(i);
			if (block.isEmpty() && block.getLength() >= occupy.length()) {

				result = new MemoryBlock(block.getStartPosition(), occupy.length(), false);
				blocks.remove(i);
				int remainLength = block.getLength() - occupy.length();
				if (remainLength > 0) {
					blocks.add(i, new MemoryBlock(result.getStartPosition() + result.getLength(), remainLength));
				}
				blocks.add(i, result);
				break;
			}
		}
		return result;
	}

	/**
	 * �ͷ��ڴ�飬�ϲ����ڵ�δ��ռ�õ��ڴ��
	 * @param releaseBlock
	 */
	public void release(MemoryBlock releaseBlock) {
		Main.test("release", releaseBlock);
		for (int i = 0; i < blocks.size(); i++) {
			MemoryBlock block = blocks.get(i);
			if (block.equals(releaseBlock)) {
				MemoryBlock newBlock = new MemoryBlock(releaseBlock, true);
				MemoryBlock neighborBlock = null;
				int insertPosition = i;
				if (i != 0 && (neighborBlock = blocks.get(i - 1)).isEmpty()) {
					blocks.remove(i - 1);
					insertPosition = i - 1;
					newBlock = neighborBlock.add(newBlock.getLength());
				}
				if (insertPosition + 1 != blocks.size() && (neighborBlock = blocks.get(insertPosition + 1)).isEmpty()) {
					blocks.remove(insertPosition + 1);
					newBlock = newBlock.add(neighborBlock.getLength());
				}
				blocks.remove(insertPosition);
				blocks.add(insertPosition, newBlock);
				return;
			}
		}
		try {
			throw new Exception("�ͷ��ڴ����");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @return �����ڴ�ռ�����
	 */
	public MemoryBlock[] listStatus() {
		return blocks.toArray(new MemoryBlock[0]);
		// return (MemoryBlock[]) blocks.stream().filter(block ->
		// !block.isEmpty()).toArray();
	}
	public static int getMEMORY_SIZE() {
		return MEMORY_SIZE;
	}
}
