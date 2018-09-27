package model.disk;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;

public class DiskFileTree extends TreeView<FileItem> {
	/**
	 * 单例模式生成目录树
	 */
	private static DiskFileTreeItem rootItem = new DiskFileTreeItem(new Disk().getRoot());
	private static DiskFileTree diskFileTree = new DiskFileTree(rootItem);

	/**
	 * 设置内容可修改且与TreeCell关联
	 * 
	 * @param root: 根节点
	 */
	private DiskFileTree(DiskFileTreeItem root) {
		super(root);
		setEditable(true);
		setCellFactory((TreeView<FileItem> treeview) -> new DiskFileTreeCell());
		addContextMenu();
	}

	/**
	 * 添加右键Item出现的上下文菜单,点击空白处不显示菜单
	 */
	private void addContextMenu() {
		ContextMenu contextMenu = new ContextMenu();
		contextMenu.getItems().addAll(addMenu(), addDeleteMenuItem(), addRenameMenuItem());

		/**
		 * 设置右键事件,文件Item不显示新建项，根Item不显示删除和重命名项
		 */
		setOnMouseClicked(e -> {
			if (e.getTarget().toString().endsWith("\'null\'")) {
				getSelectionModel().clearSelection();
				contextMenu.hide();
			} else {
				if (e.getButton() == MouseButton.SECONDARY) {
					contextMenu.hide();
					if (getSelectionModel().getSelectedItem().getValue() instanceof Files) {
						((Menu) contextMenu.getItems().get(0)).setVisible(false);
					} else {
						((Menu) contextMenu.getItems().get(0)).setVisible(true);
					}
					if (getSelectionModel().getSelectedItem().getValue().isCanBeDeleted()) {
						((MenuItem) contextMenu.getItems().get(2)).setVisible(true);
						((MenuItem) contextMenu.getItems().get(1)).setVisible(true);
					} else {
						((MenuItem) contextMenu.getItems().get(2)).setVisible(false);
						((MenuItem) contextMenu.getItems().get(1)).setVisible(false);
					}
					contextMenu.show(this, e.getScreenX(), e.getScreenY());
				} else {
					contextMenu.hide();
				}
			}
		});
	}

	/**
	 * 
	 * @return 新建选项
	 */
	private Menu addMenu() {
		Menu addMenu = new Menu("新建");
		addMenu.getItems().addAll(addTxtMenuItem(), addExeMenuItem(), addDirMenuItem());
		return addMenu;
	}

	/**
	 * 
	 * @return 新建TXT文件选项
	 */
	private MenuItem addTxtMenuItem() {
		MenuItem addTxt = new MenuItem("TXT文件");
		addTxt.setOnAction(e -> {
			DiskFileTreeItem fatherItem = (DiskFileTreeItem) getSelectionModel().getSelectedItem();
			Files files = ((Directory) fatherItem.getValue()).createTxtFile();
			fatherItem.getChildren().add(new DiskFileTreeItem(files));
		});
		return addTxt;
	}

	/**
	 * 
	 * @return 新建EXE文件选项
	 */
	private MenuItem addExeMenuItem() {
		MenuItem addExe = new MenuItem("EXE文件");
		addExe.setOnAction(e -> {
			DiskFileTreeItem fatherItem = (DiskFileTreeItem) getSelectionModel().getSelectedItem();
			Files files = ((Directory) fatherItem.getValue()).createExeFile();
			fatherItem.getChildren().add(new DiskFileTreeItem(files));
		});
		return addExe;
	}

	/**
	 * 
	 * @return 新建文件夹选项
	 */
	private MenuItem addDirMenuItem() {
		MenuItem addDir = new MenuItem("文件夹");
		addDir.setOnAction(e -> {
			DiskFileTreeItem fatherItem = (DiskFileTreeItem) getSelectionModel().getSelectedItem();
			Directory directory = ((Directory) fatherItem.getValue()).createDirectory();
			fatherItem.getChildren().add(new DiskFileTreeItem(directory));
		});
		return addDir;
	}

	/**
	 * 
	 * @return 删除菜单
	 */
	private MenuItem addDeleteMenuItem() {
		MenuItem addDelete = new MenuItem("删除");
		addDelete.setOnAction(e -> {
			TreeItem<FileItem> item = getSelectionModel().getSelectedItem();
			item.getValue().deleteFiles();
			int index = item.getParent().getChildren().indexOf(item);
			item.getParent().getChildren().remove(index);
		});
		return addDelete;
	}

	/**
	 * 
	 * @return 重命名选项
	 */
	private MenuItem addRenameMenuItem() {
		MenuItem addRename = new MenuItem("重命名");
		addRename.setOnAction(e -> {
			edit(getSelectionModel().getSelectedItem());
		});
		return addRename;
	}

	public static DiskFileTree getInstance() {
		return diskFileTree;
	}
}
