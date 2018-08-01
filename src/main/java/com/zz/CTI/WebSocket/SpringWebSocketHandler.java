package com.zz.CTI.WebSocket;

import java.io.IOException;  
import java.util.HashMap;  
import java.util.Map;  
  
import org.springframework.web.socket.CloseStatus;  
import org.springframework.web.socket.TextMessage;  
import org.springframework.web.socket.WebSocketSession;  
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.zz.CTI.API.CTIAPI;
import com.zz.CTI.Util.Logs;

import net.sf.json.JSONObject;  
  
/**
 * 提供了客户端连接,关闭,错误,发送等方法,重写这几个方法即可实现自定义业务逻辑 
 * @author zhou.zhang
 *
 */
public class SpringWebSocketHandler extends TextWebSocketHandler {
    private static final Map<String, WebSocketSession> users;  //Map来存储WebSocketSession，key用USER_ID 即在线用户列表 
  
    static {
        users =  new HashMap<String, WebSocketSession>();  
    }  
  
    public SpringWebSocketHandler() {}
  
    /** 
     * 连接成功时候，会触发页面上onopen方法 
     */  
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {  
    	
        String workNo = (String) session.getAttributes().get(CTIAPI.webSocketWorkNo);
        Logs.info(workNo + ": 成功建立websocket连接!");
        users.put(workNo, session);
        Logs.info("当前线上用户数量:" + users.size());
        //这块会实现自己业务，比如，当用户登录后，会把离线消息推送给用户  
        //TextMessage returnMessage = new TextMessage("成功建立socket连接，你将收到的离线");  
        //session.sendMessage(returnMessage);  
    }  
  
    /** 
     * 关闭连接时触发 
     */  
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
    	String workNo = (String) session.getAttributes().get(CTIAPI.webSocketWorkNo);
    	Logs.info(workNo + ": 关闭websocket连接");
        users.remove(workNo);
        Logs.info("剩余在线用户" + users.size());
    }  
  
    /** 
     * js调用websocket.send时候，会调用该方法 
     */  
    @Override  
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {  
  
        super.handleTextMessage(session, message);
  
        /** 
         * 收到消息，自定义处理机制，实现业务 
         */
        String workNo = (String) session.getAttributes().get(CTIAPI.webSocketWorkNo);
        Logs.info(workNo + ": 服务器收到消息：" + message);
        if(message.getPayload().startsWith("#anyone#")){ //单发某人  
             sendMessageToUser(workNo, new TextMessage("服务器单发：" +message.getPayload())) ;  
        }else if(message.getPayload().startsWith("#everyone#")){  
             sendMessageToUsers(new TextMessage("服务器群发：" + message.getPayload()));  
        }
    }  
  
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {  
        if(session.isOpen()) {
            session.close();  
        }
        String workNo = (String) session.getAttributes().get(CTIAPI.webSocketWorkNo);  
        Logs.error(workNo + ": 传输出现异常，关闭websocket连接... ");
        users.remove(workNo);  
    }
  
    public boolean supportsPartialMessages() {  
        return false;
    }  
    
    public void sendEventToUser(String workNo, String event) {
    	if (isOnLine(workNo)) {
    		JSONObject jsonObject = new JSONObject();
    		jsonObject.put("eventType", event);
    		sendMessageToUser(workNo, new TextMessage(jsonObject.toString()));	// 向坐席客户推送事件信息
    		closeUser(workNo); 													// webSocket签出
		}
    }
  
    /** 
     * 给某个用户发送消息 
     * 
     * @param userId 
     * @param message 
     */  
    public void sendMessageToUser(String workNo, TextMessage message) {  
        for (String id : users.keySet()) {
            if (id.equals(workNo)) {
                try {  
                    if (users.get(id).isOpen()) {  
                        users.get(id).sendMessage(message);  
                    }  
                } catch (IOException e) {
                	Logs.error(workNo + ": 单发信息失败--" + e.getMessage());
                }  
                break;  
            }
        }
    }
  
    /** 
     * 给所有在线用户发送消息 
     * 
     * @param message 
     */  
    public void sendMessageToUsers(TextMessage message) {  
        for (String workNo : users.keySet()) {  
            try {  
                if (users.get(workNo).isOpen()) {  
                    users.get(workNo).sendMessage(message);  
                }  
            } catch (IOException e) {
            	Logs.error(workNo + ": 群发信息失败--" + e.getMessage());
            }  
        }  
    }
    
    /**
     * 是否在线
     */
    public boolean isOnLine(String workNo) {
    	 for (String id : users.keySet()) {
             if (id.equals(workNo)) {
            	 return users.get(id).isOpen();
             }
         }
		return false;
    }
    
    
    /**
     * 退出
     */
    public void closeUser(String workNo) {
    	 for (String id : users.keySet()) {
             if (id.equals(workNo)) {
            	 try {
					users.get(id).close();
				} catch (IOException e) {
					Logs.error(workNo + ": 退出异常--" + e.getMessage());
				}
             }
         }
    }
  
}  