package model.cpu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import model.cpu.process.PCB;

public class DeviceManager {
	private static DeviceManager deviceManager = new DeviceManager();
	private static int MAX_NUMBER_OF_A_DEVICE = 2;
	private static int MAX_NUMBER_OF_B_DEVICE = 3;
	private static int MAX_NUMBER_OF_C_DEVICE = 3;
	private HashMap<Character, ArrayList<Device>> map_of_device;
	private HashMap<Character, Queue<PCB>> map_of_pcbQueue;
	private HashMap<PCB, Integer> map_of_time;
	private HashMap<Character, Queue<Device>> map_of_freeDeviceQueue;

	private DeviceManager() {
		map_of_device = new HashMap<>();
		map_of_pcbQueue = new HashMap<>();
		map_of_time = new HashMap<>();
		
		for (char i = 'a'; i <= 'c'; i++) {
			int iniSize;
			if (i == 'a') {
				iniSize=MAX_NUMBER_OF_A_DEVICE;
			} else if (i == 'b') {
				iniSize=MAX_NUMBER_OF_B_DEVICE;
			} else {
				iniSize=MAX_NUMBER_OF_C_DEVICE;
			}
			
			map_of_device.put(i,new ArrayList<>(iniSize));
			map_of_pcbQueue.put(i, new LinkedList<>());
			map_of_freeDeviceQueue.put(i, new LinkedList<>());
			
			for (int j = 0; j < iniSize; j++) {
				Device device = new Device(i);
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
			device.setFree(false);
		}
	}

	public void release(Device device) {
		CPU.getInstance().awake(device.getPcb());
		map_of_freeDeviceQueue.get(device.getDevice_ID()).offer(device);
		device.setFree(true);
		device.setPcb(null);
	}
	
	public void occupy(char device_ID) {
		if(!map_of_pcbQueue.get(device_ID).isEmpty()) {
			PCB pcb = map_of_pcbQueue.get(device_ID).poll();
			request(pcb, device_ID, map_of_time.get(pcb));
		}
	}

	public void work() {
		for (char i = 'a'; i <= 'c'; i++) {
			for (Device device : map_of_device.get(i)) {
				device.run();
			}
		}
	}

	public static DeviceManager getInstance() {
		return deviceManager;
	}
}
