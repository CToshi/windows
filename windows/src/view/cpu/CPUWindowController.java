package view.cpu;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import application.Main;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import model.cpu.CPU;
import model.cpu.DeviceManager;
import model.cpu.SystemClock;
import model.disk.FAT;

public class CPUWindowController {
	@FXML
	private Pane readyQueue;
	@FXML
	private Label clockCount;
	@FXML
	private Label timeSlice;
	@FXML
	private Label currentIns;
	@FXML
	private Label valueOfX;
	@FXML
	private Label timeLeft;
	@FXML
	private BorderPane runningProcessPane;
	private ProcessPane runningProcess;
	@FXML
	private GridPane diskPane;
	private Pane[][] diskGridPanes;
	@FXML
	private BorderPane deviceBorderPane1;
	@FXML
	private BorderPane deviceBorderPane2;
	@FXML
	private BorderPane deviceBorderPane3;
	@FXML
	private BorderPane deviceBorderPane4;
	@FXML
	private BorderPane deviceBorderPane5;
	@FXML
	private BorderPane deviceBorderPane6;
	@FXML
	private BorderPane deviceBorderPane7;
	@FXML
	private BorderPane deviceBorderPane8;
	private DevicePanes[] devicePanes;
	@FXML
	private Label timeLabel1;
	@FXML
	private Label timeLabel2;
	@FXML
	private Label timeLabel3;
	@FXML
	private Label timeLabel4;
	@FXML
	private Label timeLabel5;
	@FXML
	private Label timeLabel6;
	@FXML
	private Label timeLabel7;
	@FXML
	private Label timeLabel8;

	private void updateReadyQueue(Collection<Integer> idQueue) {
		// Main.test(readyQueue);
		readyQueue.getChildren().clear();
		DoubleBinding height = readyQueue.heightProperty().add(0);
		readyQueue.getChildren()
				.addAll(idQueue.stream().map(pid -> new ProcessPane(height, pid)).collect(Collectors.toList()));
		readyQueue.getChildren().add(new ArrowPane(height));
	}

	private void updateRunningPane() {
		clockCount.setText(String.valueOf(SystemClock.getInstance().getClock()));
		CPU cpu = CPU.getInstance();
		timeSlice.setText(String.valueOf(cpu.getTimeSlice()));
		currentIns.setText(cpu.getCurrentInstruction());
		valueOfX.setText(String.valueOf(cpu.getValueOfX()));
		timeLeft.setText(String.valueOf(cpu.getTimeLeft()));
		if (runningProcessPane.getCenter() == null) {
			runningProcess = new ProcessPane(cpu.getRunningPid());
			runningProcessPane.setCenter(runningProcess);
		}
		runningProcess.setPid(cpu.getRunningPid());
		updateReadyQueue(cpu.getReadyQueue());
	}

	private void initDiskPane() {
		final int ROW_LENGTH = 32;
		final int COL_LENGTH = 8;
		diskGridPanes = new Pane[ROW_LENGTH][COL_LENGTH];
		for (int i = 0; i < ROW_LENGTH; i++) {
			for (int j = 0; j < COL_LENGTH; j++) {
				Pane pane = new BorderPane(new Label(String.valueOf(i * COL_LENGTH + j)));
				diskGridPanes[i][j] = pane;
				pane.setMinSize(45, 22);
				pane.getStyleClass().add("paneWithBorder");
				diskPane.add(pane, j, i);
			}
		}
	}

	private void updateDiskPane() {
		if (diskGridPanes == null) {
			initDiskPane();
		}
		int index = 0;
		int[] status = FAT.getInstance().getFAT();
		for (int i = 0; i < diskGridPanes.length; i++) {
			for (int j = 0; j < diskGridPanes[i].length; j++) {
				Pane pane = diskGridPanes[i][j];
				if (status[index++] != 0) {
					pane.setStyle("-fx-background-color: #ff3737");
				} else {
					pane.setStyle("-fx-background-color: transparent");
				}
			}
		}
	}

	private void initDevicePane() {
		BorderPane[] borderPanes = { deviceBorderPane1, deviceBorderPane2, deviceBorderPane3, deviceBorderPane4,
				deviceBorderPane5, deviceBorderPane6, deviceBorderPane7, deviceBorderPane8 };
		Label[] timeLabels = { timeLabel1, timeLabel2, timeLabel3, timeLabel4, timeLabel5, timeLabel6, timeLabel7,
				timeLabel8 };
		for (int i = 0; i < borderPanes.length; i++) {
			StackPane stackPane = (StackPane) borderPanes[i].getCenter();
			Label idLabel = (Label) stackPane.getChildren().get(1);
			devicePanes[i] = new DevicePanes(stackPane, idLabel, timeLabels[i]);
		}
		// Main.test(GridPane.getColumnIndex(gridPane.getChildren().get(2)));
		// deviceProcessPanes = new ProcessPane[borderPanes.length];
		// for(int i = 0;i<borderPanes.length;i++){
		// BorderPane borderPane = borderPanes[i];
		// ProcessPane pane = deviceProcessPanes[i] = new ProcessPane(0);
		// borderPane.setCenter(pane);
		// }
	}

	private void updateDevicePane() {
		if (devicePanes == null) {
			initDevicePane();
			return;
		}

//		for (int i = 0; i < devicePanes.length; i++) {
//			DevicePanes devicePane = devicePanes[i];
////			devicePane.set
//		}
	}

	public void update() {
		updateRunningPane();
		updateDiskPane();
		updateDevicePane();
	}
}
