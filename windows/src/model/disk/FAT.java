package model.disk;

public class FAT {
	// ����Fat����
	private int[] Fat;

	private static FAT f = new FAT();

	private static FAT gitInstance() {
		return f;
	}

	private FAT() {
		init();
	}

	// FAT��ʼ��
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

	// �޸�FAT��capacityΪ���ļ��Ĵ�С��ͬʱ�����˴��̿�
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
					// *****numberΪ��ʼ���̿�ţ���¼���ļ�Ŀ¼���У��Է�����������Ϊ��㽫�ļ�д�����̿���

				} else {
					Fat[last] = number;
				}
			}
		}
	}

	// ����
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
