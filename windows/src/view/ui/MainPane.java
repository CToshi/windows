package view.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;

public class MainPane extends Pane {

	private static MainPane mainPane = new MainPane();

	public static MainPane getInstance() {
		return mainPane;
	}

	private MainPane() {
		init();
	}

	private void init() {
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		String url = "images/backGround.png";
		Image image = new Image(url, true);
		this.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, null, new BackgroundSize(screensize.getWidth(),
						screensize.getHeight() - TaskBar.getHboxMaxSize(), false, false, false, false))));
		Icon[] icons = IconManager.getIcons();
		for (int i = 0; i < icons.length; i++) {
			this.getChildren().add(icons[i]);
		}
		this.setOnMouseClicked(e -> {
			TaskBar.canselMenu();
			TaskBar.Selected();
			SecondaryMenu.disappear();
			if (!IconManager.isBeClick())
				IconManager.canselSelected();
			if (e.getButton() == MouseButton.SECONDARY) {
				SecondaryMenu.display(e.getX(), e.getY(), SecondaryMenu.getPriority());
			}
		});
	}

	public void reStart() {
		Platform.runLater(() -> {
			disappear(IconManager.getIcons());
			IconManager.getInstance().initIcon();
		});
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					display(IconManager.getIcons());
				});
			}
		}, 100);
	}

	public void addIcon(Icon icon) {
		this.getChildren().add(icon);
	}

	public static void display(Node[] nodes) {
		for (Node node : nodes) {
			MainPane.getInstance().getChildren().add(node);
		}
	}

	public static void display(Node node) {
		MainPane.getInstance().getChildren().add(node);
	}

	public static void disappear(Node[] nodes) {
		for (Node node : nodes) {
			MainPane.getInstance().getChildren().remove(node);
		}
	}

	public static void disappear(Node node) {
		MainPane.getInstance().getChildren().remove(node);
	}
}
