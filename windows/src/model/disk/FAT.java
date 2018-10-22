package model.disk;

import java.util.ArrayList;

public class FAT {
	// ����Fat����
	private int[] fat;

	private static FAT f = new FAT();

	public static FAT getInstance() {
		return f;
	}

	private FAT() {
		init();
	}

	/**
	 * FAT��ʼ��
	 * @return ��ʼ���Ƿ�ɹ�
	 */
	private boolean init() {
		fat = new int[Disk.getInstance().MAX_SPACE_OF_DISK];
		for(int i=0;i<3;i++) {
			fat[i]=-1;
		}
		for (int i = 3; i < Disk.getInstance().MAX_SPACE_OF_DISK; i++) {
			if (i < Disk.getInstance().MAX_SPACE_OF_DISK / Disk.getInstance().CAPACITY_OF_DISK_BLOCKS) {

				fat[i] = 0;

			} else {
				fat[i] = 0;
			}
		}
		return true;
	}

	/**
	 * �޸�FAT��capacityΪ���ļ��Ĵ�С��ͬʱ�����˴��̿�
	 * @return int:������ʼ���
	 */
	public int changeFAT(int capacity) {
		int number = 0;
		int last = 0;
		int startNum = -1;
		int numberOfDiskBlocks = capacity / Disk.getInstance().CAPACITY_OF_DISK_BLOCKS;
//		System.out.println("����Ҫ�Ĵ��̿�"+numberOfDiskBlocks);
		if (capacity % Disk.getInstance().CAPACITY_OF_DISK_BLOCKS != 0||capacity==0) {
			numberOfDiskBlocks++;
		}
		/**
		 * i��3��Disk.MAX_SPACE_OF_DISK-1 /
		 * ��ൽ255��j��0��ʼ��Ҫѭ��numberOfDiskBlocks�Σ����ѭ������ʱ��j��=numberOfDiskBlocks������̿ռ䲻�㣬��ʾ������󣬲������ѷ�����̡�
		 */
		for (int i = 3, j = 0; i < Disk.getInstance().MAX_SPACE_OF_DISK && j < numberOfDiskBlocks; i++) {
			if (fat[i] != 0) {
				continue;
			} else {
				last = number;
				number = i;
				if (last == 0) {
					startNum = number;
				} else {
					fat[last] = number;
				}
				j++;
			}

		}
		/**
		 * ���һ������Ϊ-1��
		 */
			fat[number] = -1;	
		return startNum;

	}

	/**
	 * �÷������ڻ����ļ�ռ�õĴ��̿�
	 * @param startNumber
	 * @return boolean:����false��˵����ʼ��Ŵ���Խ��
	 */
	public boolean recovery(int startNumber) {
		boolean succeed=true;
		if(startNumber>Disk.getInstance().MAX_SPACE_OF_DISK) {
			succeed=false;
		}else {
			int number = startNumber;
			while (fat[number] != -1) {
				int temp = number;
				number = fat[number];
				fat[temp] = 0;
			}

			fat[number] = 0;
		}
		return succeed;
	}

	/**
	 * �÷������ؿ��д��̿�
	 * 
	 * @return int: ���еĴ��̿���
	 */
	public int capacityOfDisk() {
		int capacity = 0;
		for (int i = Disk.getInstance().MAX_SPACE_OF_DISK / Disk.getInstance().CAPACITY_OF_DISK_BLOCKS; i < Disk.getInstance().MAX_SPACE_OF_DISK; i++) {
			if (fat[i] == 0) {
				capacity++;
			}
		}
		return capacity;
	}

	/**
	 * ����fat��number�������
	 * 
	 * @param number
	 * @return int:fat[number]
	 */
	public int getNext(int number) {
		return fat[number];
	}

	/**
	 * ����һ�����飬�������ŵ��ǿ��д��̿�ı��,������ʾ���̿�ռ�����
	 * @return  ArrayList<Integer>:���̿�ռ�����,�������еı��Ϊ��ռ�õĴ��̿�ı��
	 */
	public ArrayList<Integer> getFreeBlocks() {
		ArrayList<Integer> freeBlocks= new ArrayList<>();
		for(int i =0;i<fat.length;i++) {
			if(fat[i]!=0) {
				freeBlocks.add(i);
			}
		}
		return freeBlocks;
	}
	
	/**
	 * ����FAT��ʹ�����
	 * @return int[]:ֵ��FAT��ͬ��
	 */
	public int[] getFAT() {
		int[] returnFat=new int[fat.length];
		returnFat=fat.clone();
		return returnFat;
	}

	//׷�Ӵ��̿�
	public boolean Append(int startNum,int addNumOfBlocks) {
		boolean succeed = true;
		int index = startNum;
		while(fat[index]!=-1) {
			index =fat[index];
			System.out.println(index);
		}
		int j =0;
		for(int i =3;i<Disk.getInstance().MAX_SPACE_OF_DISK&&j<addNumOfBlocks-1;i++) {
			if(fat[i]==0) {
				fat[index] = i;
				index =i;
				j++;
			}
		}
		fat[index] = -1;
		if(j!=addNumOfBlocks) {
			succeed = false;
		}
		return succeed;
	}
	
	//����n�����̿�  �����б�Ϊ��ʼ���̿�ţ�ԭ��ռ�Ĵ��̿������޸ĺ�ռ�Ĵ��̿���
	public boolean remove(int startNum,int numOfBlocks,int nowNumOfBlocks) {
		boolean succeed =true;
		int index = startNum;
		for(int i =0;i<nowNumOfBlocks-1;i++) {
			index = fat[index];
		}
		int abandomIndex =  fat[index];
		//���һ��Ϊ-1
		fat[index] = -1;
		while(fat[abandomIndex]!=-1) {
			index = fat[abandomIndex];
			fat[abandomIndex] = 0;
			abandomIndex =index;
		}
		//ԭ���һ����̿�Ҳ�ͷ���
		fat[abandomIndex]=0;
		return succeed;
	}
}
