package view.cpu;

import java.util.Collection;
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
import model.cpu.CPU;
import model.cpu.SystemClock;

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

	private void initDiskPane(){
		final int ROW_LENGTH = 32;
		final int COL_LENGTH = 8;
		diskGridPanes = new Pane[ROW_LENGTH][COL_LENGTH];
		for(int i = 0;i<ROW_LENGTH;i++){
			for(int j = 0;j<COL_LENGTH;j++){
				Pane pane = new BorderPane(new Label(String.valueOf(i * COL_LENGTH + j)));
				diskGridPanes[i][j] = pane;
				pane.setMinSize(45, 22);
				pane.getStyleClass().add("paneWithBorder");
				diskPane.add(pane, j, i);
			}
		}
	}
	private void updateDiskPane() {
		if(diskGridPanes == null){
			initDiskPane();
		}

	}

	public void update() {
		updateRunningPane();
		updateDiskPane();
	}

}
