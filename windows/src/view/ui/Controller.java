package view.ui;

import model.disk.Directory;
import utility.CmdUtil;
import view.ui.IconManager.Type;

public class Controller {

	private static final String[] CREATE_ERROR = {"�����ɹ�","��Ŀ¼�ļ��������������","��������","�ļ��Ѵ���","�ļ���׺������ȷ"};
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
				console.addMsg("��ǰ�ļ��в�����");
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
				System.out.println("��Ŀ¼�ļ��������������");
				break;
			case 2:
				System.out.println("��������");
				break;
			case 3:
				System.out.println("�ļ��Ѵ���");
				break;
			default:
				break;
			}

			break;
		case "rmdir":

			break;
		default:
			console.addMsg("û������ָ��");
			break;
		}
	}

}
