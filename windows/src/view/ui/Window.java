package view.ui;

import com.sun.java.swing.plaf.windows.resources.windows;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.disk.Files;
import view.cpu.CPUWindow;
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
	private TextArea textArea;

	public Window(Stage stage,Type type, Files files) {
		this.initOwner(stage);
		this.type = type;
		this.fileName = files.getFileName();
		init();
		switch (type) {
		case TXT:
			createTxtWindow(files);
			break;
		case EXE:
			createExeWindow(files);
			break;
		default:
			System.out.println("Window的switch 出问题了！！！");
			break;
		}
	}

	public Window(Stage stage,Type type,String fileName) {
		this.initOwner(stage);
		this.type = type;
		this.fileName = fileName;
		init();
		switch (type) {
		case HELP:
			createHelpWindow();
			break;
		case FOLDER:
			createFolderWindow();
			break;
		case CMD:
			createCMDWindow();
			break;
		case CPU:
			createCPUWindow();
			break;
		default:
			System.out.println("Window的switch 出问题了！！！");
			break;
		}
	}

	public void init() {
		primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		this.setX(primaryScreenBounds.getWidth()/4);
		this.setY(primaryScreenBounds.getHeight()/4);
		this.setWidth(primaryScreenBounds.getWidth()/2);
		this.setHeight(primaryScreenBounds.getHeight()/2);
		this.console = null;
		this.textArea = null;
		this.root = new Pane();
		this.scene = new Scene(root);
		this.setScene(scene);
		this.setResizable(false);
		this.setOnShowing(e -> {
			TaskBar.addWindow(fileName, this);
		});
		this.setEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			TaskBar.Selected();
		});
	}

	private void createCMDWindow(){
		console = new Console();
		console.prefWidthProperty().bind(root.widthProperty());
		console.prefHeightProperty().bind(root.heightProperty());
		root.getChildren().add(console);
		this.setOnCloseRequest(e->{
			TaskBar.removeWindow(fileName, this);
			console.initConsole();
		});
		this.setTitle(fileName);
	}

	private void createTxtWindow(Files file) {
		textArea = new TextArea();
		textArea.prefWidthProperty().bind(root.widthProperty());
		textArea.prefHeightProperty().bind(root.heightProperty());
		textArea.setText(file.getContent());
		root.getChildren().add(textArea);
		this.setOnCloseRequest(e->{
			TaskBar.removeWindow(fileName, this);
			file.changeFilesContent(textArea.getText());
		});
		this.setTitle(fileName);
	}

	private void createExeWindow(Files file){
		textArea = new TextArea();
		textArea.prefWidthProperty().bind(root.widthProperty());
		textArea.prefHeightProperty().bind(root.heightProperty());
		textArea.setText(file.getContent());
		root.getChildren().add(textArea);
		this.setOnCloseRequest(e->{
			TaskBar.removeWindow(fileName, this);
			file.changeFilesContent(textArea.getText());
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

	private void createCPUWindow(){
		this.setOnCloseRequest(e->{
			TaskBar.removeWindow(fileName, this);
		});
		scene.setRoot(CPUWindow.getInstance().getMainPane());
		this.setX(primaryScreenBounds.getWidth()/2-640);
		this.setY(primaryScreenBounds.getHeight()/2-350);
		this.setWidth(1280);
		this.setHeight(700);
		this.setTitle(fileName);
	}

	public TextArea getTextArea() {
		return textArea;
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

}
