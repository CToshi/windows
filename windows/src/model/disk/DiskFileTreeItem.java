package model.disk;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DiskFileTreeItem extends TreeItem<FileItem>{
	private final ImageView directoryIcon = new ImageView(
			new Image(getClass().getResourceAsStream("../../images/folder.png"), 30, 30, true, true));
	private final ImageView filesIcon = new ImageView(new Image(getClass().getResourceAsStream("../../images/txt.png"), 30, 30, true, true));
	
	public DiskFileTreeItem(FileItem fileItem) {
		super(fileItem);
		if (fileItem instanceof Directory) {
			setGraphic(directoryIcon);
		} else {
			setGraphic(filesIcon);
		}
	}

}
