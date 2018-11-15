package view.ui;


import application.Main;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
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
	private static final String HELP_CONTENT = "cmd指令："
			+ "\nll: 显示当前路径下的文件"
			+ "\ncreate a.txt：创建一个名为a的txt文件，后缀名可为txt或exe"
			+ "\ndelete a: 删除一个名为a的文件"
			+ "\nmkdir haha：创建一个名为haha的文件夹"
			+ "\nrmdir haha：删除一个名为haha的空文件夹"
			+ "\ncopy a haha：复制文件a到haha目录下"
			+ "\nvim a：编辑一个文件，包括txt和exe"
			+ "\ncd haha：进入一个文件夹，.和..分别表示当前目录和上一级目录"
			+ "\ntype a：表示显示一个文件的内容"
			+ "\n（目录和文件名支持绝对路径，即以\\root开头，以及当前路径.\\开头）";

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
		BorderPane borderPane = new BorderPane();
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("文件");
		MenuItem menuItem = new MenuItem("保存");
		menuItem.setOnAction(e->{
			file.changeFilesContent(textArea.getText());
		});
		menu.getItems().add(menuItem);
		menuBar.getMenus().add(menu);
		menuBar.prefWidthProperty().bind(this.widthProperty());
		menuBar.prefHeight(50);
		textArea = new TextArea();
		textArea.prefWidthProperty().bind(root.widthProperty());
		textArea.prefHeightProperty().bind(root.heightProperty());
		textArea.setText(file.getContent());
		textArea.positionCaret(textArea.getLength());
		borderPane.setTop(menuBar);
		borderPane.setCenter(textArea);
		scene.setRoot(borderPane);
		this.setOnCloseRequest(e->{
			MsgWindow msgWindow = new MsgWindow(Main.getPrimaryStage());
			Button[] buttons = new Button[]{
					new Button("保存并退出"),
					new Button("退出"),
					new Button("取消"),
			};
			buttons[0].setOnAction(action->{
				file.changeFilesContent(textArea.getText());
				msgWindow.close();
				this.close();
				TaskBar.removeWindow(fileName, this);
			});
			buttons[1].setOnAction(action->{
				msgWindow.close();
				this.close();
				TaskBar.removeWindow(fileName, this);
			});
			buttons[2].setOnAction(action->{
				msgWindow.close();
			});
			msgWindow.addButtons(buttons);
			if(!textArea.getText().equals(file.getContent())){
				e.consume();
				msgWindow.show();
			}else{
				TaskBar.removeWindow(fileName, this);
			}
		});
		this.setTitle(fileName);
	}

	private void createExeWindow(Files file){
		BorderPane borderPane = new BorderPane();
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("文件");
		MenuItem menuItem = new MenuItem("保存");
		menuItem.setOnAction(e->{
			file.changeFilesContent(textArea.getText());
		});
		menu.getItems().add(menuItem);
		menuBar.getMenus().add(menu);
		menuBar.prefWidthProperty().bind(this.widthProperty());
		menuBar.prefHeight(50);
		textArea = new TextArea();
		textArea.prefWidthProperty().bind(root.widthProperty());
		textArea.prefHeightProperty().bind(root.heightProperty());
		textArea.setText(file.getContent());
		textArea.positionCaret(textArea.getLength());
		borderPane.setTop(menuBar);
		borderPane.setCenter(textArea);
		scene.setRoot(borderPane);
		this.setOnCloseRequest(e->{
			MsgWindow msgWindow = new MsgWindow(Main.getPrimaryStage());
			Button[] buttons = new Button[]{
					new Button("保存并退出"),
					new Button("退出"),
					new Button("取消"),
			};
			buttons[0].setOnAction(action->{
				file.changeFilesContent(textArea.getText());
				msgWindow.close();
				this.close();
				TaskBar.removeWindow(fileName, this);
			});
			buttons[1].setOnAction(action->{
				msgWindow.close();
				this.close();
				TaskBar.removeWindow(fileName, this);
			});
			buttons[2].setOnAction(action->{
				msgWindow.close();
			});
			msgWindow.addButtons(buttons);
			if(!textArea.getText().equals(file.getContent())){
				e.consume();
				msgWindow.show();
			}else {
				TaskBar.removeWindow(fileName, this);
			}
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
		Label content = new Label();
		this.setX((primaryScreenBounds.getWidth()-700)/2);
		this.setY((primaryScreenBounds.getHeight()-350)/2);
		this.setWidth(700);
		this.setHeight(350);
		Font font = Font.font("LiSu", FontWeight.BOLD, FontPosture.ITALIC, 20);
		content.setFont(font);
		content.setText(HELP_CONTENT);
		content.setTextAlignment(TextAlignment.LEFT);
		root.getChildren().add(content);
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
		this.setY(primaryScreenBounds.getHeight()/2-362);
		this.setWidth(1280);
		this.setHeight(785);
		this.setTitle(fileName);
	}

	public String getFileName() {
		return fileName;
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
