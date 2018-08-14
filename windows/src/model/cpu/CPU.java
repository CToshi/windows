package model.cpu;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import model.cpu.process.PCB;
import model.cpu.process.ProcessCode;
import model.memory.Memory;
import model.memory.MemoryBlock;

public class CPU {
	private static CPU cpu = new CPU();
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
	private static int TIMESLICE = 3;
	/**
	 * ÿ��ָ��ִ��ʱ�䣬��λ������ʱ�����ڣ���SystemClock)
	 */
	private static int INS_UNIT = 5;

	/**
	 * �����еĽ��̵�ʣ��ʱ��Ƭ
	 */
	private enum Queue_Type {
		BLANK, READY, BLOCKED
	}

	private InsExecutor insExecutor;
	private SystemClock systemClock;

	private CPU() {
		processQueues = new ArrayList<>(Queue_Type.values().length);
		for (int i = 0; i < Queue_Type.values().length; i++) {
			processQueues.add(new LinkedBlockingQueue<>(MAX_NUMBE_OF_PROCESS));
		}
		memory = Memory.getInstance();
		systemClock = SystemClock.getInstance();
		pcbManager = new PCBManager();
		runningProcess = strollingProcess = pcbManager.allocatePCB(new MemoryBlock(0, 0, "�й����"),
				new ProcessCode(new int[0]));
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
		pcbManager.recoverPCB(runningProcess);
	}

	/**
	 * ������ǰ����
	 */
	private void block() {

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

	public void handle() {
		if (runningProcess != strollingProcess) {
			insExecutor.execute();
			runningProcess.setRegisters(insExecutor.getRegisters());
			switch (insExecutor.getRegisters().getPSW()) {
			case TIME_OUT:
				takeTurn();
				break;
			case IO_INTERRUPT:
				block();
				break;
			case END:
				destroy();
				break;
			default:
				break;
			}
		}
	}

	/**
	 * ��ת������ǰ�������ھ������У�ʹ��������ͷ�Ľ�������
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
			insExecutor.init(runningProcess.getCode(), runningProcess.getRegisters(), TIMESLICE);
		}
	}

	private LinkedBlockingQueue<PCB> getQueue(Queue_Type type) {
		return processQueues.get(type.ordinal());
	}

	public static int getMAX_NUMBE_OF_PROCESS() {
		return MAX_NUMBE_OF_PROCESS;
	}
}
