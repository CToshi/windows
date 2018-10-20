package view.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.IndexRange;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Console extends TextArea {
	private final String DEFAULT_ROUTE = "root:\\";
	private String currentRoute;
	private int preCount;
	private int lastPos;
	private boolean isMsg = false;

	public Console() {
		initConsole();
		this.setStyle("-fx-background-insets: 0");
		this.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 20));
		this.positionCaret(getLength());
		this.setWrapText(true);
		init();
	}

	private void init() {
		this.selectionProperty().addListener(new ChangeListener<IndexRange>() {
			@Override
			public void changed(ObservableValue<? extends IndexRange> observable, IndexRange oldValue,
					IndexRange newValue) {
				if (getCaretPosition() < preCount + currentRoute.length()) {
					positionCaret(lastPos);
				}
				lastPos = getCaretPosition();
			}
		});

		this.getChildrenUnmodifiable().addListener(new ListChangeListener<Node>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Node> c) {
				while (c.next()) {
					if (c.wasAdded()) {
						for (Node n : getChildrenUnmodifiable()) {
							if (n.getClass().isAssignableFrom(ScrollPane.class)) {
								// just trying to be cool here ^^
								ScrollPane sp = (ScrollPane) n;
								sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
							}
						}
					}
				}
			}
		});

		this.textProperty().addListener(new ChangeListener<String>() {
			private boolean isInSide = false;

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!isInSide && !isMsg) {
					isInSide = !isInSide;
					boolean isEnter = (newValue.substring(preCount, newValue.length())).contains("\n");
					if (isEnter) {
						setText(oldValue + "\n");
						if ((oldValue.substring(preCount + currentRoute.length(), oldValue.length())).trim()
								.length() != 0) {
							Controller.getInstance().setMessage(
									(oldValue.substring(preCount + currentRoute.length(), oldValue.length())).trim(),
									currentRoute);
						}
						setText(getText() + currentRoute);
						preCount = getLength() - currentRoute.length();
						positionCaret(getLength());
						setScrollTop(Double.MAX_VALUE);
					} else {
						if (!(newValue.substring(preCount, newValue.length())).contains(currentRoute)) {
							setText(oldValue);
							positionCaret(getLength());
							setScrollTop(Double.MAX_VALUE);
						}
					}
					isInSide = !isInSide;
				}
			}
		});

	}

	public void returnBack() {
		if (currentRoute.equals(DEFAULT_ROUTE))
			return;
		else {
			String[] temps = currentRoute.split("(:\\\\)|(\\\\)");
			currentRoute = DEFAULT_ROUTE;
			for (int i = 1; i < temps.length - 1; i++) {
				currentRoute = currentRoute + temps[i] + "\\";
			}
		}
	}

	public void initConsole() {
		currentRoute = DEFAULT_ROUTE;
		this.preCount = 0;
		this.setText(currentRoute);
		this.lastPos = getLength();
	}

	public void addMsg(String msg) {
		isMsg = !isMsg;
		setText(getText() + "\n" + msg + "\n\n");
		isMsg = !isMsg;
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