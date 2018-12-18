package gyqx.txp.utils.info;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Scanner;

public class MachineInfo
{


	//获取CPUID方法


    public static String GetCpuID() {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
            process.getOutputStream().close();
            Scanner sc = new Scanner(process.getInputStream());
            String property = sc.next();
            String serial = sc.next();
            return serial;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

	//获取MAC方法
    public static String GetMac() {
        try {
            InetAddress ia = InetAddress.getLocalHost();//获取本地IP对象  
            //获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。  
            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
            //下面代码是把mac地址拼装成String  
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    sb.append("-");
                }
                //mac[i] & 0xFF 是为了把byte转化为正整数  
                String s = Integer.toHexString(mac[i] & 0xFF);
                sb.append(s.length() == 1 ? 0 + s : s);
            }
            //把字符串所有小写字母改为大写成为正规的mac地址并返回  
            return sb.toString().toUpperCase();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


	//获取硬盘序列号

	public static class DiskUtil {
		
	    public static String getSerialNumber(String drive) {
	        String result = "";
	        try {
	            File file = File.createTempFile("realhowto", ".vbs");
	            file.deleteOnExit();
	            FileWriter fw = new java.io.FileWriter(file);
	            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
	                    + "Set colDrives = objFSO.Drives\n"
	                    + "Set objDrive = colDrives.item(\"" + drive + "\")\n"
	                    + "Wscript.Echo objDrive.SerialNumber";  // see note
	            fw.write(vbs);
	            fw.close();
	            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
	            BufferedReader input =
	                    new BufferedReader(new InputStreamReader(p.getInputStream()));
	            String line;
	            while ((line = input.readLine()) != null) {
	                result += line;
	            }
	            input.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return result.trim();
	    }    

	}

	//获取主板序列号
	public static class MiscUtil {
	    public static String getMotherboardSN() {
	        String result = "";
	        try {
	            File file = File.createTempFile("realhowto", ".vbs");
	            file.deleteOnExit();
	            FileWriter fw = new java.io.FileWriter(file);
	            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
	                    + "Set colItems = objWMIService.ExecQuery _ \n"
	                    + "   (\"Select * from Win32_BaseBoard\") \n"
	                    + "For Each objItem in colItems \n"
	                    + "    Wscript.Echo objItem.SerialNumber \n"
	                    + "    exit for  ' do the first cpu only! \n" + "Next \n";
	            fw.write(vbs);
	            fw.close();
	            Process p = Runtime.getRuntime().exec(
	                    "cscript //NoLogo " + file.getPath());
	            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
	            String line;
	            while ((line = input.readLine()) != null) {
	                result += line;
	            }
	            input.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return result.trim();
	    }
    
	}
	public static String clientId() throws Exception
	{
		String msg = String.format("%s:%s", GetCpuID(),MiscUtil.getMotherboardSN());
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] data = md5.digest(msg.getBytes("utf-8"));
		
		String clientId = Base64.getEncoder().encodeToString(data);
		return clientId;
	}
	public static void main(String[] args) throws Exception 
	{
		//for(int i = 0; i< 100;i++){
			String clientId = clientId();
			System.out.println("clientId:" + clientId);
		//}
		HashMap<String,String> m;
//		String cpuid = GetCpuID();
//		System.out.println(cpuid);
//		
//		String mac = GetMac();
//		System.out.println(mac);
//		
//		String sn = DiskUtil.getSerialNumber("C");
//        System.out.println(sn);
//        
//        String cpuId = MiscUtil.getMotherboardSN();
//        System.out.println(cpuId);
    }

}
