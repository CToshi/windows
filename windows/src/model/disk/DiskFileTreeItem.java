package model.disk;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class DiskFileTreeItem extends TreeItem<FileItem>{
	private boolean isInit = false;
	
	public DiskFileTreeItem(FileItem fileItem) {
		super(fileItem);
	}
	
	@Override
	public ObservableList<TreeItem<FileItem>> getChildren() {
		if(!isInit) {
			isInit = true;
		}
		return super.getChildren();
	}

}
