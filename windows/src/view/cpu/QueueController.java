package view.cpu;

import java.util.Collection;
import java.util.stream.Collectors;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.Pane;

public class QueueController {
	private Pane queuePane;
	public QueueController(Pane queuePane) {
		this.queuePane = queuePane;
	}
	public void update(Collection<Integer> idQueue) {
		queuePane.getChildren().clear();
		DoubleBinding height = queuePane.heightProperty().add(0);
		queuePane.getChildren()
				.addAll(idQueue.stream().map(pid -> new ProcessPane(height, pid)).collect(Collectors.toList()));
		queuePane.getChildren().add(new ArrowPane(height));
	}

}
