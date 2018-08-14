package view.ui;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class SecondaryMenu extends Label{

	private static final Color BACKGROUND_COLOR = Color.WHITE;
	private static final double LABEL_WIDTH = 325;
	private static final double LABEL_HEIGHT = 30;
	private static Label[] labels;
	private static final String[] TEXT_STRINGS = {"    查看","    排列方式","    刷新","    粘贴"};
	private static Rectangle2D primaryScreenBounds;
	private static SecondaryMenu secondaryMenu = new SecondaryMenu();

	public static SecondaryMenu getInstance(){
		return secondaryMenu;
	}

	private SecondaryMenu(){
		primaryScreenBounds = Screen.getPrimary().getBounds();
		labels = new Label[TEXT_STRINGS.length];
		init();
	}

	private void init() {
		for (int i = 0; i < TEXT_STRINGS.length; i++) {
			Label label = new Label(TEXT_STRINGS[i]);
			label.setMinSize(LABEL_WIDTH, LABEL_HEIGHT);
			label.setMaxSize(LABEL_WIDTH, LABEL_HEIGHT);
			label.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, null, null)));
			if(i==2){
				label.setOnMouseClicked(e->{
					MainPane.getInstance().reStart();
				});
			}
			labels[i] = label;
		}
	}

	public static void display(double mouseX,double mouseY){
		double positionX = Math.min(mouseX,primaryScreenBounds.getWidth()-LABEL_WIDTH);
		double positionY = mouseY;
		if(mouseY > primaryScreenBounds.getHeight()/2){
			positionY = mouseY - labels.length*LABEL_HEIGHT;
		}
		for (int i = 0; i < labels.length; i++) {
			labels[i].setLayoutX(positionX);
			labels[i].setLayoutY(positionY);
			positionY = positionY + LABEL_HEIGHT;
		}
		MainPane.display(labels);
	}

	public static void disappear(){
		MainPane.disappear(labels);
	}
}
