package gyqx.txp.data;

import java.util.ArrayList;
import java.util.List;

public class SyncSqlSet 
{
	private List<String> insertDataLoadSqls = new ArrayList<>();
	private List<String> updateDataLoadSqls= new ArrayList<>();
	private List<String> deleteDataLoadSqls= new ArrayList<>();
	public List<String> getInsertDataLoadSqls() {
		return insertDataLoadSqls;
	}
	public void setInsertDataLoadSqls(List<String> insertDataLoadSqls) {
		this.insertDataLoadSqls = insertDataLoadSqls;
	}
	public List<String> getUpdateDataLoadSqls() {
		return updateDataLoadSqls;
	}
	public void setUpdateDataLoadSqls(List<String> updateDataLoadSqls) {
		this.updateDataLoadSqls = updateDataLoadSqls;
	}
	public List<String> getDeleteDataLoadSqls() {
		return deleteDataLoadSqls;
	}
	public void setDeleteDataLoadSqls(List<String> deleteDataLoadSqls) {
		this.deleteDataLoadSqls = deleteDataLoadSqls;
	}
	
	
	
}
