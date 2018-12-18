package gyqx.txp.runtime.processor;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class GetBeanProcessor implements Processor
{
	private Map<String,Object> beansMap = null;

	public Map<String, Object> getBeansMap() {
		return beansMap;
	}

	public void setBeansMap(Map<String, Object> beansMap) {
		this.beansMap = beansMap;
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		
		if(null == exchange.getIn().getHeader("txp-beansMap"))
		{
			exchange.getIn().setHeader("txp-beansMap",beansMap);
		}
		
	}

}
