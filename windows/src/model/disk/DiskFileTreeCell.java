package model.disk;

import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import view.ui.CreateWindows;

public class DiskFileTreeCell extends TreeCell<FileItem> {

	TextField textField;

	public DiskFileTreeCell() {

	}

	/**
	 * ����TreeItem����ʽ
	 */
	@Override
	public void updateItem(FileItem fileItem, boolean empty) {
		super.updateItem(fileItem, empty);
		if (empty) {
			setGraphic(null);
			setText(null);
		} else {
			setGraphic(getTreeItem().getGraphic());
			setText(fileItem.getFileName());
		}
	}

	/**
	 * ��ʼ�༭
	 */
	@Override
	public void startEdit() {
		super.startEdit();
		if (textField == null) {
			createTextField();
		}
		setText(null);
		setGraphic(textField);
	}

	/**
	 * ȡ���༭
	 */
	@Override
	public void cancelEdit() {
		super.cancelEdit();
		textField = null;
		setText(getItem().getFileName());
		setGraphic(getTreeItem().getGraphic());
	}

	/**
	 * �����༭�ı������ü����¼�
	 */
	private void createTextField() {
		textField = new TextField(getItem().getFileName());
		textField.setOnKeyReleased((KeyEvent t) -> {
			if (t.getCode() == KeyCode.ENTER) {
				int errorCode = getItem().changeFilesName(getItem().getFileName(), textField.getText(),getItem().getFileExtentionName());
				if (errorCode != 0) {
					switch (errorCode) {
					case 1:
						CreateWindows.getInstance().create("�ļ���������������");
						break;
					case 2:
						CreateWindows.getInstance().create("�ļ����Ѵ���");
						break;
					case 3:
						CreateWindows.getInstance().create("�ļ������Ƿ��ַ�");
						break;
					case 4:
						CreateWindows.getInstance().create("�ļ�������Ϊ��");
						break;
					}
					cancelEdit();
				} else {
					commitEdit(getItem());
				}
			} else if (t.getCode() == KeyCode.ESCAPE) {
				cancelEdit();
			}
		});
	}

}
