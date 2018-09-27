package model.disk;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;

public class DiskFileTree extends TreeView<FileItem> {
	/**
	 * ����ģʽ����Ŀ¼��
	 */
	private static DiskFileTreeItem rootItem = new DiskFileTreeItem(new Disk().getRoot());
	private static DiskFileTree diskFileTree = new DiskFileTree(rootItem);

	/**
	 * �������ݿ��޸�����TreeCell����
	 * 
	 * @param root: ���ڵ�
	 */
	private DiskFileTree(DiskFileTreeItem root) {
		super(root);
		setEditable(true);
		setCellFactory((TreeView<FileItem> treeview) -> new DiskFileTreeCell());
		addContextMenu();
	}

	/**
	 * ����Ҽ�Item���ֵ������Ĳ˵�,����հ״�����ʾ�˵�
	 */
	private void addContextMenu() {
		ContextMenu contextMenu = new ContextMenu();
		contextMenu.getItems().addAll(addMenu(), addDeleteMenuItem(), addRenameMenuItem());

		/**
		 * �����Ҽ��¼�,�ļ�Item����ʾ�½����Item����ʾɾ������������
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
	 * @return �½�ѡ��
	 */
	private Menu addMenu() {
		Menu addMenu = new Menu("�½�");
		addMenu.getItems().addAll(addTxtMenuItem(), addExeMenuItem(), addDirMenuItem());
		return addMenu;
	}

	/**
	 * 
	 * @return �½�TXT�ļ�ѡ��
	 */
	private MenuItem addTxtMenuItem() {
		MenuItem addTxt = new MenuItem("TXT�ļ�");
		addTxt.setOnAction(e -> {
			DiskFileTreeItem fatherItem = (DiskFileTreeItem) getSelectionModel().getSelectedItem();
			Files files = ((Directory) fatherItem.getValue()).createTxtFile();
			fatherItem.getChildren().add(new DiskFileTreeItem(files));
		});
		return addTxt;
	}

	/**
	 * 
	 * @return �½�EXE�ļ�ѡ��
	 */
	private MenuItem addExeMenuItem() {
		MenuItem addExe = new MenuItem("EXE�ļ�");
		addExe.setOnAction(e -> {
			DiskFileTreeItem fatherItem = (DiskFileTreeItem) getSelectionModel().getSelectedItem();
			Files files = ((Directory) fatherItem.getValue()).createExeFile();
			fatherItem.getChildren().add(new DiskFileTreeItem(files));
		});
		return addExe;
	}

	/**
	 * 
	 * @return �½��ļ���ѡ��
	 */
	private MenuItem addDirMenuItem() {
		MenuItem addDir = new MenuItem("�ļ���");
		addDir.setOnAction(e -> {
			DiskFileTreeItem fatherItem = (DiskFileTreeItem) getSelectionModel().getSelectedItem();
			Directory directory = ((Directory) fatherItem.getValue()).createDirectory();
			fatherItem.getChildren().add(new DiskFileTreeItem(directory));
		});
		return addDir;
	}

	/**
	 * 
	 * @return ɾ���˵�
	 */
	private MenuItem addDeleteMenuItem() {
		MenuItem addDelete = new MenuItem("ɾ��");
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
	 * @return ������ѡ��
	 */
	private MenuItem addRenameMenuItem() {
		MenuItem addRename = new MenuItem("������");
		addRename.setOnAction(e -> {
			edit(getSelectionModel().getSelectedItem());
		});
		return addRename;
	}

	public static DiskFileTree getInstance() {
		return diskFileTree;
	}
}
