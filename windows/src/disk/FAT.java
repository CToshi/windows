package disk;

public class FAT {
	//管理Fat数组
	private int[] Fat;
	
	private static FAT f = new FAT();
	private static FAT gitInstance() {
		return f;
	}
	private FAT() {
		init();
	}
	
	//FAT初始化
	private void init() {
		Fat = new int[Disk.MAX_SPACE_OF_DISK];
		for(int i =0;i<Disk.MAX_SPACE_OF_DISK;i++) {
			if(i<Disk.MAX_SPACE_OF_DISK/Disk.CAPACITY_OF_DISK_BLOCKS) {
				Fat[i]=-1;
			}else {
				Fat[i]=0;
			}
		}
	}
	
	//修改FAT,number为磁盘块号，next为该项的下一项编号
	public void changeFAT(int number,int next) {
		Fat[number] = next; 
	}
	
	//回收
	public void recovery(int startNumber) {
		int number = startNumber;
		
		while(Fat[number]!=-1) {
			int temp =number;
			number = Fat[number];
			Fat[temp] = 0;
		}
		
		Fat[number] = 0;
	}
}
