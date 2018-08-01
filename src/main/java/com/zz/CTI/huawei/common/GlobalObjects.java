
package com.zz.CTI.huawei.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.zz.CTI.huawei.bean.ProcessMessageQueue;
import com.zz.CTI.huawei.service.GetAgentEventThread;


public class GlobalObjects
{
    /**
     * 登录代理
     * key : agent ID
     * value : 代理事件队列
     */
    public static Map<String, ProcessMessageQueue> loginedMap = new ConcurrentHashMap<String, ProcessMessageQueue>();
    
    /**
     * 获取代理事件线程（用于客户机服务器模式）
     * key : agent ID
     * value : thread
     */
    public static Map<String, GetAgentEventThread> eventThreadMap = new ConcurrentHashMap<String, GetAgentEventThread>();
    
    /**
     * 用于身份验证的GUID（用于客户机服务器模式）
     * key : agent ID
     * value : guid
     */
    public static Map<String,String> guidMap = new ConcurrentHashMap<String, String>();  
    
    /**
     * cookie
     * key : agent ID
     * value : sessionID
     */
    public static Map<String,String> cookieMap = new ConcurrentHashMap<String, String>();  
        
    /**
     * elb session(For HEC)
     * key : agent ID
     * value : huaweielbsession=***
     */
    public static Map<String, String> elbSessionMap = new ConcurrentHashMap<String, String>();
}
