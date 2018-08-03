package model.disk;

import java.util.Arrays;

public class FAT {
	/**
	 * 管理Fat数组
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
	 *  FAT初始化
	 */
	private void init() {
		fat = new int[Disk.MAX_SPACE_OF_DISK];
		for (int i = 0; i < Disk.MAX_SPACE_OF_DISK; i++) {
			if (i < Disk.MAX_SPACE_OF_DISK / Disk.CAPACITY_OF_DISK_BLOCKS) {
				fat[i] = -1;
			} else {
				fat[i] = 0;
			}
		}
	}

	/**
	 *  修改FAT，capacity为该文件的大小，同时分配了磁盘块
	 */
	public int changeFAT(int capacity) {
		int number = 0;
		int last = 0;
		int startNum=-1;
		int numberOfDiskBlocks = capacity / Disk.CAPACITY_OF_DISK_BLOCKS;

		if (capacity % Disk.CAPACITY_OF_DISK_BLOCKS != 0) {
			numberOfDiskBlocks++;
		}

		for (int i = Disk.MAX_SPACE_OF_DISK / Disk.CAPACITY_OF_DISK_BLOCKS, j = 0; i < Disk.MAX_SPACE_OF_DISK
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
		 * 最后一项内容为-1
		 */
		fat[number] = -1;
		return startNum;
	}

	/**
	 *  回收
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
	 * 该方法获得Fat某一项的下一项
	 */
	public int next(int number) {
		int num;
		num =fat[number];
		return num;
	}

	@Override
	public String toString() {
		return  Arrays.toString(fat) ;
	}
	

	
}
