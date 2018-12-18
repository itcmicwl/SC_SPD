package gyqx.txp.runtime.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class Object2JsonZipfileProcessor implements Processor
{

	@Override
	public void process(Exchange exchange) throws Exception 
	{
		System.out.println("Checkpoint :");
		Object in = exchange.getIn();
		
	}

}