package view.ui;

import javafx.scene.image.Image;
import view.ui.Window.Type;

public class IconManager {

	private static Icon[] icons;
	private static IconManager iconManager = new IconManager();
	private final String[] ROUTES = { "images/help.png", "images/folder.png", "images/txt.png" };
	private final Type[] TYPES = { Type.HELP, Type.FOLDER, Type.TXT };
	private static final double ICON_WIDTH = 50;
	private static final double ICON_HEIGHT = 50;
	private static boolean isBeClick = false;

	public static IconManager getInstance() {
		return iconManager;
	}

	public void initIcon() {
		double x = 0;
		double y = 0;
		for (int i = 0; i < ROUTES.length; i++) {
			Image image = new Image(ROUTES[i]);
			Icon icon = new Icon(image, ICON_WIDTH, ICON_HEIGHT, x, y, TYPES[i]);
			icons[i] = icon;
			y = y + ICON_HEIGHT * 2;
		}
	}

	private IconManager() {
		icons = new Icon[ROUTES.length];
		initIcon();
	}

	public static Icon[] getIcons() {
		return icons;
	}

	public String[] getROUTES() {
		return ROUTES;
	}

	public static void canselSelected(){
		for (Icon icon : icons) {
			icon.canselSelected();
		}
	}

	public static void canselBorder(){
		for(Icon icon : icons){
			icon.canselBorder();
		}
	}

	public static boolean isBeClick() {
		return isBeClick;
	}

	public static void setBeClick(boolean isBeClick) {
		IconManager.isBeClick = isBeClick;
	}
}
