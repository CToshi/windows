package disk;

import java.util.ArrayList;

public class Disk {
	// �����������
	final static int MAX_SPACE_OF_DISK = 256;

	// ���̿��С
	final static int CAPACITY_OF_DISK_BLOCKS = 64;

	// ���̹�����̿�����
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
		// *****����ὫFAT������̿��0��1��2��3��

	}

}
