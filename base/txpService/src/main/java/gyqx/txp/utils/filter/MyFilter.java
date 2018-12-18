package gyqx.txp.utils.filter;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

public class MyFilter implements Predicate
{
	@Override
	public boolean matches(Exchange exchange) {
		
		Object in = exchange.getIn();
		
		return true;
	}

}
