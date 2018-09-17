package model.disk;

import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
				if (!textField.getText().equals("") && getItem().changeFilesName(getItem().getFileName(), textField.getText()) == 0) {
					commitEdit(getItem());
				} else {
					cancelEdit();
				}
			} else if (t.getCode() == KeyCode.ESCAPE) {
				cancelEdit();
			}
		});
	}

}
