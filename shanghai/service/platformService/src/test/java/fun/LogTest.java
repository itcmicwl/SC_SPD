package fun;

import static org.junit.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import common.web.AjaxResult;

public class LogTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Log logger = LogFactory.getLog(LogTest.class);
		logger.info("OK");
	}

}
