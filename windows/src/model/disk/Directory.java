package model.disk;
/***
 * 
 * @author jiaru
 *����Ϊ�ļ����࣬����Ŀ¼���⣬���й������8���ļ����ļ���
 */

import java.util.ArrayList;

public class Directory extends FileItem {

	private final int MAX_NUM_OF_FILES = 8;
	private ArrayList<FileItem> files;

	public Directory(Directory father, String fileName, int capacity, int startNum, int attributes) {
		super(father, fileName, "", capacity, startNum, attributes);
		files = new ArrayList<>();
	}

	/**
	 * �÷��������жϸ��ļ����Ƿ��ѿ�
	 * 
	 * @return boolean:�Ƿ�����
	 */
	public boolean isFull() {
		boolean full = true;
		if (files.size() < MAX_NUM_OF_FILES) {
			full = false;
		}
		return full;
	}

	/**
	 * �÷���Ϊ�ڸ�Ŀ¼�´����ļ�
	 * 
	 * @return boolean:���ش����Ƿ�ɹ�
	 */
	public boolean creatFile() {
		boolean succeed = true;
		// ����ļ������ļ���Ŀ�Ƿ��ѳ������ֵ
		if (!isFull()) {
			// �������Ƿ�����
			if (FAT.getInstance().capacityOfDisk() <= 0) {
				succeed = false;
			} else {
				int startNum = FAT.getInstance().changeFAT(8);
				// Ĭ�����ɿ�д�ҿհ׵��ļ�
				Files f = new Files(this, ("�½��ļ�" + files.size()), "e", 8, startNum, 1, "");
				this.files.add(f);
			}
		}
		return succeed;
	}

	/**
	 * �÷���Ϊ�ڸ�Ŀ¼�´����ļ���
	 * 
	 * @return boolean:���ش����Ƿ�ɹ�
	 */
	public boolean creatDirectory() {
		boolean succeed = true;
		if (!isFull()) {
			// �������Ƿ�����
			if (FAT.getInstance().capacityOfDisk() <= 0) {
				succeed = false;
			} else {
				int startNum = FAT.getInstance().changeFAT(8);
				// Ĭ�����ɿ�д�ҿհ׵��ļ�
				Directory f = new Directory(this, ("�½��ļ���" + files.size()), 8, startNum, 1);
				this.files.add(f);
			}
		}
		return succeed;
	}

	

	/**
	 * �ж��ļ��Ƿ��Ѿ�����
	 * 
	 * @param name��String
	 * @return boolean���Ƿ����
	 */

	public boolean isExistedName(String name) {
		boolean existed = false;
		for (FileItem f : files) {
			if (name.equals(f.getFileName())) {
				existed = true;
			}
		}
		return existed;
	}

	/**
	 * ɾ���ļ����µ�ĳ���ļ�����Ҫ�Ƕ��ļ��е��������
	 * @return ɾ���ɹ�
	 */
	public boolean removeFiles(FileItem f) {
		boolean succeed =true;
		files.remove(f);
		return succeed;
	}

	/**
	 * �÷���Ϊɾ�������ļ���
	 * @return ɾ���Ƿ�ɹ�
	 */
	@Override
	public boolean deleteFiles() {
		boolean succeed =true;
		if(this.canBeDeleted) {
			int num=files.size();
			for(int i=0;i<num;i++) {
				files.get(0).deleteFiles();
			}
			files.clear();
			fatherFile.removeFiles(this);
			FAT.getInstance().changeFAT(this.startNum);
		}else {
			succeed=false;
		}
		
		return succeed;
	}

}
