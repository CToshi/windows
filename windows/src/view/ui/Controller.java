package view.ui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.disk.Directory;
import model.disk.Files;
import utility.CmdUtil;
import utility.Util;
import view.ui.IconManager.Type;

public class Controller {

	private static final String[] CREATE_ERROR = { "创建成功", "该目录文件数超过最大限制", "容量不足", "文件已存在", "文件后缀名不正确" };
	private static final String[] MKDIR_ERROR = { "复制成功", "该目录文件数超过最大限制", "容量不足", "文件已存在" };
	private static final String[] COPY_ERROR = { "复制成功", "该目录文件数超过最大限制", "容量不足", "文件已存在" };
	private static Controller controller = new Controller();
	private Console console;

	public static Controller getInstance() {
		return controller;
	}

	private Controller() {
		console = IconManager.getInstance().getWindow(Type.CMD).getConsole();
	}

	/**
	 * 所有的指令： ll 显示文件夹内容 cd 入文件夹或者上一级目录 mkdir 创建文件夹 create 创建文件 vim 编辑文件 copy
	 * 复制文件 delete 删除文件 rmdir 删除空目录
	 *
	 * @param operation
	 * @param route
	 */
	public void setMessage(String operation, String route) {
		String[] temps = operation.split("\\s+");
		LinkedList<String> direcNames = Util.list(route.split("(:\\\\)|(\\\\)"));
		Directory directory = CmdUtil.findDirectory(direcNames);
		String operate = temps[0];
		int result = 0;
		String[] fileName = null;
		Files file = null;
		if (temps.length == 1) {
			switch (operate) {
			case "ll":
				ArrayList<String> names = directory.getAllFilesName();
				String msg = ".\n..";
				for (int i = 0; i < names.size(); i++) {
					msg = msg + "\n" + names.get(i);
				}
				console.addMsg(msg);
				break;
			default:
				console.addMsg("没有这条指令");
				break;
			}
		} else {
			LinkedList<String> path = analyseRoute(temps[1], direcNames);
			switch (operate) {
			case "create":
				if (path.size() == 0) {
					fileName = temps[1].split("\\.");
				} else {
					fileName = path.get(path.size() - 1).split("\\.");
				}
				if (fileName.length < 2) {
					console.addMsg("命令格式不正确");
					break;
				}
				result = CmdUtil.creatFiles(directory, fileName[0], fileName[1]);
				if (result != 0)
					console.addMsg(CREATE_ERROR[result]);
				break;
			case "cd":
				if (temps[1].equals(".")) {
					break;
				} else if (temps[1].equals("..")) {
					console.returnBack();
				} else {
					direcNames.add(temps[1]);
					if (CmdUtil.findDirectory(direcNames) != null) {
						console.setRoute(route + temps[1] + "\\");
					} else {
						console.addMsg("文件夹不存在");
					}
				}
				break;
			case "delete":
				file = getFile(path, direcNames, temps[1]);
				if (file == null) {
					console.addMsg("文件不存在");
					break;
				}
				if (!CmdUtil.deleteFiles(file))
					console.addMsg("删除失败");
				break;
			case "type":
				file = getFile(path, direcNames, temps[1]);
				if (file == null)
					console.addMsg("文件不存在");
				else
					console.addMsg(file.getContent());
				break;
			case "copy":
				if (temps.length <= 2) {
					console.addMsg("命令格式不正确");
					break;
				}
				file = getFile(path, direcNames, temps[1]);
				if (file == null)
					console.addMsg("文件不存在");
				else {
					System.out.println(file.getFileName());
					LinkedList<String> path2 = analyseRoute(temps[2], direcNames);
					Directory directory2 = getDirectory(path2, direcNames, temps[2]);
					if (directory2 == null)
						console.addMsg("文件夹不存在");
					else {
						result = CmdUtil.copyFiles(file, directory2);
						if (result != 0)
							console.addMsg(COPY_ERROR[result]);
					}
				}
				break;
			case "mkdir":
				result = CmdUtil.creatDirectory(directory, temps[1]);
				if (result != 0)
					console.addMsg(MKDIR_ERROR[result]);
				break;
			case "rmdir":
				direcNames.add(temps[1]);
				Directory tempDire = CmdUtil.findDirectory(direcNames);
				if (tempDire != null) {
					if (!CmdUtil.deleteDirectory(tempDire)) {
						console.addMsg("删除失败");
					}
				} else {
					console.addMsg("文件夹不存在");
				}
				break;
			case "vim":
				file = getFile(path, direcNames, temps[1]);
				if (file == null)
					console.addMsg("文件不存在");
				else
					CreateWindows.getInstance().create(file);
				break;
			default:
				console.addMsg("没有这条指令");
				break;
			}
		}

	}

	private LinkedList<String> analyseRoute(String route, LinkedList<String> direcNames) {
		LinkedList<String> path = new LinkedList<String>();
		if (route.charAt(0) == '\\' || (route.length() > 1 && (route.substring(0, 2)).equals(".\\"))) {
			if (!route.endsWith("\\"))
				route += "\\";
			Matcher matcher = null;
			if (route.charAt(0) == '\\') {
				matcher = Pattern.compile("(.*?)\\\\").matcher(route);
			} else if (route.charAt(0) == '.') {
				for (String string : direcNames)
					path.add(string);
				matcher = Pattern.compile("(.*?)\\\\").matcher(route.substring(1, route.length()));
			}
			while (matcher.find()) {
				if (matcher.start() != 0)
					path.add(matcher.group(1));
			}
		}
		return path;
	}

	private Files getFile(LinkedList<String> path, LinkedList<String> direcNames, String route) {
		if (path.size() == 0) {
			direcNames.add(route);
			Files file = CmdUtil.findFile(direcNames);
			direcNames.remove(direcNames.size() - 1);
			return file;
		} else {
			return CmdUtil.findFile(path);
		}
	}

	private Directory getDirectory(LinkedList<String> path, LinkedList<String> direcNames, String route) {
		if (path.size() == 0) {
			direcNames.add(route);
			Directory directory = CmdUtil.findDirectory(direcNames);
			direcNames.remove(direcNames.size() - 1);
			return directory;
		} else {
			return CmdUtil.findDirectory(path);
		}
	}

}
