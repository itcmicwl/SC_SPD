package gyqx.txp.runtime;

import org.apache.camel.spring.Main;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import gyqx.txp.utils.info.MachineInfo;

public class Starter {
	private static final Logger logger = LoggerFactory.getLogger(Starter.class);
	static{
		BeanUtilsBean.getInstance().getConvertUtils().register(false, true,-1);
	}

	public static void main(String configFile) throws Exception 
	{
		// Main makes it easier to run a Spring application
        Main main = new Main();
        // configure the location of the Spring XML file
        main.setApplicationContextUri(configFile);
        
        main.addMainListener(new TxpMainListener());
        String clientId = MachineInfo.clientId();
        try{
        	logger.info("clientId={}",clientId);
        	main.run(); 
        }catch(Exception ex)
        {
        	logger.error(ex.toString());
        }

	}

}
