package view.ui;

import model.disk.Directory;
import utility.CmdUtil;
import view.ui.IconManager.Type;

public class Controller {

	private static final String[] CREATE_ERROR = {"创建成功","该目录文件数超过最大限制","容量不足","文件已存在","文件后缀名不正确"};
	private static Controller controller = new Controller();
	private Console console;

	public static Controller getInstance() {
		return controller;
	}

	private Controller() {
		console = IconManager.getInstance().getWindow(Type.CMD).getConsole();
	}

	public void setMessage(String operation, String route) {
		Directory directory = CmdUtil.findDirectory(route);
		String[] temps = operation.split("\\s+");
		String operate = temps[0];
		switch (operate) {
		case "create":
			String[] fileName = temps[1].split("\\.");
			console.addMsg(CREATE_ERROR[CmdUtil.creatFiles(directory, fileName[0], fileName[1])]);
			break;
		case "cd":
			if (CmdUtil.findDirectory(route + temps[1]) != null) {
				console.setRoute(route + temps[1] + "\\");
			} else {
				console.addMsg("当前文件夹不存在");
			}
			break;
		case "delete":

			break;
		case "type":

			break;
		case "copy":

			break;
		case "mkdir":
			switch (CmdUtil.creatDirectory(directory, temps[1])) {
			case 0:
				break;
			case 1:
				System.out.println("该目录文件数超过最大限制");
				break;
			case 2:
				System.out.println("容量不足");
				break;
			case 3:
				System.out.println("文件已存在");
				break;
			default:
				break;
			}

			break;
		case "rmdir":

			break;
		default:
			console.addMsg("没有这条指令");
			break;
		}
	}

}
