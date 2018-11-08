package model.disk;

public abstract class FileItem implements Cloneable {
	/**
	 * @author BFELFISH ����ΪĿ¼��������ļ������ļ���չ�����ļ���С���ļ���ʼ��ţ��ļ����ԣ�ֻ��0����д1����ִ��2��
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
	 * �÷����жϸ��ļ����ļ������ļ���
	 *
	 * @param FileItem f
	 * @return boolean:�Ƿ�Ϊ�ļ���
	 */
	public static boolean isDirectory(FileItem f) {
		boolean directory = true;
		if (!(f instanceof Directory)) {
			directory = false;
		}
		return directory;
	}

	/**
	 * �÷���Ϊ�޸��ļ����ļ���
	 *
	 * @paramԭ�ļ������޸ĺ���ļ���
	 * @return int:0:�޸ĳɹ���1:�ļ���������������,2���ļ����Ѵ���,3:�ļ������Ƿ��ַ���4���ļ�������Ϊ��
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
	 * �÷��������޸��ļ�����
	 *
	 * @param int:attributes
	 * @return boolean:�޸��Ƿ�ɹ�
	 */
	public abstract boolean changeAttributes(int attributes);

}
