package model.disk;

public class FileItem {
/**
 * @author BFELFISH
 * 该类为目录项，包括了文件名，文件大小，文件起始块号，文件属性（只读0，可写1，可执行2）
 */
	private String fileName;
	private int capacity;
	private int startNum;
	private int attributes;
	
	public FileItem(String fileName, int capacity, int startNum,int attributes) {
		super();
		this.fileName = fileName;
		this.capacity = capacity;
		this.startNum = startNum;
		this.attributes=attributes;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	
	
	
	
	
}
