package model.disk;

import java.util.ArrayList;

/**
 * @author BFELFISH
 */

public class Disk {
	/**
	 * 磁盘最大容量
	 */

	final static int MAX_SPACE_OF_DISK = 256;

	// 磁盘块大小
	final static int CAPACITY_OF_DISK_BLOCKS = 64;

	/**
	 * 磁盘管理文件数组
	 */

	ArrayList<Files> disks;

	public Disk() {
		super();
		init();

	}

	private void init() {
		disks = new ArrayList<>(MAX_SPACE_OF_DISK);
	}

	/**
	 * 读取文件内容，给出起始块号，返回文件内容
	 * 
	 * @param number
	 * @return content
	 */
	public String readFileFromDisk(int number) {
		Files f = new Files();
		for (int i = 0; i < disks.size(); i++) {
			f = disks.get(i);
			if (f.getFileItem().getStartNum() == number) {
				break;
			}
		}
		return f.getContent();
	}

	/**
	 * 新建文件或修改文件，都是先计算是否超出空间，再重新分配磁盘，将文件保存好后返回是否成功参数
	 * 
	 * @param fileName
	 * @param content
	 * @return IsSucceed
	 */
	public boolean writeIntoDisk(String fileName, String content) {
		boolean IsSucceed = false;
		int capacity = content.getBytes().length;
		if (capacity <= FAT.getInstance().capacityOfDisk() * CAPACITY_OF_DISK_BLOCKS) {
			int startNum = FAT.getInstance().changeFAT(capacity);
			Files f = new Files(fileName,capacity,startNum,0,content);
			disks.add(f);
			IsSucceed = true;
		}
		return IsSucceed;

	}

	/**
	 * 当删除文件时，只需要给出文件目录项中文件的起始块号就可以了
	 */

	public void deleteFile(int startNum) {
		FAT.getInstance().recovery(startNum);
	}

}
