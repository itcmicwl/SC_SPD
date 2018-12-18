package gyqx.txp.utils.exception;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.PrintWriter;

import gyqx.txp.message.TxpMessage;

public class ErrorMessager 
{
	public static String fromException(Exception ex)
	{

		CharArrayWriter cwriter = new CharArrayWriter(100);
		PrintWriter pw = new PrintWriter(cwriter);
		ex.printStackTrace(pw);
		pw.flush();
		
		return cwriter.toString();		
	}
	public static String fromTxpMessage(TxpMessage msg)
	{
		String s = String.format("中心出错:任务:%s,分类 信息:%s,\nDataSyncDatetime:%s,DataSyncVersion:%d,fromId:%s\n%s",msg.getTaskName(),
				msg.getCatalog(),
				msg.getDataSyncDatetime(),msg.getDataSyncVersion(),msg.getFromId(),
				msg.getDescription()
				);
		return s;
	}

}
