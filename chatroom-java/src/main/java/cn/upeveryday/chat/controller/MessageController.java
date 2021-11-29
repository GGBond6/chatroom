package cn.upeveryday.chat.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.upeveryday.chat.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ggbond
 * 每个客户端都有一个这个类的对象与之对应（多例）
 */
@ServerEndpoint(value = "/chat/{username}")
@Controller
public class MessageController {
    /**
     * 下面的方法不行
     * spring默认管理单例，而websocket为多例对象
     */
//    @Autowired
//    MessageService messageService;

    //  这里使用静态，让 service 属于类
    private static MessageService messageService;

    // 注入的时候，给类的 service 注入
    @Autowired
    public void setChatService(MessageService messageService) {
        MessageController.messageService = messageService;
    }

    //日志对象
    private static final Logger log = LoggerFactory.getLogger(MessageController.class);
    /**
     * 记录当前在线连接数
     * 使用Map存储不同客户端对应的不同的session
     * 可以通过Session对象发送消息给指定的客户端
     * 定义为static是因为所有对象共用一个map即可
     * ConcurrentHashMap支持并发的子实现类
     */
    private static Map<String, Session> onlineUsers = new ConcurrentHashMap<>();

    /**
     * 连接建立成功
     * 将当前所有在线用户，存储到result对象中的JSON数组users中
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        onlineUsers.put(username, session);
        log.info("新用户{}加入，当前在线人数为{}", username, onlineUsers.size());

        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        //users为数组类型，result={"users":[]}
        result.set("users", array);
        for (Object key : onlineUsers.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("username", key);
            array.add(jsonObject);
            //result={"users:[{"username":xxx1},{"username":xxx2]}}
        }
        //将对象序列化成json字符串进行传输，并且将消息发送给所有人
        sendAllMessage(JSONUtil.toJsonStr(result));
    }

    /**
     * 断开连接时调用的方法
     *
     * @param session
     */
    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        onlineUsers.remove(username);
        log.info("用户{}下线，当前人数为{}", username, onlineUsers.size());
    }

    /**
     * 收到客户端消息后调用的方法（消息的中转站）
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("username") String username) {
        log.info("客户端收到用户{}的消息：{}", username, message);
        //消息反序列化：将字符串解析为json对象
        JSONObject msg = JSONUtil.parseObj(message);
        //to表示发送给哪个用户（保存在客户端发送来的message中）
        String toUsername = msg.getStr("to");
        //text表示发送的消息文本
        String text = msg.getStr("text");
        //根据用户名获取session，再通过session发送消息文本
        Session toSession = onlineUsers.get(toUsername);
        //将接收的消息转达给to用户
        if (toSession != null) {
            //服务端再把消息进行组装，组装后的消息包含发送人和发送的内容
            JSONObject jsonObject = new JSONObject();
            //from表示此消息来自于哪个用户
            jsonObject.set("from", username);
            jsonObject.set("text", text);
            //服务端给指定客户端发送消息
            sendMessage(jsonObject.toString(), toSession);
        }
    }

    @OnError
    public void onError(Throwable throwable) {
        log.error("发生错误");
        throwable.printStackTrace();
    }

    /**
     * 全局消息
     *
     * @param message
     */
    private void sendAllMessage(String message) {
        try {
            for (Session session : onlineUsers.values()) {
                log.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
                //同步
                //session.getBasicRemote().sendText(message);
                //异步（非阻塞）
                session.getAsyncRemote().sendText(message);
            }
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }

    /**
     * 单个消息
     *
     * @param message
     */
    private void sendMessage(String message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            //toSession.getBasicRemote().sendText(message);
            toSession.getAsyncRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }
}
