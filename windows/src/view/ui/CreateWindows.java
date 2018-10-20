package view.ui;

import application.Main;
import view.ui.IconManager.Type;

public class CreateWindows {

	private static CreateWindows createWindows = new CreateWindows();

	public static CreateWindows getInstance(){
		return createWindows;
	}

	private CreateWindows(){

	}

	public void create(String fileExtentionName,String fileName, String content){
		Type type = null;
		if(fileName.equals("txt"))type = Type.TXT;
		else if (fileName.equals("exe"))type = Type.EXE;
		else System.out.println("目录树输入的文件扩展名错误");
		Window window = new Window(Main.getPrimaryStage(), type, fileName);
		window.getTextArea().setText(content);
		window.show();
	}

}
