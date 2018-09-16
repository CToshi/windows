package model.disk;

import javafx.scene.control.TreeCell;

public class DiskFileTreeCell extends TreeCell<FileItem> {

	public DiskFileTreeCell() {
		
	}

	@Override
	public void updateItem(FileItem fileItem, boolean empty) {
		super.updateItem(fileItem, empty);
		if (empty) {
			setText(null);
		} else {
			setText(fileItem.getFileName());
		}
	}

}
