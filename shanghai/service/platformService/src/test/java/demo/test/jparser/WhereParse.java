package demo.test.jparser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;

public class WhereParse {
	@Test
	public void test() throws Exception
	{
		//String where = "f1=? and (f2=34 or f3 like ?)  or not (bw between ? and ? and key <= 0) ";
		
		String where = "exists (select * from t) or f1 = @param and a like @param and a1 between @param1 and @param or fin in(a,'b',123,@param )";
		parse(where);
	}
	private void parse(String where) throws Exception
	{
		String sql = "select *,t.*,t.f1,a2.f1, al1.f3+a2.f4 as m, (select * from v) as f,"
				+ " funx(f1,(select sum(f1) from tt),@param + f2) as cnt from t al1 inner join (select * from t2) a2 where " + where;
		CCJSqlParserManager parser = new CCJSqlParserManager();
		StringReader reader = new StringReader(sql);
		List<String> list = new ArrayList<String>();
		Statement stmt = parser.parse(new StringReader(sql));
		if (stmt instanceof Select) {
			
			PlainSelect pselect = (PlainSelect) ((Select)stmt).getSelectBody();
			Expression exp = pselect.getWhere();
			List<SelectItem> selectItems = pselect.getSelectItems();
			
			AllTableColumns cd;
			
			AllColumns acd;
			
			if(exp instanceof BinaryExpression)
			{
				System.out.println(exp);
				Expression left = ((BinaryExpression) exp).getLeftExpression();
				Expression right = ((BinaryExpression) exp).getRightExpression();
				if(exp instanceof OrExpression)
				{
					OrExpression orExp = (OrExpression) exp;
					
					if(right instanceof Parenthesis)
					{
						Parenthesis parenthesis = (Parenthesis) right;
					}
				}
				LikeExpression likeExp;
				JdbcParameter jdbcParam;
				Between b;

				MinorThanEquals mt;

				Column col;
				
			
			}
			System.out.println("d");

		}
	}

}
