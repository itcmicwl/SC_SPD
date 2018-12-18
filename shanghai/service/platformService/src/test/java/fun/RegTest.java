package fun;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.JoinColumn;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StringUtils;

import common.base.formatter.MultiDateFormat;

public class RegTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws ParseException {
		
		String sa =  String.format("%06x", 123456);
		System.out.println(sa);
		
		
		String s = "INSERT INTO sys_string VALUES (?, ?, ?, ?, ?, ?, ?, sysdate(), 1);";
		Pattern pat = Pattern.compile("^\\s*insert\\s+into\\s+((\\w+))\\s+",Pattern.CASE_INSENSITIVE);
		Matcher mat = pat.matcher(s);
		if(mat.find())
		{
			for(int i = 0; i< mat.groupCount();i++)
			{
				System.out.println(mat.group(i));
			}
		}
		
		/*String s = "jsession=lsdfklkkd1232;";
		Pattern pat = Pattern.compile("jsession=((\\w+))");
		Matcher mat = pat.matcher(s);
		if(mat.find())
		{
			for(int i = 0; i< mat.groupCount();i++)
			{
				System.out.println(mat.group(i));
			}
		}*/
		
		/*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		Date d = format.parse("2017-06-27T16:00:00.000Z");*/
	/*	
		MultiDateFormat format1 = new MultiDateFormat();
		Date d0 = format1.parse("2017-06-27T16:00:00.000Z");
		String path = "d:/adsf;/usr/ddd";
		if(path.indexOf(";") >0)
		{
			String[] ss = path.split(";");
			if(System.getProperty("os.name").toLowerCase().indexOf("window") >=0 )
			{
				System.out.println(ss[0]);
			}else{
				System.out.println(ss[1]);
			}
		}
		*/
		/*Pattern pat = Pattern.compile("/usid-(([\\w\\-]+))/");
		Matcher mat = pat.matcher("localhost/platform/usid-2BD2CED69AF40D98CA314B2BF85F82EA/sys/org/list");
		
		StringBuffer sb = new StringBuffer();
		if(mat.find())
		{
			for(int i = 0; i< mat.groupCount();i++)
			{
				System.out.println(mat.group(i));
			}
			
			mat.appendReplacement(sb, "/");
			
		}
		mat.appendTail(sb);*/
		
//		String s = "%{yyyy-MMdd hh:mm:ss}%03d";
//		Pattern pat = Pattern.compile("%\\{([yY]{2,4}\\-?[mM]{2}.+)\\}");
//		
//		Matcher mat = pat.matcher(s);
//		
//		String format="";
//		String dateFormat=null;
//		StringBuffer sb = new StringBuffer();
//
//		if(mat.find())
//		{
//			for(int i = 0; i< mat.groupCount();i++)
//			{
//				System.out.println(mat.group(i));
//			}			
//			dateFormat = mat.group(1);
//			
//			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
//			
//			mat.appendReplacement(sb, sdf.format(new Date()));
//			mat.appendTail(sb);
//			format = sb.toString();
//		}
//		
//		System.out.println(String.format(format, 123));
		
		
		
		/*String s = "货位编号方式(HLBHFS)";
		Pattern pat = Pattern.compile("(.+)\\(((\\w+))\\)");
		
		Matcher mat = pat.matcher(s);

		if(mat.matches())
		{
			for(int i = 0;i < mat.groupCount();i++)
			{
				System.out.println("i=" + i +":" + mat.group(i));
			}
		}*/
		
		/*Pattern patAnn = Pattern.compile("([A-Z]\\w+)(\\(((([a-z]\\w*=(\"[^\",]+\"|\\d+)),)*([a-z]\\w*=(\"[^\",]+\"|\\d+)))\\))?"); //Pattern.compile("([A-Z]\\w+)(\\(((.+))\\))?");
		Pattern patAnnval = Pattern.compile("([a-z]\\w*)=(\"([^\",]+)\"|(\\d+))");
		String s = "JoinColumn";//"JoinColumn(name=\"s中困s\",table=234,referencedColumnName=\"dd\")";
		
		Matcher mat = patAnn.matcher(s);
		String annName = null;
		if(mat.matches())
		{
			for(int i = 0;i < mat.groupCount();i++)
			{
				System.out.println("i=" + i +":" + mat.group(i));
			}
			annName = mat.group(1);
			s = mat.group(3);
		}
		if(annName != null)
		{
			mat = patAnnval.matcher(s);
			while(mat.find())
			{
				for(int i = 0;i < mat.groupCount();i++)
				{
					System.out.println("i=" + i +":" + mat.group(i));
				}
			}
		}*/
		//System.out.println(sb);
	}

}
