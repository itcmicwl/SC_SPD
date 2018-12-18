package gyqx.txp.message;

import java.util.List;
import java.util.Map;

import gyqx.txp.data.RecordSet;

public class TablesSyncBody implements java.io.Serializable
{
	private RecordSet data;

	public RecordSet getData() {
		return data;
	}

	public void setData(RecordSet data) {
		this.data = data;
	}	

}
