package gyqx.txp.runtime.processor;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import gyqx.txp.utils.json.Json;

public class CheckpointProcessor implements Processor
{

	@Override
	public void process(Exchange exchange) throws Exception 
	{
		System.out.println("Checkpoint :");
		Object in = exchange.getIn();
		//String msg = exchange.getIn().getBody(String.class);
		Map<String,Object> map =  exchange.getProperties();
		//Object obj = Json.readObject(msg);
		//System.out.println(msg);
	}
	
	public void doSomething(String body)
	{
		System.out.println("doSomething:" + body);
	}

}
