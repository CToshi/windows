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
	 * @return Files :����һ���ļ�������ļ�Ϊnull���������ʧ��
	 */
	public Files createFile() {
		Files f=null;
		
		// ����ļ������ļ���Ŀ�Ƿ��ѳ������ֵ
		if (!isFull()) {
			// �������Ƿ�����
			if (FAT.getInstance().capacityOfDisk()> 0) {
				int startNum = FAT.getInstance().changeFAT(8);
				// Ĭ�����ɿ�д�ҿհ׵��ļ�
				f = new Files(this, ("��" + files.size()), "e", 8, startNum, 1, "");
				this.files.add(f);
			} 
		}
		return f;
	}

	/**
	 * �÷���Ϊ�ڸ�Ŀ¼�´����ļ���
	 * 
	 * @return Directory:�����ļ��У����Ϊnull�򴴽�ʧ��
	 */
	public Directory createDirectory() {
		Directory f=null;
		if (!isFull()) {
			// �������Ƿ�����
			if (FAT.getInstance().capacityOfDisk()>0) {
				int startNum = FAT.getInstance().changeFAT(8);
				// Ĭ�����ɿ�д�ҿհ׵��ļ�
				 f = new Directory(this, ("��" + files.size()), 8, startNum, 1);
				this.files.add(f);
			} 
		}
		return f;
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
			FAT.getInstance().recovery(this.startNum);
		}else {
			succeed=false;
		}
		
		return succeed;
	}

	@Override
	public boolean changeAttributes(int attributes) {
		boolean succeed =true;
		for(FileItem f:files) {
			if(!FileItem.isDirectory(f)) {
				Files f2 = (Files)f;
				f2.changeAttributes(attributes);
			}
		}
		return succeed;
	}
	
	
}
