package model.disk;

/**
 * @author BFELFISH
 */

public class Disk {
	/**
	 * �����������
	 */

	public final  int MAX_SPACE_OF_DISK = 256;

	// ���̿��С
	public final  int CAPACITY_OF_DISK_BLOCKS = 64;

	/**
	 * ���̹���root
	 */
	private  Directory root;

	private static Disk disk=new Disk();
	
	public static Disk getInstance() {
		return disk;
	}
	public Disk() {
		init();

	}

	//������Ŀ¼
	private  void init() {
		root = new Directory(null,"root", 0, 2, 1);
		root.setCanBeDeleted(false);
	}

	public  Directory getRoot() {
		return root;
	}

	/**
	 * ���̸�ʽ��
	 * @return boolean:����ɾ���Ƿ�ɹ�
	 */
	public  boolean format() {
		boolean succeed=true;
		root.setCanBeDeleted(true);
		root.deleteFiles();
		return succeed;
	}
	
}

