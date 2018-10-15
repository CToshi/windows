package view.ui;

import application.Main;

public class IconManager {

	public static enum Type {
		FOLDER, HELP, TXT, START, CMD
	}

	private Icon[] icons;
	private final String[] ROUTES = { "images/help.png", "images/computer.png", "images/txt.png" };
	private final Type[] TYPES = { Type.HELP, Type.FOLDER, Type.TXT };
	private final double ICON_WIDTH = 85;
	private final double ICON_HEIGHT = 85;
	private boolean isBeClick = false;
	private final String[] FILENAMES = { "帮助", "此电脑", "233" };
	private Window[] windows = { new Window(Main.getPrimaryStage(), Type.HELP, "帮助"),
			new Window(Main.getPrimaryStage(), Type.FOLDER, "此电脑"), null,
			new Window(Main.getPrimaryStage(), Type.CMD, "CMD") };
	private static IconManager iconManager = new IconManager();

	public static IconManager getInstance() {
		return iconManager;
	}

	private IconManager() {
		icons = new Icon[ROUTES.length];
		initIcon();
	}

	public void initIcon() {
		double x = 0;
		double y = 0;
		for (int i = 0; i < ROUTES.length; i++) {
			Icon icon = new Icon(ROUTES[i], ICON_WIDTH, ICON_HEIGHT, x, y, TYPES[i], FILENAMES[i], windows[i]);
			icons[i] = icon;
			y = y + ICON_HEIGHT + 30;
		}
	}

	public Icon[] getIcons() {
		return icons;
	}

	public String[] getROUTES() {
		return ROUTES;
	}

	public void canselSelected() {
		for (Icon icon : icons) {
			icon.canselSelected();
		}
	}

	public void canselBorder() {
		for (Icon icon : icons) {
			icon.canselBorder();
		}
	}

	public boolean isBeClick() {
		return isBeClick;
	}

	public void setBeClick(boolean isBeClick) {
		this.isBeClick = isBeClick;
	}

	public Window getWindow(Type type) {
		switch (type) {
		case HELP:
			return windows[0];
		case FOLDER:
			return windows[1];
		case CMD:
			return windows[3];
		default:
			System.out.println("getWindow出错，返回null");
			return null;
		}


	}

}
