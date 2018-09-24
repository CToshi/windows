package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
		Main.primaryStage = primaryStage;
		try {
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setMaximized(true);
			RootPane root = new RootPane(primaryStage);
			Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
			primaryStage.setScene(scene);
			primaryStage.show();
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
