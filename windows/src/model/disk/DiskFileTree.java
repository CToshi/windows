package model.disk;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;

public class DiskFileTree extends TreeView<FileItem> {
	/**
	  *  单例模式生成目录树
	 */
	private static DiskFileTreeItem rootItem = new DiskFileTreeItem(new Disk().getRoot());
	private static DiskFileTree diskFileTree = new DiskFileTree(rootItem);
	
	private DiskFileTree(DiskFileTreeItem root) {
		super(root);
		setCellFactory((TreeView<FileItem> treeview) -> new DiskFileTreeCell());
		addContextMenu();
	}

	private void addContextMenu() {
		ContextMenu contextMenu = new ContextMenu();
		contextMenu.getItems().addAll(addMenu(), addDeleteMenuItem());
		
		setOnMouseClicked(e -> {
			if (e.getTarget().toString().endsWith("\'null\'")) {
				getSelectionModel().clearSelection();
				contextMenu.hide();
			} else {
				if (e.getButton() == MouseButton.SECONDARY) {
					contextMenu.hide();
					contextMenu.show(this, e.getScreenX(), e.getScreenY());
				} else {
					contextMenu.hide();
				}
			}
		});
	}
	
	private Menu addMenu() {
		Menu addMenu = new Menu("新建");
		addMenu.getItems().addAll(addTxtMenuItem(), addExeMenuItem(), addDirMenuItem());
		return addMenu;
	}
	
	private MenuItem addTxtMenuItem() {
		MenuItem addTxt = new MenuItem("TXT文件");
		addTxt.setOnAction(e->{
			TreeItem<FileItem> fatherItem = getSelectionModel().getSelectedItem();
			((Directory)fatherItem.getValue()).creatFile();
			fatherItem.getChildren();
		});
		return addTxt;
	}
	
	private MenuItem addExeMenuItem() {
		MenuItem addExe = new MenuItem("EXE文件");
		addExe.setOnAction(e->{
			
		});
		return addExe;
	}
	
	private MenuItem addDirMenuItem() {
		MenuItem addDir = new MenuItem("文件夹");
		addDir.setOnAction(e->{
			
		});
		return addDir;
	}
	
	private MenuItem addDeleteMenuItem() {
		MenuItem addDelete = new MenuItem("删除");
		addDelete.setOnAction(e->{
			
		});
		return addDelete;
	}
	
	public static DiskFileTree getInstance() {
		return diskFileTree;
	}
}
