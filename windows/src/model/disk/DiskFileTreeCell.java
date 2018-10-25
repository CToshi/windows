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
				int errorCode = getItem().changeFilesName(getItem().getFileName(), textField.getText(),getItem().getFileExtentionName());
				if (errorCode != 0) {
					switch (errorCode) {
					case 1:
						CreateWindows.getInstance().create("文件名字数超出限制");
						break;
					case 2:
						CreateWindows.getInstance().create("文件名已存在");
						break;
					case 3:
						CreateWindows.getInstance().create("文件名含非法字符");
						break;
					case 4:
						CreateWindows.getInstance().create("文件名不得为空");
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
