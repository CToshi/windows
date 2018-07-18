package disk;

public class FAT {
	//����Fat����
	private int[] Fat;
	
	private static FAT f = new FAT();
	private static FAT gitInstance() {
		return f;
	}
	private FAT() {
		init();
	}
	
	//FAT��ʼ��
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
	
	//�޸�FAT,numberΪ���̿�ţ�nextΪ�������һ����
	public void changeFAT(int number,int next) {
		Fat[number] = next; 
	}
	
	//����
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
