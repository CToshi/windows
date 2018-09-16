package view.disk;

import javafx.scene.layout.VBox;
import model.disk.DiskFileTree;

public class DiskFileTreePane extends VBox{
	private static DiskFileTreePane diskFileTreePane = new DiskFileTreePane();
	
	private DiskFileTreePane() {
		super.getChildren().add(DiskFileTree.getInstance());
	}
	
	public static DiskFileTreePane getInstance() {
		return diskFileTreePane;
	}
}
