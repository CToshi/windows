package view.ui;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.disk.DiskFileTreePane;
import view.ui.IconManager.Type;

public class Window extends Stage {

	private Label label;
	private Pane root;
	private Scene scene;
	private Type type;
	private Rectangle2D primaryScreenBounds;
	private String fileName;
	private Console console;

	public Window(Stage stage,Type type,String fileName) {
		this.console = null;
		this.root = new Pane();
		this.scene = new Scene(root);
		this.setScene(scene);
		this.initOwner(stage);
		this.type = type;
		this.fileName = fileName;
		this.setResizable(false);
		init(type);
	}

	public void init(Type type) {
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
		this.setOnShowing(e -> {
			TaskBar.addWindow(fileName, this);
		});
		this.setEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			TaskBar.Selected();
		});
		primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		this.setX(primaryScreenBounds.getWidth()/4);
		this.setY(primaryScreenBounds.getHeight()/4);
		this.setWidth(primaryScreenBounds.getWidth()/2);
		this.setHeight(primaryScreenBounds.getHeight()/2);
	}

	private void createCMDWindow(){
		Console console = new Console();
		console.prefWidthProperty().bind(root.widthProperty());
		console.prefHeightProperty().bind(root.heightProperty());
		setConsole(console);
		root.getChildren().add(console);
		this.setOnCloseRequest(e->{
			TaskBar.removeWindow(fileName, this);
			console.setRoute(console.getDEFAULT_ROUTE());
			console.setText(console.getDEFAULT_ROUTE());
			console.positionCaret(console.getLength());
		});
		this.setTitle("CMD");
	}

	private void createTxtWindow() {
		this.setOnCloseRequest(e->{
			TaskBar.removeWindow(fileName, this);
		});
		this.setTitle(fileName);
	}

	private void createFolderWindow() {
		this.setOnCloseRequest(e->{
			TaskBar.removeWindow(fileName, this);
		});
		root.getChildren().add(DiskFileTreePane.getInstance());
		DiskFileTreePane.getInstance().prefHeightProperty().bind(root.heightProperty());
		this.setTitle(fileName);
	}

	private void createHelpWindow() {
		this.setOnCloseRequest(e->{
			TaskBar.removeWindow(fileName, this);
		});
		this.setTitle(fileName);
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

	public Console getConsole() {
		if(console!=null)return console;
		return null;
	}

	private void setConsole(Console console) {
		this.console = console;
	}
}
