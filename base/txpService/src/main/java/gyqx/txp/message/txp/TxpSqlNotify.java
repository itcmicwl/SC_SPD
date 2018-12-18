package gyqx.txp.message.txp;

public class TxpSqlNotify extends TxpNotify
{
	private String method;
	private String tableName;
	private String sql;
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	@Override
	public boolean check(TxpNotify txp) {
		if(txp instanceof TxpSqlNotify)
		{
			TxpSqlNotify tsn = (TxpSqlNotify) txp;
			if(tsn.method.equalsIgnoreCase(this.method) &&
					tsn.tableName.equalsIgnoreCase(this.tableName) &&
					tsn.getCmd().equals(this.getCmd()))
				return true;
		}
		return false;
	}
	

}
