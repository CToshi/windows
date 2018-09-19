package model.disk;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DiskFileTreeItem extends TreeItem<FileItem>{
	private final Node directoryIcon = new ImageView(
			new Image(getClass().getResourceAsStream("../../images/directory.png")));
	private final Node filesIcon = new ImageView(new Image(getClass().getResourceAsStream("../../images/files.png")));
	
	public DiskFileTreeItem(FileItem fileItem) {
		super(fileItem);
		if (fileItem instanceof Directory) {
			setGraphic(directoryIcon);
		} else {
			setGraphic(filesIcon);
		}
	}

}
