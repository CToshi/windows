package utility;

import model.disk.Directory;
import model.disk.Disk;
import model.disk.DiskFileTree;
import model.disk.DiskFileTreeItem;
import model.disk.FileItem;
import model.disk.Files;

public class CmdUtil {

	/**
	 * ͨ��·���ҵ����ļ�
	 *
	 * @param String:route
	 * @return Files:f�����ҵ����ļ�����Ϊnull��·������
	 */
	public static Files findFile(String route) {
		Files f = null;
		route = "root" + route;
		String[] names = route.split("\\");
		Directory d = Disk.getInstance().getRoot();
		FileItem f2 = null;
		for (int i = 1; i < names.length; i++) {
			f2 = d.findFiles(names[i]);
			// ��Ŀ¼��û�и��ļ�
			if (f2 == null) {
				break;
			} else if (Directory.isDirectory(f2)) {// ����һ���ļ���
				d = (Directory) f2;
			} else if (i != names.length - 1) { // ����һ���ļ�����·���ϲ��������·������
				break;
			} else {// ��һ���ļ�����·����ȷ
				f = (Files) f2;
			}
		}
		return f;
	}

	/**
	 * ͨ��·���ҵ��ļ���
	 *
	 * @param String
	 *            : route
	 * @return Directory :d �����ҵ����ļ��У���null��·������
	 */

	public static Directory findDirectory(String route) {
		Directory d = Disk.getInstance().getRoot();
		String[] names = route.split("(:\\\\)|(\\\\)");
		if(names.length == 1)return d;
		FileItem f = null;
		for (int i = 1; i < names.length; i++) {
			f = d.findFiles(names[i]);
			if (f == null) {// ���ļ�������
				break;
			} else if (!Directory.isDirectory(f)) {// �ⲻ��һ���ļ���
				break;
			} else {
				d = (Directory) f;
			}
		}
		return d;
	}

	/**
	 * �����ļ���������Ŀ¼���ļ������Լ��ļ�����
	 *
	 * @param Directory:father
	 * @param String:
	 *            fileName
	 * @param String:fileExtentionName
	 * @return int:errorCode 0�������ɹ���1����Ŀ¼�ļ�������������ƣ�2����������,3:�ļ��Ѵ���,4:�ļ���׺������ȷ
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
			} else if (fileExtentionName.equals(".exe")) {
				f = father.createExeFile();
			} else {
				errorCode = 4;
			}
			if (f == null) {
				errorCode = 2;
			}
			// �Խ�Ŀ¼��
			else {
				DiskFileTreeItem fatherItem = father.getMyItem();
				fatherItem.getChildren().add(new DiskFileTreeItem(f));
				System.out.println(233);
			}
		}
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
			// �Խ�Ŀ¼��
			DiskFileTreeItem item = f.getMyItem();
			item.getParent().getChildren().remove(item);
		} else {
			succeed = false;
		}
		return succeed;
	}

	/**
	 * ��ĳ�ļ����Ƶ�ĳĿ¼��
	 *
	 * @param Files:f
	 *            �ڼ�������ļ�
	 * @param Directory:d
	 *            ���Ƶ���Ŀ¼
	 * @return int:errorCode 0�����Ƴɹ���1����Ŀ¼�ļ�������������ƣ�2����������,3:�ļ��Ѵ���
	 */
	public static int copyFiles(Files f, Directory d) {
		int errorCode = 0;
		if (d.isFull()) {
			errorCode = 1;
		} else if (d.isExistedName(f.getFileName())) {
			errorCode = 3;
		} else {
			Files newFile = null;
			newFile = d.createFile(f.getFileName(), f.getFileExtentionName(), f.getCapacity(), f.getAttributes(),
					f.getContent());
			if (newFile == null) {
				errorCode = 2;
			}
			// �Խ�Ŀ¼��
			else {
				DiskFileTreeItem fatherItem = d.getMyItem();
				fatherItem.getChildren().add(new DiskFileTreeItem(newFile));
			}
		}
		return errorCode;
	}

	/**
	 * �÷������ڴ����ļ���
	 *
	 * @param d
	 * @param fileName
	 * @return int:errorCode 0�����Ƴɹ���1����Ŀ¼�ļ�������������ƣ�2����������,3:�ļ��Ѵ���
	 */
	public static int creatDirectory(Directory d, String fileName) {
		int errorCode = 0;
		if (d.isExistedName(fileName)) {
			errorCode = 3;
		} else if (d.isFull()) {
			errorCode = 1;
		} else {
			Directory newDirectory = d.createDirectory(fileName);
			if (newDirectory == null) {
				errorCode = 2;
			}
			// �Խ�Ŀ¼��
			else {
				DiskFileTreeItem fatherItem = d.getMyItem();
				fatherItem.getChildren().add(new DiskFileTreeItem(newDirectory));
			}
		}
		return errorCode;
	}

	/**
	 * �÷�������ɾ���ǿ��ļ���
	 *
	 * @return int:errorCode 0:ɾ���ɹ���1���ļ��зǿգ�2���ļ��в���ɾ��
	 */
	public static int deleteEmptyDirectory(Directory d) {
		int errorCode = 0;
		if (!d.isCanBeDeleted()) {
			errorCode = 2;
		} else if (d.getCapacity() > 8) {
			errorCode = 1;
		} else {
			d.deleteFiles();
			// �Խ�Ŀ¼��
			DiskFileTreeItem item = d.getMyItem();
			item.getParent().getChildren().remove(item);
		}
		return errorCode;
	}

	/**
	 * �÷�������ɾ���ļ��У����ۿջ�����
	 *
	 * @return boolean :�ļ������Ϊ����ɾ���ļ�����ɾ��ʧ��
	 */
	public static boolean deleteDirectory(Directory d) {
		boolean succeed = true;
		if (d.isCanBeDeleted()) {
			d.deleteFiles();
			// �Խ�Ŀ¼��
			DiskFileTreeItem item = d.getMyItem();
			item.getParent().getChildren().remove(item);
		} else {
			succeed = false;
		}
		return succeed;
	}

	/**
	 * �÷��������޸��ļ�����
	 *
	 * @param f
	 * @param attribute
	 * @return boolean:���������Բ�������ʱ���޸�ʧ��
	 */
	public static boolean changeFilesAttribute(Files f, int attribute) {
		boolean succeed = true;
		if (attribute >= 0 && attribute <= 2) {
			// txt����Ϊ��ִ���ļ�
			if (f.getFileExtentionName().equals(".txt") && attribute == 2) {
				succeed = false;
			} else {
				f.changeAttributes(attribute);
			}
		} else {
			succeed = false;
		}
		return succeed;
	}

	/***
	 * ���̸�ʽ��
	 *
	 * @return ��ʽ���ɹ�����ʧ��
	 */
	public static boolean format() {
		boolean succeed = true;
		Disk.getInstance().format();
		DiskFileTree.getInstance().getRoot().getChildren().clear();
		return succeed;
	}
}
