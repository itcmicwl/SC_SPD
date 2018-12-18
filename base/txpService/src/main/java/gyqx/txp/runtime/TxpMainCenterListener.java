package gyqx.txp.runtime;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.main.MainListener;
import org.apache.camel.main.MainSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxpMainCenterListener implements MainListener 
{
	private static final Logger logger = LoggerFactory.getLogger(TxpMainCenterListener.class);
	private CamelContext context;
	@Override
	public void beforeStart(MainSupport main) {
		logger.info("beforeStart");
	}

	@Override
	public void configure(CamelContext context) {
		logger.info("configure");
		this.context = context;
		
	}

	@Override
	public void afterStart(MainSupport main) {
		logger.info("afterStart");
		
		ProducerTemplate template = context.createProducerTemplate();
/*		template.sendBody("direct:startSync","");*/
	}

	@Override
	public void beforeStop(MainSupport main) {
		logger.info("beforeStop");
		
	}

	@Override
	public void afterStop(MainSupport main) {
		logger.info("afterStop");
		
	}

}
