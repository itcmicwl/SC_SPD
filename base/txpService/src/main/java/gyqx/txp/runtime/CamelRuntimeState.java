package gyqx.txp.runtime;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


public class CamelRuntimeState 
{
	public volatile static CamelContext context;
	public volatile static ApplicationContext appContext;
	public volatile static ProducerTemplate producerTemplate;
	
	public static <T> T getBean(String name) 
    {
    	if(appContext == null)
    		throw new RuntimeException("ApplicationContext not init!");
        return (T)appContext.getBean(name);
    }
    public static <T> T getBean(Class<T> requiredType, Object... args) throws BeansException
    {
    	if(appContext == null)
    		throw new RuntimeException("ApplicationContext not init!");
    	return appContext.getBean(requiredType,args);
    }
    public static <T> T getBean(Class<T> requiredType) throws BeansException
    {
    	if(appContext == null)
    		throw new RuntimeException("ApplicationContext not init!");
    	return appContext.getBean(requiredType);
    }
	

}
