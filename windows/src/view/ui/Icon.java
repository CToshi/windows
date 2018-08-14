package view.ui;

import application.Main;
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
import javafx.stage.Stage;
import view.ui.Window.Type;

public class Icon extends Label {

	private Image image;
	private ImageView imageView;
	private Border stroke;
	private Stage window;
	private Type type;
	private static final Color ON = new Color(1, 1, 1, 0.2);
	private static final Color CLICK = new Color(0.4, 0.8, 1, 0.2);
	private static final Color UNSELECTED = new Color(0.4, 0.8, 1, 0);
	private boolean isClick = false;

	public Icon(Image image, double width, double height, double x, double y, Type type) {
		this.type = type;
		this.image = image;
		imageView = new ImageView(image);
		this.setGraphic(imageView);
		this.setWidth(width);
		this.setHeight(height);
		this.setLayoutX(x);
		this.setLayoutY(y);
		init();
	}

	private void init(){
		BorderStroke borderStroke = new BorderStroke(Color.WHITE, BorderStrokeStyle.DASHED, new CornerRadii(0),
				new BorderWidths(1));
		stroke = new Border(borderStroke);
		window = new Window(Main.getPrimaryStage(), this.image, type);
		this.setOnMouseClicked(e -> {
			if(!isClick){
				IconManager.canselSelected();
				IconManager.canselBorder();
				this.setBorder(stroke);
				this.setBackground(new Background(new BackgroundFill(CLICK, null, null)));
				isClick = true;
				IconManager.setBeClick(true);
			}else {
				this.setBackground(new Background(new BackgroundFill(ON, null, null)));
				isClick = false;
				IconManager.setBeClick(false);
			}
			if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2){
				window.show();
				isClick = false;
				canselBorder();
				canselSelected();
				IconManager.setBeClick(false);
			}
		});
		this.setOnMouseEntered(e -> {
			if(!isClick)
				this.setBackground(new Background(new BackgroundFill(ON, null, null)));
			else
				this.setBackground(new Background(new BackgroundFill(CLICK, null, null)));
		});
		this.setOnMouseExited(e -> {
			if (!isClick)
				this.setBackground(null);
			else
				this.setBackground(new Background(new BackgroundFill(ON, null, null)));
			IconManager.setBeClick(false);
		});
	}

	public void canselSelected() {
		this.setBackground(null);
		this.isClick = false;
	}

	public void canselBorder(){
		this.setBorder(null);
	}

}
