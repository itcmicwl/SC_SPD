package gyqx.ws.websocket;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.security.UserChecker;
import gyqx.platform.sys.service.ISysUserService;
import gyqx.platform.sys.vo.UserBaseInfoVo;
import gyqx.ws.Constance;
import gyqx.ws.TextMessageKind;
import gyqx.ws.GYTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SpringWebSocketHandler extends TextWebSocketHandler {
    public static ObjectMapper mapper = new ObjectMapper();
    private static  Map<String, WebSocketSession> users = new ConcurrentHashMap<>();     //在线用户列表
    private static Logger logger = LoggerFactory.getLogger(SpringWebSocketHandler.class);
    @Autowired
    ISysUserService userService;

    public SpringWebSocketHandler() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 连接成功时候，会触发页面上onopen方法
     */
    public  void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userKey = session.getUri().getQuery();
        users.put(userKey,session);
        sendOnlineUsers();
    }

    /**
     * 关闭连接时触发
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        String userKey = session.getUri().getQuery();
        users.remove(userKey);
        sendOnlineUsers();
    }



    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        String userKey = session.getUri().getQuery();
        if (session.isOpen()) {
            session.close();
        }
        users.remove(userKey);
        sendOnlineUsers();
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if(StringUtils.hasText(message.getPayload())){
            GYTextMessage<String> cm = mapper.readValue(message.getPayload(), new GYTextMessage<String>().getClass());//前端只能发送字符串文本消息
            if(UserChecker.isSuperAdministrator(cm.getFromUser()) && cm.getData().startsWith(Constance.TEXT_MESSAGE_CMD)) {
                cm.setMsgKind(TextMessageKind.CMD);
                cm.setData(cm.getData().substring(Constance.TEXT_MESSAGE_CMD.length()));
            }else{      //非超级管理员只能发送文本消息
                cm.setMsgKind(TextMessageKind.TEXT);
            }
            TextMessage tMsg = new TextMessage(mapper.writeValueAsString(cm));
            sendMessageToUser(cm.getToUser()+"/"+cm.getAppCode(),tMsg);
        }
        super.handleTextMessage(session, message);
    }
    public boolean supportsPartialMessages() {
        return false;
    }


    /**
     * 给指定个用户发送消息
     *
     * @param userId
     * @param message
     */
    public String sendMessageToUser(String userId, TextMessage message) {
        if (users.get(userId) == null) return "-1";
        WebSocketSession session = users.get(userId);
        System.out.println("sendMessage:" + session);
        if (!session.isOpen()) return "-1";
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            return "-1";
        }
        return userId;
    }

    /**
     * 给指定用户发送消息
     * @param userIds
     * @param message
     * @return 发送成功的用户ID
     */
    public List<String> sendMessageToUsers(List<String> userIds, TextMessage message) {
        List<String> res = new ArrayList<>();
        if (userIds == null || userIds.size() == 0) return null;
        userIds.forEach(userId ->{
            String sendRes = sendMessageToUser(userId,message);
            if(!"-1".equals(sendRes)){
                res.add(sendRes);
            }
        });
        return res;
    }

    /**
     * 给所有在线用户发送消息     *
     * @param message
     * @return 发送成功的用户ID
     */
    public List<String> sendMessageToAllUsers(TextMessage message) {
        List<String> res = new ArrayList<>();
        Set<String> clientIds = users.keySet();
        for (String clientId : clientIds) {
            String sendRes = sendMessageToUser(clientId,message);
            if(!"-1".equals(sendRes)){
                res.add(sendRes);
            }
        }
        return  res;
    }

    /**
     * 推送在线用户信息
     */
    private void sendOnlineUsers() throws Exception{
        Set<String> ids = new HashSet<>(users.keySet());
        if(ids.size()>0){
            Set<String> userIds = ids.stream().map(item ->item.substring(0,item.indexOf("/"))).collect(Collectors.toSet());
            List<UserBaseInfoVo> lisOnlineUser = userService.getUsers(userIds);
            if (lisOnlineUser.size()>0){
                GYTextMessage<List<UserBaseInfoVo>> msg = new GYTextMessage<>();
                msg.setData(lisOnlineUser);
                msg.setMsgKind(TextMessageKind.ONLINE_USER);
                msg.setMsgDate(new Date());
                TextMessage tMsg = new TextMessage(mapper.writeValueAsString(msg));
                sendMessageToAllUsers(tMsg);
            }
        }
    }
}
