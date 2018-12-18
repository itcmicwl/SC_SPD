package gyqx.txp;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.apache.camel.dataformat.zipfile.ZipFileDataFormat;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gyqx.txp.runtime.Starter;
import gyqx.txp.utils.json.Json;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import oracle.jdbc.*;
import oracle.jdbc.dcn.*;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;


public class MainTestMysql2Oracle 
{
	private static final Logger logger = LoggerFactory.getLogger("test");

	@Test
	public void test() throws Exception
	{
		//Starter.main("/txpConfig/txp-mysql2oracle.xml");
		
		Starter.main("/testConfig/txp-oracleDataCompare.xml");
		
		/*String jsonCenter = FileUtils.readFileToString(new File("D:/temp/四川/t/txp_center.json"),"utf-8");
		String jsonTest = FileUtils.readFileToString(new File("D:/temp/四川/t/txp_test.json"),"utf-8");
		
		List<Map<String,Object>> center = (List<Map<String,Object>>) Json.readObject(jsonCenter);
		List<Map<String,Object>> test = (List<Map<String,Object>>) Json.readObject(jsonTest);
		
		Map<String,Map<String,Object>> mapCenter = new HashMap<>();
		
		for(Map<String,Object> record :center)
		{
			mapCenter.put((String)record.get("id"),record);
		}
		List<Map<String,Object>> result = new ArrayList<>();
		for(Map<String,Object> record :test)
		{
			Map<String,Object> centerRecord = mapCenter.get(record.get("id"));
			if(centerRecord == null)
				continue;
			
			String centerPid = (String) centerRecord.get("pid");
			String testPid = (String) record.get("pid");
			
			if(!centerPid.equalsIgnoreCase(testPid))
			{
				logger.error("id={}; center pid={}; test pid={},version={}",
						centerRecord.get("id"),centerPid,testPid,record.get("version"));
				result.add(record);
				record.put("version", 3);
			}			
			
		}
		String sResult = Json.writeObject(result);
		FileUtils.writeStringToFile(new File("D:/temp/四川/txp_result.json"),sResult,"utf-8");*/
		
	}
}


