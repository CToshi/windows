package view.ui;

import application.Main;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import model.disk.Files;
import view.ui.IconManager.Type;

public class CreateWindows {

	private static CreateWindows createWindows = new CreateWindows();

	public static CreateWindows getInstance() {
		return createWindows;
	}

	private CreateWindows() {

	}

	public void create(Files file) {
		Type type = null;
		if (file.getFileExtentionName().equals(".txt"))
			type = Type.TXT;
		else if (file.getFileExtentionName().equals(".e"))
			type = Type.EXE;
		else
			System.out.println("目录树输入的文件扩展名错误");
		Window window = TaskBar.getWindow(file.getFileName());
		if (window != null) {
			window.toFront();
		} else {
			window = new Window(Main.getPrimaryStage(), type, file);
			window.show();
		}
	}

	public void create(String string){
		MsgWindow msgWindow = new MsgWindow(Main.getPrimaryStage());
		Label label = new Label(string);
		label.setTextAlignment(TextAlignment.CENTER);
		msgWindow.setAlwaysOnTop(true);
		msgWindow.show();
	}

}
