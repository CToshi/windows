package view.cpu;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class ArrowPane extends BorderPane{

	public ArrowPane(DoubleBinding width){
		this();
		this.prefWidthProperty().bind(width);
	}
	public ArrowPane() {
//		Test.setBackground(this, Color.RED);
		Polygon polygon = new Polygon();
		this.setCenter(polygon);
//		this.getChildren().add(polygon);
		this.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double x = 0;
				double y = 0;
				double length = newValue.doubleValue();
				polygon.getPoints().clear();
				Double[] points = new Double[] { x, y + length / 2, x + length / 2, y,
						x + length/2, y + length / 4,
						x + length, y + length / 4,
						x + length, y + length * 0.75,
						x + length/2, y + length * 0.75,
						x + length / 2, y + length, };
				polygon.getPoints().addAll(points);
			}
		});

	}
}
