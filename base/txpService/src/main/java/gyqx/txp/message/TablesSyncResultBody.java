package gyqx.txp.message;

import java.util.List;
import java.util.Map;

import gyqx.txp.data.SyncDataSet;

public class TablesSyncResultBody implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8386758807390933593L;
	private List<SyncDataSet> data;

	public List<SyncDataSet> getData() {
		return data;
	}

	public void setData(List<SyncDataSet> data) {
		this.data = data;
	}
	

}
