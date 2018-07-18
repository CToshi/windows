package disk;

import java.util.ArrayList;

public class Disk {
	// 磁盘最大容量
	final static int MAX_SPACE_OF_DISK = 256;

	// 磁盘块大小
	final static int CAPACITY_OF_DISK_BLOCKS = 64;

	// 磁盘管理磁盘块数组
	ArrayList<DiskBlock> disks;

//	// 磁盘的文件分配表
//	int[] FAT;

	public Disk() {
		super();
		disks = new ArrayList<>(MAX_SPACE_OF_DISK);
//		FAT = new int[MAX_SPACE_OF_DISK];
	}

	public void init() {
		// 初始化FAT
//		for (int i = 0; i < MAX_SPACE_OF_DISK; i++) {
//			if (i < MAX_SPACE_OF_DISK / CAPACITY_OF_DISK_BLOCKS) {
//				FAT[i] = -1;
//			} else {
//				FAT[i] = 0;
//			}
//
//		}

		DiskBlock d;
		for (int i = 0; i < MAX_SPACE_OF_DISK; i++) {
			d = new DiskBlock(i);
			disks.add(d);
		}
		// *****这里会将FAT存进磁盘块号0，1，2，3的
	}

}
