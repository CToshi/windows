package utility;

import model.disk.Directory;

public class  CmdUtil {
	
	/**
	 * 创建文件，给出父目录和文件名，以及文件属性
	 * @param Directory:father
	 * @param String: fileName
	 * @param String:fileExtentionName
	 * @return boolean:返回是否成功
	 */
	public static boolean  creatFiles(Directory father,String fileName,String fileExtentionName) {
	boolean succeed =true;
	if(fileExtentionName.equals(".txt")) {
		father.createTxtFile();
	}else {
		father.createExeFile();
	}
	
	return succeed;
	}

}
