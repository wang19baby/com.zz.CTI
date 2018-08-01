
package com.zz.CTI.huawei.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.util.ObjectUtils;

import com.zz.CTI.API.CTIAPI;
import com.zz.CTI.Bean.AgentUser;
import com.zz.CTI.Util.Logs;
import com.zz.CTI.huawei.bean.ProcessMessageQueue;
import com.zz.CTI.huawei.common.GlobalObjects;
import com.zz.CTI.huawei.request.Request;
import com.zz.CTI.huawei.util.StringUtils;

/**
 * <p>Title: Agent Service </p>
 * <p>Description: Agent Service </p>
 */
public class AgentService {
	
	public static ExecutorService threads = Executors.newCachedThreadPool();
        
    /**
     * 坐席签入
     * request method : PUT
     * request URL : http(s)://ip:port/agentgateway/resource/onlineagent/{agentid}
     * 请求消息体的一个示例:
     * {"password":"","phonenum":"40038","status":"4","releasephone":"false","agenttype":"4"}
     */
    public static String login(String workNo, String password, String phonenum, AgentUser agentUser) {
    	String url = CTIAPI.prefix + "onlineagent/" + workNo; //坐席签入接口地址
        String resp = "";
        
        Map<String, Object> loginParam = new HashMap<String, Object>();        
        loginParam.put("password","");            		// 坐席密码(默认为空)
        loginParam.put("phonenum",phonenum);   			// 坐席电话
        loginParam.put("autoanswer",false);             // 是否自动应答(true：自动应答 false：手动应答)
        loginParam.put("autoenteridle",true);         	// 是否自动进入空闲态(true：空闲态 false：整理态)
        loginParam.put("status",4);                     // 签入后的状态(4：空闲态 5：整理态)
        loginParam.put("releasephone", true);           // 座席挂机后是否进入非长通态 (true：非长通态  false：长通态)
        loginParam.put("ip",CTIAPI.localIP);            // 座席ip(默认127.0.0.1)
        Map<String, Object> result = Request.put(workNo, url, loginParam); //提交并获取请求
        
        try {
            resp = StringUtils.beanToJson(result); //将返回Map转Json
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        
        if ("0".equals(result.get("retcode"))) {
        	GlobalObjects.loginedMap.put(workNo, new ProcessMessageQueue());   			// 为每个登录代理创建一个事件队列
        	GetAgentEventThread thread = new GetAgentEventThread(workNo, agentUser);    // 为每个登录代理创建一个GET事件线程
			threads.submit(thread);
			GlobalObjects.eventThreadMap.put(workNo, thread);
			Logs.info(workNo + " login success");
        } else {
        	Logs.info(workNo + " login failed ---" + resp);
        }
        return resp;
    }
    
    /**
     * 强制签入
     * request method : PUT
     * request URL : http(s)://ip:port/agentgateway/resource/onlineagent/{agentid}/forcelogin
     */
    public static String forceLogin(String workNo, String password, String phonenum, AgentUser agentUser) {
    	if (!ObjectUtils.isEmpty(GlobalObjects.eventThreadMap.get(workNo))) {
    		clearResourse(workNo);
		}
        
    	String url = CTIAPI.prefix + "onlineagent/" + workNo + "/forcelogin";
        String resp = "";
        Map<String, Object> forceLoginParam = new HashMap<String, Object>();        
        forceLoginParam.put("password", "");            	// 坐席密码(默认为空)
        forceLoginParam.put("phonenum", phonenum);   		// 坐席电话
        forceLoginParam.put("autoanswer", false);           // 是否自动应答(true：自动应答 false：手动应答)
        forceLoginParam.put("autoenteridle", true);         // 是否自动进入空闲态(true：空闲态 false：整理态)
        forceLoginParam.put("status", 4);                   // 签入后的状态(4：空闲态 5：整理态)
        forceLoginParam.put("releasephone", true);          // 座席挂机后是否进入非长通态 (true：非长通态  false：长通态)
        forceLoginParam.put("ip", CTIAPI.localIP);          // 座席ip(默认127.0.0.1)
        
        Map<String, Object> result = Request.put(workNo, url, forceLoginParam);
        
        try {
            resp = StringUtils.beanToJson(result); //将返回Map转Json
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        
        if ("0".equals(result.get("retcode"))) {
        	GlobalObjects.loginedMap.put(workNo, new ProcessMessageQueue());   // 为每个登录代理创建一个事件队列
        	GetAgentEventThread thread = new GetAgentEventThread(workNo, agentUser);      // 为每个登录代理创建一个GET事件线程
			threads.submit(thread);
			GlobalObjects.eventThreadMap.put(workNo, thread);
			
			Logs.info(workNo + " forceLogin success");
        } else {
        	Logs.error(workNo + " forceLogin failed ---" + resp);
        }      
        return resp;
    }
    
    /**
     * 重置技能队列
     * request method : POST
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/onlineagent/{agentid}/resetskill/{autoflag}?skillid={skillid}&phonelinkage={phonelinkage}
     * 其中，ip为agentgateway服务器地址，port为agentgateway服务器的HTTP（或HTTPS）端口号，{agentid}为座席工号，
     * {autoflag}为是否自动签入技能标志（true为自动签入座席所配置技能，false为手工签入技能队列， true以外的参数都作为false处理），
     * {skillid}为技能队列ID（当签入方式为false时，此时skillid参数必选，为技能队列ID字符串组，例如1;2;3，座席签入的技能队列为此id和所配置的交集，最大长度为100个字符），
     * {phonelinkage}为是否话机联动（1是，0否）。
     */
    public static String resetskill(String workNo) {
        String url = CTIAPI.prefix + "onlineagent/" + workNo + "/resetskill/true?phonelinkage=1";
        String resp = "";
        /*
         * 响应消息体示例:
         * {"message":"","retcode":"0"}
         */
        Map<String, Object> result = Request.post(workNo,url, null);
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        return resp;
    }
    
    /**
     * 座席签出
     * request method : DELETE
     * request URL : http(s)://ip:port/agentgateway/resource/onlineagent/{agentid}/logout
     * 
     * 在这个URL中，IP表示代理网关的IP地址，端口表示代理网关的http或HTTPS端口号，
     * { agentid }表明代理人的座席工号
     */
    public static String logout(String workNo) {
        String url = CTIAPI.prefix + "onlineagent/" + workNo + "/logout";
        String resp = "";
        
        /*
         * 响应消息体示例:
         * {"message":"","retcode":"0"}
         */
        Map<String, Object> result = Request.delete(workNo,url,null);
        
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        
        if ("0".equals(result.get("retcode"))) {
        	clearResourse(workNo);
        	 Logs.info(workNo + " logout success");
        } else {
        	 Logs.info(workNo + " logout failed ---" + resp);
        }
        return resp;
    }
    
    /**
     * 强制签出
     * request method : DELETE
     * request URL : http(s)://ip:port/agentgateway/resource/onlineagent/{agentid}/forcelogout
     * 
     * 在这个URL中，IP表示代理网关的IP地址，端口表示代理网关的http或HTTPS端口号，
     * { agentid }表明代理人的座席工号
     */
    public static String forceLogout(String workNo) {
        String url = CTIAPI.prefix + "onlineagent/" + workNo + "/forcelogout";
        String resp = "";
        
        Map<String, Object> result = Request.delete(workNo,url,null);
        
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        
        if ("0".equals(result.get("retcode"))) {
        	clearResourse(workNo);
        	 Logs.info(workNo + " forceLogout success");
        } else {
        	 Logs.info(workNo + " forceLogout failed ---" + resp);
        }
        return resp;
    }
    
    
    /**
     * 座席示闲
     * request method : POST
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/onlineagent/{agentid}/sayfree
     * 在这个URL中，IP表示代理网关的IP地址，端口表示代理网关的http或HTTPS端口号，
     * { agentid }表明代理人的坐席工号。
     */
    public static String ready(String workNo) {
        String url = CTIAPI.prefix + "onlineagent/" + workNo + "/sayfree";
        String resp = "";
        Map<String, Object> result = Request.post(workNo,url, null);
        
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        return resp;
    }
    
    /**
     * 坐席示忙
     * request method : POST
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/onlineagent/{agentid}/saybusy?reason={reason}
     * 在这个URL中，IP表示代理网关的IP地址，端口表示代理网关的http或HTTPS端口号，
     * { agentid }表明代理人的工作证。{原因}表示忙原因代码（值的范围从200到250。
     * 如果值设置为0或无传输原因，则不设置“忙原因代码”）。
     */
    public static String busy(String workNo) {
        String url = CTIAPI.prefix + "onlineagent/" + workNo + "/saybusy";
        String resp = "";
        Map<String, Object> result = Request.post(workNo,url, null);
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        return resp;
    }
    
    /**
     * 坐席进入工作
     * request method : POST
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/onlineagent/{agentid}/work
     */
    public static String work(String workNo) {
        String url = CTIAPI.prefix + "onlineagent/" + workNo + "/work";
        String resp = "";
        Map<String, Object> result = Request.post(workNo,url, null);
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        return resp;
    }
    
    /**
     * 退出工作
     * request method : POST
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/onlineagent/{agentid}/cancelwork
     */
    public static String outwork(String workNo) {
        String url = CTIAPI.prefix + "onlineagent/" + workNo + "/cancelwork";
        String resp = "";
        Map<String, Object> result = Request.post(workNo,url, null);
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        return resp;
    }
    
    /**
     * 发布公告
     * request method : POST
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/onlineagent/{agentid}/notifybulletin
     */
    public static String notifybulletin(String workNo, String targetname, String bulletindata) {
    	System.out.println(workNo + " : send notice begin.");
        
        String url = CTIAPI.prefix + "onlineagent/" + workNo + "/notifybulletin";
        
        Map<String, Object> noticeParam = new HashMap<String, Object>();        
        noticeParam.put("targettype", 0);            	// 公告发布类型。0: 班组；1: 技能队列
        noticeParam.put("targetname", targetname);      // 班组名称或技能队列名称。最大长度为100个字符
        noticeParam.put("bulletindata", bulletindata);  // 公告数据。最大长度为900个字符
        System.out.println(noticeParam);
        
        String resp = "";
        
        Map<String, Object> result = Request.post(workNo, url, noticeParam);
        
        try {
        	System.out.println(workNo + " request URL : " + url);
            resp = StringUtils.beanToJson(result);
            
            System.out.println(workNo + " receive message : " + resp);
        } catch (IOException e) {
        	System.out.println("result is not variable" + e.getMessage());
        }
        
        System.out.println(workNo + " : send notice end.");
        return resp;
    }
    
    /**
     * 查询指定座席配置技能队列
     * request method : GET
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/onlineagent/{workno}/agentskillsbyworkno/{agentid}
     * 其中，ip为agentgateway服务器地址，port为agentgateway服务器的HTTP（或HTTPS）端口号，{workNo}为座席工号，{agentid}为待查询的座席工号。
     */
    public static String agentskillsbyworkno(String workNo, String agentid) {
    	System.out.println(workNo + " : get skill by workno begin.");
        
        String url = CTIAPI.prefix + "onlineagent/" + workNo + "/agentskillsbyworkno/" + agentid;
        
        String resp = "";
        
        /*
         * 响应消息体示例:
         * {"message": "","retcode": "0","result": [{
		 *	"name": "文字交谈_jinsitao",
		 *	"id": 10,
		 *	"mediatype": 1},
		 *	{"name": "语音_jinsitao",
		 *	"id": 25,
		 *	"mediatype": 5}]}
         */
        Map<String, Object> result = Request.get(workNo, url);
        
        try {
        	System.out.println(workNo + " request URL : " + url);
            resp = StringUtils.beanToJson(result);
            
            System.out.println(workNo + " receive message : " + resp);
        } catch (IOException e) {
        	System.out.println("result is not variable" + e.getMessage());
        }
        
        System.out.println(workNo + " : get skill by workno end.");
        return resp;
    }
    
    /**
     * 查询指定座席详细座席信息
     * request method : GET
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/agentgroup/{workNo}/agentbyworkno/{agentid}
     * 其中，ip为agentgateway服务器地址，port为agentgateway服务器的HTTP（或HTTPS）端口号，{workNo}为座席工号，{agentid}为待查询的座席工号。
     */
    public static String agentbyworkno(String workNo, String agentid) {
        String url = CTIAPI.prefix + "agentgroup/" + workNo + "/agentbyworkno/" + agentid;
        String resp = "";
        Map<String, Object> result = Request.get(workNo, url);
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        return resp;
    }
    
    /**
     * 批量查询座席的信息
     * request method : POST
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/agentgroup/{agentid}/agentinfonoskills
     */
    public static String agentinfonoskills(String workNo, List<String> agentList) {
        String url = CTIAPI.prefix + "agentgroup/" + workNo + "/agentinfonoskills";
        Map<String, Object> agentParam = new HashMap<String, Object>();
        agentParam.put("agentlist", agentList);
        String resp = "";
        Map<String, Object> result = Request.post(workNo,url, agentParam);
        
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        return resp;
    }
    
    
    /**
     * 查询所属VDN所有座席的WAS配置姓名
     * request method : GET
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/agentgroup/{agentid}/agentwasnameonvdn
     */
    public static String agentwasnameonvdn(String workNo) {
        String url = CTIAPI.prefix + "agentgroup/" + workNo + "/agentwasnameonvdn";
        String resp = "";
        Map<String, Object> result = Request.get(workNo, url);
        
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        return resp;
    }
    
    
    /**
     * 查询所在VDN所有座席当前状态信息
     * request method : GET
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/agentgroup/{agentid}/allagentstatus
     */
    public static String allagentstatus(String workNo) {
    	System.out.println(workNo + " : get agentstatus info begin.");
        
        String url = CTIAPI.prefix + "agentgroup/" + workNo + "/allagentstatus";
        
        String resp = "";
        
        Map<String, Object> result = Request.get(workNo, url);
        
        try {
        	System.out.println(workNo + " request URL : " + url);
            resp = StringUtils.beanToJson(result);
            
            System.out.println(workNo + " receive message : " + resp);
        } catch (IOException e) {
        	System.out.println("result is not variable" + e.getMessage());
        }
        
        System.out.println(workNo + " : get agentstatus info end.");
        return resp;
    }
    
    /**
     * 查询所属VDN在线座席信息
     * request method : GET
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/agentgroup/{agentid}/onlineagentonvdn
     */
    public static String onlineagentonvdn(String workNo) {
        String url = CTIAPI.prefix + "agentgroup/" + workNo + "/onlineagentonvdn";
        String resp = "";
        Map<String, Object> result = Request.get(workNo, url);
        
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        return resp;
    }
    
    /**
     * 查询指定座席班组信息
     * request method : GET
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/agentgroup/{workno}/groupbyagent/{agentid}
     */
    public static String groupbyagent(String workNo, String agentid) {
        String url = CTIAPI.prefix + "agentgroup/" + workNo + "/groupbyagent/" + agentid;
        String resp = "";
        Map<String, Object> result = Request.get(workNo, url);
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        return resp;
    }
    
    /**
     * 查询指定班组的座席信息
     * request method : GET
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/agentgroup/{agentid}/agentongroupex?groupid={groupid}
     */
    public static String agentongroupex(String workNo, String groupid) {
        String url = CTIAPI.prefix + "agentgroup/" + workNo + "/agentongroupex?groupid=" + groupid;
        String resp = "";
        Map<String, Object> result = Request.get(workNo, url);
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	System.out.println("result is not variable" + e.getMessage());
        }
        return resp;
    }
    
    /**
     * 查询所在VDN所有班组信息
     * request method : GET
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/agentgroup/{agentid}/grouponvdn
     */
    public static String grouponvdn(String workNo) {
        String url = CTIAPI.prefix + "agentgroup/" + workNo + "/grouponvdn";
        String resp = "";
        Map<String, Object> result = Request.get(workNo, url);
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        return resp;
    }
    
    /**
     * 资源清晰
     */
    public static void clearResourse(String workNo) {
    	GlobalObjects.eventThreadMap.get(workNo).end();
    	GlobalObjects.eventThreadMap.remove(workNo);
    	GlobalObjects.guidMap.remove(workNo);
    	GlobalObjects.elbSessionMap.remove(workNo);//For HEC
    }
}
