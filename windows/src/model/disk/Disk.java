package model.disk;

import java.io.FileNotFoundException;
import java.util.ArrayList;


/**
 * @author BFELFISH
 */

public class Disk {
	/**
	 *  �����������
	 */
	
	final static int MAX_SPACE_OF_DISK = 256;
	/**
	 * ���̴�С
	 */
	final static int CAPACITY_OF_DISK_BLOCKS = 64;

	/**
	 *  ���̹�����̿�����
	 */
	
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

		/**
		 *  *****����ὫFAT������̿��0��1��2��3��
		 */

	}

	/**
	 * ��������Ƕ����̣�����һ���ַ�����������FAT��һ� ͨ�����ļ�Ŀ¼��õ��ļ�����ʼ��š�
	 */
	
	public String readFromDisk(int number) {
		String content = new String("");
		DiskBlock d;
		while (number != -1) {
			d = disks.get(number);
			try {
				content += d.readFronFile();
			} catch (FileNotFoundException e) {
				System.out.println("��ȡ����ʱ����");
			}
			number = FAT.getInstance().next(number);
		}
		return content;
	}

	public void writeToDisk(String content) {
		DiskBlock d;
		/**
		 *  ��������ļ���С
		 */
		int capacity = 0;

		int number = FAT.getInstance().changeFAT(capacity);
		int indexOfStart = 0;
		int indexOfEnd = CAPACITY_OF_DISK_BLOCKS;
		String ct;
		while (number != -1) {
			d = disks.get(number);
			ct = content.substring(indexOfStart, indexOfEnd);
			try {
				d.writeIntoFile(ct);
			} catch (FileNotFoundException e) {
				System.out.println("д�����ʱ����");
			}
			number = FAT.getInstance().next(number);

		}

	}

	/**
	 * ��ɾ���ļ�ʱ��ֻ��Ҫ�����ļ�Ŀ¼�����ļ�����ʼ��žͿ�����
	 */

	public void deleteFile(int startNum) {
		FAT.getInstance().recovery(startNum);
	}
}
