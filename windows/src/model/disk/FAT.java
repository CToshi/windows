package model.disk;

public class FAT {
	// 管理Fat数组
	private int[] Fat;

	private static FAT f = new FAT();

	private static FAT gitInstance() {
		return f;
	}

	private FAT() {
		init();
	}

	// FAT初始化
	private void init() {
		Fat = new int[Disk.MAX_SPACE_OF_DISK];
		for (int i = 0; i < Disk.MAX_SPACE_OF_DISK; i++) {
			if (i < Disk.MAX_SPACE_OF_DISK / Disk.CAPACITY_OF_DISK_BLOCKS) {
				Fat[i] = -1;
			} else {
				Fat[i] = 0;
			}
		}
	}

	// 修改FAT，capacity为该文件的大小，同时分配了磁盘块
	public void changeFAT(int capacity) {
		int number = 0;
		int last = 0;
		int numberOfDiskBlocks = capacity / Disk.CAPACITY_OF_DISK_BLOCKS;

		if (capacity % Disk.CAPACITY_OF_DISK_BLOCKS != 0) {
			numberOfDiskBlocks++;
		}

		for (int i = Disk.MAX_SPACE_OF_DISK / Disk.CAPACITY_OF_DISK_BLOCKS, j = 0; i < Disk.MAX_SPACE_OF_DISK
				&& j < numberOfDiskBlocks; i++, j++) {
			if (Fat[i] != 0) {
				continue;
			} else {
				last = number;
				number = i;
				if (number == 0) {
					// *****number为起始磁盘块号，记录在文件目录项中，以方便后面以这个为起点将文件写到磁盘块中

				} else {
					Fat[last] = number;
				}
			}
		}
	}

	// 回收
	public void recovery(int startNumber) {
		int number = startNumber;

		while (Fat[number] != -1) {
			int temp = number;
			number = Fat[number];
			Fat[temp] = 0;
		}

		Fat[number] = 0;
	}
}
