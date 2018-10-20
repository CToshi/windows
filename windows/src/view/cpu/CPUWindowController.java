package view.cpu;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import model.cpu.CPU;
import model.cpu.DeviceManager;
import model.cpu.SystemClock;
import model.disk.FAT;
import model.memory.Memory;
import model.memory.MemoryBlock;
import utility.Util;

public class CPUWindowController {
	@FXML
	private Pane readyQueue;
	private QueueController readyQueueController;
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
	@FXML
	private VBox memoryVBox;
	@FXML
	private Pane aQueue;
	@FXML
	private Pane bQueue;
	@FXML
	private Pane cQueue;
	private Collection<QueueController> deviceQueueControllers;

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
		if (readyQueueController == null) {
			readyQueueController = new QueueController(readyQueue);
		}
		runningProcess.setPid(cpu.getRunningPid());
		readyQueueController.update(cpu.getReadyQueue());
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
					pane.setStyle("-fx-background-color: #ff3737; -fx-background-radius: 7px");
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
		devicePanes = new DevicePanes[borderPanes.length];
		for (int i = 0; i < borderPanes.length; i++) {
			StackPane stackPane = (StackPane) borderPanes[i].getCenter();
			Label idLabel = (Label) stackPane.getChildren().get(1);
			devicePanes[i] = new DevicePanes(stackPane, idLabel, timeLabels[i]);
		}
		if (deviceQueueControllers == null) {
			deviceQueueControllers = Util.list(aQueue, bQueue, cQueue).stream().map(pane -> new QueueController(pane))
					.collect(Collectors.toList());
		}
	}

	private void updateDevicePane() {
		if (devicePanes == null) {
			initDevicePane();
			return;
		}
		List<Pair<Integer, Integer>> deviceMsgs = DeviceManager.getInstance().getUsingProcess();
		for (int i = 0; i < devicePanes.length; i++) {
			DevicePanes devicePane = devicePanes[i];
			int pid = deviceMsgs.get(i).getKey();
			int time = deviceMsgs.get(i).getValue();
			if (pid == 0) {
				devicePane.setVisible(false);
			} else {
				devicePane.setVisible(true);
				devicePane.setID(pid);
				devicePane.setTime(time);
			}
		}
		Iterator<List<Integer>> iterator = DeviceManager.getInstance().getRequestQueues().stream()
				.map(queue -> queue.stream().map(pcb -> pcb.getID()).collect(Collectors.toList())).iterator();
		deviceQueueControllers.stream().forEach(controller -> controller.update(iterator.next()));

	}

	private void updateMemoryPane() {
		memoryVBox.getChildren().clear();
		MemoryBlock[] blocks = Memory.getInstance().listStatus();
		for (MemoryBlock block : blocks) {
			// if(block == blocks[blocks.length-1]){
			// break;
			// }
			double height = memoryVBox.getHeight() * block.getLength() / Memory.getMEMORY_SIZE();
			// Main.test(memoryVBox.getHeight(), block.getLength(),
			// Memory.getMEMORY_SIZE(), height);
			int pid = CPU.getInstance().getPID(block);
			String text = "#" + String.valueOf(pid);
			if (pid == 0) {
				text = "系统占用";
			} else if (pid == -1) {
				text = "";
			}
			memoryVBox.getChildren().addAll(new MemoryBlockView(text, height));
		}

		// memoryVBox.getChildren().addAll(pane);

	}

	public void update() {
		updateRunningPane();
		updateDiskPane();
		updateDevicePane();
		updateMemoryPane();
	}
}
