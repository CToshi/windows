package model.disk;

public abstract class FileItem implements Cloneable {
	/**
	 * @author BFELFISH 该类为目录项，包括了文件名，文件扩展名，文件大小，文件起始块号，文件属性（只读0，可写1，可执行2）
	 */
	protected final static int MAX_SIZE_OF_FILE_NAME = 8;
	protected Directory fatherFile;
	protected String fileName;
	protected String fileExtentionName;
	protected int capacity;
	protected int startNum;
	protected int attributes;
	protected boolean canBeDeleted = true;
	private DiskFileTreeItem myItem;

	public FileItem(Directory father, String fileName, String fileExtentionName, int capacity, int startNum,
			int attributes) {
		this.fatherFile = father;
		this.fileName = fileName;
		this.fileExtentionName = fileExtentionName;
		this.capacity = capacity;
		this.startNum = startNum;
		if (fileExtentionName.equals(".e")) {
			this.attributes = 2;
		} else {
			this.attributes = attributes;
		}
	}

	/**
	 * 该方法判断该文件是文件还是文件夹
	 *
	 * @param FileItem f
	 * @return boolean:是否为文件夹
	 */
	public static boolean isDirectory(FileItem f) {
		boolean directory = true;
		if (!(f instanceof Directory)) {
			directory = false;
		}
		return directory;
	}

	/**
	 * 该方法为修改文件的文件名
	 *
	 * @param原文件名与修改后的文件名
	 * @return int:0:修改成功，1:文件名字数超出限制,2：文件名已存在,3:文件名含非法字符，4：文件名不得为空
	 */
	public int changeFilesName(String fileName, String newFileName,String fileExtentionName) {
		int errorCode = 0;
		if (newFileName == null || newFileName.equals("")) {
			return 4;
		}
		if (newFileName.getBytes().length >= FileItem.MAX_SIZE_OF_FILE_NAME) {
			return 1;
		}
		if (fatherFile.isExistedName(newFileName,fileExtentionName)) {
			return 2;
		}
		if (newFileName.contains("$") || newFileName.contains(".") || newFileName.contains("/")) {
			return 3;
		}
		this.fileName = newFileName;
		return errorCode;
	}

	public abstract boolean deleteFiles();

	public String getFileName() {
		return fileName;
	}

	public String getFileExtentionName() {
		return fileExtentionName;
	}

	public void setFileExtentionName(String fileExtentionName) {
		this.fileExtentionName = fileExtentionName;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getStartNum() {
		return startNum;
	}

	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}

	public int getAttributes() {
		return attributes;
	}

	public void setAttributes(int attributes) {
		this.attributes = attributes;
	}

	public boolean isCanBeDeleted() {
		return canBeDeleted;
	}

	public void setCanBeDeleted(boolean canBeDeleted) {
		this.canBeDeleted = canBeDeleted;
	}

	public Directory getFatherFile() {
		return fatherFile;
	}

	public DiskFileTreeItem getMyItem() {
		return myItem;
	}

	public void setMyItem(DiskFileTreeItem myItem) {
		this.myItem = myItem;
	}

	/**
	 * 该方法用于修改文件属性
	 *
	 * @param int:attributes
	 * @return boolean:修改是否成功
	 */
	public abstract boolean changeAttributes(int attributes);

}
