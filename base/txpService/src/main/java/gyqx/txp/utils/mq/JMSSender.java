package gyqx.txp.utils.mq;

import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class JMSSender 
{
	private static final Logger logger = LoggerFactory.getLogger(JMSSender.class);
	public static JMSSender current = null;
	public JMSSender(JmsTemplate jmsTemplate)
	{
		this.jmsTemplate = jmsTemplate;
		current = this;
	}
	private JmsTemplate jmsTemplate;
	
	
	public void send(String destinationName,String message,Map<String,Object> propertySet)
	{
		jmsTemplate.send(destinationName, new MessageCreator(){
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage msg =  session.createTextMessage(message);
				if(propertySet != null)
					propertySet.forEach((name,value)->{
						try {
							msg.setObjectProperty(name, value);
						} catch (JMSException e) {
							e.printStackTrace();
							logger.error(e.toString());
						}
					});	
				return msg;
			}
		});
	}
	public void sendTopic(String topicName,String message,Map<String,Object> propertySet) throws JMSException
	{
		
		ConnectionFactory factory = jmsTemplate.getConnectionFactory();
		
		Connection cnn = null;
		Session session = null;
		try{
			cnn = factory.createConnection();
			cnn.start();  
	        session = cnn.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);  
	        // 创建主题  
	        Topic topic = session.createTopic(topicName);  
	        MessageProducer producer = session.createProducer(topic);  
	        // NON_PERSISTENT 非持久化 PERSISTENT 持久化,发送消息时用使用持久模式  
	        producer.setDeliveryMode(DeliveryMode.PERSISTENT);  
	        TextMessage msg = session.createTextMessage(message);  
	        if(propertySet != null)
				propertySet.forEach((name,value)->{
					try {
						msg.setObjectProperty(name, value);
					} catch (JMSException e) {
						e.printStackTrace();
						logger.error(e.toString());
					}
				});	
	        producer.send(msg);  
	        session.commit();  
	        
		}finally{
			if(session != null)
				session.close();
			if(cnn != null)
				cnn.close();
		}
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

}
