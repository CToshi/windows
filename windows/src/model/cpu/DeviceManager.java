package model.cpu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import application.Main;
import javafx.util.Pair;
import model.cpu.process.PCB;

public class DeviceManager {
	private static DeviceManager deviceManager;
	private static int MAX_NUMBER_OF_A_DEVICE = 2;
	private static int MAX_NUMBER_OF_B_DEVICE = 3;
	private static int MAX_NUMBER_OF_C_DEVICE = 3;
	private ArrayList<Pair<Integer, Integer>> usingProcess;
	private HashMap<Character, ArrayList<Device>> map_of_device;
	private HashMap<Character, Queue<PCB>> map_of_pcbQueue;
	private HashMap<PCB, Integer> map_of_time;
	private HashMap<Character, Queue<Device>> map_of_freeDeviceQueue;
	static {
		deviceManager = new DeviceManager();
	}

	private DeviceManager() {
		usingProcess = new ArrayList<>(MAX_NUMBER_OF_A_DEVICE + MAX_NUMBER_OF_B_DEVICE + MAX_NUMBER_OF_C_DEVICE);
		map_of_device = new HashMap<>();
		map_of_pcbQueue = new HashMap<>();
		map_of_time = new HashMap<>();
		map_of_freeDeviceQueue = new HashMap<>();

		for (int i = 0; i < MAX_NUMBER_OF_A_DEVICE + MAX_NUMBER_OF_B_DEVICE + MAX_NUMBER_OF_C_DEVICE; i++) {
			usingProcess.add(new Pair<Integer, Integer>(0, 0));
		}

		int index = 0;
		for (char i = 'A'; i <= 'C'; i++) {
			int iniSize;
			if (i == 'A') {
				iniSize = MAX_NUMBER_OF_A_DEVICE;
			} else if (i == 'B') {
				iniSize = MAX_NUMBER_OF_B_DEVICE;
			} else {
				iniSize = MAX_NUMBER_OF_C_DEVICE;
			}

			map_of_device.put(i, new ArrayList<>(iniSize));
			map_of_pcbQueue.put(i, new LinkedList<>());
			map_of_freeDeviceQueue.put(i, new LinkedList<>());

			for (int j = 0; j < iniSize; j++) {
				Device device = new Device(i, index++);
				map_of_device.get(i).add(device);
				map_of_freeDeviceQueue.get(i).offer(device);
			}
		}
	}

	public void request(PCB pcb, char device_ID, int time) {
		if (map_of_freeDeviceQueue.get(device_ID).isEmpty()) {
			map_of_pcbQueue.get(device_ID).offer(pcb);
			map_of_time.put(pcb, time);
		} else {
			Device device = map_of_freeDeviceQueue.get(device_ID).poll();
			device.setRemainTime(time);
			device.setPcb(pcb);
			device.setFree(false);
			usingProcess.set(device.getIndex_of_usingProcess(), new Pair<Integer, Integer>(pcb.getID(), time));
		}
	}

	public void release(Device device) {
		CPU.getInstance().awake(device.getPcb());
		map_of_freeDeviceQueue.get(device.getDevice_ID()).offer(device);
		device.setFree(true);
		device.setPcb(null);
		usingProcess.set(device.getIndex_of_usingProcess(), new Pair<Integer, Integer>(0, 0));
	}

	public void occupy(char device_ID) {
		if (!map_of_pcbQueue.get(device_ID).isEmpty()) {
			PCB pcb = map_of_pcbQueue.get(device_ID).poll();
			request(pcb, device_ID, map_of_time.get(pcb));
		}
	}

	public void work() {
		for (char i = 'A'; i <= 'C'; i++) {
			for (Device device : map_of_device.get(i)) {
				device.run(usingProcess);
			}
		}
	}

	public ArrayList<Pair<Integer, Integer>> getUsingProcess() {
		return usingProcess;
	}

	public Collection<Queue<PCB>> getRequestQueues() {
		Iterator<Entry<Character, Queue<PCB>>> iterator = map_of_pcbQueue.entrySet().iterator();
		return Stream.generate(iterator::next).limit(map_of_pcbQueue.size()).map(entry -> entry.getValue())
				.collect(Collectors.toList());
	}

	public static DeviceManager getInstance() {
		return deviceManager;
	}
}