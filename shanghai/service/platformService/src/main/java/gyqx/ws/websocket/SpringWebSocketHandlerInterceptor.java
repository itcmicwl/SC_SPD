package gyqx.ws.websocket;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * WebSocket拦截器
 * @author WANG
 *
 */
@Component
public class SpringWebSocketHandlerInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
//        System.out.println("Before Handshake");
//        if (request instanceof ServletServerHttpRequest) {
//            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
//            HttpSession session = servletRequest.getServletRequest().getSession(false);
//            if (session != null) {
//                //使用userName区分WebSocketHandler，以便定向发送消息
//                String userName = (String) session.getAttribute("SESSION_USERNAME");
//                if (userName==null) {
//                    userName="default-system";
//                }
//                attributes.put("WEBSOCKET_USERNAME",userName);
//            }
//        }
        return super.beforeHandshake(request, response, wsHandler, attributes);

    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        // TODO Auto-generated method stub
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
