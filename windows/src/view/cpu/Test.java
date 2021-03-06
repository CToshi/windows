package view.cpu;

import java.util.Timer;
import java.util.TimerTask;

import application.Main;
import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.cpu.SystemClock;

public class Test extends Application {

//	private DoubleBinding getBinding() {
//		return new SimpleDoubleProperty().add(0);
//	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		runlater(()->{
//			for(int i = 0;i<11;i++){
//				Main.test(CPU.getInstance().create("x++ x++ x++ x++ x++ x++ x++ x++ x++ x++ x++ x++ "));
//			}
//			CPU.getInstance().create("!A9 x++ x++ x++ x++ x++");
//			CPU.getInstance().create("!A9");
//			CPU.getInstance().create("!A9");
//			CPU.getInstance().create("!A1");

//			for(int i = 0;i<10;i++){
//				CPU.getInstance().create("!B1");
//			}
//			CPU.getInstance().create("x++ x++ x++ x++ x++ x++ x++ x++ x++ x++"
//					+ " x++ x++ x++ x++ x++"
//					+ " x++ x++ x++ x++ x++"
//					+ " x++ x++ x++ x++ x++"
//					+ " x++ x++ x++ x++ x++"
//					+ " x++ x++ x++ x++ x++"
//					+ " x++ x++ x++ x++ x++"
//					+ " x++ x++ x++ x++ x++"
//					+ " x++ x++ x++ x++ x++"
//					+ " x++ x++ x++ x++ x++"
//					+ " x++ x++ x++ x++ x++"
//					+ " x++ x++ x++ x++ x++"
//					+ " x++ x++ x++ x++ x++");
//
//			CPU.getInstance().create("x=99");
//			CPU.getInstance().create("x--");
//			for(int i = 0;i<2;i++){
//				CPU.getInstance().create("x++ x-- x=99 x++");
//			}
//			for(int i = 0;i<10;i++){
//				Main.test(CodeBuilder.getRandomCodes());
//			}
//			CPU.getInstance().create("!A9 x++ x=2 x--");
		});
		Pane root = CPUWindow.getInstance().getMainPane();
		Scene scene = new Scene(root);
		scene.setOnMouseClicked(e -> {
			Main.test(e.getX(), e.getY());
		});
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(event->{
			SystemClock.getInstance().stop();
			timer.cancel();
		});

	}

	public static void setBackground(Region pane, Paint color) {
		pane.setBackground(new Background(new BackgroundFill(color, null, null)));
	}

	public static void showBinding(DoubleBinding binding, String show) {
		binding.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Main.test(show, oldValue, newValue);
			}
		});
	}
	private static Timer timer = new Timer();
	public static void runlater(Runnable runnable){
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				runnable.run();
			}
		}, 500);
	}

	public static void main(String[] args) {
		launch(args);
	}


}