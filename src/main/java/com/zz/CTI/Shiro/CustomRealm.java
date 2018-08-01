package com.zz.CTI.Shiro;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ObjectUtils;

import com.zz.CTI.Bean.AgentPermission;
import com.zz.CTI.Bean.AgentRole;
import com.zz.CTI.Bean.AgentUser;
import com.zz.CTI.Bean.User;
import com.zz.CTI.Util.Logs;
import com.zz.CTI.WebSocket.SpringWebSocketHandler;
import com.zz.CTI.huawei.service.AgentService;
import com.zz.CTI.service.AgentUserService;
import com.zz.CTI.service.CTIService;

import net.sf.json.JSONObject;

public class CustomRealm extends AuthorizingRealm {
	
	@Autowired
	private SessionDAO sessionDAO;  
	
	@Autowired
	private CTIService ctiService;
	 
	@Autowired
	private AgentUserService agentUserService;
	 
    @Bean//这个注解会从Spring容器拿出Bean
    public SpringWebSocketHandler infoHandler() {
        return new SpringWebSocketHandler();
    }
	
	 /** 
     * 登陆认证
     */  
    @Override  
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
    	Logs.info("*************** 登陆认证 ******************");
    	//令牌——基于用户名和密码的令牌    
    	UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
    	
    	// 踢出此账号在线用户
    	Collection<Session> sessions = sessionDAO.getActiveSessions();
    	for(Session session : sessions) {
    		Object object = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
    		if (!ObjectUtils.isEmpty(object)) {
    			String workNo = ((User) JSONObject.toBean(JSONObject.fromObject(object).
    					getJSONObject("primaryPrincipal"), User.class)).getWorkno();
    			if (token.getUsername().equals(workNo)) {
    				infoHandler().sendEventToUser(workNo, "AgentSession_ThirdLogin");
        			sessionDAO.delete(session);
				}
			}
    	}
    	
        // Json对象转java对象
        User userDetail = (User) JSONObject.toBean(JSONObject.fromObject(AgentService.agentbyworkno(token.getUsername(), token.getUsername()))
        		.getJSONObject("result"), User.class);
        AgentUser agentUser = agentUserService.getAgentUserByUserNo(String.valueOf(token.getPassword()));
        if (StringUtils.isBlank(userDetail.getName()) || !userDetail.getName().equals(agentUser.getWorkName())) {
        	throw new UnknownAccountException("坐席名称不匹配，请联系管理员");
		}
        userDetail.setUserNo(agentUser.getUserNo());
    	//让shiro框架去验证账号密码  
    	return new SimpleAuthenticationInfo(userDetail, String.valueOf(token.getPassword()), getName());
    } 
    
    /**
     * 获取授权
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
	    //用户名    
    	String userNo = ((User) principals.getPrimaryPrincipal()).getUserNo();
	    //根据用户名来添加相应的权限和角色  
	    if(StringUtils.isNotBlank(userNo)) {
	    	AgentUser agentUser = ctiService.getAgentUserByUserNo(userNo);
	    	
	    	Logs.info(userNo + " :授权: " + agentUser);
	    	
	        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();  
	        addPermission(agentUser.getAgentPermissions(), info);  
	        addRole(agentUser.getAgentRole(), info);
	        return info;  
	    }
	    return null;    
    }  
		
    /**
     * 添加角色
     * @param username 
     * @param info 
     */  
    private void addRole(AgentRole agentRole, SimpleAuthorizationInfo info) {
        if(StringUtils.isNotBlank(agentRole.getRoleTag())) {
        	info.addRole(agentRole.getRoleTag());  
        }  
    }
  
    /**
     * 添加权限
     * @param username 
     * @param info
     * @return 
     */  
    private void addPermission(List<AgentPermission> agentPermissions,SimpleAuthorizationInfo info) {
        for (AgentPermission agentPermission : agentPermissions) {
            info.addStringPermission(agentPermission.getPermissionTag()); //添加权限    
        }
    }    
    
    /**
     * 清除缓存  
     */
    public void clearCached() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();  
        super.clearCache(principals);  
    }  
}