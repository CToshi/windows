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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import view.ui.IconManager.Type;

public class StartMenu implements FaMenu{

	private static final Color BACKGROUND_COLOR = new Color(0, 0, 0, 0.7);
	private static final Color ON_COLOR = new Color(0, 0, 0, 0.5);
	private final String[] ROUTES = { "images/folder.png", "images/help.png", "images/close.png" };
	private final String[] TIPS = { "文件夹", "帮助", "关机" };
	private static final String[] FILENAMES = { "1", "2" };
	public static final double LABEL_WIDTH = 140;
	private static final double LABEL_HEIGHT = 70;
	private static final double IMAGE_SIZE = 50;
	private static ArrayList<EventHandler<MouseEvent>> handlers;
	private boolean showing = false;
	private boolean on = false;
	private VBox vBox;

	public StartMenu() {
		init();
	}

	public void init() {
		vBox = new VBox();
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		double fitY = screensize.getHeight() - TaskBar.getHboxMaxSize();
		vBox.setLayoutX(0);
		vBox.setLayoutY(fitY - ROUTES.length * LABEL_HEIGHT);
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
			label.setTextFill(Color.WHITE);
			label.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, null, null)));
			label.setOnMouseClicked(handlers.get(i));
			label.setOnMouseEntered(e -> {
				label.setBackground(new Background(new BackgroundFill(ON_COLOR, null, null)));
			});
			label.setOnMouseExited(e -> {
				label.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, null, null)));
			});
			vBox.getChildren().add(label);
		}
	}

	private void initHandlers() {
		EventHandler<MouseEvent> folderHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Window window = new Window(Main.getPrimaryStage(), new Image(ROUTES[0]), Type.FOLDER, FILENAMES[0]);
				window.show();
			}
		};

		EventHandler<MouseEvent> helpHandler = new EventHandler<MouseEvent>() {
			Window window = new Window(Main.getPrimaryStage(), new Image(ROUTES[1]), Type.HELP, FILENAMES[1]);
			@Override
			public void handle(MouseEvent event) {
				window.show();
			}
		};

		EventHandler<MouseEvent> closeHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Main.getPrimaryStage().close();
			}
		};
		handlers.addAll(Arrays.asList(folderHandler, helpHandler, closeHandler));
	}

	@Override
	public VBox getVBox() {
		return vBox;
	}

	@Override
	public boolean isShowing() {
		return showing;
	}

	@Override
	public void setShowing(boolean showging) {
		this.showing = showging;
	}

	@Override
	public boolean isOn() {
		return on;
	}

	@Override
	public void setOn(boolean on) {
		this.on = on;
	}

}
