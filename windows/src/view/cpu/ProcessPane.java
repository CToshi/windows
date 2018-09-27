package view.cpu;

import java.lang.reflect.Field;
import java.util.Collection;

import com.sun.org.apache.xml.internal.security.Init;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import utility.Util;

public class ProcessPane extends StackPane{
//	private Circle circle;
	private Label label;
	private Ellipse ellipse;
	private static Paint COLORS[];
	static {
		Field[] fields = Color.class.getFields();
		int[] indices = { 8, 10, 11, 12, 13, 15, 44, 48, 49, 53, 97 };
		COLORS = new Paint[11];
		for (int i = 0; i < COLORS.length; i++) {
			int index = indices[i];
			try {
				COLORS[i] = (Paint) fields[index].get(null);
				COLORS[i] = Color.WHITESMOKE;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public ProcessPane(int pid) {
//		Test.setBackground(this, Color.RED);
//		Circle circle = new Circle();
		ellipse = new Ellipse();
		ellipse.setStroke(Color.BLACK);
		ellipse.setFill(COLORS[pid]);
		ellipse.radiusXProperty().bind(this.widthProperty().multiply(0.45));
		ellipse.radiusYProperty().bind(this.widthProperty().multiply(0.45));
//		ellipse.radiusYProperty().bind(this.heightProperty().multiply(0.5));

		BorderPane circlePane = new BorderPane(ellipse);
//		circlePane.prefWidthProperty().bind(this.widthProperty());
//		circlePane.prefHeightProperty().bind(this.heightProperty());
		label = new Label("#" + String.valueOf(pid));
		BorderPane labelPane = new BorderPane(label);
//		labelPane.prefWidthProperty().bind(this.widthProperty());
//		labelPane.prefHeightProperty().bind(this.heightProperty());
		this.getChildren().addAll(circlePane, labelPane);
	}
	public ProcessPane(DoubleBinding r, int pid){
		this(pid);
		this.prefWidthProperty().bind(r);
		this.prefHeightProperty().bind(r);
	}
	public void setPid(int pid){
		label.setText("#" + String.valueOf(pid));
		ellipse.setFill(COLORS[pid]);
	}


}
