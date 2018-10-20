package view.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.IndexRange;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Console extends TextArea {
	private final String DEFAULT_ROUTE = "root:\\";
	private String currentRoute;
	private int preCount;
	private int lastPos;

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

		this.textProperty().addListener(new ChangeListener<String>() {
			private boolean isInSide = false;

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!isInSide) {
					isInSide = !isInSide;
					boolean isEnter = (newValue.substring(preCount,newValue.length())).contains("\n");
					if (isEnter) {
						if((oldValue.substring(preCount+currentRoute.length(),oldValue.length())).trim().length()!=0){
							Controller.getInstance().setMessage((oldValue.substring(preCount+currentRoute.length(),oldValue.length())).trim(),currentRoute);
						}else{
							setText(oldValue +"\n"+currentRoute);
						}
						preCount = getText().length() - currentRoute.length();
						positionCaret(getLength());
						setScrollTop(getHeight());
					} else {
						if (!(newValue.substring(preCount, newValue.length())).contains(currentRoute)) {
							setText(oldValue);
							positionCaret(getLength());
							setScrollTop(getHeight());
						}
					}
					isInSide = !isInSide;
				}
			}
		});

	}

	public void initConsole() {
		currentRoute = DEFAULT_ROUTE;
		this.preCount = 0;
		this.setText(currentRoute);
		this.lastPos = getLength();
	}

	public void addMsg(String msg) {
		setText(getText()+"\n"+ msg + "\n\n" + currentRoute);
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