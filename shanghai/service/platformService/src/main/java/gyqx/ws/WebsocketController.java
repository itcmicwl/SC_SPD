package gyqx.ws;
import javax.servlet.http.HttpServletRequest;

import gyqx.ws.websocket.SpringWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;


@Controller
public class WebsocketController {
    @Bean//这个注解会从Spring容器拿出Bean
    public SpringWebSocketHandler infoHandler() {
        return new SpringWebSocketHandler();
    }

    @RequestMapping("/websocket/send")
    @ResponseBody
    public String send(HttpServletRequest request) {
        String username = request.getParameter("username");
        infoHandler().sendMessageToUser(username, new TextMessage("你好，测试！！！！"));
        return null;
    }
}
