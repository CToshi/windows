package view.ui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;

public class IconName{

	private Label label;
	private TextField textField;

	public IconName(String name,double x,double y) {
		label = new Label(name);
		textField = new TextField();
		init(x,y);
	}

	private void init(double x,double y){
		label.setLayoutX(x);
		label.setLayoutY(y);
		textField.setLayoutX(x);
		textField.setLayoutY(y);
		label.setOnMouseClicked(e->{
			if(e.getButton() == MouseButton.PRIMARY && e.getClickCount()%2 == 0){

			}
		});
	}
}
