package view.cpu;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class DevicePanes {
	private Pane pane;
	private Label idLabel;
	private Label timeLabel;
	public DevicePanes(Pane pane, Label idLabel, Label timeLabel) {
		this.pane = pane;
		this.idLabel = idLabel;
		this.timeLabel = timeLabel;
	}
	public void setID(int value){
		idLabel.setText("#" + String.valueOf(value));
	}

	public void setTime(int value){
		timeLabel.setText(String.valueOf(value));
	}
	public void setVisible(boolean value){
		pane.setVisible(value);
		idLabel.setVisible(value);
		timeLabel.setVisible(value);
	}
}
