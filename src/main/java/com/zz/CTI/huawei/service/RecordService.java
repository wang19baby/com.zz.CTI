package com.zz.CTI.huawei.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.zz.CTI.API.CTIAPI;
import com.zz.CTI.Util.Logs;
import com.zz.CTI.huawei.request.Request;
import com.zz.CTI.huawei.util.StringUtils;

public class RecordService {
	
    /**
     * 开始放音
     * request method : PUT
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/recordplay/{agentid}/play
     * 在这个URL中，IP表示代理网关的IP地址，端口表示代理网关的http或HTTPS端口号，{ agentid }表明代理人的坐席工号
     * 
     * 请求消息体示例:
     * {"voicepath":"/root/temp/Users/filename"，"startpostion":12，"volumechange":20，"speedchange":20，"times":1，"codeformat":0}
     */
    public static String recordPlay(String workNo, String recordId) {
        String url = CTIAPI.prefix + "recordplay/" + workNo + "/play";
        Map<String, Object> recordPlayParam = new HashMap<String, Object>();
        recordPlayParam.put("voicepath", recordId); //文件路径
        String resp = "";
        Map<String,Object> result = Request.put(workNo,url, recordPlayParam);
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        return resp;
    }
    
    /**
     * 停止放音
     * request method : DELETE
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/recordplay/{agentid}/stopplay
     * 在这个URL中，IP表示代理网关的IP地址，端口表示代理网关的http或HTTPS端口号，
     * { agentid }表明代理人的坐席工号。
     */
    public static String recordStopPlay(String workNo) {
        String url = CTIAPI.prefix + "recordplay/" + workNo + "/stopplay";
        String resp = "";
        Map<String,Object> result = Request.delete(workNo, url, null);
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        return resp;
    }
    
    /**
     * 暂停放音
     * request method : POST
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/recordplay/{agentid}/pauseplay
     * 其中，ip为agentgateway服务器地址，port为agentgateway服务器的HTTP（或HTTPS）端口号，{agentid}为座席工号。
     */
    public static String recordPausePlay(String workNo) {
        String url = CTIAPI.prefix + "recordplay/" + workNo + "/pauseplay";
        String resp = "";
        Map<String, Object> result = Request.post(workNo, url, null);
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        return resp;
    }
    
    /**
     * 恢复放音
     * request method : POST
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/recordplay/{agentid}/resumeplay
     * 其中，ip为agentgateway服务器地址，port为agentgateway服务器的HTTP（或HTTPS）端口号，{agentid}为座席工号。
     */
    public static String recordResumePlay(String workNo) {
        String url = CTIAPI.prefix + "recordplay/" + workNo + "/resumeplay";
        String resp = "";
        Map<String, Object> result = Request.post(workNo, url, null);
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        return resp;
    }
    
    /**
     * 放音快进
     * request method : POST
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/recordplay/{agentid}/forefast/{time}
     * 其中，ip为agentgateway服务器地址，port为agentgateway服务器的HTTP（或HTTPS）端口号，{agentid}为座席工号。
     */
    public static String recordForeFast(String workNo, String time) {
        String url = CTIAPI.prefix + "recordplay/" + workNo + "/forefast/" + time;
        String resp = "";
        Map<String, Object> result = Request.post(workNo, url, null);
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        return resp;
    }
    
    /**
     * 放音快退
     * request method : POST
     * 
     * request URL : http(s)://ip:port/agentgateway/resource/recordplay/{agentid}/backfast/{time}
     * 其中，ip为agentgateway服务器地址，port为agentgateway服务器的HTTP（或HTTPS）端口号，{agentid}为座席工号。
     */
    public static String recordBackFast(String workNo, String time) {
        String url = CTIAPI.prefix + "recordplay/" + workNo + "/backfast/" + time;
        String resp = "";
        Map<String, Object> result = Request.post(workNo, url, null);
        try {
            resp = StringUtils.beanToJson(result);
        } catch (IOException e) {
        	Logs.error("result is not variable" + e.getMessage());
        }
        return resp;
    }
}
