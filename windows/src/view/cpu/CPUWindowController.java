package view.cpu;

import java.util.Collection;
import java.util.stream.Collectors;

import application.Main;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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

	private void updateReadyQueue(Collection<Integer> idQueue) {
		// Main.test(readyQueue);
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
		if(runningProcessPane.getCenter() == null){
			runningProcess = new ProcessPane(cpu.getRunningPid());
			runningProcessPane.setCenter(runningProcess);
		}
		runningProcess.setPid(cpu.getRunningPid());
	}

	public void update() {
		updateRunningPane();
	}

}
