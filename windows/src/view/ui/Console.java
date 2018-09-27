package view.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Console extends TextArea {
	public static final String DEFAULT_ROUTE = "root:\\";
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
				System.out.println(newValue + "," + oldValue);
				String lastLine = temps[temps.length - 1];
				// if(lastLine.contains(currentRoute)){
				// System.out.println(233);
				// }
				if (!lastLine.contains(currentRoute)) {
					System.out.println(lastLine + "," + currentRoute);
					// setText(oldValue);
				}
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
		String current = getText();
		int selectionStart = getSelection().getStart();
		if (!current.substring(selectionStart).contains("\n")) {
			super.replaceSelection(text + text + currentRoute);
		}
	}

	public String getRoute() {
		return currentRoute;
	}

	public void setRoute(String route) {
		this.currentRoute = route;
	}
}