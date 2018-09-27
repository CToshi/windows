package model.disk;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

public class DiskFileTreeItem extends TreeItem<FileItem> {
	private final ImageView directoryIcon = new ImageView(
			new Image(getClass().getResourceAsStream("../../images/folder.png"), 30, 30, true, true));
	private final ImageView txtFileIcon = new ImageView(
			new Image(getClass().getResourceAsStream("../../images/txt.png"), 30, 30, true, true));
	private final ImageView exeFileIcon = new ImageView(
			new Image(getClass().getResourceAsStream("../../images/exe.png"), 30, 30, true, true));

	public DiskFileTreeItem(FileItem fileItem) {
		super(fileItem);
		fileItem.setMyItem(this);
		if (fileItem instanceof Directory) {
			setGraphic(directoryIcon);
		} else {
			if (((Files) fileItem).isTxtFile()) {
				setGraphic(txtFileIcon);
			} else {
				setGraphic(exeFileIcon);
			}
		}
	}

}
