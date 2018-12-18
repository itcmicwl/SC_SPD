package gyqx.txp.runtime.status;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TableRecordsCache 
{
	private LinkedBlockingQueue<TableTxpStatus> queue = new LinkedBlockingQueue<>();
	//private ConcurrentLinkedQueue<TableTxpStatus> queue = new ConcurrentLinkedQueue<>(); too slow ! ? 
	
	private ConcurrentHashMap<String,TableTxpStatus> map = new ConcurrentHashMap<>();	

	public void refresh(TableTxpStatus status)
	{
		TableTxpStatus old = map.get(status.key());
		if(old == null)
		{
			map.put(status.key(),status);
			queue.add(status);
			
			while(queue.size() >1024*1024)
			{
				old = queue.poll();
				map.remove(old.key());
			}
		}else{
			old.copyFrom(status);
			queue.remove(old);
			queue.add(old);	
		}		
		
	}
	public void remove(TableTxpStatus status)
	{
		queue.remove(status);
		map.remove(status.key());
	}
	public void removeAll()
	{
//		TableTxpStatus status;
//		while(queue.size() >0)
//		{
//			status = queue.poll();
//			map.remove(status.getKey());
//		}  
		queue.clear();
		map.clear();
	}

	public boolean isExist(TableTxpStatus status)
	{
		return map.get(status.key() ) != null;
	}
	
}
