package model.cpu;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import application.Main;
import model.cpu.process.PCB;
import model.cpu.process.ProcessCode;
import model.memory.Memory;
import model.memory.MemoryBlock;

public class CPU {
	private static CPU cpu;
	private static int MAX_NUMBE_OF_PROCESS = 11;
	// private CPURegisters registers;
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
	private static int TIME_SLICE = 3;
	/**
	 * 每条指令执行时间，单位：个（时钟周期）（SystemClock)
	 */
	private static int INS_UNIT = 5;

	/**
	 * 运行中的进程的剩余时间片
	 */
	private enum Queue_Type {
		BLANK, READY, BLOCKED
	}

	private InsExecutor insExecutor;
	private int currentTime;
	static {
		cpu = new CPU();
	}

	private CPU() {
		processQueues = new ArrayList<>(Queue_Type.values().length);
		for (int i = 0; i < Queue_Type.values().length; i++) {
			processQueues.add(new LinkedBlockingQueue<>(MAX_NUMBE_OF_PROCESS));
		}
		memory = Memory.getInstance();
		pcbManager = new PCBManager();
		runningProcess = strollingProcess = pcbManager.allocatePCB(new MemoryBlock(0, 0), new ProcessCode(new int[0]));
		waitingQueue = new LinkedBlockingQueue<>();
		insExecutor = new InsExecutor();
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
	 * 撤销当前进程
	 * 回收PCB
	 */
	private void destroy() {
		pcbManager.recoverPCB(runningProcess);
	}

	/**
	 * 阻塞当前进程
	 */
	private void block() {

	}

	/**
	 * 唤醒线程
	 *
	 * @param pcb
	 */
	public void awake(PCB pcb) {
		try {
			getQueue(Queue_Type.READY).put(pcb);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 当Memory有空间且pcbManager可申请PCB时，申请PCB并将其放到就绪队列
	 * 申请失败时code进入等待队列
	 *
	 * @param code
	 */
	private void allocatePCB(ProcessCode code) {
		MemoryBlock block = memory.allocate(code);
		Main.test(block, !pcbManager.available());
		if (block == null || !pcbManager.available()) {
			try {
				waitingQueue.put(code);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		getQueue(Queue_Type.READY).add(pcbManager.allocatePCB(block, code));
	}

	public void work() {
		currentTime++;
		if (currentTime % INS_UNIT == 0) {
			handle();
		}
	}

	public void handle() {
		Main.test("handle");
		insExecutor.execute();
		// runningProcess.setRegisters(insExecutor.getRegisters());
		switch (insExecutor.getRegisters().getPSW()) {
		case TIME_OUT:
			Main.test("taketune");
			takeTurn();
			break;
		case IO_INTERRUPT:
			Main.test("block");
			block();
			break;
		case END:
			Main.test("destroy");
			destroy();
			takeTurn();
			break;
		default:
			Main.test("nothing");
			break;
		}
	}

	/**
	 * 轮转，将当前进程置于就绪队列，使就绪队列头的进程运行
	 */
	private void takeTurn() {
		// try {
		// runningProcess =
		// getQueue(Queue_Type.READY).poll(systemClock.getTimeUnit() / 2,
		// TimeUnit.MILLISECONDS);
		runningProcess = getQueue(Queue_Type.READY).poll();
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		if (runningProcess == null) {
			runningProcess = strollingProcess;
		} else {
			insExecutor.init(runningProcess.getCode(), runningProcess.getRegisters(), TIME_SLICE);
		}
	}

	private LinkedBlockingQueue<PCB> getQueue(Queue_Type type) {
		return processQueues.get(type.ordinal());
	}

	public static int getMAX_NUMBE_OF_PROCESS() {
		return MAX_NUMBE_OF_PROCESS;
	}

	public int getTimeSlice() {
		return TIME_SLICE;
	}

	public String getCurrentInstruction() {
		return Compiler.decode(insExecutor.getIns());
	}

	public int getValueOfX() {
		return insExecutor.getRegisters().getAX();
	}

	public int getTimeLeft() {
		return insExecutor.getTimeLeft();
	}

	public int getRunningPid() {
		return runningProcess.getID();
	}
}
