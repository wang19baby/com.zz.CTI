package com.zz.CTI.Shiro;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import com.zz.CTI.Bean.User;
import com.zz.CTI.Util.Logs;

import net.sf.json.JSONObject;

public class ShiroUtil {
	@Autowired
	private static SessionDAO sessionDAO;
	
	/**
     * 验证是否登陆
     */
    public static boolean isAuthenticated(HttpSession session, HttpServletRequest request, 
    		HttpServletResponse response) {
        boolean status = false;

        SessionKey key = new WebSessionKey(session.getId(), request, response);
        try{
            Session se = SecurityUtils.getSecurityManager().getSession(key);
            Object obj = se.getAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY);
            if(obj != null){
                status = (Boolean) obj;
            }
        } catch(Exception e) {
        	Logs.error("验证是否登录异常：" + e.getMessage());
        }
        return status;
    }
    
    /**
     * 获取用户登录信息
     */
    public static User getUserInfo() {
        try {
        	return (User) SecurityUtils.getSubject().getPrincipal();
        } catch(Exception e) {
        	Logs.error("获取用户登录信息异常：" + e.getMessage());
        }
        return null;
    }
    
    public static boolean isOnLine(String userNo) {
    	Collection<Session> sessions = sessionDAO.getActiveSessions();
    	for(Session session : sessions) {
    		Object object = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
    		if (!ObjectUtils.isEmpty(object)) {
    			String userNoOnLine = ((User) JSONObject.toBean(JSONObject.fromObject(object).
    					getJSONObject("primaryPrincipal"), User.class)).getUserNo();
    			if (userNo.equals(userNoOnLine)) {
    				return true;
				}
			}
    	}
		return false;
    }
    
    /**
     * 获取用户登录信息
     */
    public User getUserInfo(HttpSession session, HttpServletRequest request, 
    		HttpServletResponse response) {
        SessionKey key = new WebSessionKey(session.getId(), request, response);
        try{
            Session se = SecurityUtils.getSecurityManager().getSession(key);
            Object obj = se.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            SimplePrincipalCollection coll = (SimplePrincipalCollection) obj;
            
            return (User) coll.getPrimaryPrincipal();
        } catch(Exception e) {
        	Logs.error("获取用户登录信息：" + e.getMessage());
        }
        return null;
    }
}
