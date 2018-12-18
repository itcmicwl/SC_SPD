package gyqx.txp.utils.transform;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.util.Assert;


public class $TxExecutor 
{
	private TransformOption opt;
	public $TxExecutor(TransformOption opt)
	{
		this.opt = opt;
	}
	private void doBeanCopy(Object srcObject) throws IllegalAccessException, InvocationTargetException
	{
		if(opt.getTargetBean() instanceof Map )
		{
			Map dstMap = (Map) opt.getTargetBean();
			if(srcObject instanceof Map)
			{
				Map srcMap = (Map)srcObject;
				
				Iterator iter = srcMap.entrySet().iterator();
				while(iter.hasNext()){
					Entry entry = (Entry) iter.next();
					dstMap.put( entry.getKey(),entry.getValue());
				}
				
			}else{
				PropertyUtilsBean pub = BeanUtilsBean.getInstance().getPropertyUtils();
				PropertyDescriptor[] origDescriptors =
		                pub.getPropertyDescriptors(srcObject);
		            for (int i = 0; i < origDescriptors.length; i++) {
		                String name = origDescriptors[i].getName();
		                if ("class".equals(name)) {
		                    continue; // No point in trying to set an object's class
		                }
		                if (pub.isReadable(srcObject, name) ) {
		                    try {
		                        Object value =
		                            pub.getSimpleProperty(srcObject, name);
		                        dstMap.put(name,value);
		                    } catch (NoSuchMethodException e) {
		                        // Should not happen
		                    }
		                }
		            }
			}
		}else
			BeanUtils.copyProperties(opt.getTargetBean(),srcObject);		
	}
	private void doBeanCopyByExpression() throws Exception
	{
		Object dstBean = opt.getTargetBean();
		PropertyUtilsBean pub = BeanUtilsBean.getInstance().getPropertyUtils();
		HashMap<String,Object> context = opt.getMapBeans();
		Map dstMap = null;
		if(dstBean instanceof Map)
		{
			dstMap = (Map) dstBean;
		}
		for(FieldExpression fexp: opt.getFieldExpressions())
		{					
			Object val;
			if( fexp.getExpression_type() == FieldExpression.type_field)
			{
				val = fexp.getValue();
			}else{
				val = fexp.getExpressionValue(context);
			}
			if(dstMap != null)
			{
				dstMap.put( fexp.getAliasName(),val);
			}else
			{
				Assert.notNull(pub.getPropertyDescriptor(dstBean, fexp.getAliasName()),"目标bean中不存在属性:" +fexp.getAliasName());

				pub.setSimpleProperty(dstBean, fexp.getAliasName(), val);
			}					
		}			
	}
	public void done() throws Exception
	{
		if(opt.getBeans().length == 1 
				&& (opt.getFieldExpressions() == null || opt.getFieldExpressions().size() ==0))
		{
			Object srcObject = opt.getBeans()[0];
			
			doBeanCopy(srcObject);
		}else{
			doBeanCopyByExpression();				
		}
	}

}
