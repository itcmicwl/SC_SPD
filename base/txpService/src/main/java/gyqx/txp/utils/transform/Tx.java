package gyqx.txp.utils.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.util.Assert;

public class Tx 
{
	static{
		BeanUtilsBean.getInstance().getConvertUtils().register(false, true,-1);
	}
	public static TransformOption transform(Object... beans)
	{
		return new TransformOption(beans);
	}	
	public static ListToMapOption copylist(Object list)
	{
		return new ListToMapOption(list,false);
	}
	
	public static ListToMapOption converList(Object list)
	{
		return new ListToMapOption(list,true);
	}
	
	public static <T> List<T> fromMapListToObjectList(List<Map<String,Object>> from,Class<T> cls ) throws Exception
	{
		List<T> to = new ArrayList<>();
		Assert.notNull(from," from == null!");
		Assert.notNull(cls," cls == null!");
		for(Map<String,Object> m: from)
		{
			T t = cls.newInstance();
			t = Tx.transform(m).to(t);
			to.add(t);
		}
		
		return to;
	}
	
}
