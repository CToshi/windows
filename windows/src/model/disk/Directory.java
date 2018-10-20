package model.disk;
/***
 *
 * @author jiaru
 *该类为文件夹类，除了目录项外，还有管理最多8个文件或文件夹
 */

import java.util.ArrayList;

public class Directory extends FileItem implements Cloneable{

	private final int MAX_NUM_OF_FILES = 8;
	private ArrayList<FileItem> files;

	public Directory(Directory father, String fileName, int capacity, int startNum, int attributes) {
		super(father, fileName, "", capacity, startNum, attributes);
		files = new ArrayList<>();
	}

	/**
	 * 该方法用于判断该文件夹是否已满
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
	 * 该方法为在该目录下创建txt文件
	 *
	 * @return Files :返回一个文件，如果文件为null则表明创建失败
	 */
	public Files createTxtFile() {
		return createFile(createFileName(),".txt",8,1,"");
	}

	/**
	 * 该方法为在该目录下创建exe文件
	 * @return Files:返回一个文件，如果文件为null则表明创建失败
	 */
	public Files createExeFile() {
		return createFile(createFileName(),".e",8,1,"");
	}

	public Files createFile(String fileName,String fileExtentionName,int capacity,int attribute,String content) {
			Files f=null;
		// 检查文件夹下文件数目是否已超过最大值
		if (!isFull()) {
			// 检查磁盘是否已满
			if (FAT.getInstance().capacityOfDisk()> 0) {
				int startNum = FAT.getInstance().changeFAT(8);
				// 默认生成可写且空白的文件
				f = new Files(this,fileName,fileExtentionName, capacity, startNum, attribute, content);
				this.files.add(f);
				this.capacity+=f.capacity;
			}
		}
		return f;
	}
	/**
	 * 该方法为在该目录下创建文件夹
	 *
	 * @return Directory:返回文件夹，如果为null则创建失败
	 */
	public Directory createDirectory() {
		return createDirectory(createFileName());
	}
	public Directory createDirectory(String fileName) {
		Directory f=null;
		if (!isFull()) {
			// 检查磁盘是否已满
			if (FAT.getInstance().capacityOfDisk()>0) {
				int startNum = FAT.getInstance().changeFAT(8);
				// 默认生成可写且空白的文件
				 f = new Directory(this,fileName, 8, startNum, 1);
				this.files.add(f);
				this.capacity+=f.capacity;
			}
		}
		return f;
	}


	private String createFileName() {
		String fileName="新";
		for(Integer i=0;i<8;i++) {
			if(isExistedName((fileName+i.toString()))) {
				continue;
			}else {
				fileName+=i.toString();
				break;
			}
		}
		return fileName;
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
	 * @return boolean：false表示文件不可删除
	 */
	public boolean removeFiles(FileItem f) {
		boolean succeed =true;
		if(f.isCanBeDeleted()) {
			files.remove(f);
			this.capacity-=f.capacity;
		}else {
			succeed =false;
		}

		return succeed;
	}

	/**
	 * 通过文件名来查找文件或者文件夹
	 * @param String:fileName
	 * @return FileItem:f 找到的内容，若为null则查找失败
	 */
	public FileItem findFiles(String fileName) {
		FileItem f=null;
		for(FileItem f2:files) {
			if(f2.fileName.equals(fileName)) {
				f=f2;
				break;
			}
		}
		return f;
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
			if(this.canBeDeleted&&this.fatherFile!=null) {
				fatherFile.removeFiles(this);
				FAT.getInstance().recovery(this.startNum);
			}


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

	/**
	 * 该方法获取该目录下的所有文件夹和文件名
	 * @return
	 */
	public  ArrayList<String> getAllFilesName(){
		ArrayList<String> names= new ArrayList<String>();
		for(FileItem f:files) {
			names.add(f.getFileName()+f.getFileExtentionName());
		}
		return names;

	}

}
