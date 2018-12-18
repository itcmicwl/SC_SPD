package gyqx.txp.biz;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gyqx.txp.configuration.SyncTaskConfig;
import gyqx.txp.configuration.TaskConfig;
import gyqx.txp.configuration.TxpServerConfig;
import gyqx.txp.data.queue.NotifyMessageQueue;
import gyqx.txp.message.MessageBodyKind;
import gyqx.txp.message.TaskNotifyMessage;
import gyqx.txp.message.TxpNotifyMessage;
import gyqx.txp.message.txp.TxpSqlNotify;
import gyqx.txp.utils.json.Json;

public class NotifyProcessor implements Processor
{
	final static Logger logger = LoggerFactory.getLogger(NotifyProcessor.class);
	public NotifyProcessor()
	{
		
	}
	@Override
	public void process(Exchange exchange) throws Exception 
	{
		logger.info("NotifyProcessor :begin");
		String json = exchange.getIn().getBody(String.class);
		logger.info("NotifyProcessor :{}",json);
		TxpSqlNotify tsn = Json.read(json,TxpSqlNotify.class);
		try{
			List<TaskConfig> taskConfigs= TxpServerConfig.cfg.queryTaskConfigsByTableName(tsn.getTableName()) ;
			if(CollectionUtils.isEmpty(taskConfigs))
			{
				logger.info("NotifyProcessor :tableName:{} ,find task == null",tsn.getTableName());
				exchange.getIn().setBody(null);
				return;
			}
			List<TaskNotifyMessage> tnMessages = new ArrayList<>();
			for( TaskConfig taskConfig: taskConfigs){
				TaskNotifyMessage taskNotifyMessage = new TaskNotifyMessage();
				taskNotifyMessage.setTaskName(taskConfig.getName());
				taskNotifyMessage.setCatalog(taskConfig.getCatalog());
				taskNotifyMessage.setCmd(taskConfig.getCatalog() );	
				tnMessages.add(taskNotifyMessage);
			}
			
			exchange.getIn().setBody(tnMessages);
		}catch(Exception ex){
			logger.error(ex.toString());
		}
	}	

}
