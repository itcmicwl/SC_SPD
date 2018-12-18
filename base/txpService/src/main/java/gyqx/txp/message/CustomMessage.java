package gyqx.txp.message;

import java.util.List;

public class CustomMessage 
{
	private String taskName;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public List<CustomRecords> getRecords() {
		return records;
	}

	public void setRecords(List<CustomRecords> records) {
		this.records = records;
	}

	private List<CustomRecords> records;
	

}
