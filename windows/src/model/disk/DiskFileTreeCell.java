package model.disk;

import javafx.scene.control.TreeCell;

public class DiskFileTreeCell extends TreeCell<DiskFile> {

	public DiskFileTreeCell() {
		
	}

	@Override
	public void updateItem(DiskFile diskFile, boolean empty) {
		super.updateItem(diskFile, empty);
		if (empty) {
			setText(null);
		} else {
			setText(diskFile.toString());
		}
	}

}
