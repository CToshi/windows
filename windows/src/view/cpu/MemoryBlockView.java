package view.cpu;

import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import model.memory.Memory;
import model.memory.MemoryBlock;

public class MemoryBlockView extends BorderPane{
	public MemoryBlockView(String text, double height) {
		Label label = new Label(text);
//		BorderPane borderPane = new BorderPane(label);
//		Pane rectanglePane = new Pane();
//		rectanglePane.setPrefHeight(height);
//		this.getChildren().addAll(borderPane, rectanglePane);
		this.setPrefHeight(height);
		this.setCenter(label);
//		this.setBackground(new Background(new BackgroundFill(color, null, null)));
		this.setStyle("-fx-border-color: #000000");
	}
}
