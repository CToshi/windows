package model.disk;

import java.io.FileNotFoundException;
import java.util.ArrayList;


/**
 * @author BFELFISH
 */

public class Disk {
	/**
	 *  磁盘最大容量
	 */
	
	final static int MAX_SPACE_OF_DISK = 256;
	/**
	 * 磁盘大小
	 */
	final static int CAPACITY_OF_DISK_BLOCKS = 64;

	/**
	 *  磁盘管理磁盘块数组
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
		 *  *****这里会将FAT存进磁盘块号0，1，2，3的
		 */

	}

	/**
	 * 这个方法是读磁盘，返回一个字符串，参数是FAT第一项。 通过查文件目录项得到文件的起始块号。
	 */
	
	public String readFromDisk(int number) {
		String content = new String("");
		DiskBlock d;
		while (number != -1) {
			d = disks.get(number);
			try {
				content += d.readFronFile();
			} catch (FileNotFoundException e) {
				System.out.println("读取磁盘时出错");
			}
			number = FAT.getInstance().next(number);
		}
		return content;
	}

	public void writeToDisk(String content) {
		DiskBlock d;
		/**
		 *  这里计算文件大小
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
				System.out.println("写入磁盘时出错");
			}
			number = FAT.getInstance().next(number);

		}

	}

	/**
	 * 当删除文件时，只需要给出文件目录项中文件的起始块号就可以了
	 */

	public void deleteFile(int startNum) {
		FAT.getInstance().recovery(startNum);
	}
}
