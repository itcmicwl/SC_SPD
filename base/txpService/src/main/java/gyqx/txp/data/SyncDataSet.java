package gyqx.txp.data;

import java.util.List;
import java.util.Map;

public class SyncDataSet implements java.io.Serializable
{
	private String tableName;
	private List<Map<String,Object>> insertList,updateList,deleteList;

	public List<Map<String,Object>> getInsertList() {
		return insertList;
	}

	public void setInsertList(List<Map<String,Object>> insertList) {
		this.insertList = insertList;
	}

	public List<Map<String,Object>> getUpdateList() {
		return updateList;
	}

	public void setUpdateList(List<Map<String,Object>> updateList) {
		this.updateList = updateList;
	}

	public List<Map<String,Object>> getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(List<Map<String,Object>> deleteList) {
		this.deleteList = deleteList;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
