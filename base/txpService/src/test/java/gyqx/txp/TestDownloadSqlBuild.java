package gyqx.txp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import gyqx.txp.configuration.TableConfig;
import gyqx.txp.utils.sql.TaskSqlBuilder;

public class TestDownloadSqlBuild 
{
	@Test
	public void test() throws Exception
	{
		TableConfig tc1,tc2,tc3;
		tc1 = new TableConfig();
		tc1.setTableName("tab1");
		tc1.setFkName("pid1");
		tc1.setParentId("id");
		tc1.setParentTableName("tab2");
		
		tc2 = new TableConfig();
		tc2.setTableName("tab2");
		tc2.setFkName("pid3");
		tc2.setParentId("id");
		tc2.setParentTableName("tab3");
		
		tc3 = new TableConfig();
		tc3.setTableName("tab3");
		tc3.setSiteIdFieldName("hosp_id");
		
		
		TableConfig.setDefaultValues(tc1);
		TableConfig.setDefaultValues(tc2);
		TableConfig.setDefaultValues(tc3);
		
		List<TableConfig> tcs = new ArrayList<>();
		tcs.add(tc1);
		tcs.add(tc2);
		tcs.add(tc3);
		
		Map<String,String> map = TaskSqlBuilder.buildSqlForDownloadTask(tcs,"'h01','h02'");
		System.out.println("");
		
	}

}
