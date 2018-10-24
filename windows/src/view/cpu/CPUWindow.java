package view.cpu;

import java.io.IOException;
import java.net.URL;

import controller.CPUWindowController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import model.cpu.SystemClock;

public class CPUWindow {
	private static CPUWindow cpuWindow;
	static {
		cpuWindow = new CPUWindow();
	}
	private Pane mainPane;

	private CPUWindow() {
		URL location = getClass().getResource("/view/cpu/FXML_CPUWindow.fxml");
		FXMLLoader loader = new FXMLLoader(location);
		try {
			mainPane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		CPUWindowController controller = loader.getController();
		SystemClock.getInstance().addEvent(() -> {
			Platform.runLater(() -> {
				controller.update();
			});
		});
	}

	public static CPUWindow getInstance() {
		return cpuWindow;
	}

	public Pane getMainPane() {
		return mainPane;
	}
}
