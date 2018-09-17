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
	 * 设置TreeItem的样式
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
	 * 开始编辑
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
	 * 取消编辑
	 */
	@Override
	public void cancelEdit() {
		super.cancelEdit();
		textField = null;
		setText(getItem().getFileName());
		setGraphic(getTreeItem().getGraphic());
	}

	/**
	 * 创建编辑文本框并设置键盘事件
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
