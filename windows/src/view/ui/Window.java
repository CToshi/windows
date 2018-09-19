package view.ui;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.ui.IconManager.Type;

public class Window extends Stage {

	private Label label;
	private Pane root;
	private Scene scene;
	private Type type;
	private Rectangle2D primaryScreenBounds;
	private String fileName;

	public Window(Stage stage, Image image,Type type,String fileName) {
		this.root = new Pane();
		this.scene = new Scene(root);
		this.setScene(scene);
		this.initOwner(stage);
		this.type = type;
		this.fileName = fileName;
		init(type);
	}

	public void init(Type type) {
		this.setOnShowing(e -> {
			TaskBar.addWindow(fileName, this);
		});
		this.setOnCloseRequest(e -> {
			TaskBar.removeWindow(fileName, this);
		});
		this.setEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			TaskBar.Selected();
		});
		primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		this.setX(primaryScreenBounds.getWidth()/4);
		this.setY(primaryScreenBounds.getHeight()/4);
		this.setWidth(primaryScreenBounds.getWidth()/2);
		this.setHeight(primaryScreenBounds.getHeight()/2);
		switch (type) {
		case HELP:
			createHelpWindow();
			break;
		case FOLDER:
			createFolderWindow();
			break;
		case TXT:
			createTxtWindow();
			break;
		case CMD:
			createCMDWindow();
			break;
		default:
			System.out.println("Window的switch 出问题了！！！");
			break;
		}
	}

	private void createCMDWindow(){
		
	}

	private void createTxtWindow() {
		this.setTitle(fileName +"Txt");
	}

	private void createFolderWindow() {
		this.setTitle(fileName + "Folder");
	}

	private void createHelpWindow() {
		this.setTitle(fileName + "Help");
	}

	public Type getType() {
		return type;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

}
