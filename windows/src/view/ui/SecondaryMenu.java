package view.ui;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class SecondaryMenu extends Label {

	private static final Color BACKGROUND_COLOR = Color.WHITE;
	private static final double LABEL_WIDTH = 325;
	private static final double LABEL_HEIGHT = 30;
	private static Label[] backLabels;
	private static Label[] iconLabels;
	private static final String[] BACK_STRINGS = { "    查看", "    排列方式", "    刷新", "    粘贴", "    在此处打开Powershell窗口" };
	private static final String[] ICON_STRINGS = { "    修改名字" };
	private static Rectangle2D primaryScreenBounds;
	private static SecondaryMenu secondaryMenu = new SecondaryMenu();
	public static final int BACKGROUND = 1;
	public static final int ICON = 2;
	private static int priority = BACKGROUND;

	public static SecondaryMenu getInstance() {
		return secondaryMenu;
	}

	private SecondaryMenu() {
		primaryScreenBounds = Screen.getPrimary().getBounds();
		backLabels = new Label[BACK_STRINGS.length];
		iconLabels = new Label[ICON_STRINGS.length];
		init();
	}

	private void init() {
		for (int i = 0; i < BACK_STRINGS.length; i++) {
			Label label = new Label(BACK_STRINGS[i]);
			label.setMinSize(LABEL_WIDTH, LABEL_HEIGHT);
			label.setMaxSize(LABEL_WIDTH, LABEL_HEIGHT);
			label.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, null, null)));
			if (i == 2) {
				label.setOnMouseClicked(e -> {
					MainPane.getInstance().reStart();
				});
			}
			if (i == 4) {
				label.setOnMouseClicked(e -> {
					if(!IconManager.getCmd().isShowing()){
						IconManager.getCmd().show();
					}else {
						IconManager.getCmd().toFront();
					}
				});
			}
			backLabels[i] = label;
		}

		for (int i = 0; i < ICON_STRINGS.length; i++) {
			Label label = new Label(ICON_STRINGS[i]);
			label.setMinSize(LABEL_WIDTH, LABEL_HEIGHT);
			label.setMaxSize(LABEL_WIDTH, LABEL_HEIGHT);
			label.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, null, null)));
			if (i == 0) {
				label.setOnMouseClicked(e -> {

				});
			}
			iconLabels[i] = label;
		}
	}

	public static void display(double mouseX, double mouseY, int type) {
		double positionX = Math.min(mouseX, primaryScreenBounds.getWidth() - LABEL_WIDTH);
		double positionY = mouseY;
		if (mouseY > primaryScreenBounds.getHeight() / 2) {
			positionY = mouseY - backLabels.length * LABEL_HEIGHT;
		}
		if (type == BACKGROUND && priority == BACKGROUND) {
			for (int i = 0; i < backLabels.length; i++) {
				backLabels[i].setLayoutX(positionX);
				backLabels[i].setLayoutY(positionY);
				positionY = positionY + LABEL_HEIGHT;
			}
			MainPane.display(backLabels);
		} else if (type == ICON) {
			for (int i = 0; i < iconLabels.length; i++) {
				iconLabels[i].setLayoutX(positionX);
				iconLabels[i].setLayoutY(positionY);
				positionY = positionY + LABEL_HEIGHT;
			}
			MainPane.display(iconLabels);
			priority = ICON;
		}
	}

	public static void disappear() {
		MainPane.disappear(backLabels);
		MainPane.disappear(iconLabels);
	}

	public static int getPriority() {
		return priority;
	}

	public static void setPriority(int priority) {
		SecondaryMenu.priority = priority;
	}
}
