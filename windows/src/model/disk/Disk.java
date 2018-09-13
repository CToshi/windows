package model.disk;

import java.util.ArrayList;


/**
 * @author BFELFISH
 */

public class Disk {
	/**
	 * �����������
	 */

	final static int MAX_SPACE_OF_DISK = 256;

	// ���̿��С
	final static int CAPACITY_OF_DISK_BLOCKS = 64;

	/**
	 * ���̹���root
	 */
	Directory root;

	public Disk() {
		init();

	}

	//������Ŀ¼
	private void init() {
		root = new Directory(null,"root", 0, 2, 1);
		root.setCanBeDeleted(false);
	}

	
}

