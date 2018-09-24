package model.cpu;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import model.cpu.process.PCB;
import model.cpu.process.ProcessCode;
import model.memory.MemoryBlock;

/**
 *
 * @author 黄芝林
 *
 */
public class PCBManager {
	/**
	 * pid到PCb的映射map
	 */
	private ConcurrentHashMap<Integer, PCB> map;
	/**
	 * 空白PCB队列
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
		/**
		 * 不回收闲逛进程
		 */
		if(pcb.getID() == 0) {
			return;
		}
		map.remove(pcb.getID(), pcb);
		try {
			queue.put(pcb.getID());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @return 是否可以申请pcb
	 */	public boolean available() {
		return !queue.isEmpty();
	}
}
