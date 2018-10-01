package model.disk;

/**
 * @author BFELFISH
 */

public class Disk {
	/**
	 * 磁盘最大容量
	 */

	public final  int MAX_SPACE_OF_DISK = 256;

	// 磁盘块大小
	public final  int CAPACITY_OF_DISK_BLOCKS = 64;

	/**
	 * 磁盘管理root
	 */
	private  Directory root;

	private static Disk disk=new Disk();
	
	public static Disk getInstance() {
		return disk;
	}
	public Disk() {
		init();

	}

	//创建根目录
	private  void init() {
		root = new Directory(null,"root", 0, 2, 1);
		root.setCanBeDeleted(false);
	}

	public  Directory getRoot() {
		return root;
	}

	/**
	 * 磁盘格式化
	 * @return boolean:返回删除是否成功
	 */
	public  boolean format() {
		boolean succeed=true;
		root.setCanBeDeleted(true);
		root.deleteFiles();
		return succeed;
	}
	
}

