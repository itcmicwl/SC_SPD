package gyqx.ws.websocket;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebMvc
@EnableWebSocket
public class SpringWebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
    @Autowired
    SpringWebSocketHandler handler;
    @Autowired
    SpringWebSocketHandlerInterceptor handlerShakerInceptor;

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(handler, "/websocket").setAllowedOrigins("*").addInterceptors(handlerShakerInceptor);
        registry.addHandler(handler, "/sockjs").setAllowedOrigins("*").addInterceptors(handlerShakerInceptor).withSockJS();
    }

//    @Bean
//    public TextWebSocketHandler webSocketHandler() {
//        return new SpringWebSocketHandler();
//    }
}
