package view.ui;

import application.Main;
import model.disk.Files;
import view.ui.IconManager.Type;

public class CreateWindows {

	private static CreateWindows createWindows = new CreateWindows();

	public static CreateWindows getInstance(){
		return createWindows;
	}

	private CreateWindows(){

	}

	public void create(Files file){
		Type type = null;
		if(file.getFileExtentionName().equals(".txt"))type = Type.TXT;
		else if (file.equals(".exe"))type = Type.EXE;
		else System.out.println("目录树输入的文件扩展名错误");
		Window window = new Window(Main.getPrimaryStage(), type, file);
		window.show();
	}

}
