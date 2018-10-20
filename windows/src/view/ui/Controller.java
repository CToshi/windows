package view.ui;

import java.util.ArrayList;

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

	/**
	 * ���е�ָ�
	 * ll   		��ʾ�ļ�������
	 * cd 		 	���ļ��л�����һ��Ŀ¼
	 * mkdir	 	�����ļ���
	 * create 		�����ļ�
	 * vim			�༭�ļ�
	 * copy 		�����ļ�
	 * delete		ɾ���ļ�
	 * rmdir		ɾ����Ŀ¼
	 * @param operation
	 * @param route
	 */
	public void setMessage(String operation, String route) {
		String[] temps = operation.split("\\s+");
		Directory directory = CmdUtil.findDirectory(route);
		String operate = temps[0];
		if(temps.length == 1){
			switch (operate) {
			case "ll":
				ArrayList<String> names = directory.getAllFilesName();
				String msg = ".\n..";
				for(int i=0;i<names.size();i++){
					msg = msg + "\n" + names.get(i);
				}
				console.addMsg(msg);
				break;
			default:
				console.addMsg("û������ָ��");
				break;
			}
		}else{
			if(temps[1].charAt(0) == '\\'){

			}else if(temps[1].charAt(0) == '.'){

			}
			switch (operate) {
			case "create":
				String[] fileName = temps[1].split("\\.");
				int result = CmdUtil.creatFiles(directory, fileName[0], fileName[1]);
				if(result!=0)
					console.addMsg(CREATE_ERROR[result]);
				break;
			case "cd":
				if (temps[1].equals(".")){
					break;
				}else if (temps[1].equals("..")) {
					console.returnBack();
				}else if(CmdUtil.findDirectory(route + temps[1])!=null){
					console.setRoute(route+temps[1]+"\\");
				}else {
					console.addMsg("�ļ��в�����");
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

}
