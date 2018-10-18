package view.ui;

import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Console extends TextArea {
	private final String DEFAULT_ROUTE = "root:\\";
	private String currentRoute;

	public Console() {
		currentRoute = DEFAULT_ROUTE;
		this.setText(currentRoute);
		this.setStyle("-fx-background-insets: 0");
		this.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 20));
		this.positionCaret(getLength());
		this.setWrapText(true);
		init();
	}

	private void init() {
		this.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.LEFT
					|| e.getCode() == KeyCode.RIGHT) {
				this.positionCaret(getLength());
			}
		});

		this.setOnMouseClicked(e -> {
			this.positionCaret(getLength());
		});

		this.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String[] temps = newValue.split("\n");
				String lastLine = temps[temps.length - 1];
				if (!lastLine.contains(currentRoute)) {
					setText(oldValue);
				}
			}
		});

		this.setOnKeyPressed(e->{
			if(e.getCode() == KeyCode.ENTER){
				String current = getText();
				String[] tmp = current.split("\n");
				String lastLine = tmp[tmp.length-1];
				if(lastLine.length()>currentRoute.length()){
					String oparetion = (lastLine.substring(currentRoute.length(),lastLine.length())).trim();
					Controller.getInstance().setMessage(oparetion, currentRoute);
				}
//				String string = getText();
//				Main.test("-", string, '-');
//				setText(string);
			}
		});

	}

	@Override
	public void replaceText(int start, int end, String text) {
		String current = getText();
		if (!current.substring(start).contains("\n")) {
			super.replaceText(start, end, text);
		}
	}

	@Override
	public void replaceSelection(String text) {
		if(!text.equals("\n"))return;
		String current = getText();
		int selectionStart = getSelection().getStart();
		if(selectionStart==0)return;
		if (!current.substring(selectionStart).contains("\n")) {
			super.replaceSelection("\n" + currentRoute);
		}
	}

	public String getRoute() {
		return currentRoute;
	}

	public void setRoute(String route) {
		this.currentRoute = route;
	}

	public String getDEFAULT_ROUTE() {
		return DEFAULT_ROUTE;
	}
}