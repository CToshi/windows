package view.ui;

import java.util.ArrayList;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class RootPane extends BorderPane {

//	private MenuBar menuBar;
	private Stage stage;

	public RootPane(Stage stage) {
		this.stage = stage;
		this.prefWidthProperty().bind(stage.widthProperty());
		this.prefHeightProperty().bind(stage.heightProperty());

//		menuBar = new MenuBar(this);
//		menuBar.prefWidthProperty().bind(this.widthProperty());
		this.setCenter(MainPane.getInstance());
		this.setBottom(TaskBar.getInstance());
//		this.setBottom(menuBar);
	}

	public Stage getStage() {
		return stage;
	}

	public void exit(){
		stage.close();
	}

}
