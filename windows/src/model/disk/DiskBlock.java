package model.disk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DiskBlock {
	// 磁盘块块号
	private Integer number;

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

	// 这里对文件(磁盘块)初始化
	private void FileInit() {
		content = new File("disk/" + number.toString() + ".txt");
	}

<<<<<<< HEAD
	// 读取磁盘块内容
=======
	/**
	 *  读取磁盘块内容
	 * @return contentOfDiskBlock
	 * @throws FileNotFoundException
	 */
>>>>>>> master
	public String readFronFile() throws FileNotFoundException {

		String contentOfDiskBlock = "";
		try (Scanner diskblock = new Scanner(content)) {
			while (diskblock.hasNext()) {
				contentOfDiskBlock = contentOfDiskBlock + diskblock.nextLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new FileNotFoundException("文件不存在");
		}
		return contentOfDiskBlock;

	}

	// 写磁盘块内容
	public void writeIntoFile(String contentOfFile) throws FileNotFoundException {
		try (PrintWriter diskblock = new PrintWriter(content)) {
			diskblock.print(contentOfFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new FileNotFoundException("文件创建失败");
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
