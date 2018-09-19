package model.disk;

/**
 * @author BFELFISH
 */

public class Disk {
	/**
	 * 磁盘最大容量
	 */

	public final static int MAX_SPACE_OF_DISK = 256;

	// 磁盘块大小
	public final static int CAPACITY_OF_DISK_BLOCKS = 64;

	/**
	 * 磁盘管理root
	 */
	private Directory root;

	public Disk() {
		init();

	}

	//创建根目录
	private void init() {
		root = new Directory(null,"root", 0, 2, 1);
		root.setCanBeDeleted(false);
	}

	public Directory getRoot() {
		return root;
	}

	
}

