package disk;

import java.io.File;


public class DiskBlock {
	// 磁盘块块号
	private int number;

	// 磁盘块状态
	private int statusOfUsed;

	// 每个磁盘块其实是一个文件
	private File content;

	public DiskBlock(int number) {
		super();
		this.number = number;
		statusOfUsed = 0;
		FileInit();
	}

	//*****这里对文件(磁盘块)初始化
	private void FileInit() {
		
	}
	
	//*****读取磁盘块内容
	public void readFronFile() {
		
	}
	
	//*****写磁盘块内容
	public void writeIntoFile() {
		
	}
	
	
	public int getStatusOfUsed() {
		return statusOfUsed;
	}

	public void setStatusOfUsed(int statusOfUsed) {
		this.statusOfUsed = statusOfUsed;
	}

	public int getNumber() {
		return number;
	}

}
