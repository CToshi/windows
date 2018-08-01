package model.disk;

public class DiskFile {
	private String fileName;

	public DiskFile(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return fileName;
	}
	
	public boolean isDirectory() {
		if(fileName.endsWith(".e")||fileName.endsWith(".c")) {
			return false;
		}
		return true;
	}
}
