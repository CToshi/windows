package model.disk;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import model.cpu.CPU;
import model.cpu.CPU.Result;
import view.ui.CreateWindows;

public class DiskFileTree extends TreeView<FileItem> {
	/**
	 * 单例模式生成目录树
	 */
	private static DiskFileTreeItem rootItem = new DiskFileTreeItem(Disk.getInstance().getRoot());
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
		contextMenu.getItems().addAll(addMenu(), addEditItem(), addExecuteItem(), addDeleteMenuItem(),
				addRenameMenuItem(), addChangeAttributeMenu());

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
						((MenuItem) contextMenu.getItems().get(1)).setVisible(true);
						((MenuItem) contextMenu.getItems().get(2)).setVisible(true);
						((Menu) contextMenu.getItems().get(5)).setVisible(true);
						if (getSelectionModel().getSelectedItem().getValue().getAttributes() == 0) {
							((RadioMenuItem) ((Menu) contextMenu.getItems().get(5)).getItems().get(0))
									.setSelected(true);
						} else if (getSelectionModel().getSelectedItem().getValue().getAttributes() == 1) {
							((RadioMenuItem) ((Menu) contextMenu.getItems().get(5)).getItems().get(1))
									.setSelected(true);
						} else {
							((RadioMenuItem) ((Menu) contextMenu.getItems().get(5)).getItems().get(2))
									.setSelected(true);
						}
						if (getSelectionModel().getSelectedItem().getValue().getFileExtentionName().equals(".txt")) {
							((RadioMenuItem) ((Menu) contextMenu.getItems().get(5)).getItems().get(2)).setDisable(true);
							((MenuItem) contextMenu.getItems().get(2)).setVisible(false);
						} else {
							((RadioMenuItem) ((Menu) contextMenu.getItems().get(5)).getItems().get(2))
									.setDisable(false);
							((MenuItem) contextMenu.getItems().get(2)).setVisible(true);
						}
					} else {
						((Menu) contextMenu.getItems().get(0)).setVisible(true);
						((MenuItem) contextMenu.getItems().get(1)).setVisible(false);
						((MenuItem) contextMenu.getItems().get(2)).setVisible(false);
						((Menu) contextMenu.getItems().get(5)).setVisible(false);
					}
					if (getSelectionModel().getSelectedItem().getValue().isCanBeDeleted()) {
						((MenuItem) contextMenu.getItems().get(4)).setVisible(true);
						((MenuItem) contextMenu.getItems().get(3)).setVisible(true);
					} else {
						((MenuItem) contextMenu.getItems().get(4)).setVisible(false);
						((MenuItem) contextMenu.getItems().get(3)).setVisible(false);
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
	 * @return 编辑选项
	 */
	private MenuItem addEditItem() {
		MenuItem edit = new MenuItem("编辑");
		edit.setOnAction(e -> {
			DiskFileTreeItem item = (DiskFileTreeItem) getSelectionModel().getSelectedItem();
			Files file = (Files) item.getValue();
			CreateWindows.getInstance().create(file);
		});
		return edit;
	}

	/**
	 * 
	 * @return 执行选项
	 */
	private MenuItem addExecuteItem() {
		MenuItem execute = new MenuItem("运行");
		execute.setOnAction(e -> {
			DiskFileTreeItem item = (DiskFileTreeItem) getSelectionModel().getSelectedItem();
			Files file = (Files) item.getValue();
			CPU.getInstance().create(file.getContent());
			Result result = CPU.getInstance().create(file.getContent());
			if (result == Result.MEMORY_NOT_ENOUGH) {
				CreateWindows.getInstance().create("内存不够");
			} else if (result == Result.PCB_NOT_ENOUGH) {
				CreateWindows.getInstance().create("PCB不够");
			} else if (result == Result.COMPILE_ERROR) {
				CreateWindows.getInstance().create("编译错误");
			}
		});
		return execute;
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
			if (files != null) {
				fatherItem.getChildren().add(new DiskFileTreeItem(files));
			} else {
				CreateWindows.getInstance().create("文件夹容量不足");
			}
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
			if (files != null) {
				fatherItem.getChildren().add(new DiskFileTreeItem(files));
			} else {
				CreateWindows.getInstance().create("文件夹容量不足");
			}
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
			if (directory != null) {
				fatherItem.getChildren().add(new DiskFileTreeItem(directory));
			} else {
				CreateWindows.getInstance().create("文件夹容量不足");
			}
		});
		return addDir;
	}

	/**
	 * 
	 * @return 删除菜单
	 */
	private MenuItem addDeleteMenuItem() {
		MenuItem delete = new MenuItem("删除");
		delete.setOnAction(e -> {
			DiskFileTreeItem item = (DiskFileTreeItem) getSelectionModel().getSelectedItem();
			item.getValue().deleteFiles();
			item.getParent().getChildren().remove(item);
		});
		return delete;
	}

	/**
	 * 
	 * @return 重命名选项
	 */
	private MenuItem addRenameMenuItem() {
		MenuItem rename = new MenuItem("重命名");
		rename.setOnAction(e -> {
			edit(getSelectionModel().getSelectedItem());
		});
		return rename;
	}

	public Menu addChangeAttributeMenu() {
		Menu changeAttribute = new Menu("修改属性");
		RadioMenuItem readOnly = new RadioMenuItem("只读");
		RadioMenuItem canWrite = new RadioMenuItem("可写");
		RadioMenuItem canExecute = new RadioMenuItem("可执行");
		readOnly.setOnAction(e -> {
			getSelectionModel().getSelectedItem().getValue().changeAttributes(0);
		});
		canWrite.setOnAction(e -> {
			getSelectionModel().getSelectedItem().getValue().changeAttributes(1);
		});
		canExecute.setOnAction(e -> {
			getSelectionModel().getSelectedItem().getValue().changeAttributes(2);
		});
		ToggleGroup tGroup = new ToggleGroup();
		tGroup.getToggles().addAll(readOnly, canWrite, canExecute);
		changeAttribute.getItems().addAll(readOnly, canWrite, canExecute);
		return changeAttribute;
	}

	public static DiskFileTree getInstance() {
		return diskFileTree;
	}
}
