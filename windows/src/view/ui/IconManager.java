package view.ui;

import application.Main;
import javafx.scene.image.Image;

public class IconManager {

	public static enum Type {
		FOLDER, HELP, TXT, START, CMD
	}

	private static Icon[] icons;
	private final String[] ROUTES = { "images/help.png", "images/computer.png", "images/txt.png" };
	private final Type[] TYPES = { Type.HELP, Type.FOLDER, Type.TXT };
	private static final double ICON_WIDTH = 85;
	private static final double ICON_HEIGHT = 85;
	private static boolean isBeClick = false;
	private static final String[] FILENAMES = { "帮助", "此电脑", "233" };
	private static Window help;
	private static Window folder;
	private static Window cmd;
	private static IconManager iconManager = new IconManager();

	public static IconManager getInstance() {
		return iconManager;
	}

	public void initIcon() {
		double x = 0;
		double y = 0;
		for (int i = 0; i < ROUTES.length; i++) {
			Icon icon = new Icon(ROUTES[i], ICON_WIDTH, ICON_HEIGHT, x, y, TYPES[i], FILENAMES[i]);
			icons[i] = icon;
			y = y + ICON_HEIGHT + 30;
		}
	}

	private IconManager() {
		icons = new Icon[ROUTES.length];
		help = new Window(Main.getPrimaryStage(), Type.HELP, "帮助");
		folder = new Window(Main.getPrimaryStage(), Type.FOLDER, "此电脑");
		cmd = new Window(Main.getPrimaryStage(), Type.CMD, "CMD");
		initIcon();
	}

	public static Icon[] getIcons() {
		return icons;
	}

	public String[] getROUTES() {
		return ROUTES;
	}

	public static void canselSelected() {
		for (Icon icon : icons) {
			icon.canselSelected();
		}
	}

	public static void canselBorder() {
		for (Icon icon : icons) {
			icon.canselBorder();
		}
	}

	public static boolean isBeClick() {
		return isBeClick;
	}

	public static void setBeClick(boolean isBeClick) {
		IconManager.isBeClick = isBeClick;
	}

	public static Window getHelp() {
		return help;
	}

	public static Window getFolder() {
		return folder;
	}

	public static Window getCmd() {
		return cmd;
	}

}
