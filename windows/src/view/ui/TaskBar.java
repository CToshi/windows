package view.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import view.ui.IconManager.Type;

public class TaskBar extends HBox {

	private static final double HBOX_MAX_SIZE = 60;
	private static final Color SELECTED = new Color(1, 1, 1, 0.2);
	private static final Color UNSELECTED = new Color(1, 1, 1, 0);

	/**
	 * ROUTES和TYPES数组要一一对应
	 */
	private static final String[] ROUTES = { "images/help.png", "images/folder.png", "images/txt.png",
			"images/begin.png", "images/cmd.png" , "images/cpu.png", "images/exe.png"};
	private static final Type[] TYPES = { Type.HELP, Type.FOLDER, Type.TXT, Type.START ,Type.CMD, Type.CPU, Type.EXE};

	private static ArrayList<Window> windows;
	private static ArrayList<WindowMenu> menus;
	private static ArrayList<Label> labels;
	private Dimension screensize;
	private static double fitY;

	private static TaskBar taskBar = new TaskBar();

	public static TaskBar getInstance() {
		return taskBar;
	}

	private TaskBar() {
		this.setMaxHeight(HBOX_MAX_SIZE);
		this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0),
				new BorderWidths(1));
		Border border = new Border(borderStroke);
		this.setBorder(border);

		screensize = Toolkit.getDefaultToolkit().getScreenSize();
		fitY = screensize.getHeight() - TaskBar.getHboxMaxSize();
		windows = new ArrayList<Window>();
		menus = new ArrayList<WindowMenu>();
		labels = new ArrayList<Label>();

		for (int i = 0; i < TYPES.length; i++) {
			menus.add(new WindowMenu(TYPES[i]));
		}
		for (int i = 0; i < TYPES.length; i++) {
			WindowMenu menu = menus.get(i);
			Label label = new Label();
			label.setMaxSize(HBOX_MAX_SIZE, HBOX_MAX_SIZE);
			label.setMinSize(HBOX_MAX_SIZE, HBOX_MAX_SIZE);
			ImageView imageView = new ImageView(new Image(ROUTES[i], true));
			imageView.setFitHeight(menu.getIMAGE_SIZE());
			imageView.setFitWidth(menu.getIMAGE_SIZE());
			label.setGraphic(imageView);
			label.setAlignment(Pos.CENTER);
			label.setBorder(border);
			label.setOnMouseClicked(e -> {
				if (e.getButton() == MouseButton.PRIMARY) {
					if (!menu.isShowing()) {
						label.setBackground(new Background(new BackgroundFill(SELECTED, null, null)));
						menu.setShowing(true);
						MainPane.getInstance().display(menu.getVBox());
					} else {
						canselMenu();
					}
				}
			});
			label.setOnMouseEntered(e -> {
				label.setBackground(new Background(new BackgroundFill(SELECTED, null, null)));
				menu.setOn(true);
			});
			label.setOnMouseExited(e -> {
				if (!menu.isShowing()) {
					label.setBackground(new Background(new BackgroundFill(UNSELECTED, null, null)));
				}
				menu.setOn(false);
			});
			if (menu.getType() == Type.START) {
				this.getChildren().add(label);
			}
			labels.add(label);
		}
		this.setOnMouseClicked(e -> {
//			Selected();
			canselMenu();
		});
	}

	public static double getHboxMaxSize() {
		return HBOX_MAX_SIZE;
	}

	public static void addWindow(String fileName, Window window) {
		for (int i = 0; i < menus.size(); i++) {
			if (menus.get(i).getType() == window.getType()) {
				WindowMenu menu = menus.get(i);
				if (menu.getAmount() == 0) {
					getInstance().getChildren().add(labels.get(i));
				}
				menu.addLabel(fileName, window);
				window.setLabel(labels.get(i));
				menu.setPos(HBOX_MAX_SIZE * (getInstance().getChildren().indexOf(labels.get(i))),
						fitY - menu.getAmount() * menu.getLABEL_HEIGHT());
				// labels[i].setText(fileName);
				break;
			}
		}
		windows.add(window);
	}

	public static void removeWindow(String fileName, Window window) {
		for (int i = 0; i < menus.size(); i++) {
			WindowMenu menu = menus.get(i);
			if (menus.get(i).getType() == window.getType()) {
				menu.removeLabel(fileName);
				if (menu.getAmount() < 1) {
					getInstance().getChildren().remove(labels.get(i));
					break;
				}
			}
		}
		for(int i=0;i<menus.size();i++){
			WindowMenu menu = menus.get(i);
			if(menu.getAmount()>= 1){
				menu.setPos(HBOX_MAX_SIZE*getInstance().getChildren().indexOf(labels.get(i)),
						fitY - menu.getAmount() * menu.getLABEL_HEIGHT());
			}
		}
		windows.remove(window);
	}

	public static void canselMenu() {
		for (int i = 0; i < menus.size() && i < labels.size(); i++) {
			WindowMenu menu = menus.get(i);
			if (menu.isShowing() && !menu.isOn()) {
				labels.get(i).setBackground(new Background(new BackgroundFill(UNSELECTED, null, null)));
				MainPane.getInstance().disappear(menu.getVBox());
				menu.setShowing(false);
			}
		}
	}

	public static void Selected() {
		for (Window window : windows) {
			if (window.isFocused()) {
				window.getLabel().setBackground(new Background(new BackgroundFill(SELECTED, null, null)));
			} else {
				window.getLabel().setBackground(new Background(new BackgroundFill(UNSELECTED, null, null)));
			}
		}
	}

	public static Window getWindow(String fileName){
		for (Window window : windows) {
			if(window.getFileName() == fileName){
				return window;
			}
		}
		return null;
	}
}
