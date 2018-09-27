package model.disk;

/**
 * @author BFELFISH
 */

public class Disk {
	/**
	 * �����������
	 */

	public final static int MAX_SPACE_OF_DISK = 256;

	// ���̿��С
	public final static int CAPACITY_OF_DISK_BLOCKS = 64;

	/**
	 * ���̹���root
	 */
	private static Directory root;

	public Disk() {
		init();

	}

	//������Ŀ¼
	private static void init() {
		root = new Directory(null,"root", 0, 2, 1);
		root.setCanBeDeleted(false);
	}

	public Directory getRoot() {
		return root;
	}

	/**
	 * ���̸�ʽ��
	 * @return boolean:����ɾ���Ƿ�ɹ�
	 */
	public static boolean format() {
		boolean succeed=true;
		root.setCanBeDeleted(true);
		root.deleteFiles();
		init();
		return succeed;
	}
	
}

