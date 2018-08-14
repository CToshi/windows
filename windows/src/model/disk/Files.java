package model.disk;

public class Files {
	/**
	 * @author BFELFISH
	 * 该类为文件类，包括了文件目录项和文件内容，是磁盘管理的主要元素
	 */
	
	private FileItem fileItem;
	private String content;
	
	public Files() {
		
	}
	
	public Files(int startNum) {
		fileItem=new FileItem("新建文件", 0, startNum, 0);
		content="";
	}

	public Files(String fileName, int capacity, int startNum,int attributes,String content) {
		this.fileItem=new FileItem(fileName, capacity, startNum, attributes);
		this.content=content;
	}

	public FileItem getFileItem() {
		return fileItem;
	}

	public void setFileItem(FileItem fileItem) {
		this.fileItem = fileItem;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
	
	
	
}
