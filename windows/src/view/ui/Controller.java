package view.ui;

import java.util.ArrayList;

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

	/**
	 * 所有的指令：
	 * ll   		显示文件夹内容
	 * cd 		 	入文件夹或者上一级目录
	 * mkdir	 	创建文件夹
	 * create 		创建文件
	 * vim			编辑文件
	 * copy 		复制文件
	 * delete		删除文件
	 * rmdir		删除空目录
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
				console.addMsg("没有这条指令");
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
					console.addMsg("文件夹不存在");
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

}
