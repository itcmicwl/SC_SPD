package gyqx.txp.data;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RecordFile 
{
	public static void writeRecord(List<Map<String,Object>> record)
	{
		
	}
	private static void writeRecord(Map<String,Object> row)
	{
		ObjectMapper mapper = new ObjectMapper();
		
	}
	private static void writeObject(Object v)
	{
		if(v == null)
		{
			
		}
		if(v instanceof String)
		{
			
		}else if(v instanceof Date)
		{
			
		}
	}
	

}
