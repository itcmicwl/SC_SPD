package gyqx.txp.runtime.status;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import gyqx.txp.message.TaskNotifyMessage;
import gyqx.txp.utils.json.Json;


public class LoggerRedoWorker 
{
	private static final Logger logger = LoggerFactory.getLogger(LoggerRedoWorker.class);
	private OutputStreamWriter loggerWriter = null;
	
	private String dir;
	private String redoLogFileName;
	
	private int writeCount = 0;

	public LoggerRedoWorker(String redoLogFileName) throws IOException 
	{
		URL url = LoggerRedoWorker.class.getResource("/");
		File file = FileUtils.toFile(url);
		if(!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		dir = file.getParentFile().getAbsolutePath().replace("\\","/");
		logger.info("dir={}",dir);
		
		String path = file.getAbsolutePath().replace("\\","/") + "/" + redoLogFileName;
		logger.info("redoLogFileName={}",path);
		String folder = path.substring(0, path.lastIndexOf("/"));
		File logFolder = new File(folder);
		if(!logFolder.exists())
		{
			logFolder.mkdirs();
		}
		File logFile = new File(path);
		if(!logFile.exists()){
			logFile.createNewFile();
		}
		dir = logFile.getParentFile().getAbsolutePath().replace("\\","/");
		this.redoLogFileName = logFile.getName();
		logger.info("redoLogFileName={}",path);
		loggerWriter = new OutputStreamWriter(new FileOutputStream(logFile,true), "utf-8");
		
	}
	private static final String left_barrier = "[~|";
	private static final String right_barrier = "|~]";
	
	public synchronized void redo(IRedoCallback cb) throws IOException
	{
		
		ObjectMapper mapper = new ObjectMapper(); 
		
		File tmpLog = new File(dir + "/" + redoLogFileName + "t1"); 
		if(tmpLog.exists())
		{
			BufferedReader reader = IOUtils.toBufferedReader( new InputStreamReader(new FileInputStream(tmpLog), Charsets.toCharset("utf8")));	
			redo(reader,mapper,cb);
			reader.close();	
		}
		
		tmpLog = new File(dir + "/" + redoLogFileName + "t");
		if(tmpLog.exists())
		{
			BufferedReader reader = IOUtils.toBufferedReader( new InputStreamReader(new FileInputStream(tmpLog), Charsets.toCharset("utf8")));	
			redo(reader,mapper,cb);
			reader.close();	
		}
		
		
		BufferedReader reader = IOUtils.toBufferedReader( new InputStreamReader(new FileInputStream(dir + "/" + redoLogFileName), Charsets.toCharset("utf8")));	
		redo(reader,mapper,cb);
		reader.close();			

	}
	/**
	 * 截断日志
	 * 先备份之前的日志，以便在故障中恢复
	 * @throws IOException
	 */
	public synchronized void truncate() throws IOException
	{
		File tmpLog = new File(dir + "/" + redoLogFileName + "t");
		if(tmpLog.exists())
		{
			FileUtils.copyFile(tmpLog, new File(dir + "/" + redoLogFileName + "t1"));
			tmpLog.delete();
		}
		
		File log = new File(dir + "/" + redoLogFileName );
		FileUtils.copyFile(log, new File(dir + "/" + redoLogFileName + "t"));		
		log.delete();
		
		loggerWriter = new OutputStreamWriter(new FileOutputStream(log), "utf-8");
		
		writeCount = 0; // 计数清0
		
	}
	/**
	 * 清理在截断日志操作中生成的临时文件
	 */
	public synchronized void clearTemp()
	{
		File tmpLog = new File(dir + "/" + redoLogFileName + "t");
		if(tmpLog.exists())
			tmpLog.delete();
		tmpLog = new File(dir + "/" + redoLogFileName + "t1");
		if(tmpLog.exists())
			tmpLog.delete();
	}
	
	private void redo(BufferedReader reader,ObjectMapper mapper, IRedoCallback cb) throws IOException
	{
		
		int status = 0;// 
		StringBuilder sb = new StringBuilder();
		int ihead ,itail;
		while(true)
		{
			String line = reader.readLine();
			if(line == null)
				break;
			switch(status)
			{
			case 0:
				ihead = line.indexOf(left_barrier);
				itail = line.indexOf(right_barrier);
				if(ihead >=0)
				{
					status = 1; // begin line parse 
				}				
				if(itail >=0 && itail == line.length() -3 )
				{
					sb.append( line.substring(ihead +3, itail));
					status = 2; // line parse finish.
				}else{
					sb.append(line.substring(ihead +3,line.length()));
				}
				break;
			case 1:
				itail = line.indexOf(right_barrier);
				if(itail >=0 && itail == line.length() -3 )
				{
					sb.append( line.substring(0, itail));
					status = 2; // line parse finish.
				}else{
					sb.append(line.substring(0,line.length()));
				}
				break;
			}
			if(status == 2)
			{
				parseLine(cb,mapper,sb.toString());
				status = 0;
				sb.setLength(0);
			}
		}
	}
	private void parseLine(IRedoCallback cb,ObjectMapper mapper,String line) throws JsonProcessingException, IOException
	{
		JsonNode jnode = mapper.readTree(line);
		String ts = jnode.get("tag").asText();
		if(ts.equalsIgnoreCase(TaskWorkStatus.LOG_TAG))
		{
			TaskWorkStatus twStatus = new TaskWorkStatus();
			twStatus.loadJson(jnode);
			cb.redoTaskWorkStatus(twStatus);
			
		}else if(ts.equalsIgnoreCase(TableTxpStatus.LOG_TAG))
		{
			TableTxpStatus ttStatus = new TableTxpStatus();
			ttStatus.loadJson(jnode);
			
			cb.redoTableTxpStatus(ttStatus);
		}else if(ts.equalsIgnoreCase(TaskNotifyMessage.LOG_TAG))
		{
			TaskNotifyMessage ttStatus = new TaskNotifyMessage();
			ttStatus.loadJson(jnode);
			
			cb.redoTaskNotifyMessage(ttStatus);
		}
	}	

	public  int writeLog(StatusLogItem item) throws IOException
	{
		return writeLog(item,true);
	}
	public  int writeLog(StatusLogItem item,boolean bForceFlush) throws IOException
	{
		String msg = Json.write(item);
		return writeData(msg,bForceFlush);
	}
	
	private synchronized int writeData(String msg,boolean bForceFlush) throws IOException
	{
		String sNow = DateFormatUtils.format(new Date(),"yyyy-MM-dd hh:mm:ss.SSS" );
		loggerWriter.write(sNow);
		loggerWriter.write(left_barrier);
		loggerWriter.write(msg);
		loggerWriter.write(right_barrier);
		loggerWriter.write("\n");
		if(bForceFlush)
			loggerWriter.flush();
		writeCount ++;
		
		return writeCount;
		
	}
	public void flush() throws IOException
	{
		loggerWriter.flush();
	}
	public void close() throws IOException
	{
		loggerWriter.flush();
		loggerWriter.close();
	}

}
