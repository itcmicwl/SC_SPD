package gyqx.txp.txpService;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;

import gyqx.txp.runtime.Starter;
import gyqx.txp.utils.exception.ErrorMessager;
import gyqx.txp.utils.json.Json;

/**
 * 系统入口
 *
 */
public class App 
{
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	private static final String defaultConfigFile = "/txpConfig/txp.xml";
    public static void main( String[] args ) throws Exception
    {
    	logger.info("...start...");
		if(args == null || args.length == 0)
    	{
    		logger.info("args == null || args.length == 0");
    		try {
				Starter.main(defaultConfigFile);
			} catch (Exception e) {
				e.printStackTrace();
				logger.info(ErrorMessager.fromException(e));
			}
    	}
    	
    	String selConfigFile = defaultConfigFile;
    	for(int i = 0; i< args.length;i++)
    	{
    		String ps = args[i];
    		logger.info(ps);
    		if(ps.equals("-config"))
    		{
    			if(++i < args.length){
    				selConfigFile = args[i];
    				logger.info(selConfigFile);
    			}
    			else
    				throw new RuntimeException("参数-config 缺少配置文件信息!");
    		}
    	}
    	
    	try {
    		Starter.main(selConfigFile);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(ErrorMessager.fromException(e));
		}
    	
    }	
}
