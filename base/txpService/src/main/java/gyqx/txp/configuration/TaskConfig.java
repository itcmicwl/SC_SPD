package gyqx.txp.configuration;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

public abstract class TaskConfig 
{
	protected String name;
	protected String catalog;
	protected String idempotentExpression;	
	protected List<String> tableNames = null;
	protected TaskTrigger trigger = null;
	protected boolean directCmd = false;	
	public TaskConfig()
	{
		
	}
	public abstract void init();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}	
	public String getIdempotentExpression() {
		return idempotentExpression;
	}
	public void setIdempotentExpression(String idempotent) {
		this.idempotentExpression = idempotent;
	}
	public List<String> getTableNames() {
		return tableNames;
	}

	public void setTableNames(List<String> tableNames) {
		this.tableNames = tableNames;
	}
	public TaskTrigger getTrigger() {
		return trigger;
	}
	public void setTrigger(TaskTrigger trigger) {
		this.trigger = trigger;
	}	
	public boolean hasTable(String tableName)
	{
		Assert.isTrue(tableNames != null,"任务" + name + ":tables 没有配置!");
		for(String tn:tableNames)
		{
			if(tn.equalsIgnoreCase(tableName))
				return true;
		}
		return false;
	}
	public boolean isDirectCmd() {
		return directCmd;
	}
	public void setDirectCmd(boolean directCmd) {
		this.directCmd = directCmd;
	}
	@Override
	public String toString() {
		return "TaskConfig [name=" + name + ", catalog=" + catalog + ", idempotentExpression=" + idempotentExpression
				+ ", tableNames=" + tableNames + ", trigger=" + trigger + ", directCmd=" + directCmd + "]";
	}
	
	

}
