package gyqx.txp.data;

import java.util.HashMap;
import java.util.Map;

public class KV extends HashMap<String,Object> 
{
	public KV(Object ...objects)
	{
		
		for(int i = 0; i< objects.length; i += 2)
		{
			if(objects[i].getClass().isArray())
			{
				Object[] kv = (Object[]) objects[i];
				if(kv[0].getClass().isArray())
				{
					for(int j = 0; j < kv.length;j++)
					{
						Object[] subkv = (Object[]) kv[0];
						this.put(subkv[0].toString(),subkv[1]);
					}
					
				}else
				this.put(kv[0].toString(),kv[1]);
			}else{
				this.put(objects[i].toString(),objects[i+1]);
			}
		}
	}
	public static KV from (Object... objects)
	{
		return new KV(objects);
	}
	public static <K,V> Map<K,V> New(Object... objects)
	{
		HashMap<K,V> map = new HashMap<>();
		for(int i = 0; i< objects.length; i += 2)
		{
			if(objects[i].getClass().isArray())
			{
				Object[] kv = (Object[]) objects[i];
				if(kv[0].getClass().isArray())
				{
					for(int j = 0; j < kv.length;j++)
					{
						Object[] subkv = (Object[]) kv[0];
						map.put((K)subkv[0],(V)subkv[1]);
					}
					
				}else
				map.put((K)kv[0],(V)kv[1]);
			}else{
				map.put((K)objects[i],(V)objects);
			}
		}
		return map;
	}

}
