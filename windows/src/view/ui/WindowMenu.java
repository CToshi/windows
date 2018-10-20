package view.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;

import application.Main;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import view.ui.IconManager.Type;

public class WindowMenu{

	private final String[] TIPS = { "CPU", "此电脑", "帮助", "关机"};
	private final String[] ROUTES = { "images/txt.png", "images/computer.png", "images/help.png", "images/close.png"};
	private final Color BACKGROUND_COLOR = new Color(0, 0, 0, 0.7);
	private final Color ON_COLOR = new Color(0, 0, 0, 0.5);
	private final double LABEL_WIDTH = 140;
	private final double LABEL_HEIGHT = 50;
	private final double IMAGE_SIZE = 50;

	private Type type;
	private static ArrayList<EventHandler<MouseEvent>> handlers;
	private boolean showing = false;
	private boolean on = false;
	private VBox vBox;

//	private static final String[] FILENAMES = { "1", "2" };

	public WindowMenu(Type type) {
		init(type);
	}

	public void init(Type type) {
		this.type = type;
		vBox = new VBox();
		if(type == Type.START){
			initStart();
		}
	}

	public void addLabel(String text, Window window) {
		if(this.type == Type.HELP && getAmount() >= 1)
			return;
		Label label = new Label(text);
		label.setAlignment(Pos.CENTER);
		Rectangle rectangle = new Rectangle(LABEL_WIDTH, LABEL_HEIGHT);
		rectangle.setArcHeight(15);
		rectangle.setArcWidth(15);
		label.setShape(rectangle);
		label.setTextFill(Color.WHITE);
		label.setFont(Font.font("",FontWeight.LIGHT,20));
		label.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, null, null)));
		BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0),
				new BorderWidths(1));
		Border border = new Border(borderStroke);
		label.setBorder(border);
		label.setMaxSize(LABEL_WIDTH, LABEL_HEIGHT);
		label.setMinSize(LABEL_WIDTH, LABEL_HEIGHT);
		label.setOnMouseClicked(e -> {
			window.toFront();
		});
//		label.setLayoutY();
		vBox.getChildren().add(label);
	}

	public void removeLabel(String text) {
		ObservableList<Node> nodes = vBox.getChildren();
		Label label = null;
		for (int i = 0; i < nodes.size(); i++) {
			label = (Label) vBox.getChildren().get(i);
			if (label.getText().equals(text))
				break;
		}
		if (label == null)
			return;
		nodes.remove(label);
	}

	private void initStart() {
		vBox = new VBox();
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		double fitY = screensize.getHeight() - TaskBar.getInstance().getHboxMaxSize();
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
				if(!IconManager.getInstance().getWindow(IconManager.Type.FOLDER).isShowing()){
					IconManager.getInstance().getWindow(IconManager.Type.FOLDER).show();
				}else {
					IconManager.getInstance().getWindow(IconManager.Type.FOLDER).toFront();
				}
			}
		};

		EventHandler<MouseEvent> helpHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(!IconManager.getInstance().getWindow(IconManager.Type.HELP).isShowing()){
					IconManager.getInstance().getWindow(IconManager.Type.HELP).show();
				}else {
					IconManager.getInstance().getWindow(IconManager.Type.HELP).toFront();
				}
			}
		};

		EventHandler<MouseEvent> closeHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Main.getPrimaryStage().close();
			}
		};

		EventHandler<MouseEvent> cpuHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(!IconManager.getInstance().getWindow(IconManager.Type.CPU).isShowing()){
					IconManager.getInstance().getWindow(IconManager.Type.CPU).show();
				}else {
					IconManager.getInstance().getWindow(IconManager.Type.CPU).toFront();
				}
			}
		};
		handlers.addAll(Arrays.asList(cpuHandler,folderHandler, helpHandler, closeHandler));
	}

	public Type getType() {
		return type;
	}

	public int getAmount() {
		return vBox.getChildren().size();
	}

	public void setPos(double layoutX, double layoutY) {
		vBox.setLayoutX(layoutX);
		vBox.setLayoutY(layoutY);
	}

	public double getHeight() {
		return vBox.getChildren().size() * LABEL_HEIGHT;
	}

	public VBox getVBox() {
		return vBox;
	}

	public boolean isShowing() {
		return showing;
	}

	public void setShowing(boolean showging) {
		this.showing = showging;
	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}

	public double getLABEL_WIDTH() {
		return LABEL_WIDTH;
	}

	public double getLABEL_HEIGHT() {
		return LABEL_HEIGHT;
	}

	public double getIMAGE_SIZE() {
		return IMAGE_SIZE;
	}
}

