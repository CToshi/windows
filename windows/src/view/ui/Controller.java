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
				System.out.println("�����ɹ�");
				break;
			case 1:
				System.out.println("��Ŀ¼�ļ�������������Ƴɹ�");
				break;
			case 2:
				console.setText(console.getText() + "\n\n��������!\n\n" + route);
				// console.positionCaret(console.getLength());
				break;
			case 3:
				System.out.println("�ļ��Ѵ���");
				break;
			case 4:
				System.out.println("�ļ���׺������ȷ");
				break;
			default:
				break;
			}
			break;
		case "cd":
			if (CmdUtil.findDirectory(route + temps[1]) != null) {
				console.setRoute(route+temps[1]+"\\");
			}else{
				console.setText(console.getText() + "\n\n��ǰ�ļ��в�����!\n\n" + route);
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
			console.setText(console.getText() + "\n\nû������ָ��!\n\n" + route);
			console.positionCaret(console.getLength());
			break;
		}
	}

}
