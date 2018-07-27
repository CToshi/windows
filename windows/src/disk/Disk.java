package disk;

import java.util.ArrayList;

public class Disk {
	// 磁盘最大容量
	final static int MAX_SPACE_OF_DISK = 256;

	// 磁盘块大小
	final static int CAPACITY_OF_DISK_BLOCKS = 64;

	// 磁盘管理磁盘块数组
	ArrayList<DiskBlock> disks;

	public Disk() {
		super();
		disks = new ArrayList<>(MAX_SPACE_OF_DISK);

	}

	public void init() {

		DiskBlock d;
		for (int i = 0; i < MAX_SPACE_OF_DISK; i++) {
			d = new DiskBlock(i);
			disks.add(d);
		}
		// *****这里会将FAT存进磁盘块号0，1，2，3的

	}

}
