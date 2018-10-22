package model.disk;

import java.util.ArrayList;

public class FAT {
	// 管理Fat数组
	private int[] fat;

	private static FAT f = new FAT();

	public static FAT getInstance() {
		return f;
	}

	private FAT() {
		init();
	}

	/**
	 * FAT初始化
	 * @return 初始化是否成功
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
	 * 修改FAT，capacity为该文件的大小，同时分配了磁盘块
	 * @return int:返回起始块号
	 */
	public int changeFAT(int capacity) {
		int number = 0;
		int last = 0;
		int startNum = -1;
		int numberOfDiskBlocks = capacity / Disk.getInstance().CAPACITY_OF_DISK_BLOCKS;
//		System.out.println("所需要的磁盘块"+numberOfDiskBlocks);
		if (capacity % Disk.getInstance().CAPACITY_OF_DISK_BLOCKS != 0||capacity==0) {
			numberOfDiskBlocks++;
		}
		/**
		 * i从3到Disk.MAX_SPACE_OF_DISK-1 /
		 * 最多到255，j从0开始，要循环numberOfDiskBlocks次，如果循环结束时，j！=numberOfDiskBlocks，则磁盘空间不足，提示保存错误，并回收已分配磁盘。
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
		 * 最后一项内容为-1。
		 */
			fat[number] = -1;	
		return startNum;

	}

	/**
	 * 该方法用于回收文件占用的磁盘块
	 * @param startNumber
	 * @return boolean:返回false则说明起始块号错误，越界
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
	 * 该方法返回空闲磁盘块
	 * 
	 * @return int: 空闲的磁盘块数
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
	 * 返回fat第number项的内容
	 * 
	 * @param number
	 * @return int:fat[number]
	 */
	public int getNext(int number) {
		return fat[number];
	}

	/**
	 * 返回一个数组，数组里存放的是空闲磁盘块的编号,用于显示磁盘块占用情况
	 * @return  ArrayList<Integer>:磁盘块占用情况,该数组中的编号为被占用的磁盘块的编号
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
	 * 返回FAT的使用情况
	 * @return int[]:值与FAT相同的
	 */
	public int[] getFAT() {
		int[] returnFat=new int[fat.length];
		returnFat=fat.clone();
		return returnFat;
	}

	//追加磁盘块
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
	
	//回收n个磁盘块  参数列表为起始磁盘块号，原来占的磁盘块数和修改后占的磁盘块数
	public boolean remove(int startNum,int numOfBlocks,int nowNumOfBlocks) {
		boolean succeed =true;
		int index = startNum;
		for(int i =0;i<nowNumOfBlocks-1;i++) {
			index = fat[index];
		}
		int abandomIndex =  fat[index];
		//最后一块为-1
		fat[index] = -1;
		while(fat[abandomIndex]!=-1) {
			index = fat[abandomIndex];
			fat[abandomIndex] = 0;
			abandomIndex =index;
		}
		//原最后一块磁盘块也释放了
		fat[abandomIndex]=0;
		return succeed;
	}
}
