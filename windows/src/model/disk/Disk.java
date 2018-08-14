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
	 * ���̹����ļ�����
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
	 * ��ȡ�ļ����ݣ�������ʼ��ţ������ļ�����
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
	 * �½��ļ����޸��ļ��������ȼ����Ƿ񳬳��ռ䣬�����·�����̣����ļ�����ú󷵻��Ƿ�ɹ�����
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
	 * ��ɾ���ļ�ʱ��ֻ��Ҫ�����ļ�Ŀ¼�����ļ�����ʼ��žͿ�����
	 */

	public void deleteFile(int startNum) {
		FAT.getInstance().recovery(startNum);
	}

}
