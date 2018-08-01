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
	  *  ����ģʽ����Ŀ¼��
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
		Menu addMenu = new Menu("�½�");
		MenuItem addTxtMenuItem = new MenuItem("TXT�ļ�");
		MenuItem addExeMenuItem = new MenuItem("EXE�ļ�");
		MenuItem addDirMenuItem = new MenuItem("�ļ���");
		MenuItem delelteMenuItem = new MenuItem("ɾ��");
		
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
