package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.cpu.DeviceManager;
import model.cpu.SystemClock;
import view.disk.DiskFileTreePane;
import javafx.stage.StageStyle;
import model.disk.FAT;
import view.ui.RootPane;

public class Main extends Application {
	public static void test(Object... ts) {
		for (Object t : ts) {
			System.out.print(t + " ");
		}
		System.out.println();
	}

	private static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {

//		Scene scene = new Scene(DiskFileTreePane.getInstance());
//		primaryStage.setScene(scene);
//		primaryStage.show();

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
//		Scene scene = new Scene(DiskFileTreePane.getInstance());
//		primaryStage.setScene(scene);
//		primaryStage.show();

		Main.primaryStage = primaryStage;
		FAT.getInstance().toString();
		try {
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setMaximized(true);
			RootPane root = new RootPane(primaryStage);
			Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setOnHidden(e->{
				SystemClock.getInstance().stop();
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}
}
