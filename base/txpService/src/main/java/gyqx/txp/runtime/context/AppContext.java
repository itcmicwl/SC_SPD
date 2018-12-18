package gyqx.txp.runtime.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import gyqx.txp.runtime.CamelRuntimeState;

@Component
public class AppContext implements ApplicationContextAware 
{
	private volatile static ApplicationContext context = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
		CamelRuntimeState.appContext = context;
	}

	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext context) {
		AppContext.context = context;
	}

}
