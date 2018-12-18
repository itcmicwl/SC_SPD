package gyqx.txp.txpService;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tanukisoftware.wrapper.WrapperSimpleApp;

public class WrapperAppListener extends WrapperSimpleApp
{
	private static final Logger logger = LoggerFactory.getLogger(WrapperAppListener.class);

	protected WrapperAppListener(String[] args) {
		super(args);
		
	}
	@Override
	public void controlEvent(int event) {
		super.controlEvent(event);		
	}
	@Override
	public Integer start(String[] args) {
		logger.info("...start...");
    	return super.start(args);
	}
	@Override
	public int stop(int arg0) {
		logger.info("...stop...code:{}",arg0);
		return super.stop(arg0);
	}

}
