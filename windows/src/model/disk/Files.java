package model.disk;

public class Files {
	/**
	 * @author BFELFISH
	 * ����Ϊ�ļ��࣬�������ļ�Ŀ¼����ļ����ݣ��Ǵ��̹������ҪԪ��
	 */
	
	private FileItem fileItem;
	private String content;
	
	public Files() {
		
	}
	
	public Files(int startNum) {
		fileItem=new FileItem("�½��ļ�", 0, startNum, 0);
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
