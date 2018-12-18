package gyqx.txp.utils.comparision.file;

import java.util.Arrays;

public class FileData 
{
	private String name;
	private byte[] data;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "FileData [name=" + name + ", data=" + data.length + "]";
	}
	
	

}
