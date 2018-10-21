package model.disk;

/**
 * @author BFELFISH ����Ϊ�ļ��࣬�������ļ�����
 */
public class Files extends FileItem {
	// �ļ�����
	private String content;

	public Files(Directory father, String fileName, String fileExtentionName, int capacity, int startNum,
			int attributes, String content) {
		super(father, fileName, fileExtentionName, capacity, startNum, attributes);
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * �÷������޸ĸ�����
	 *
	 * @param �����޸ĺ������
	 * @return int:����һ�����֣�0���޸ĳɹ���1��ֻ�������ļ������޸ģ�2�����̿ռ䲻��
	 */
	public int changeFilesContent(String content) {
		int errorCode = 0;
		while (true) {
			if (this.attributes == 0) {
				errorCode = 1;
				break;
			}
			int numberOfBlocks;
			int capacities;
			int numberOfUsedBlocks = this.capacity/ Disk.getInstance().CAPACITY_OF_DISK_BLOCKS + 1;
			// ��������ļ�ռ�ö��ٸ����̿�,����ǿ�ִ���ļ���һ��һ�ֽ�
			if (this.fileExtentionName.equals(".txt")) {
				 capacities = content.getBytes().length;
				numberOfBlocks = capacities / Disk.getInstance().CAPACITY_OF_DISK_BLOCKS + 1;
			} else {
				String[] contents = content.split("\n");
				 capacities = contents.length;
				numberOfBlocks = capacities / Disk.getInstance().CAPACITY_OF_DISK_BLOCKS + 1;
			}
			if (numberOfBlocks > FAT.getInstance().capacityOfDisk()+numberOfUsedBlocks) {
				errorCode = 2;
				break;
			}else {
				FAT.getInstance().recovery(this.startNum);
				this.startNum=FAT.getInstance().changeFAT(capacities);
				this.capacity=capacities;
				this.content = content;
				
			}		
			break;
		}
		
		return errorCode;
	}

	@Override
	/**
	 * ɾ���ļ�
	 */
	public boolean deleteFiles() {
		boolean succeed = true;
		if (this.canBeDeleted) {
			fatherFile.removeFiles(this);
			FAT.getInstance().recovery(this.startNum);
		} else {
			succeed = false;
		}
		return succeed;
	}

	@Override
	public boolean changeAttributes(int attributes) {
		boolean succeed = true;
		this.attributes = attributes;
		return succeed;

	}

	public boolean isTxtFile() {
		if (fileExtentionName.equals(".txt")) {
			return true;
		}
		return false;
	}
}
