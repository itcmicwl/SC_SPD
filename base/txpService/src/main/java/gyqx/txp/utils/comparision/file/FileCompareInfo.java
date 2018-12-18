package gyqx.txp.utils.comparision.file;

public class FileCompareInfo {
	private String fileName;
	private String hash;
	private long size;
	private boolean omit;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	
	public boolean isOmit() {
		return omit;
	}
	public void setOmit(boolean omit) {
		this.omit = omit;
	}
	@Override
	public String toString() {
		return "FileCompareInfo [fileName=" + fileName + ", hash=" + hash + ", size=" + size + ", omit=" + omit + "]";
	}
	
}
