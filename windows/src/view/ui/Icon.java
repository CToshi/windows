package view.ui;

import application.Main;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import view.ui.IconManager.Type;

public class Icon extends Label {

	private Image image;
	private ImageView imageView;
	private Border stroke;
	private Window window;
	// private String fileName;
	private Type type;
	private static final Color ON_COLOR = new Color(1, 1, 1, 0.2);
	private static final Color CLICK = new Color(0.4, 0.8, 1, 0.2);
	private boolean isClick = false;

	public Icon(String url, double width, double height, double x, double y, Type type, String fileName) {
		this.type = type;
		this.image = new Image(url);
		imageView = new ImageView(image);
		imageView.setFitHeight(height);
		imageView.setFitWidth(width);
		this.setGraphic(imageView);
		// this.setWidth(width);
		// this.setHeight(height);
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.setText(fileName);
		this.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 15));
		this.setTextFill(Color.WHITE);
		this.setContentDisplay(ContentDisplay.TOP);
		init();
	}

	private void init() {
		BorderStroke borderStroke = new BorderStroke(Color.WHITE, BorderStrokeStyle.DASHED, new CornerRadii(0),
				new BorderWidths(1));
		stroke = new Border(borderStroke);
		if (type == Type.HELP) {
			window = IconManager.getHelp();
		} else if (type == Type.FOLDER) {
			window = IconManager.getFolder();
		} else if (type == Type.CMD) {
			window = IconManager.getCmd();
		}
		this.setOnMouseClicked(e -> {
			if (!isClick) {
				IconManager.canselSelected();
				IconManager.canselBorder();
				this.setBorder(stroke);
				this.setBackground(new Background(new BackgroundFill(CLICK, null, null)));
				isClick = true;
				IconManager.setBeClick(true);
			} else {
				this.setBackground(new Background(new BackgroundFill(ON_COLOR, null, null)));
				isClick = false;
				IconManager.setBeClick(false);
			}
			if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() % 2 == 0) {
				if (type == Type.TXT) {
					window = new Window(Main.getPrimaryStage(),  type, this.getText());
					window.show();
				} else {
					if (window.isShowing())
						window.toFront();
					else
						window.show();
				}
				isClick = false;
				canselBorder();
				canselSelected();
				IconManager.setBeClick(false);
			}
		});
		this.setOnMouseEntered(e -> {
			SecondaryMenu.setPriority(SecondaryMenu.ICON);
			if (!isClick)
				this.setBackground(new Background(new BackgroundFill(ON_COLOR, null, null)));
			else
				this.setBackground(new Background(new BackgroundFill(CLICK, null, null)));
		});
		this.setOnMouseExited(e -> {
			SecondaryMenu.setPriority(SecondaryMenu.BACKGROUND);
			if (!isClick)
				this.setBackground(null);
			else
				this.setBackground(new Background(new BackgroundFill(ON_COLOR, null, null)));
			IconManager.setBeClick(false);
		});
	}

	public void canselSelected() {
		this.setBackground(null);
		this.isClick = false;
	}

	public void canselBorder() {
		this.setBorder(null);
	}

	public String getFileName() {
		return this.getText();
	}

	public void setFileName(String fileName) {
		this.setText(fileName);
	}
}
