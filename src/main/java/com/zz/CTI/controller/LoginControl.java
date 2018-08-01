package com.zz.CTI.controller;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zz.CTI.Bean.AgentUser;
import com.zz.CTI.Bean.User;
import com.zz.CTI.Shiro.ShiroUtil;
import com.zz.CTI.Util.Logs;
import com.zz.CTI.huawei.service.AgentService;
import com.zz.CTI.service.AgentUserService;
import com.zz.CTI.service.CTIService;

import net.sf.json.JSONObject;
  
/** 
 * 登录认证的控制器 
 */  
@Controller
@RequestMapping("/sys")
public class LoginControl { 
	
	@Resource
	private CTIService ctiService;
	
	@Resource
	private AgentUserService agentUserService;
	
	/**
	 * 登录
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		if (ShiroUtil.isAuthenticated(session, request, response)) {
			// 已登录
	    	return "index";
		} else {
			// 未登录
	    	return "login/login";
		}
	}
  
    /** 
     * 登录 
     * @param session HttpSession 
     * @param username  用户名 
     * @param password  密码 
     * @return 
     */  
    @RequestMapping(value="/login", method = RequestMethod.POST,
    		produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String login(@RequestBody User user, HttpSession session) {
    	JSONObject jsonresult = new JSONObject();
    	AgentUser agentUser = agentUserService.getAgentUserByUserNo(user.getUserNo());
    	
    	if (ObjectUtils.isEmpty(agentUser)) {
        	jsonresult.put("retcode", "100-103");
        	jsonresult.put("error_msg", ctiService.getErrorMsg(jsonresult.getString("retcode")));
            return jsonresult.toString();
		} else if (StringUtils.isBlank(agentUser.getWorkNo())) {
        	jsonresult.put("retcode", "100-100");
        	jsonresult.put("error_msg", ctiService.getErrorMsg(jsonresult.getString("retcode")));
            return jsonresult.toString();
		}
    	jsonresult = JSONObject.fromObject(AgentService.login(agentUser.getWorkNo(), "", user.getPhonenumber(), agentUser));
    	
		if ("0".equals(jsonresult.getString("retcode"))) {
	    	Subject subject = SecurityUtils.getSubject(); //当前Subject
	        UsernamePasswordToken usernamePasswordToken = new 
	                UsernamePasswordToken(agentUser.getWorkNo(), agentUser.getUserNo());
	        try {
	            subject.login(usernamePasswordToken);
	        } catch (UnknownAccountException e) {
	        	Logs.error(agentUser.getUserNo() + ": 坐席名称不匹配  :" + e.getMessage());
	        	AgentService.forceLogout(agentUser.getWorkNo());
	        	jsonresult.put("retcode", "100-101");
	        	jsonresult.put("error_msg", ctiService.getErrorMsg(jsonresult.getString("retcode")));
	            return jsonresult.toString();
	        }  catch (Exception e) {
	        	Logs.error(agentUser.getUserNo() + ": Shiro安全登陆异常 :" + e.getMessage());
	        	AgentService.forceLogout(agentUser.getWorkNo());
	        	jsonresult.put("retcode", "100-102");
	        	jsonresult.put("error_msg", ctiService.getErrorMsg(jsonresult.getString("retcode")));
	            return jsonresult.toString();
	        }
	        // 登录成功，将信息设置到HttpSession作用范围域中
	     	session.setAttribute("workNo", agentUser.getWorkNo());
	        // 重置技能队列
	        AgentService.resetskill(agentUser.getWorkNo());
		} else {
			// 登录失败，返回错误描述
			jsonresult.put("error_msg", ctiService.getErrorMsg(jsonresult.getString("retcode")));
		}
		
	    return jsonresult.toString();
    }
    
    /** 
     * 强制签入
     * @param session HttpSession 
     * @param username  用户名 
     * @param password  密码 
     * @return 
     */  
    @RequestMapping(value="/forcelogin", method = RequestMethod.POST,
    		produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String forceLogin(@RequestBody User user, HttpSession session) {
    	JSONObject jsonresult = new JSONObject();
    	AgentUser agentUser = agentUserService.getAgentUserByUserNo(user.getUserNo());
    	
    	if (ObjectUtils.isEmpty(agentUser)) {
        	jsonresult.put("retcode", "100-103");
        	jsonresult.put("error_msg", ctiService.getErrorMsg(jsonresult.getString("retcode")));
            return jsonresult.toString();
		} else if (StringUtils.isBlank(agentUser.getWorkNo())) {
        	jsonresult.put("retcode", "100-100");
        	jsonresult.put("error_msg", ctiService.getErrorMsg(jsonresult.getString("retcode")));
            return jsonresult.toString();
		}
    	
		// 进行强制登录请求
		jsonresult = JSONObject.fromObject(AgentService.forceLogin(agentUser.getWorkNo(), "", user.getPhonenumber(), agentUser));
		
		if ("0".equals(jsonresult.getString("retcode"))) {
	    	Subject subject = SecurityUtils.getSubject(); //当前Subject
	        UsernamePasswordToken usernamePasswordToken = new 
	                UsernamePasswordToken(agentUser.getWorkNo(), agentUser.getUserNo());
	        try {
	            subject.login(usernamePasswordToken);
	        } catch (UnknownAccountException e) {
	        	Logs.error(agentUser + ": 坐席名称不匹配  :" + e.getMessage());
	        	AgentService.forceLogout(agentUser.getWorkNo());
	        	jsonresult.put("retcode", "100-101");
	        	jsonresult.put("error_msg", ctiService.getErrorMsg(jsonresult.getString("retcode")));
	            return jsonresult.toString();
	        }  catch (Exception e) {
	        	Logs.error("====== Shiro安全登陆异常 =======" + e.getMessage());
	        	AgentService.forceLogout(agentUser.getWorkNo());
	        	jsonresult.put("retcode", "100-102");
	        	jsonresult.put("error_msg", ctiService.getErrorMsg(jsonresult.getString("retcode")));
	            return jsonresult.toString();
	        }
			// 登录成功，将信息设置到HttpSession作用范围域中
	        session.setAttribute("workNo", agentUser.getWorkNo());
	        // 重置技能队列
	        AgentService.resetskill(user.getWorkno());
		} else {
			// 登录失败，返回错误描述
			jsonresult.put("error_msg", ctiService.getErrorMsg(jsonresult.getString("retcode")));
		}
        return jsonresult.toString();
    }
    
	/**
	 * 坐席签出
	 * @param session
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Subject subject = SecurityUtils.getSubject();
		String workNo = ShiroUtil.getUserInfo().getWorkno();
		
		// 进行签出请求
		JSONObject jsonresult = JSONObject
				.fromObject(AgentService.logout(workNo));
		
		if ("0".equals(jsonresult.getString("retcode"))) {
		    if (subject.isAuthenticated()) {  
		        subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
		    }  
		} else {
			// 签出失败
			jsonresult.put("error_msg", ctiService.getErrorMsg(jsonresult.getString("retcode"))); // 返回错误描述
		}
		return "login/login";
	}
}  