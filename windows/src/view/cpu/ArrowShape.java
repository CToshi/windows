package view.cpu;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import application.Main;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import utility.Util;

public class ArrowShape extends Pane{
	private Polygon polygon;
	private double[] attrs = {};
	private DoubleBinding[] attrBindings;

	public ArrowShape(DoubleBinding[] attrs) {
		this.attrBindings = attrs;
		this.attrs = new double[attrBindings.length];
		polygon = new Polygon();
		for (int i = 0; i < this.attrs.length; i++) {
			this.attrs[i] = attrBindings[i].doubleValue();
			int index = i;
			attrBindings[i].addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					ArrowShape.this.attrs[index] =  newValue.doubleValue();
//					Main.test(index, ArrowShape.this.attrs[index]);
					updatePolygon();

				}
			});
		}
		updatePolygon();

	}

	private void updatePolygon() {
		double x = attrs[0], y = attrs[1], length = attrs[2];
		polygon.getPoints().clear();
		Double[] points = new Double[] { x, y + length / 2, x + length / 2, y,
				x + length/2, y + length / 4,
				x + length, y + length / 4,
				x + length, y + length * 0.75,
				x + length/2, y + length * 0.75,
				x + length / 2, y + length, };
		polygon.getPoints().addAll(points);
	}

	public Collection<Node> getNodes() {
		return Util.list(new Node[] { polygon });
	}
}
