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
				fat[i] = 0;
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
		
		
		/**
		 * i从Disk.MAX_SPACE_OF_DISK / Disk.CAPACITY_OF_DISK_BLOCKS开始，最多到255，j从0开始，要循环numberOfDiskBlocks次，如果循环结束时，j！=numberOfDiskBlocks，则磁盘空间不足，提示保存错误，并回收已分配磁盘。
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
		 * 最后一项内容为-1。
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

	/**
	 * 该方法返回空闲磁盘块
	 * @return capacity    空闲的磁盘块数
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
	 * 重写toString方法，方便存进磁盘。
	 */
	@Override
	public String toString() {
		return  Arrays.toString(fat) ;
	}
	

	
	
}
