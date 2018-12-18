package gyqx.spdhdi.baseData.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;

/**
 * @Author liangwu
 * @Date 12/14/18 9:00 AM
 */
@Configuration
@EnableJms
public class JmsConfig {
    @Resource
    private ConnectionFactory localPooledConnectionFactory;

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(localPooledConnectionFactory);
        factory.setConcurrency("1-1");
        return factory;
    }
}
