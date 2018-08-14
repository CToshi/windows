package model.cpu;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import model.cpu.process.PCB;
import model.cpu.process.ProcessCode;
import model.memory.MemoryBlock;

/**
 *
 * @author ��֥��
 *
 */
public class PCBManager {
	/**
	 * pid��PCb��ӳ��map
	 */
	private ConcurrentHashMap<Integer, PCB> map;
	/**
	 * �հ�PCB����
	 */
	private LinkedBlockingQueue<Integer> queue;

	public PCBManager() {
		map = new ConcurrentHashMap<>();
		queue = new LinkedBlockingQueue<>(CPU.getMAX_NUMBE_OF_PROCESS());
		for (int i = 0; i < CPU.getMAX_NUMBE_OF_PROCESS(); i++) {
			try {
				queue.put(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private int getNewPID() {
		Integer pid = queue.poll();
		if (pid != null) {
			return pid;
		}
		return -1;
	}

	public PCB allocatePCB(MemoryBlock block, ProcessCode code) {
		int id = getNewPID();
		if (id == -1) {
			return null;
		}
		PCB pcb = new PCB(id, block, code);
		map.put(id, pcb);
		return pcb;
	}

	public PCB getPCB(int pid) {
		return map.get(pid);
	}

	public void recoverPCB(PCB pcb) {
		map.remove(pcb.getID(), pcb);
		try {
			queue.put(pcb.getID());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @return �Ƿ��������pcb
	 */
		return !queue.isEmpty();
	}
}