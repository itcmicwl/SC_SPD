package gyqx.txp.utils.transform;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.Assert;


public class TransformOption 
{
	private Object[] beans;
	private List list;
	private Object targetBean;
	private String[] beanNames;
	private List<FieldExpression> fieldExpressions;	
	private HashMap<String ,Object> mapBeans ;
	
	private static Pattern pat ;
	private static Pattern patField ;
	private static Pattern patFieldWild ;
	static {
		pat = Pattern.compile("^\\s*([\\w\\+\\-\\*/\\(\\)\\.,\\[\\]\\s]+)\\s+as\\s+(([a-z]\\w*))\\s*$");
		patFieldWild = Pattern.compile("^\\s*(([a-z]\\w*))\\.\\*\\s*$");
		patField = Pattern.compile("^\\s*([a-z]\\w*)\\.(([a-z]\\w*))\\s*$");
	}
	public List getSourceList()
	{
		return list;
	}
	public Object[] getBeans() {
		return beans;
	}
	public void setBeans(Object[] beans) {
		this.beans = beans;
	}
	public Object getTargetBean() {
		return targetBean;
	}
	public void setTargetBean(Object targetBean) {
		this.targetBean = targetBean;
	}
	public String[] getBeanNames() {
		return beanNames;
	}
	public void setBeanNames(String[] beanNames) {
		this.beanNames = beanNames;
	}
	
	public TransformOption(Object[] beans)
	{
		this.beans = beans;
	}
	public TransformOption(List list)
	{
		this.list = list;
	}
	public TransformOption named(String names)
	{
		Assert.notEmpty(beans);
		Assert.hasText(names);
		Assert.isNull(list);
		beanNames = names.split(",");
		if(beans.length != beanNames.length)
			throw new IllegalArgumentException("beans.length != beanNames.length");
		mapBeans = new HashMap<>();
		for(int i = 0; i< beans.length ;i++)
		{
			mapBeans.put(beanNames[i], beans[i]);
		}
		return this;
	}
	public TransformOption from(String fields)
	{
		Assert.notEmpty(beanNames,"需要用named方法为bean 设定名称！");		
		
		List<String> sfieldExpressions  = parseSegments(fields);
		fieldExpressions = new ArrayList<>();
		FieldExpression fexp = null;
		for(String s:sfieldExpressions)
		{
			String field = s;
			Matcher mat = patFieldWild.matcher(field);
			if(mat.matches()) // 如： a.* 
			{
				String beanName = mat.group(1);
				Object bean = mapBeans.get(beanName);
				Assert.notNull(bean,"不正确的bean名称：" + beanName);
				
				BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(bean);
				
				for(PropertyDescriptor pd:bw.getPropertyDescriptors())
				{
					String aliasName = pd.getName();
					fexp = new FieldExpression();
					fexp.setBeanName(beanName);
					fexp.setBean(bean);
					fexp.setAliasName(aliasName);
					fexp.setExpression(pd.getName());
					fexp.setExpression_type(FieldExpression.type_field);
				}
				
			}else{
				mat = patField.matcher(field);
				if(mat.matches()) // 如：a.f1 
				{					
					fexp = parseFieldExpression(mat);					
				}else{
			
					mat = pat.matcher(field);
					if(mat.matches())
					{
						String exprs = mat.group(1);
						String aliasName = mat.group(2);
						
						mat = patField.matcher(exprs);
						if(mat.matches()) // 如：a.f1 
						{
							fexp = parseFieldExpression(mat);
							fexp.setAliasName(aliasName);
						}else{
						
							fexp = new FieldExpression();
							fexp.setExpression(exprs);
							fexp.setAliasName(aliasName);
							fexp.setExpression_type(FieldExpression.type_expression);
						}
						
					}else
						throw new IllegalArgumentException("不正常的表达式："+ field + ";可能少了别名！");
				}
			}
			
			fieldExpressions.add(fexp);
		}
		
		return this;
	}
	private FieldExpression parseFieldExpression(Matcher mat)
	{

		FieldExpression fexp = null;
		// 如：a.f1 
		String beanName = mat.group(1);
		String pname = mat.group(2);
		String aliasName = pname;
		Object bean = mapBeans.get(beanName);
		Assert.notNull(bean,"不正确的bean名称：" + beanName);
		if(bean instanceof Map)
		{
			Map map =(Map) bean;
			Assert.isTrue( map.containsKey(pname));
		}else
		{
			BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(bean);
			Assert.notNull( bw.getPropertyDescriptor(pname),"不正确的bean属性名：" + pname);
		}
		
		fexp = new FieldExpression();
		fexp.setBeanName(beanName);
		fexp.setBean(bean);
		fexp.setAliasName(aliasName);
		fexp.setExpression(pname);
		fexp.setExpression_type(FieldExpression.type_field);
		
		return fexp;
	}
	
	private List<String> parseSegments(String fields)
	{
		int status = 0;
		StringBuilder sb = new StringBuilder();
		List<String> segs = new ArrayList<>();
		
		int cur = 0,len = fields.length();
		Integer flag = 1;
		Stack<Integer> opts = new Stack<>();
		while(cur < len)
		{
			char chr = fields.charAt(cur);
			switch(status)
			{
			case 0: // not in '('
				if(chr ==',' && opts.size() == 0)
				{
					//status = 1;
					segs.add(sb.toString());
					sb.setLength(0);
					break;
				}else if(chr == '(')
				{
					opts.push(flag);
				}else if(chr == ')')
				{
					opts.pop();
				}
				sb.append(chr);
				break;
			}
			cur++;
		}
		if(sb.length()>0)
			segs.add(sb.toString());
		return segs;
		
	}
	public <T> T to(T targetBean) throws Exception
	{
		Assert.notNull(beans);
		Assert.notNull(targetBean);
		Assert.isTrue(beans.length >0);
		
		if(beanNames != null && beanNames.length >0)
			Assert.isTrue(beanNames.length == beans.length );
		
		this.targetBean = targetBean;
		new $TxExecutor(this).done();
		return targetBean;
	}
	public Map toMap() throws Exception
	{
		LinkedHashMap map = new LinkedHashMap();
		
		return to(map);
	}
	public List<FieldExpression> getFieldExpressions() {
		return fieldExpressions;
	}
	public void setFieldExpressions(List<FieldExpression> fieldExpressions) {
		this.fieldExpressions = fieldExpressions;
	}
	public HashMap<String ,Object> getMapBeans() {
		return mapBeans;
	}
	public void setMapBeans(HashMap<String ,Object> mapBeans) {
		this.mapBeans = mapBeans;
	}
	
	

}
