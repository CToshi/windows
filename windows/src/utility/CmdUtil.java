package utility;

import model.disk.Directory;
import model.disk.Disk;
import model.disk.DiskFileTree;
import model.disk.DiskFileTreeItem;
import model.disk.FileItem;
import model.disk.Files;

public class CmdUtil {

	/**
	 * 通过路径找到该文件
	 *
	 * @param String:route
	 * @return Files:f返回找到的文件，若为null则路径错误
	 */
	public static Files findFile(String route) {
		Files f = null;
		route = "root" + route;
		String[] names = route.split("\\");
		Directory d = Disk.getInstance().getRoot();
		FileItem f2 = null;
		for (int i = 1; i < names.length; i++) {
			f2 = d.findFiles(names[i]);
			// 该目录下没有该文件
			if (f2 == null) {
				break;
			} else if (Directory.isDirectory(f2)) {// 这是一个文件夹
				d = (Directory) f2;
			} else if (i != names.length - 1) { // 这是一个文件但在路径上不是在最后，路径错误
				break;
			} else {// 是一个文件而且路径正确
				f = (Files) f2;
			}
		}
		return f;
	}

	/**
	 * 通过路径找到文件夹
	 *
	 * @param String
	 *            : route
	 * @return Directory :d 返回找到的文件夹，若null则路径错误
	 */

	public static Directory findDirectory(String route) {
		Directory d = Disk.getInstance().getRoot();
		String[] names = route.split("(:\\\\)|(\\\\)");
		if(names.length == 1)return d;
		FileItem f = null;
		for (int i = 1; i < names.length; i++) {
			f = d.findFiles(names[i]);
			if (f == null) {// 该文件不存在
				break;
			} else if (!Directory.isDirectory(f)) {// 这不是一个文件夹
				break;
			} else {
				d = (Directory) f;
			}
		}
		return d;
	}

	/**
	 * 创建文件，给出父目录和文件名，以及文件属性
	 *
	 * @param Directory:father
	 * @param String:
	 *            fileName
	 * @param String:fileExtentionName
	 * @return int:errorCode 0：创建成功，1：该目录文件数超过最大限制，2：容量不足,3:文件已存在,4:文件后缀名不正确
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
			// 对接目录树
			else {
				DiskFileTreeItem fatherItem = father.getMyItem();
				fatherItem.getChildren().add(new DiskFileTreeItem(f));
				System.out.println(233);
			}
		}
		return errorCode;
	}

	/**
	 * 该方法为删除文件
	 *
	 * @param Files：f
	 * @return boolean：表示删除成功与否，false则表示改文件不可删除
	 */
	public static boolean deleteFiles(Files f) {
		boolean succeed = true;
		if (f.isCanBeDeleted()) {
			f.deleteFiles();
			// 对接目录树
			DiskFileTreeItem item = f.getMyItem();
			item.getParent().getChildren().remove(item);
		} else {
			succeed = false;
		}
		return succeed;
	}

	/**
	 * 将某文件复制到某目录下
	 *
	 * @param Files:f
	 *            在剪贴板的文件
	 * @param Directory:d
	 *            复制到该目录
	 * @return int:errorCode 0：复制成功，1：该目录文件数超过最大限制，2：容量不足,3:文件已存在
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
			// 对接目录树
			else {
				DiskFileTreeItem fatherItem = d.getMyItem();
				fatherItem.getChildren().add(new DiskFileTreeItem(newFile));
			}
		}
		return errorCode;
	}

	/**
	 * 该方法用于创建文件夹
	 *
	 * @param d
	 * @param fileName
	 * @return int:errorCode 0：复制成功，1：该目录文件数超过最大限制，2：容量不足,3:文件已存在
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
			// 对接目录树
			else {
				DiskFileTreeItem fatherItem = d.getMyItem();
				fatherItem.getChildren().add(new DiskFileTreeItem(newDirectory));
			}
		}
		return errorCode;
	}

	/**
	 * 该方法用于删除非空文件夹
	 *
	 * @return int:errorCode 0:删除成功，1：文件夹非空，2：文件夹不可删除
	 */
	public static int deleteEmptyDirectory(Directory d) {
		int errorCode = 0;
		if (!d.isCanBeDeleted()) {
			errorCode = 2;
		} else if (d.getCapacity() > 8) {
			errorCode = 1;
		} else {
			d.deleteFiles();
			// 对接目录树
			DiskFileTreeItem item = d.getMyItem();
			item.getParent().getChildren().remove(item);
		}
		return errorCode;
	}

	/**
	 * 该方法用于删除文件夹，无论空还是满
	 *
	 * @return boolean :文件夹如果为不可删除文件夹则删除失败
	 */
	public static boolean deleteDirectory(Directory d) {
		boolean succeed = true;
		if (d.isCanBeDeleted()) {
			d.deleteFiles();
			// 对接目录树
			DiskFileTreeItem item = d.getMyItem();
			item.getParent().getChildren().remove(item);
		} else {
			succeed = false;
		}
		return succeed;
	}

	/**
	 * 该方法用于修改文件属性
	 *
	 * @param f
	 * @param attribute
	 * @return boolean:当输入属性并不存在时则修改失败
	 */
	public static boolean changeFilesAttribute(Files f, int attribute) {
		boolean succeed = true;
		if (attribute >= 0 && attribute <= 2) {
			// txt不能为可执行文件
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
	 * 磁盘格式化
	 *
	 * @return 格式化成功还是失败
	 */
	public static boolean format() {
		boolean succeed = true;
		Disk.getInstance().format();
		DiskFileTree.getInstance().getRoot().getChildren().clear();
		return succeed;
	}
}
