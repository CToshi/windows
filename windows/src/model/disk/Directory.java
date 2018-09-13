package model.disk;
/***
 * 
 * @author jiaru
 *该类为文件夹类，除了目录项外，还有管理最多8个文件或文件夹
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
	 * 该方法用于判断该文件夹是否已空
	 * 
	 * @return boolean:是否满了
	 */
	public boolean isFull() {
		boolean full = true;
		if (files.size() < MAX_NUM_OF_FILES) {
			full = false;
		}
		return full;
	}

	/**
	 * 该方法为在该目录下创建文件
	 * 
	 * @return boolean:返回创建是否成功
	 */
	public boolean creatFile() {
		boolean succeed = true;
		// 检查文件夹下文件数目是否已超过最大值
		if (!isFull()) {
			// 检查磁盘是否已满
			if (FAT.getInstance().capacityOfDisk() <= 0) {
				succeed = false;
			} else {
				int startNum = FAT.getInstance().changeFAT(8);
				// 默认生成可写且空白的文件
				Files f = new Files(this, ("新建文件" + files.size()), "e", 8, startNum, 1, "");
				this.files.add(f);
			}
		}
		return succeed;
	}

	/**
	 * 该方法为在该目录下创建文件夹
	 * 
	 * @return boolean:返回创建是否成功
	 */
	public boolean creatDirectory() {
		boolean succeed = true;
		if (!isFull()) {
			// 检查磁盘是否已满
			if (FAT.getInstance().capacityOfDisk() <= 0) {
				succeed = false;
			} else {
				int startNum = FAT.getInstance().changeFAT(8);
				// 默认生成可写且空白的文件
				Directory f = new Directory(this, ("新建文件夹" + files.size()), 8, startNum, 1);
				this.files.add(f);
			}
		}
		return succeed;
	}

	

	/**
	 * 判断文件是否已经存在
	 * 
	 * @param name：String
	 * @return boolean：是否存在
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
	 * 删除文件夹下的某个文件，主要是对文件夹的数组操作
	 * @return 删除成功
	 */
	public boolean removeFiles(FileItem f) {
		boolean succeed =true;
		files.remove(f);
		return succeed;
	}

	/**
	 * 该方法为删除整个文件夹
	 * @return 删除是否成功
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
