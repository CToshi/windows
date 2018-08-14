package view.ui;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class MenuBar extends javafx.scene.control.MenuBar{

	private Menu startMenu;
	private RootPane rootPane;
	public MenuBar(RootPane rootPane){
		initStartMenu();
		this.rootPane = rootPane;
		this.getMenus().addAll(startMenu);
	}

	private void initStartMenu(){
		startMenu = new Menu("开始");
		MenuItem toShi = new MenuItem("ToShi");
		MenuItem siCheng = new MenuItem("SiCheng");
		MenuItem jiaRu = new MenuItem("JiaRu");
		MenuItem close = new MenuItem("关机");
		close.setOnAction(e->{
			rootPane.exit();
		});
		startMenu.getItems().addAll(toShi,siCheng,jiaRu,close);
	}
}
