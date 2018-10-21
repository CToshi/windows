package view.ui;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MsgWindow extends Stage {

	private BorderPane root;
	private HBox hBox;
	private VBox vBox;
	private Scene scene;
	private static final double DEFAULT_WIDTH = 300;
	private static final double DEFAULT_HEIGHT = 100;
	private Rectangle2D primaryScreenBounds;

	public MsgWindow(Stage stage) {
		this.primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		this.setAlwaysOnTop(true);
		this.initOwner(stage);
		this.setWidth(DEFAULT_WIDTH);
		this.setHeight(DEFAULT_HEIGHT);
		this.setX((primaryScreenBounds.getWidth() - DEFAULT_WIDTH) / 2);
		this.setY((primaryScreenBounds.getHeight() - DEFAULT_HEIGHT) / 2);
		this.root = new BorderPane();
		this.hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
		this.vBox = new VBox();
        vBox.setSpacing(40);
        vBox.getChildren().add(hBox);
        vBox.setAlignment(Pos.CENTER);
        root.setCenter(vBox);
		this.scene = new Scene(root);
		this.setScene(scene);
		this.setResizable(false);
	}

	public void addNode(Button[] buttons) {
		for (int i = 0; i < buttons.length; i++) {
//			buttons[i].setLayoutY((DEFAULT_HEIGHT - 50) / 2);
//			buttons[i].setLayoutX(i * (DEFAULT_WIDTH / 3)+20);
			hBox.getChildren().add(buttons[i]);
		}
	}

}
