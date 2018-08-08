package model.disk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DiskBlock {
	/**
	 *  ���̿���
	 */
	private Integer number;

	/**
	 *  ���̿�״̬
	 */
	private int statusOfUsed;

	/**
	 *  ÿ�����̿���ʵ��һ���ļ�
	 */
	private File content;

	public DiskBlock(int number) {
		super();
		this.number = number;
		statusOfUsed = 0;
		fileInit();
	}

	/**
	 *  ������ļ�(���̿�)��ʼ��
	 */
	private void fileInit() {
		content = new File("disk/" + number.toString() + ".txt");
	}

	/**
	 *  ��ȡ���̿�����
	 * @return contentOfDiskBlock
	 * @throws FileNotFoundException
	 */
	public String readFronFile() throws FileNotFoundException {
		
		String contentOfDiskBlock = "";
		try (Scanner diskblock = new Scanner(content)) {
			while (diskblock.hasNext()) {
				contentOfDiskBlock = contentOfDiskBlock + diskblock.nextLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new FileNotFoundException("�ļ�������");
		}
		return contentOfDiskBlock;

	}

	/**
	 *  д���̿�����
	 * @param contentOfFile
	 * @throws FileNotFoundException
	 */
	public void writeIntoFile(String contentOfFile) throws FileNotFoundException {
		try (PrintWriter diskblock = new PrintWriter(content)) {
			diskblock.print(contentOfFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new FileNotFoundException("�ļ�����ʧ��");
		}

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
