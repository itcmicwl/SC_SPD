package gyqx.txp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import gyqx.txp.data.KV;
import gyqx.txp.message.TaskNotifyMessage;
import gyqx.txp.runtime.status.IRedoCallback;
import gyqx.txp.runtime.status.LoggerRedoWorker;
import gyqx.txp.runtime.status.TableRecordsCache;
import gyqx.txp.runtime.status.TableTxpStatus;
import gyqx.txp.runtime.status.TaskWorkStatus;
import gyqx.txp.utils.json.Json;


public class WorkStatusLogTest 
{
	private static final Logger logger1 = LoggerFactory.getLogger(WorkStatusLogTest.class);
	int count = 0;
	
	List<TableTxpStatus> sts = new ArrayList<>(); 
	//@Test
	public void test() throws IOException
	{
		
		//Gson gson = new GsonBuilder().create();		 
		/*TableRecordsCache cache = new TableRecordsCache();		
		
		LoggerRedoWorker.current().redo(new IRedoCallback(){

			@Override
			public void redoTaskWorkStatus(TaskWorkStatus twStatus) {

				
			}

			@Override
			public void redoTableTxpStatus(TableTxpStatus ttStatus) {
				count ++;
				cache.refresh(ttStatus);
				
				if(count > 500000 && count <=501000)
				{
					sts.add(ttStatus);
				}
//				try {
//					//System.out.println(Json.writeObject(ttStatus));
//				} catch (JsonProcessingException e) {
//					e.printStackTrace();
//				}				
			}

			@Override
			public void redoTaskNotifyMessage(TaskNotifyMessage ttStatus) {
				// TODO Auto-generated method stub
				
			}
			
		});
		System.out.println(System.currentTimeMillis());*/
//		for(TableTxpStatus st :sts)
//		{
//			cache.remove(st);
//		}
		//cache.removeAll();
		//System.out.println(System.currentTimeMillis());
		//System.out.println(System.currentTimeMillis());
		
		
	/*	
		for(int i = 0; i< 1000000;i++){
			TableTxpStatus status = new TableTxpStatus();	
			status.setTableName("sys_table");
			status.setKeyMap(KV.from("id","some-test-id-1234567890:" + i,"version",i));
			status.setLastSyncDateBegin(new Date());
			status.setLastSyncDateEnd(new Date());
			status.setStatus("00");	
			//String s = Json.write(status);//gson.toJson(status);//(String) JSON.toJSONString(status) ;//status.getTableName();// Json.write(status);
			if(i%100 ==0)
				System.out.println(i);
			//logger.info(s);
			LoggerRedoWorker.current().writeLog(status);
			cache.refresh(status);
			//LoggerRedoWorker.current().writeLog(status);
		}*/
//		ObjectMapper mapper = new ObjectMapper();  
//		JsonNode jnode = mapper.readTree("{\"tableName\":\"sys_table\",\"lastKeyMap\":{\"id\":\"1234\",\"version\":0},\"lastSyncDateBegin\":1510104891406,\"lastSyncDateEnd\":1510104891406,\"status\":\"00\",\"tag\":\"tab\"}");
//		
//		TableTxpStatus status = new TableTxpStatus();
//		
//		status.loadJson(jnode);
		
		System.out.println("...end...");
		
		System.in.read();
		
		
	}
	//@Test
	public void testFileOutputStream() throws IOException
	{
		
		File path = new File("d:/temp/redo.log");
		path.createNewFile();
		FileOutputStream loggerOutputStream = new FileOutputStream(path);		
		OutputStreamWriter writer = new OutputStreamWriter(loggerOutputStream,"utf-8");
		for(int i = 0; i< 1000000;i++){
			TableTxpStatus status = new TableTxpStatus();	
			status.setTableName("sys_table");
			status.setKeyMap(KV.from("id","some-test-id-1234567890:" + i,"version",i));
			status.setLastSyncDateBegin(new Date());
			status.setLastSyncDateEnd(new Date());
			status.setStatus("00");	
			String msg = Json.write(status);//gson.toJson(status);//(String) JSON.toJSONString(status) ;//status.getTableName();// Json.write(status);
			if(i%100 ==0)
				System.out.println(i);

			
			writer.write(msg);
			writer.flush();
//			byte[] data = msg.getBytes("utf-8");
//			loggerOutputStream.write(data);
//			loggerOutputStream.flush();
		}
		//loggerOutputStream.flush();
		loggerOutputStream.close();		
	}
	
	@Test
	public void testRedoWorker() throws IOException
	{
		LoggerRedoWorker redoWorker = new LoggerRedoWorker("redo-test.log");
		redoWorker.clearTemp();
		for(int i = 0; i< 1000000;i++){
			TableTxpStatus status = new TableTxpStatus();	
			status.setTableName("sys_table");
			status.setKeyMap(KV.from("id","some-test-id-1234567890:" + i,"version",i));
			status.setLastSyncDateBegin(new Date());
			status.setLastSyncDateEnd(new Date());
			status.setStatus("00");	
			
			if(i%100 ==0)
				System.out.println(i);
			
			redoWorker.writeLog(status);
			redoWorker.flush();
		}
		
	}
	//@Test
	public void testRedoWorkerRedo() throws IOException
	{
		LoggerRedoWorker redoWorker = new LoggerRedoWorker("redo-test.log");
		List<TableTxpStatus> ttss = new ArrayList<>(); 
		redoWorker.redo(new IRedoCallback(){

			@Override
			public void redoTaskWorkStatus(TaskWorkStatus twStatus) {
				
			}

			@Override
			public void redoTableTxpStatus(TableTxpStatus ttStatus) {
				ttss.add(ttStatus);
			}

			@Override
			public void redoTaskNotifyMessage(TaskNotifyMessage ttStatus) {
				
			}
			
		});
		redoWorker.truncate();
		redoWorker.clearTemp();
		
	}
	
	//@Test
	public void writeLogWithFileChannel() throws IOException
	{
		File outFile = new File("f:/temp/Export.cs");  
		RandomAccessFile file = new RandomAccessFile(outFile,"rw");
		
		
		//FileOutputStream fos = new FileOutputStream(outFile);  
		
		FileChannel channelOut = file.getChannel();// fos.getChannel();  
		
		ByteBuffer buffer = ByteBuffer.allocate(50);  
		buffer.put(new byte[]{1,2,4,5,6,7,8,9,10});
		buffer.flip();
//		byte b = buffer.get();
//		
//		b = buffer.get();
		byte[] buf = new byte[10];
//		buffer.get(buf,0,5);
//		buffer.flip();
		System.out.println(outFile.length());
		file.read(buf);
		channelOut.position( channelOut.size());
		
		while (buffer.hasRemaining()) 
			channelOut.write(buffer);
		
		channelOut.close();
		
	}

}
