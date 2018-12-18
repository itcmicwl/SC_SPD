package gyqx.txp.utils.transform;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.Assert;

import com.googlecode.aviator.AviatorEvaluator;

public class FieldExpression 
{
	public static final int type_field = 0;
	public static final int type_expression = 1;
	public static final int type_value = 2;
	
	private int expression_type;
	private String aliasName;
	private String expression;
	private String beanName;
	private Object bean;
	
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public int getExpression_type() {
		return expression_type;
	}
	public void setExpression_type(int expression_type) {
		this.expression_type = expression_type;
	}
	
	public Object getValue()
	{
		Assert.isTrue(expression_type == type_field,"不是字段类型的表达式：" + expression);
		if(bean instanceof Map)
		{
			return ((Map)bean).get(expression);
		}
		
		BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(bean);	

		return bw.getPropertyValue(expression);
		
	}
	public Object getExpressionValue(HashMap context)
	{
		Assert.isTrue(expression_type == type_expression,"不是计算类型的表达式：" + expression);
		return AviatorEvaluator.execute(expression,context);
	}
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public Object getBean() {
		return bean;
	}
	public void setBean(Object bean) {
		this.bean = bean;
	}

}
