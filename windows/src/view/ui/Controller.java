package view.ui;

import model.disk.Directory;
import utility.CmdUtil;
import view.ui.IconManager.Type;

public class Controller {

	private static Controller controller = new Controller();

	public static Controller getInstance() {
		return controller;
	}

	private Controller() {

	}

	public void setMessage(String operation, String route) {
		Directory directory = CmdUtil.findDirectory(route);
		Console console = IconManager.getInstance().getWindow(Type.CMD).getConsole();
		String[] temps = operation.split("\\s+");
		String operate = temps[0];
		switch (operate) {
		case "create":
			String[] fileName = temps[1].split("\\.");
			switch (CmdUtil.creatFiles(directory, fileName[0], fileName[1])) {
			case 0:
				System.out.println("创建成功");
				break;
			case 1:
				System.out.println("该目录文件数超过最大限制成功");
				break;
			case 2:
				console.setText(console.getText() + "\n\n容量不足!\n\n" + route);
				// console.positionCaret(console.getLength());
				break;
			case 3:
				System.out.println("文件已存在");
				break;
			case 4:
				System.out.println("文件后缀名不正确");
				break;
			default:
				break;
			}
			break;
		case "cd":
			if (CmdUtil.findDirectory(route + temps[1]) != null) {
				console.setRoute(route+temps[1]+"\\");
			}else{
				console.setText(console.getText() + "\n\n当前文件夹不存在!\n\n" + route);
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
			console.setText(console.getText() + "\n\n没有这条指令!\n\n" + route);
			console.positionCaret(console.getLength());
			break;
		}
	}

}
