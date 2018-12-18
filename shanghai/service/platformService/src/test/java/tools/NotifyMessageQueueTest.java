package tools;

import org.junit.Test;

import common.utils.jms.NotifyMessageQueue;
import common.utils.txp.TxpSqlNotify;

public class NotifyMessageQueueTest 
{
	@Test
	public void test() throws InterruptedException
	{
		NotifyMessageQueue queue = new NotifyMessageQueue();
		for(int i = 0; i< 1000;i++){
			TxpSqlNotify tsn = new TxpSqlNotify();
			tsn.setCmd("cmd");
			tsn.setMethod("update");
			tsn.setTableName("t" + i);
			queue.put(tsn);
		}		
		System.out.println("OK1");
		for(int i = 0; i< 100;i++)
		{
			queue.poll();
		}
		System.out.println("OK2");
		for(int i = 0; i< 99;i++){
			TxpSqlNotify tsn = new TxpSqlNotify();
			tsn.setCmd("cmd");
			tsn.setMethod("update");
			tsn.setTableName("t" + i);
			queue.put(tsn);
		}
		System.out.println("OK3");
		
		for(int i = 10; i< 100;i++){
			TxpSqlNotify tsn = new TxpSqlNotify();
			tsn.setCmd("cmd");
			tsn.setMethod("update");
			tsn.setTableName("t" + i);
			queue.put(tsn);
		}
		System.out.println("OK4");
		
		for(int i = 1000; i< 1001;i++){
			TxpSqlNotify tsn = new TxpSqlNotify();
			tsn.setCmd("cmd");
			tsn.setMethod("update");
			tsn.setTableName("t" + i);
			queue.put(tsn);
		}
		
		System.out.println("OK4");
	}

}
