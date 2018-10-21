package view.ui;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class SecondaryMenu extends Label {

	private final Color BACKGROUND_COLOR = Color.WHITE;
	private static final Color ON = new Color(0.5, 0.5, 0.5, 1);
	private final double LABEL_WIDTH = 325;
	private final double LABEL_HEIGHT = 30;
	private Label[] backLabels;
	private Label[] iconLabels;
	private static final String[] BACK_STRINGS = { "    查看", "    排列方式", "    刷新", "    粘贴", "    在此处打开Powershell窗口" };
	private static final String[] ICON_STRINGS = { "    打开" };
	private Rectangle2D primaryScreenBounds;
	private final int BACKGROUND = 1;
	private final int ICON = 2;
	private int priority = BACKGROUND;
	private static SecondaryMenu secondaryMenu = new SecondaryMenu();

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
					if (!IconManager.getInstance().getWindow(IconManager.Type.CMD).isShowing()) {
						IconManager.getInstance().getWindow(IconManager.Type.CMD).show();
					} else {
						IconManager.getInstance().getWindow(IconManager.Type.CMD).toFront();
					}
				});
			}
			label.setOnMouseEntered(e -> {
				label.setBackground(new Background(new BackgroundFill(ON, null, null)));
			});
			label.setOnMouseExited(e -> {
				label.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, null, null)));
			});
			backLabels[i] = label;
		}

		for (int i = 0; i < ICON_STRINGS.length; i++) {
			Label label = new Label(ICON_STRINGS[i]);
			label.setMinSize(LABEL_WIDTH, LABEL_HEIGHT);
			label.setMaxSize(LABEL_WIDTH, LABEL_HEIGHT);
			label.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, null, null)));
			label.setOnMouseClicked(e -> {

			});
			iconLabels[i] = label;
		}
	}

	public void display(double mouseX, double mouseY, int type) {
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
			MainPane.getInstance().display(backLabels);
		} else if (type == ICON) {
			for (int i = 0; i < iconLabels.length; i++) {
				iconLabels[i].setLayoutX(positionX);
				iconLabels[i].setLayoutY(positionY);
				positionY = positionY + LABEL_HEIGHT;
			}
			MainPane.getInstance().display(iconLabels);
			priority = ICON;
		}
	}

	public void disappear() {
		MainPane.getInstance().disappear(backLabels);
		MainPane.getInstance().disappear(iconLabels);
	}

	public int getICON() {
		return ICON;
	}

	public int getBACKGROUND() {
		return BACKGROUND;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}
