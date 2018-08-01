package model.disk;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class DiskFileTreeItem extends TreeItem<DiskFile>{
	private boolean isInit = false;
	
	public DiskFileTreeItem(DiskFile diskFile) {
		super(diskFile);
	}
	
	@Override
	public ObservableList<TreeItem<DiskFile>> getChildren() {
		if(!isInit) {
			isInit = true;
		}
		return super.getChildren();
	}
	
//	@Override
//	public boolean isLeaf() {
//		if (getValue().isDirectory()) {
//			
//		}
//		return true;
//	}

}
