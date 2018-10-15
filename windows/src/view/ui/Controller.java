package view.ui;

import utility.CmdUtil;

public class Controller {

	private Controller controller = new Controller();

	public Controller getInstance() {
		return controller;
	}

	private Controller() {

	}

	public void setMessage(String msg) {
		if(msg == null){
			System.out.println("信息为空");
		}
		String[] temps = msg.split("\\s{2,}|\t");
		String operate = temps[0];

		switch (operate) {
		case "create":
			break;
		case "delete":

			break;
		case "type":

			break;
		case "copy":

			break;
		case "mkdir":

			break;
		case "rmdir":

			break;
		default:
			System.out.println("没有这个指令");
			break;
		}
	}

}
