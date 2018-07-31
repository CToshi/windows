package view.disk;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import model.disk.DiskFile;
import model.disk.DiskFileTreeCell;
import model.disk.DiskFileTreeItem;

public class DiskFileTree extends TreeView<DiskFile> {
	/**
	  *  单例模式生成目录树
	 */
	
	private static DiskFileTreeItem root = new DiskFileTreeItem(new DiskFile("root"));
	private static DiskFileTree diskFileTree = new DiskFileTree(root);
	
	public DiskFileTree(DiskFileTreeItem root) {
		super(root);
		setCellFactory((TreeView<DiskFile> treeview) -> new DiskFileTreeCell());
		initContextMenu();
	}

	public void initContextMenu() {
		ContextMenu contextMenu = new ContextMenu();
		Menu addMenu = new Menu("新建");
		MenuItem addTxtMenuItem = new MenuItem("TXT文件");
		MenuItem addExeMenuItem = new MenuItem("EXE文件");
		MenuItem addDirMenuItem = new MenuItem("文件夹");
		MenuItem delelteMenuItem = new MenuItem("删除");
		
		addMenu.getItems().addAll(addTxtMenuItem, addExeMenuItem, addDirMenuItem);
		contextMenu.getItems().addAll(addMenu, delelteMenuItem);
		
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
	
	public static DiskFileTree getInstance() {
		return diskFileTree;
	}
}
