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

	private static final String[] CREATE_ERROR = { "�����ɹ�", "��Ŀ¼�ļ��������������", "��������", "�ļ��Ѵ���", "�ļ���׺������ȷ" };
	private static final String[] MKDIR_ERROR = { "���Ƴɹ�", "��Ŀ¼�ļ��������������", "��������", "�ļ��Ѵ���" };
	private static final String[] COPY_ERROR = { "���Ƴɹ�", "��Ŀ¼�ļ��������������", "��������", "�ļ��Ѵ���" };
	private static Controller controller = new Controller();
	private Console console;

	public static Controller getInstance() {
		return controller;
	}

	private Controller() {
		console = IconManager.getInstance().getWindow(Type.CMD).getConsole();
	}

	/**
	 * ���е�ָ� ll ��ʾ�ļ������� cd ���ļ��л�����һ��Ŀ¼ mkdir �����ļ��� create �����ļ� vim �༭�ļ� copy
	 * �����ļ� delete ɾ���ļ� rmdir ɾ����Ŀ¼
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
				console.addMsg("û������ָ��");
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
					console.addMsg("�����ʽ����ȷ");
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
						console.addMsg("�ļ��в�����");
					}
				}
				break;
			case "delete":
				file = getFile(path, direcNames, temps[1]);
				if (file == null) {
					console.addMsg("�ļ�������");
					break;
				}
				if (!CmdUtil.deleteFiles(file))
					console.addMsg("ɾ��ʧ��");
				break;
			case "type":
				file = getFile(path, direcNames, temps[1]);
				if (file == null)
					console.addMsg("�ļ�������");
				else
					console.addMsg(file.getContent());
				break;
			case "copy":
				if (temps.length <= 2) {
					console.addMsg("�����ʽ����ȷ");
					break;
				}
				file = getFile(path, direcNames, temps[1]);
				if (file == null)
					console.addMsg("�ļ�������");
				else {
					System.out.println(file.getFileName());
					LinkedList<String> path2 = analyseRoute(temps[2], direcNames);
					Directory directory2 = getDirectory(path2, direcNames, temps[2]);
					if (directory2 == null)
						console.addMsg("�ļ��в�����");
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
						console.addMsg("ɾ��ʧ��");
					}
				} else {
					console.addMsg("�ļ��в�����");
				}
				break;
			case "vim":
				file = getFile(path, direcNames, temps[1]);
				if (file == null)
					console.addMsg("�ļ�������");
				else
					CreateWindows.getInstance().create(file);
				break;
			default:
				console.addMsg("û������ָ��");
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
