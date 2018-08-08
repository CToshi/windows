package model.disk;

import java.util.Arrays;

public class FAT {
	/**
	 * ����Fat����
	 */
	private int[] fat;

	private static FAT f = new FAT();

	public  static FAT getInstance() {
		return f;
	}

	private FAT() {
		init();
	}

	/**
	 *  FAT��ʼ��
	 */
	private void init() {
		fat = new int[Disk.MAX_SPACE_OF_DISK];
		for (int i = 0; i < Disk.MAX_SPACE_OF_DISK; i++) {
			if (i < Disk.MAX_SPACE_OF_DISK / Disk.CAPACITY_OF_DISK_BLOCKS) {
				fat[i] = 0;
			} else {
				fat[i] = 0;
			}
		}
	}

	/**
	 *  �޸�FAT��capacityΪ���ļ��Ĵ�С��ͬʱ�����˴��̿�
	 */
	public int changeFAT(int capacity) {
	
		int number = 0;
		int last = 0;
		int startNum=-1;
		
		int numberOfDiskBlocks = capacity / Disk.CAPACITY_OF_DISK_BLOCKS;
		if (capacity % Disk.CAPACITY_OF_DISK_BLOCKS != 0) {
			numberOfDiskBlocks++;
		}
		
		
		/**
		 * i��Disk.MAX_SPACE_OF_DISK / Disk.CAPACITY_OF_DISK_BLOCKS��ʼ����ൽ255��j��0��ʼ��Ҫѭ��numberOfDiskBlocks�Σ����ѭ������ʱ��j��=numberOfDiskBlocks������̿ռ䲻�㣬��ʾ������󣬲������ѷ�����̡�
		 */
		for (int i = 0, j = 0; i < Disk.MAX_SPACE_OF_DISK
				&& j < numberOfDiskBlocks; i++, j++) {
			if (fat[i] != 0) {
				continue;
			} else {
				last = number;
				number = i;
				if (last == 0) {
					startNum = number;

				} else {
					fat[last] = number;
				}
			}
			
		}
		
		/**
		 * ���һ������Ϊ-1��
		 */
		fat[number] = -1;
		
		return startNum;
	}

	/**
	 *  ����
	 */
	public void recovery(int startNumber) {
		int number = startNumber;

		while (fat[number] != -1) {
			int temp = number;
			number = fat[number];
			fat[temp] = 0;
		}

		fat[number] = 0;
	}
	
	/**
	 * �÷������Fatĳһ�����һ��
	 */
	public int next(int number) {
		int num;
		num =fat[number];
		return num;
	}

	/**
	 * �÷������ؿ��д��̿�
	 * @return capacity    ���еĴ��̿���
	 */
	public int capacityOfDisk() {
		int capacity =0;
		for(int i = Disk.MAX_SPACE_OF_DISK/Disk.CAPACITY_OF_DISK_BLOCKS;i<Disk.MAX_SPACE_OF_DISK;i++) {
			if(fat[i]==0) {
				capacity++;
			}
		}
		return capacity;
	}
	
	/**
	 * ��дtoString���������������̡�
	 */
	@Override
	public String toString() {
		return  Arrays.toString(fat) ;
	}
	

	
	
}
