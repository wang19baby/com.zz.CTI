package com.zz.CTI.huawei.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.zz.CTI.API.CTIAPI;
import com.zz.CTI.Util.Logs;
import com.zz.CTI.huawei.request.Request;
import com.zz.CTI.huawei.util.StringUtils;

public class CallService {
	
    /**
     * 座机外呼
     * request method : PUT
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/voicecall/{agentid}/callout
     * 在这个URL中，IP表示代理网关的IP地址，端口表示代理网关的http或HTTPS端口号，{ agentid }表明代理人的坐席工号
     * 
     * 请求消息体示例:
     * {"caller":"40038","called":"40040","skillid":25,"callappdata":"","callcontrolid":0,"mediaability":1}
     */
    public static String voicecallOut(String workNo,String called) {
        String url = CTIAPI.prefix + "voicecall/" + workNo + "/callout";
        Map<String, Object> callOutParam = new HashMap<String, Object>();
        callOutParam.put("called", called); 		//被叫号码
        String resp = "";
        Map<String,Object> result = Request.put(workNo,url, callOutParam);
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        return resp;
    }
    
    /**
     * 座机内呼
     * request method : PUT
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/voicecall/{agentid}/callinner
     * 在这个URL中，IP表示代理网关的IP地址，端口表示代理网关的http或HTTPS端口号，{ agentid }表明代理人的坐席工号
     * 
     * 请求消息体示例:
     * {"called":"40040"}
     */
    public static String voicecallInner(String workNo,String called) {
        String url = CTIAPI.prefix + "voicecall/" + workNo + "/callinner";
        Map<String, Object> callOutParam = new HashMap<String, Object>();
        callOutParam.put("called", called); //被叫号码(坐席号)
        String resp = "";
        Map<String,Object> result = Request.put(workNo,url, callOutParam);
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        return resp;
    }
    
    /**
     * 挂断呼叫
     * request method : DELETE
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/voicecall/{agentid}/release
     * 在这个URL中，IP表示代理网关的IP地址，端口表示代理网关的http或HTTPS端口号，
     * { agentid }表明代理人的坐席工号。
     */
    public static String voiceRelease(String workNo) {
        String url = CTIAPI.prefix + "voicecall/" + workNo + "/release";
        String resp = "";
        Map<String,Object> result = Request.delete(workNo,url,null);
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        return resp;
    }
    
    /**
     * 挂断话机
     * request method : DELETE
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/voicecall/{agentid}/releasephone
     * 在这个URL中，IP表示代理网关的IP地址，端口表示代理网关的http或HTTPS端口号，
     * { agentid }表明代理人的坐席工号。
     */
    public static String voiceReleasePhone(String workNo) {
        String url = CTIAPI.prefix + "voicecall/" + workNo + "/releasephone";
        String resp = "";
        Map<String,Object> result = Request.delete(workNo,url,null);
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        return resp;
    }
    
    /**
     * 呼叫应答
     * request method : PUT
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/voicecall/{agentid}/answer
     * 在这个URL中，IP表示代理网关的IP地址，端口表示代理网关的http或HTTPS端口号，{ agentid }表明代理人的坐席工号
     */
    public static String voiceAnswer(String workNo) {
        String url = CTIAPI.prefix + "voicecall/" + workNo + "/answer";
        String resp = "";
        Map<String,Object> result = Request.put(workNo,url, null);
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        return resp;
    }
    
    /**
     * 呼叫转移
     * request method : POST
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/voicecall/{agentid}/transfer
     * 其中，ip为agentgateway服务器地址，port为agentgateway服务器的HTTP（或HTTPS）端口号，{agentid}为座席工号。
     */
    public static String voiceTransfer(String workNo, Map<String, Object> transferParam) {
        String url = CTIAPI.prefix + "voicecall/" + workNo + "/transfer";
        String resp = "";
        Map<String, Object> result = Request.post(workNo, url, transferParam);
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        return resp;
    }
    
