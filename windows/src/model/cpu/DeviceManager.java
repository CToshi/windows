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
	private HashMap<Character, Queue<Device>> map_of_freeDeviceQueue;

	private DeviceManager() {
		map_of_device = new HashMap<>();
		map_of_pcbQueue = new HashMap<>();

		map_of_device.put('A', new ArrayList<>(MAX_NUMBER_OF_A_DEVICE));
		map_of_device.put('B', new ArrayList<>(MAX_NUMBER_OF_B_DEVICE));
		map_of_device.put('C', new ArrayList<>(MAX_NUMBER_OF_C_DEVICE));

		map_of_pcbQueue.put('A', new LinkedList<>());
		map_of_pcbQueue.put('B', new LinkedList<>());
		map_of_pcbQueue.put('C', new LinkedList<>());

		map_of_freeDeviceQueue.put('A', new LinkedList<>());
		map_of_freeDeviceQueue.put('B', new LinkedList<>());
		map_of_freeDeviceQueue.put('C', new LinkedList<>());

		for (int i = 0; i < MAX_NUMBER_OF_A_DEVICE; i++) {
			Device device = new Device('A');
			map_of_device.get('A').add(device);
			map_of_freeDeviceQueue.get('A').offer(device);
		}
		for (int i = 0; i < MAX_NUMBER_OF_B_DEVICE; i++) {
			Device device = new Device('B');
			map_of_device.get('B').add(device);
			map_of_freeDeviceQueue.get('B').offer(device);
		}
		for (int i = 0; i < MAX_NUMBER_OF_C_DEVICE; i++) {
			Device device = new Device('C');
			map_of_device.get('C').add(device);
			map_of_freeDeviceQueue.get('C').offer(device);
		}
	}

	public Device request(PCB pcb, char device_ID, int time) {
		if(map_of_freeDeviceQueue.get(device_ID).isEmpty()) {
			map_of_pcbQueue.get(device_ID).offer(pcb);
			return null;
		}else {
			Device device = map_of_freeDeviceQueue.get(device_ID).poll();
			device.setRemainTime(time);
			device.setFree(false);
			return device;
		}
	}
	
	public void release(Device device) {
		map_of_freeDeviceQueue.get(device.getDevice_ID()).offer(device);
		device.setFree(true);
		device.setRemainTime(0);
	}

	public static DeviceManager getInstance() {
		return deviceManager;
	}
}
