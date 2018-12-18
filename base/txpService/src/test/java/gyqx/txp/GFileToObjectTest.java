package gyqx.txp;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import gyqx.txp.message.TxpMessage;
import gyqx.txp.utils.groovy.GroovyScriptHelper;

public class GFileToObjectTest 
{
	//@Test
	public void test() throws Exception
	{
		String fn = "D:\\Workspaces\\eclipse\\txpService\\target\\txpfiles\\simpleCommand\\a-select-cmd.groovy";
		TxpMessage message = GroovyScriptHelper.getObjectFromFile(new File(fn), TxpMessage.class);
		System.out.println(message);
	}
	@Test
	public void testGetBase64() throws IOException
	{
		String fn="d:\\111.zip";
		File file = new File(fn);
		System.out.println( file.getParentFile().getPath());
		byte[] data = FileUtils.readFileToByteArray(new File(fn));
		String base64 = Base64.getMimeEncoder().encodeToString(data);
		
		FileUtils.write(new File("d:\\111.txt"),base64,"utf-8");
		System.out.println(base64);
	}
	@Test
	public void testrun() throws IOException
	{
		String fn="D:\\Work\\dev\\csharp\\Winsock\\Peer\\Peer\\bin\\Release\\peer.exe";
		File file = new File(fn);
		File dir = file.getParentFile();
		Runtime.getRuntime().exec(fn,null,dir);
		
	}
	//@Test
	public void testvp()
	{
		String[] ss = new String[]{"a","b","c"};
		vp("a",ss);
	}
	private void vp(String v,String ... params)
	{
		System.out.println("");
	}

}
