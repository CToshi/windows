package view.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;

import application.Main;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import view.ui.Window.Type;

public class StartMenu extends Label {

	private static final Color BACKGROUND_COLOR = new Color(0, 0, 0, 0.7);
	private static final Color ON = new Color(0, 0, 0, 0.5);
	private static Label[] labels;
	private static StartMenu startMenu = new StartMenu();
	private final String[] ROUTES = { "images/close.png", "images/help.png", "images/folder.png", "images/txt.png" };
	private final String[] TIPS = { "关机", "帮助", "文件夹", "文件" };
	private static final double LABEL_WIDTH = 140;
	private static final double LABEL_HEIGHT = 70;
	private static final double IMAGE_SIZE = 50;
	private static ArrayList<EventHandler<MouseEvent>> handlers;

	public static StartMenu getInstance() {
		return startMenu;
	}

	private StartMenu() {
		init();
	}

	public void init() {
		labels = new Label[ROUTES.length];
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		double fitY = screensize.getHeight() - TaskBar.getHboxMaxSize();
		handlers = new ArrayList<EventHandler<MouseEvent>>();
		initHandlers();
		for (int i = 0; i < ROUTES.length; i++) {
			Image image = new Image(ROUTES[i], true);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(IMAGE_SIZE);
			imageView.setFitHeight(IMAGE_SIZE);
			Label label = new Label(TIPS[i]);
			label.setGraphic(imageView);
			label.setMinSize(LABEL_WIDTH, LABEL_HEIGHT);
			label.setMaxSize(LABEL_WIDTH, LABEL_HEIGHT);
			label.setLayoutX(0);
			label.setLayoutY(fitY - (i + 1) * LABEL_HEIGHT);
			label.setTextFill(Color.WHITE);
			label.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, null, null)));
			label.setOnMouseClicked(handlers.get(i));
			label.setOnMouseEntered(e->{
				label.setBackground(new Background(new BackgroundFill(ON, null, null)));
			});
			label.setOnMouseExited(e->{
				label.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, null, null)));
			});
			labels[i] = label;
		}
	}

	private void initHandlers() {
		EventHandler<MouseEvent> closeHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Main.getPrimaryStage().close();
			}
		};

		EventHandler<MouseEvent> helpHandler = new EventHandler<MouseEvent>() {
			Window window = new Window(Main.getPrimaryStage(), new Image(ROUTES[1]), Type.HELP);
			@Override
			public void handle(MouseEvent event) {
				window.show();
			}
		};

		EventHandler<MouseEvent> folderHandler = new EventHandler<MouseEvent>() {
			Window window = new Window(Main.getPrimaryStage(), new Image(ROUTES[2]), Type.FOLDER);
			@Override
			public void handle(MouseEvent event) {
				window.show();
			}
		};

		EventHandler<MouseEvent> txtHandler = new EventHandler<MouseEvent>() {
			Window window = new Window(Main.getPrimaryStage(), new Image(ROUTES[3]), Type.TXT);
			@Override
			public void handle(MouseEvent event) {
				window.show();
			}
		};
		handlers.addAll(Arrays.asList(closeHandler,helpHandler,folderHandler,txtHandler));
	}

	public static Label[] getLabels() {
		return labels;
	}

}
