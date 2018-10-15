package utility;

import model.disk.Directory;
import model.disk.Disk;
import model.disk.Files;

public class CmdUtil {

	/**
	 * �����ļ���������Ŀ¼���ļ������Լ��ļ�����
	 *
	 * @param Directory:father
	 * @param String: fileName
	 * @param String:fileExtentionName
	 * @return int:errorCode 0�������ɹ���1����Ŀ¼�ļ�������������ƣ�2����������,3:�ļ��Ѵ���
	 */
	public static int creatFiles(Directory father, String fileName, String fileExtentionName) {
		int errorCode = 0;
		if (father.isExistedName(fileName)) {
			errorCode = 3;
		} else if (father.isFull()) {
			errorCode = 1;
		} else {
			Files f = null;
			if (fileExtentionName.equals(".txt")) {
				f = father.createTxtFile();
			} else {
				f = father.createExeFile();
			}
			if (f == null) {
				errorCode = 2;
			}
		}
		// �Խ�Ŀ¼��
		return errorCode;
	}

	/**
	 * �÷���Ϊɾ���ļ�
	 *
	 * @param Files��f
	 * @return boolean����ʾɾ���ɹ����false���ʾ���ļ�����ɾ��
	 */
	public static boolean deleteFiles(Files f) {
		boolean succeed = true;
		if (f.isCanBeDeleted()) {
			f.deleteFiles();
		} else {
			succeed = false;
		}
		// �Խ�Ŀ¼��
		return succeed;
	}

	/**
	 * ��ĳ�ļ����Ƶ�ĳĿ¼��
	 *
	 * @param Files:f �ڼ�������ļ�
	 * @param Directory:d ���Ƶ���Ŀ¼
	 * @return int:errorCode 0�����Ƴɹ���1����Ŀ¼�ļ�������������ƣ�2����������,3:�ļ��Ѵ���
	 */
	public static int copyFiles(Files f, Directory d) {
		int errorCode = 0;
		if (d.isFull()) {
			errorCode = 1;
		} else if (d.isExistedName(f.getFileName())) {
			errorCode = 3;
		} else {
			// ������Ҫ�Խ�Ŀ¼��
			Files newFile = null;
			newFile = d.createFile(f.getFileName(), f.getFileExtentionName(), f.getCapacity(), f.getAttributes(),
					f.getContent());
			if (newFile == null) {
				errorCode = 2;
			}
		}
		return errorCode;
	}

	/**
	 * �÷������ڴ����ļ���
	 * @param d
	 * @param fileName
	 * @return int:errorCode 0�����Ƴɹ���1����Ŀ¼�ļ�������������ƣ�2����������,3:�ļ��Ѵ���
	 */
	public static int creatDirectory(Directory d, String fileName) {
		int errorCode =0;
		if(d.isExistedName(fileName)) {
			errorCode=3;
		}else if(d.isFull()) {
			errorCode=1;
		}else {

			Directory newDirectory=d.createDirectory(fileName);
			if(newDirectory==null) {
				errorCode=2;
			}
		}
		return errorCode;
	}

	/**
	 * �÷�������ɾ���ǿ��ļ���
	 * @return int:errorCode 0:ɾ���ɹ���1���ļ��зǿգ�2���ļ��в���ɾ��
	 */
	public static int deleteEmptyDirectory(Directory d) {
		int errorCode =0;
		if(!d.isCanBeDeleted()) {
			errorCode=2;
		}else if(d.getCapacity()>8) {
			errorCode=1;
		}else{
			d.deleteFiles();
		}
		return errorCode;
	}

	/**
	 * �÷�������ɾ���ļ��У����ۿջ�����
	 * @return boolean :�ļ������Ϊ����ɾ���ļ�����ɾ��ʧ��
	 */
	public static boolean deleteDirectory(Directory d) {
		boolean succeed=true;
		if(d.isCanBeDeleted()) {
			d.deleteFiles();
		}else {
			succeed=false;
		}
		return succeed;
	}

	/**
	 * �÷��������޸��ļ�����
	 * @param f
	 * @param attribute
	 * @return boolean:���������Բ�������ʱ���޸�ʧ��
	 */
	public static boolean changeFilesAttribute(Files f,int attribute) {
		boolean succeed = true;
		if(attribute>=0&&attribute<=2) {
			//txt����Ϊ��ִ���ļ�
			if(f.getFileExtentionName().equals(".txt")&&attribute==2) {
				succeed=false;
			}else {
				f.changeAttributes(attribute);
			}
		}else {
			succeed=false;
		}
		return succeed;
	}

	/***
	 * ���̸�ʽ��
	 * @return ��ʽ���ɹ�����ʧ��
	 */
	public static boolean format() {
		boolean succeed = true;
		Disk.format();
		return succeed;
	}
}