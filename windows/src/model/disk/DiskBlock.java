package model.disk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DiskBlock {
	/**
	 * @author jiaru
	 * ´ÅÅÌ¿é´æµÄÊÇ×Ö·û´®
	 */
	
	private Integer number;

	private int statusOfUsed;

	private String content;

	public DiskBlock(int number) {
		super();
		this.number = number;
		statusOfUsed = 0;
		FileInit();
	}

	
	private void FileInit() {
		content = "";
	}


	

	

}