    /**
     * 查询所有呼叫CallID信息
     * request method : GET
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/calldata/{agentid}/allcallid
     * 其中，ip为agentgateway服务器地址，port为agentgateway服务器的HTTP（或HTTPS）端口号，{agentid}为座席工号。
     */
    public static String allcallid(String workNo) {
        String url = CTIAPI.prefix + "calldata/" + workNo + "/allcallid";
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
     * 根据呼叫CallID查询呼叫信息
     * request method : GET
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/calldata/{agentid}/callinfobycallid/{callid}
     * 其中，ip为agentgateway服务器地址，port为agentgateway服务器的HTTP（或HTTPS）端口号，{agentid}为座席工号，{callid}为被查询呼叫ID
     */
    public static String callinfobycallid(String workNo) {
        String url = CTIAPI.prefix + "calldata/" + workNo + "/callinfobycallid/1523256889-279074";
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
     * 查询呼叫信息
     * request method : GET
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/calldata/{agentid}/callinfo?isNoContainLastCall={isNoContainLastCall}
     * 其中，ip为agentgateway服务器地址，port为agentgateway服务器的HTTP（或HTTPS）端口号，{agentid}为座席工号。
     * {isNoContainLastCall}表示当前座席无呼叫时，是否不查询上一通呼叫信息（boolean型）。
     */
    public static String callinfo(String workNo) {
        String url = CTIAPI.prefix + "calldata/" + workNo + "/callinfo?isNoContainLastCall=false";
        String resp = "";
        /*
         * 响应消息体示例:
         * {"message":"","retcode":"0","result":{"callfeature":0,"callid":"1476838963-224",
         * "caller":"70004","called":"60015","callskill":"voice","callskillid":20011,"orgicallednum":"",
         * "calldata":"","begintime":1476838963000,"endtime":null,"userPriority":0,"trunkNo":65535,"logontimes":0}}
         */
        Map<String, Object> result = Request.get(workNo, url);
        
        try {
        	System.out.println(workNo + " request URL : " + url);
            resp = StringUtils.beanToJson(result);
            
            System.out.println(workNo + " receive message : " + resp);
        } catch (IOException e) {
        	System.out.println("result is not variable" + e.getMessage());
        }
        
        System.out.println(workNo + " : get callinfo end.");
        return resp;
    }
    
    /**
     * 查询指定座席上通话方电话号码信息
     * request method : GET
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/calldata/{agentid}/remotenumbers/{agentid}
     * 其中，ip为agentgateway服务器地址，port为agentgateway服务器的HTTP（或HTTPS）端口号，{agentid}为座席工号。
     */
    public static String remotenumbers(String workNo) {
    	System.out.println(workNo + " : get remotenumbers begin.");
        
        String url = CTIAPI.prefix + "calldata/" + workNo + "/remotenumbers";
        
        String resp = "";
        
        /*
         * 响应消息体示例:
         * {"message":"","retcode":"0","result":["70093","70092"]}
         */
        Map<String, Object> result = Request.get(workNo, url);
        
        try {
        	System.out.println(workNo + " request URL : " + url);
            resp = StringUtils.beanToJson(result);
            
            System.out.println(workNo + " receive message : " + resp);
        } catch (IOException e) {
        	System.out.println("result is not variable" + e.getMessage());
        }
        
        System.out.println(workNo + " : get remotenumbers end.");
        return resp;
    }
    
    /**
     * 查询应答来话前呼叫信息
     * request method : GET
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/calldata/{agentid}/callinfobeforeanswer
     * 其中，ip为agentgateway服务器地址，port为agentgateway服务器的HTTP（或HTTPS）端口号，{agentid}为座席工号。
     */
    public static String callinfobeforeanswer(String workNo) {
        String url = CTIAPI.prefix + "calldata/" + workNo + "/callinfobeforeanswer";
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
     * 查询指定座席呼叫ID
     * request method : GET
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/calldata/{workno}/allcallinfoEx/{agentid}
     * 其中，ip为agentgateway服务器地址，port为agentgateway服务器的HTTP（或HTTPS）端口号，{agentid}为座席工号，{workno}为待查询座席工号。
     */
    public static String allcallinfoEx(String workNo, String agentid) {
    	System.out.println(workNo + " : get allcallinfoEx begin.");
        
        String url = CTIAPI.prefix + "calldata/" + workNo + "/allcallinfoEx/" + agentid;
        
        String resp = "";
        
        /*
         * 响应消息体示例:
         * {"message":"","retcode":"0","result":["70093","70092"]}
         */
        Map<String, Object> result = Request.get(workNo, url);
        
        try {
        	System.out.println(workNo + " request URL : " + url);
            resp = StringUtils.beanToJson(result);
            
            System.out.println(workNo + " receive message : " + resp);
        } catch (IOException e) {
        	System.out.println("result is not variable" + e.getMessage());
        }
        
        System.out.println(workNo + " : get allcallinfoEx end.");
        return resp;
    }
    
    /**
     * 查询指定座席所有呼叫信息
     * request method : GET
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/calldata/{workNo}/allcallinfo/{agentid}
     * 其中，ip为agentgateway服务器地址，port为agentgateway服务器的HTTP（或HTTPS）端口号，{workNo}为座席工号，{agentid}为待查询的座席工号。
     */
    public static String allcallinfo(String workNo, String agentid) {
        String url = CTIAPI.prefix + "calldata/" + workNo + "/allcallinfo/" + agentid;
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
     * 查询座席当前呼叫统计信息
     * request method : GET
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/calldata/{agentid}/statistics
     * 其中，ip为agentgateway服务器地址，port为agentgateway服务器的HTTP（或HTTPS）端口号，{workNo}为座席工号，{agentid}为待查询的座席工号。
     */
    public static String statistics(String workNo) {
        String url = CTIAPI.prefix + "calldata/" + workNo + "/statistics";
        String resp = "";
        Map<String, Object> result = Request.get(workNo, url);
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        return resp;
    }
}
