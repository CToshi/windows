package model.cpu;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import model.cpu.process.PCB;
import model.cpu.process.ProcessCode;
import model.memory.Memory;
import model.memory.MemoryBlock;

public class CPU {
	private static CPU cpu = new CPU();
	private static int MAX_NUMBE_OF_PROCESS = 11;
	private CPURegisters registers;
	private ArrayList<LinkedBlockingQueue<PCB>> processQueues;
	private LinkedBlockingQueue<ProcessCode> waitingQueue;
	/**
	 * 运行中的进程
	 */
	private PCB runningProcess;
	private Memory memory;
	private PCBManager pcbManager;
	/**
	 * 闲逛进程
	 */
	private PCB strollingProcess;
	/**
	 * 轮转时间片，单位：条（指令）
	 */
	private static int TIME_UNIT = 3;
	/**
	 * 每条指令执行时间，单位：个（时钟周期）（SystemClock)
	 */
	private static int INS_UNIT = 5;
	/**
	 * 运行中的进程的剩余时间片
	 */
	private int leftTime;

	private enum QUEUE_TYPE {
		BLANK, READY, BLOCKED
	}

	private CPU() {
		processQueues = new ArrayList<>(QUEUE_TYPE.values().length);
		for (int i = 0; i < QUEUE_TYPE.values().length; i++) {
			processQueues.add(new LinkedBlockingQueue<>(MAX_NUMBE_OF_PROCESS));
		}
		memory = Memory.getInstance();
		pcbManager = new PCBManager();
		runningProcess = strollingProcess = pcbManager.allocatePCB(new MemoryBlock(0, 0, "闲逛进程"),
				new ProcessCode(new int[0]));
	}

	public static CPU getInstance() {
		return cpu;
	}

	private boolean create(ProcessCode code) {
		if (code == null) {
			return false;
		}
		allocatePCB(code);
		return true;
	}

	/**
	 *
	 * @param instructions
	 * @return 编译是否成功
	 */
	public boolean create(String instructions) {
		return create(Compiler.compile(instructions));
	}

	/**
	 * 当Memory有空间且pcbManager可申请PCB时，申请PCB并将其放到就绪队列
	 * 申请失败时code进入等待队列
	 *
	 * @param code
	 */
	private void allocatePCB(ProcessCode code) {
		MemoryBlock block = memory.allocate(code);
		if (block == null || !pcbManager.available()) {
			try {
				waitingQueue.put(code);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		getQueue(QUEUE_TYPE.READY).add(pcbManager.allocatePCB(block, code));
	}

	private void deal() {
		if (runningProcess.getCode().getNext() == Compiler.getEndCode()) {

		}
	}

	private void takeTurn() {
		if (getQueue(QUEUE_TYPE.READY).isEmpty()) {
			runningProcess = strollingProcess;
			return;
		}
		try {
			runningProcess = getQueue(QUEUE_TYPE.READY).take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private LinkedBlockingQueue<PCB> getQueue(QUEUE_TYPE type) {
		return processQueues.get(type.ordinal());
	}

	public static int getMAX_NUMBE_OF_PROCESS() {
		return MAX_NUMBE_OF_PROCESS;
	}
}
