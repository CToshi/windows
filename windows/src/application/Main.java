package application;

import javafx.application.Application;
import javafx.stage.Stage;
import model.disk.DiskFileTree;
import model.disk.FAT;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.disk.DiskFileTreePane;
import view.ui.RootPane;

public class Main extends Application {
	private static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {

		Scene scene = new Scene(DiskFileTreePane.getInstance());
		primaryStage.setScene(scene);
		primaryStage.show();
		
//		Main.primaryStage = primaryStage;
//		FAT.getInstance().toString();
//		try {
//			primaryStage.initStyle(StageStyle.UNDECORATED);
//			primaryStage.setMaximized(true);
//			RootPane root = new RootPane(primaryStage);
//			Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
//			primaryStage.setScene(scene);
//			primaryStage.show();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}
}
