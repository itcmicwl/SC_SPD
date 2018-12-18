package gyqx.txp.utils.comparision;

import java.util.Map;

import com.google.common.base.Objects;

import groovy.lang.Script;
import gyqx.txp.data.KV;
import gyqx.txp.utils.groovy.GroovyScriptHelper;

public class MapEqual
{
	private String[] compareFieldNames = null;
	private String expression = null;
	private Script script = null;

	public MapEqual(String[] compareFieldNames )
	{
		this.compareFieldNames = compareFieldNames;
	}
	
	public MapEqual(String expression)
	{
		this.expression = expression;
		this.script = GroovyScriptHelper.parse(expression);
	}
	
	public MapEqual(String expression,Map<String,Object> paramMap)
	{
		this.expression = expression;
		this.script = GroovyScriptHelper.parse(expression);
		if(paramMap != null)
		{
			paramMap.forEach((name,val)->{
				script.getBinding().setVariable(name, val);
			});
		}		
	}
	
	public boolean compare(Map left, Map right) 
	{
		if(expression == null && compareFieldNames != null){
			for(String fn:compareFieldNames)
			{
				Object lo = left.get(fn);
				Object ro = right.get(fn);
				
				if(!Objects.equal(lo, ro)){
					if(lo != null && ro != null )
					{
						Class clsl = lo.getClass();
						Class clsr = ro.getClass();
						if(!clsl.equals(clsr))
						{
							return lo.toString().equals(ro.toString());
						}
						
					}
					return false;
				}
			}			
			return true;
		}
		if(expression == null)
			throw new RuntimeException("未正确设置参数！");
		boolean b = (boolean)GroovyScriptHelper.eval(script, KV.from("left",left,"right",right));
		return b;		
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
	

}
