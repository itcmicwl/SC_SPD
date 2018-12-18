package gyqx.txp.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import gyqx.txp.configuration.TxpServerConfig;
import gyqx.txp.data.queue.NotifyMessageQueue;
import gyqx.txp.message.TaskNotifyMessage;

public class NotifyMessageBucketBase 
{
	private  NotifyMessageQueue queue = new NotifyMessageQueue();
	private  NotifyMessageQueue localQueue = new NotifyMessageQueue();
	public  void put(TaskNotifyMessage nm) throws InterruptedException
	{
		queue.put(nm);
	}
	
	public  TaskNotifyMessage pull() throws InterruptedException
	{		
		return queue.poll(2, TimeUnit.SECONDS);
	}
	
	public  void filterPut(List<TaskNotifyMessage> nms) throws InterruptedException
	{
		for(TaskNotifyMessage tnm: nms)
			localQueue.put(tnm);
	}
	
	public  List<TaskNotifyMessage> pullTaskNotifications() throws InterruptedException
	{
		List<TaskNotifyMessage> messages = new ArrayList<>();
		Map<String,TaskNotifyMessage> map = new HashMap<>();
		while(localQueue.size() >0)
		{
			TaskNotifyMessage tnm= localQueue.poll(2, TimeUnit.SECONDS);
			if(null == map.get(tnm.getTaskName()))
			{
				map.put(tnm.getTaskName(),tnm);
				messages.add(tnm);
			}
		}
		return messages;
	}

}
