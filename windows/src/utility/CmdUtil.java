package utility;

import model.disk.Directory;

public class  CmdUtil {
	
	/**
	 * �����ļ���������Ŀ¼���ļ������Լ��ļ�����
	 * @param Directory:father
	 * @param String: fileName
	 * @param String:fileExtentionName
	 * @return boolean:�����Ƿ�ɹ�
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
