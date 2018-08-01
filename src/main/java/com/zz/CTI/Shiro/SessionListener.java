package com.zz.CTI.Shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.context.annotation.Bean;

import com.zz.CTI.Util.Logs;
import com.zz.CTI.WebSocket.SpringWebSocketHandler;
import com.zz.CTI.huawei.service.AgentService;  
  
/**
 * Shiro session监听
 * @author zhou.zhang
 *
 */
public class SessionListener implements org.apache.shiro.session.SessionListener {
    @Bean//这个注解会从Spring容器拿出Bean
    public SpringWebSocketHandler infoHandler() {
        return new SpringWebSocketHandler();
    }
  
    /**
     * 会话创建触发 已进入shiro的过滤连就触发这个方法  
     */
    @Override  
    public void onStart(Session session) {
    	Logs.info(session.getId() + ": 会话创建");
    }  
  
    /**
     * 退出会话
     */
    @Override  
    public void onStop(Session session) {
    	Logs.info(session.getId() + ": 退出会话");
    }  
  
    /**
     * 会话过期时触发 
     */
    @Override  
    public void onExpiration(Session session) {
    	Logs.info(session.getId() + ": 会话过期");
    	String workNo = (String) session.getAttribute("workNo");
    	if (StringUtils.isNotBlank(workNo)) {
    		AgentService.forceLogout(workNo);
    		infoHandler().sendEventToUser(workNo, "AgentSession_Destroyed");
		}
    }  
}  