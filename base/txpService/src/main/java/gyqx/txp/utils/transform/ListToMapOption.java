package gyqx.txp.utils.transform;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class ListToMapOption 
{
	private Object list;
	private boolean withRef ;
	public ListToMapOption(Object list,boolean withRef)
	{
		this.list = list;
		this.withRef = withRef;
	}
	private String keyName;
	public ListToMapOption withKeyName(String keyName)
	{
		this.keyName = keyName;
		return this;
	}
	
	private <V> V copyNewValue(Class<V> cls,Object srcObject) throws InstantiationException, IllegalAccessException, InvocationTargetException
	{
		V v = cls.newInstance();
		
		BeanUtils.copyProperties(v,srcObject);
		
		return v;
	}
	
	public <V> HashMap<String,V> toMap() throws InstantiationException, IllegalAccessException, InvocationTargetException
	{
		Class<V> cls ;
		if(ObjectUtils.isArray(list) && Array.getLength(list) >0)
		{
			Object item = Array.get(list, 0);
			cls = (Class<V>) item.getClass();
			return toMap(cls);
		}else if(list instanceof List && ((List) list).size() >0)
		{
			List items = (List) list;
			cls = (Class<V>) items.get(0).getClass();
			return toMap(cls);
		}
		return null;
	}

	public <V> HashMap<String,V> toMap(Class<V> cls) throws InstantiationException, IllegalAccessException, InvocationTargetException
	{
		HashMap<String,V> map = new HashMap<String,V>();
		if(!StringUtils.hasText(keyName))
		{
			keyName = "id";
		}
		if(ObjectUtils.isArray(list))
		{
			for(int i = 0; i< Array.getLength(list); i++)
			{
				Object item = Array.get(list, i);
				BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(item);
				String key = bw.getPropertyValue(keyName).toString();
				
				V v = null;
				if(!withRef)
					v = copyNewValue(cls,item);
				else
					v = (V) item;
				map.put(key, v);
			}	
		}else if(list instanceof List )
		{
			List items = (List) list;
			for(Object item:items)
			{
				BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(item);
				String key = bw.getPropertyValue(keyName).toString();
				V v = null;
				if(!withRef)
					v = copyNewValue(cls,item);
				else
					v = (V) item;
				map.put(key, v);
			}			
		}
		return map;
	}

}
