package model.cpu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

import application.Main;
import model.cpu.process.PCB;
import model.cpu.process.ProcessCode;
import model.memory.Memory;
import model.memory.MemoryBlock;
import model.memory.MemoryOccupy;

public class CPU {
	private static CPU cpu;
	private static int MAX_NUMBE_OF_PROCESS = 11;
	// private CPURegisters registers;
	private ArrayList<LinkedBlockingQueue<PCB>> processQueues;
	private LinkedBlockingQueue<ProcessCode> waitingQueue;
	/**
	 * �����еĽ���
	 */
	private PCB runningProcess;
	private Memory memory;
	private PCBManager pcbManager;
	/**
	 * �й����
	 */
	private PCB strollingProcess;
	/**
	 * ��תʱ��Ƭ����λ������ָ�
	 */
	private static int TIME_SLICE = 3;
	/**
	 * ÿ��ָ��ִ��ʱ�䣬��λ������ʱ�����ڣ���SystemClock)
	 */
	private static int INS_UNIT = 3;

	/**
	 * �����еĽ��̵�ʣ��ʱ��Ƭ
	 */
	private enum Queue_Type {
		BLANK, READY, BLOCKED
	}

	private InsExecutor insExecutor;
	private int currentTime;
	private DeviceManager deviceManager = DeviceManager.getInstance();

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
		ProcessCode systemCode = new ProcessCode(new int[64]);
		runningProcess = strollingProcess = pcbManager.allocatePCB(memory.allocate(systemCode), systemCode);
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
	 * @return �����Ƿ�ɹ�
	 */
	public boolean create(String instructions) {
		return create(Compiler.compile(instructions));
	}

	/**
	 * ������ǰ����
	 * ����PCB
	 */
	private void destroy() {
		if(runningProcess.getID() != 0){
			pcbManager.recoverPCB(runningProcess);
			memory.release(runningProcess.getMemoryBlock());
		}
	}

	/**
	 * ������ǰ����
	 */
	private void block() {
		deviceManager.request(runningProcess, insExecutor.getDeviceID(), insExecutor.getDeviceTime());
	}

	/**
	 * �����߳�
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
	 * ��Memory�пռ���pcbManager������PCBʱ������PCB������ŵ���������
	 * ����ʧ��ʱcode����ȴ�����
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
		getQueue(Queue_Type.READY).add(pcbManager.allocatePCB(block, code));
	}

	public void work() {
		currentTime++;
		if (currentTime % INS_UNIT == 0) {
			handle();
		}
	}

	public void handle() {
//		Main.test("handle", getCurrentInstruction());
		insExecutor.execute();
		// runningProcess.setRegisters(insExecutor.getRegisters());
		switch (insExecutor.getRegisters().getPSW()) {
		case TIME_OUT:
//			Main.test("taketune");
			setToReady();
			takeTurn();
			break;
		case IO_INTERRUPT:
//			Main.test("block");
			block();
			takeTurn();
			break;
		case END:
//			Main.test("destroy");
			destroy();
			takeTurn();
			break;
		default:
//			Main.test("nothing");
			break;
		}
	}

	/**
	 * �����ֳ��������������еĽ��̵�״̬����Ϊ����
	 */
	private void setToReady() {
		runningProcess.setRegisters(insExecutor.getRegisters());
		try {
			getQueue(Queue_Type.READY).put(runningProcess);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ת��ʹ��������ͷ�Ľ��̱�Ϊ����̬
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

	public Collection<Integer> getReadyQueue() {
		return getQueue(Queue_Type.READY).stream().map(pcb -> pcb.getID()).collect(Collectors.toList());
	}

	public int getPID(MemoryBlock block) {
		return pcbManager.getPID(block);
	}
}
