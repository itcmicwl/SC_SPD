package gyqx.txp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import gyqx.txp.configuration.FileCompareConfig;
import gyqx.txp.utils.comparision.file.DirCompareRequest;
import gyqx.txp.utils.comparision.file.DirCompareInfo;
import gyqx.txp.utils.comparision.file.DirCompareResult;
import gyqx.txp.utils.comparision.file.FileCompareUtil;
import gyqx.txp.utils.script.TxpScript;

public class TestFileCompare 
{
	//@Test
	public void test() throws Exception
	{
		FileCompareConfig filecfg = new FileCompareConfig();
		filecfg.setKey("k1");
		filecfg.setBasePath("F:\\test\\aliyun\\t0");
		//F:\test\aliyun01\t\新建文件夹0z
		// F:/test/aliyun01/t/test - 副本.bat
		FileCompareConfig filecc = new FileCompareConfig();
		filecc.setKey("k1");
		filecc.setBasePath("F:\\test\\aliyun01\\t");
		filecc.getExcludeFiles().add("test - 副本.bat");
		filecc.getExcludePaths().add("新建文件夹0z");
		
		File ftest = new File("F:\\test\\aliyun01\\t\\新建文件夹0z");
		filecc.needExclude(ftest);		

		DirCompareInfo dciTarget= FileCompareUtil.createDirCompareInfo(filecc);
		
		DirCompareRequest request = new DirCompareRequest();
		request.setKey("k1");
		request.setCompareInfo(dciTarget);
		
		DirCompareResult result = FileCompareUtil.compare(filecfg, request);
		
		FileCompareUtil.merge(filecc,  result);
		System.in.read();
	}
	//@Test
	public void txpScriptTest()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss-SSS");
		String s = formatter.format(new Date());
		String script = "#<dos>\r\n"
				+ "net start txpService\r\n"
				+ "net start mysql5_7";
		TxpScript.parse(script);
	}
	@Test
	public void listProc() throws IOException
	{
		// 创建系统进程
		  ProcessBuilder pb = new ProcessBuilder("jps");//"tasklist");
		  Process p = pb.start();
		  BufferedReader out = new BufferedReader(new InputStreamReader(new BufferedInputStream(p.getInputStream()), Charset.forName("GB2312")));
		  BufferedReader err = new BufferedReader(new InputStreamReader(new BufferedInputStream(p.getErrorStream())));
		  System.out.println("Window 系统进程列表");
		  String ostr;
		  
		  while ((ostr = out.readLine()) != null)
			  System.out.println(ostr);
		  String estr = err.readLine();
		  if (estr != null) {
			  System.out.println("\nError Info");
			  System.out.println(estr);
		  }
		 
	}
	//@Test
	public void execmd()
	{
		//String commandStr="wmic CPU get ProcessorID";
		String commandStr="ping baidu.com ";
		BufferedReader br = null;  
        try {  
        	
        	//Runtime.getRuntime().
            Process p = Runtime.getRuntime().exec(commandStr);  
            
            br = new BufferedReader(new InputStreamReader(p.getInputStream(),"gb2312"));  
            String line = null;  
            StringBuilder sb = new StringBuilder();  
            while ((line = br.readLine()) != null) {  
                //sb.append(line + "\n");  
            	System.out.println(line);
            }  
            System.out.println(sb.toString());  
        } catch (Exception e) {  
            e.printStackTrace();  
        }   
        finally  
        {  
            if (br != null)  
            {  
                try {  
                    br.close();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
	}

}
