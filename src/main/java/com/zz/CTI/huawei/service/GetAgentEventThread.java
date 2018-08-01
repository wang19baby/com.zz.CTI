package com.zz.CTI.huawei.service;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.TextMessage;

import com.zz.CTI.API.CTIAPI;
import com.zz.CTI.Bean.AgentCall;
import com.zz.CTI.Bean.AgentRecord;
import com.zz.CTI.Bean.AgentUser;
import com.zz.CTI.Util.DateUtil;
import com.zz.CTI.Util.EmptyUtil;
import com.zz.CTI.Util.JsonMapper;
import com.zz.CTI.Util.Logs;
import com.zz.CTI.Util.SpringContextUtil;
import com.zz.CTI.WebSocket.SpringWebSocketHandler;
import com.zz.CTI.huawei.common.GlobalConstant;
import com.zz.CTI.huawei.common.GlobalObjects;
import com.zz.CTI.huawei.request.Request;
import com.zz.CTI.huawei.util.StringUtils;
import com.zz.CTI.service.CTIService;

import net.sf.json.JSONObject;

/**
 * 获取所有在线代理事件
 * <p>Description: obtain all online agent event </p>
 */
public class GetAgentEventThread implements Runnable {
	// 调用service
	CTIService ctiService = (CTIService) SpringContextUtil.getBean("ctiService");
	
    @Bean//这个注解会从Spring容器拿出Bean
    public SpringWebSocketHandler infoHandler() {
        return new SpringWebSocketHandler();
    }
	
    private boolean isAlive = true;
    
    private AgentUser agentUser;
    private String workNo;

    
    /**
	 * @param workNo
	 */
	public GetAgentEventThread(String workNo, AgentUser agentUser) {
		super();
		this.agentUser = agentUser;
		this.workNo = workNo;
	}


	@SuppressWarnings("unchecked")
    @Override
    public void run() {        
        Map<String, Object> map = null;
        String retcode = null;
        Map<String, Object> event = null;
        String url = CTIAPI.prefix + "agentevent/" + workNo;
        while (isAlive) {        	
            try {
                map = null;
                map = Request.get(workNo,url);
            } catch (Exception e) {
            	Logs.error("get event : " + e.getMessage());
            }
            
            if (map != null) { //有事件触发
            	
                retcode = (String) map.get("retcode");
                if (GlobalConstant.BACK_TYPE_SUCCESS.equals(retcode)) {
                	
                	event = (Map<String, Object>) map.get("event");
                	if (event != null) {
                		
//                		Logs.info(workNo + " 触发事件：" + event);
                        //get logged in agent event only
                        if (GlobalObjects.loginedMap.containsKey(event.get("workNo"))) {
                        	
                    		// 向坐席客户推送事件信息
                    		infoHandler().sendMessageToUser(workNo, new TextMessage(JsonMapper.toJsonString(event)));
                            weccEventHandle(event, agentUser);
                        }
                    }
                }else if("100-006".equals(retcode)) { //坐席未登录
                	try {
                		Logs.info(workNo + ": 坐席中途退出");
                		// 向坐席客户推送事件信息
                		infoHandler().sendMessageToUser(workNo, new TextMessage(JsonMapper.toJsonString(map)));
//						GlobalObjects.loginedMap.get(workNo).putMessage(StringUtils.beanToJson(map));
						AgentService.clearResourse(workNo);
					} catch (Exception e) {
						Logs.error(e.getMessage());
					}
                }
            } else { //没事件
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                	Logs.error(e.getMessage());
                }   
            }
        } 
    }
    
    
    /**
     * 事件处理
     * @param event
     */
    public void weccEventHandle(Map<String, Object> event, AgentUser user) {
        AgentCall agentCall = new AgentCall();
        try {
//            String message = StringUtils.beanToJson(event);
//            GlobalObjects.loginedMap.get(workNo).putMessage(message);
            
            String eventType = (String) event.get("eventType");
            switch (eventType) {
            
			case "AgentOther_PhoneAlerting":
				// 座席物理话机振铃
				if (EmptyUtil.isNotEmpty(event.get("content"))) {
					JSONObject jsonObjectRing = JSONObject.fromObject(StringUtils.beanToJson(event.get("content")));
					agentCall = new AgentCall(user.getUserNo(), user.getWorkNo(), jsonObjectRing.getString("callid"), 
							user.getPhoneNumber(), jsonObjectRing.getString("caller"), DateUtil.getDate(), 2, false);
					ctiService.insertAgentCall(agentCall);
				}
				break;
				
			case "AgentEvent_Talking":
				// 坐席进入通话
				agentCall.setCallId(JSONObject.fromObject(StringUtils.beanToJson(event.get("content"))).getString("callid"));
				ctiService.updateAgentCall(agentCall);
				break;
				
			case "AgentMediaEvent_Record":
				// 录音开始
				JSONObject jsonObjectRecord = JSONObject.fromObject(StringUtils.beanToJson(event.get("content")));
				AgentRecord agentRecord = new AgentRecord(jsonObjectRecord.getString("recordID"), null, user.getUserNo(), user.getWorkNo(), 
						jsonObjectRecord.getString("recordID"), jsonObjectRecord.getString("locationId"), DateUtil.getDate(), null, 
						jsonObjectRecord.getString("fileName"));
				ctiService.insertAgentRecord(agentRecord);
				break;
				
			default:
				break;
			}
        }  catch (Exception e) {
        	Logs.error("Event Handle Failed --" + e.getMessage());
        }
    }
    
    /**
     * stop
     */
    public void end()
    {
        isAlive = false;
    }
 
}
    