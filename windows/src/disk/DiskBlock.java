package disk;

import java.io.File;


public class DiskBlock {
	// ���̿���
	private int number;

	// ���̿�״̬
	private int statusOfUsed;

	// ÿ�����̿���ʵ��һ���ļ�
	private File content;

	public DiskBlock(int number) {
		super();
		this.number = number;
		statusOfUsed = 0;
		FileInit();
	}

	//*****������ļ�(���̿�)��ʼ��
	private void FileInit() {
		
	}
	
	//*****��ȡ���̿�����
	public void readFronFile() {
		
	}
	
	//*****д���̿�����
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
