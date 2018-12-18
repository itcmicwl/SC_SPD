package gyqx.txp.runtime;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.main.MainListener;
import org.apache.camel.main.MainSupport;
import org.apache.camel.spring.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;

import gyqx.txp.data.KV;
import gyqx.txp.message.CustomMessage;
import gyqx.txp.message.MessageBodyKind;
import gyqx.txp.message.TxpMessage;
import gyqx.txp.utils.json.Json;
import gyqx.txp.utils.mq.JMSSender;

public class TxpMainListener implements MainListener 
{
	private static final Logger logger = LoggerFactory.getLogger("my.txp.test");
	private CamelContext context;
	private ApplicationContext appContext;
	@Override
	public void beforeStart(MainSupport main) {
		logger.info("beforeStart");
	}

	@Override
	public void configure(CamelContext context) {
		logger.info("configure");
		this.context = context;
		
	}
	
	private void test()
	{
		String message = "message...";
		Map<String,Object> propertySet = new HashMap<>();
		
		TxpMessage tm = new TxpMessage();
		tm.setKind(MessageBodyKind.CallService);
		tm.setTaskName("taskName");
		
		CustomMessage cm = new CustomMessage();
		cm.setTaskName("a task");
		
		tm.setBody(cm);		
		
		try {
			message = Json.writeObject(tm);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		//propertySet.put("date", new Date());
		
		JMSSender.current.send("testMessageReceived", message, propertySet);
	}
	
	private void testLogger()
	{
		for(int i = 0; i< 100; i++)
		logger.info("a:{};uuid:{}", i,UUID.randomUUID() );
		
		System.exit(0);
	}

	@Override
	public void afterStart(MainSupport main) {
		logger.info("afterStart");
		
		appContext = ((Main) main).getApplicationContext();

		ProducerTemplate template = context.createProducerTemplate();
		
		CamelRuntimeState.appContext = appContext;
		CamelRuntimeState.context = context;
		CamelRuntimeState.producerTemplate = template;
		
		
		//test();
		//testLogger();
		//JMSSender.current.send("startDataSync", "生产厂商信息同步", KV.from("sync_task_name","生产厂商信息同步"));
		//template.sendBody("direct:startSync","");
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
