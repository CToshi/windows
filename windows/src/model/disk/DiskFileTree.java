package model.disk;

import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;

public class DiskFileTree extends TreeView<FileItem> {
	/**
	 * ����ģʽ����Ŀ¼��
	 */
	private static DiskFileTreeItem rootItem = new DiskFileTreeItem(Disk.getInstance().getRoot());
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
		contextMenu.getItems().addAll(addMenu(), addDeleteMenuItem(), addRenameMenuItem(), addChangeAttributeMenu());

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
						((Menu) contextMenu.getItems().get(3)).setVisible(true);
						if (getSelectionModel().getSelectedItem().getValue().getAttributes() == 0) {
							((RadioMenuItem) ((Menu) contextMenu.getItems().get(3)).getItems().get(0))
									.setSelected(true);
						} else if (getSelectionModel().getSelectedItem().getValue().getAttributes() == 1) {
							((RadioMenuItem) ((Menu) contextMenu.getItems().get(3)).getItems().get(1))
									.setSelected(true);
						} else {
							((RadioMenuItem) ((Menu) contextMenu.getItems().get(3)).getItems().get(2))
									.setSelected(true);
						}
						if(getSelectionModel().getSelectedItem().getValue().getFileExtentionName().equals(".txt")) {
							((RadioMenuItem) ((Menu) contextMenu.getItems().get(3)).getItems().get(2)).setDisable(true);
						} else {
							((RadioMenuItem) ((Menu) contextMenu.getItems().get(3)).getItems().get(2)).setDisable(false);
						}
					} else {
						((Menu) contextMenu.getItems().get(0)).setVisible(true);
						((Menu) contextMenu.getItems().get(3)).setVisible(false);
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
			if (files != null) {
				fatherItem.getChildren().add(new DiskFileTreeItem(files));
			} else {
				error_of_missingCapacity();
			}
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
			if (files != null) {
				fatherItem.getChildren().add(new DiskFileTreeItem(files));
			} else {
				error_of_missingCapacity();
			}
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
			if (directory != null) {
				fatherItem.getChildren().add(new DiskFileTreeItem(directory));
			} else {
				error_of_missingCapacity();
			}
		});
		return addDir;
	}

	private void error_of_missingCapacity() {
		Alert alert = new Alert(Alert.AlertType.ERROR, "�ļ�����������");
		alert.setHeaderText(null);
		alert.show();
	}

	/**
	 * 
	 * @return ɾ���˵�
	 */
	private MenuItem addDeleteMenuItem() {
		MenuItem delete = new MenuItem("ɾ��");
		delete.setOnAction(e -> {
			DiskFileTreeItem item = (DiskFileTreeItem) getSelectionModel().getSelectedItem();
			item.getValue().deleteFiles();
			item.getParent().getChildren().remove(item);
		});
		return delete;
	}

	/**
	 * 
	 * @return ������ѡ��
	 */
	private MenuItem addRenameMenuItem() {
		MenuItem rename = new MenuItem("������");
		rename.setOnAction(e -> {
			edit(getSelectionModel().getSelectedItem());
		});
		return rename;
	}

	public Menu addChangeAttributeMenu() {
		Menu changeAttribute = new Menu("�޸�����");
		RadioMenuItem readOnly = new RadioMenuItem("ֻ��");
		RadioMenuItem canWrite = new RadioMenuItem("��д");
		RadioMenuItem canExecute = new RadioMenuItem("��ִ��");
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
