package gyqx.txp.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;


public class Hash 
{
	public static String hash(String val,String salt)
	{
		return new Sha256Hash(val + salt).toString();
	}
	
	public static String hash(String val)
	{
		return new Sha256Hash(val).toString();
	}
	public static String hashMD5(String val)
	{
		return new Md5Hash(val).toString();
	}
	
	public static String getStreamHash(InputStream fis,String alg) throws Exception
	{
		MessageDigest md5 = MessageDigest.getInstance(alg);//"SHA-256"

		byte[] buffer = new byte[4*1024];
		while(true){
			int r = fis.read(buffer);
			if(r <=0)
				break;
			md5.update(buffer, 0, r);
		}
		fis.close();
		String hash = Base64.encode(md5.digest()) ;
		return hash;
	}
	public static String getFileHash(File file,String alg) throws Exception
	{
		FileInputStream fis = new FileInputStream (file);
		String hash = getStreamHash(fis,alg) ;
		return hash;
	}

}

