package gyqx.txp.data.queue;

import java.util.concurrent.locks.ReentrantLock;
import gyqx.txp.message.TaskNotifyMessage;

public class NotifyMessageQueue extends ArrayBlockingQueue<TaskNotifyMessage>
{
	public NotifyMessageQueue(){
		this(1000);
	}

	public NotifyMessageQueue(int capacity) {
		super(capacity);
	}
	
	public boolean add(TaskNotifyMessage e)
	{
		final ReentrantLock lock = this.lock;
        lock.lock();
        try {
        	if(checkEqual(e))
        		return false;
        	return super.add(e);
        } finally {
            lock.unlock();
        }     
				
	}
	
	private boolean checkEqual(TaskNotifyMessage e)
	{
		if(this.count == 0)
			return false;
		for(int cur = this.takeIndex;;)
        { 	
			TaskNotifyMessage item = (TaskNotifyMessage) items[cur];
        	if(item.getTaskName().equalsIgnoreCase(e.getTaskName()) )
        		return true;
        	
        	if(++cur == items.length)
        		cur = 0;
        	if(cur == this.putIndex)
        		break;
        } 
		return false;
	}
	
	public void put(TaskNotifyMessage e) throws InterruptedException
	{
		final ReentrantLock lock = this.lock;
        lock.lock();
        try {
        	if(checkEqual(e))
        		return ;
        	super.put(e);
        } finally {
            lock.unlock();
        }
	}
	

}
