package demo.test.jparser;

import static org.junit.Assert.*;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import com.github.pagehelper.PageHelper;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.*;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;



public class JSqlParserCase {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws JSQLParserException {
		String sql = "select a.* ,b.f1,case when f1=3 then 12 end as v,count(*) as cnt,(select fun(f1) "
				+ "from tab1) as cnt2 from tab2 t1 inner join tab3 t2 on t1.id = t2.id "
				+ " where t1.f3 between 1 and 3 and #{v23.b} = ?  or #{x23.b45} = 123 and not #{x.b} =2 ";
		
		Pattern pat = Pattern.compile("#\\{([a-z](\\w)*(\\.[a-z](\\w)*)?)\\}");
		
		Matcher mat = pat.matcher(sql);
		
		HashMap<String,Integer> mapParams = new HashMap<>();
		int paramCount = 0;
		StringBuffer sb = new StringBuffer();
		boolean bFind = false;
		while(mat.find())
		{
			bFind = true;
			int cnt =mat.groupCount();
			for(int i = 0; i< cnt;i++)
			{
				System.out.println("" + i + ":" + mat.group(i));
				
			}
			String paramName = mat.group(1);
			Integer pi = mapParams.get(paramName);
			if(pi == null)
			{
				mat.appendReplacement(sb, "@param" + (++paramCount));
				mapParams.put(paramName, paramCount);
				
			}else{
				mat.appendReplacement(sb, "@param" + pi);
			}
		}
		mat.appendTail(sb);
		
		sql = sb.toString();
		
		getTableNameBySql(sql);
	}

	private List<String> getTableNameBySql(String sql) throws JSQLParserException {
		CCJSqlParserManager parser = new CCJSqlParserManager();
		StringReader reader = new StringReader(sql);
		List<String> list = new ArrayList<String>();
		Statement stmt = parser.parse(new StringReader(sql));
		if (stmt instanceof Select) {
			Select selectStatement = (Select) stmt;
			TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
			List tableList = tablesNamesFinder.getTableList(selectStatement);
			for (Iterator iter = tableList.iterator(); iter.hasNext();) {
				String tableName = iter.next().toString();
				list.add(tableName);
			}
		}
		
		
		return list;
	}

}
