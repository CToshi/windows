package view.ui;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Window extends Stage {

	private Image image;
	private Label label;
	private Pane root;
	private Scene scene;
	private Type type;
	private Rectangle2D primaryScreenBounds;

	public static enum Type {
		FOLDER, HELP, TXT
	}

	public Window(Stage stage, Image image,Type type) {
		this.root = new Pane();
		this.scene = new Scene(root);
		this.setScene(scene);
		this.initOwner(stage);
		this.image = image;
		this.label = new Label();
		this.type = type;
		init(type);
	}

	public void init(Type type) {
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(TaskBar.getHboxMaxSize());
		imageView.setFitHeight(TaskBar.getHboxMaxSize());
		label.setGraphic(imageView);
		label.setOnMouseClicked(e -> {
			this.toFront();
		});
		this.setOnShowing(e -> {
			TaskBar.addWindow(label, this);
		});
		this.setOnCloseRequest(e -> {
			TaskBar.removeWindow(label, this);
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
		}
	}

	public Label getLabel() {
		return label;
	}

	private void createTxtWindow() {
		this.setTitle("Txt");
	}

	private void createFolderWindow() {
		this.setTitle("Folder");
	}

	private void createHelpWindow() {
		this.setTitle("Help");
	}

	public Type getType() {
		return type;
	}

}
