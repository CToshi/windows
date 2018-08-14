package view.ui;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class TaskBar extends HBox {

	private static final double HBOX_MAX_SIZE = 50;
	private static TaskBar taskBar = new TaskBar();
	private static ArrayList<Window> windows;
	private static final Color SELECTED = new Color(1, 1, 1, 0.2);
	private static final Color UNSELECTED = new Color(1, 1, 1, 0);
	private static Label label;
	private static boolean showing = false;
	private static boolean signal = false;

	public static TaskBar getInstance() {
		return taskBar;
	}

	private TaskBar() {
		init();
	}

	private void init() {
		windows = new ArrayList<Window>();
		this.setMaxHeight(HBOX_MAX_SIZE);
		this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0),
				new BorderWidths(1));
		Border border = new Border(borderStroke);
		this.setBorder(border);
		Image image = new Image("images/begin.png", true);
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(HBOX_MAX_SIZE);
		imageView.setFitHeight(HBOX_MAX_SIZE);
		label = new Label();
		label.setGraphic(imageView);
		label.setOnMouseClicked(e -> {
			if (!showing) {
				showMenu();
				signal = true;
			} else {
				canselMenu();
			}
		});
		label.setOnMouseExited(e -> {
			signal = false;
		});
		this.getChildren().add(label);
		this.setOnMouseClicked(e -> {
			Selected();
			if (showing && !signal)
				canselMenu();
		});
	}

	public static double getHboxMaxSize() {
		return HBOX_MAX_SIZE;
	}

	public static void addWindow(Label label, Window window) {
		getInstance().getChildren().add(label);
		windows.add(window);
	}

	public static void removeWindow(Label label, Window window) {
		getInstance().getChildren().remove(label);
		windows.remove(window);
	}

	public static void showMenu() {
		label.setBackground(new Background(new BackgroundFill(SELECTED, null, null)));
		showing = true;
		MainPane.display(StartMenu.getLabels());
	}

	public static void canselMenu() {
		label.setBackground(new Background(new BackgroundFill(UNSELECTED, null, null)));
		MainPane.disappear(StartMenu.getLabels());
		showing = false;
	}

	public static void Selected() {
		for (Window window : windows) {
			if (window.isFocused()) {
				window.getLabel().setBackground(new Background(new BackgroundFill(SELECTED, null, null)));
			} else {
				window.getLabel().setBackground(new Background(new BackgroundFill(UNSELECTED, null, null)));
			}
		}
	}
}
