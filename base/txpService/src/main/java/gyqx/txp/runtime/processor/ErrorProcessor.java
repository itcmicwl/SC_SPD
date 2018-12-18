package gyqx.txp.runtime.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorProcessor implements Processor
{
	private static final Logger logger = LoggerFactory.getLogger(ErrorProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception 
	{
		logger.error("-------------------------------------------");  
        //这样获取异常信息  
		exchange.getProperty("CamelFailureRouteId",String.class);
		logger.error("FromEndpoint:" + exchange.getFromEndpoint());
		logger.error("FromRouteId:" +  exchange.getFromRouteId());
        Exception exce = exchange.getProperty("CamelExceptionCaught", Exception.class);  
        logger.error(exce.getMessage());  
        exce.printStackTrace();  
        
	}

}
