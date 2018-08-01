package com.zz.CTI.dao;

import java.util.List;
import java.util.Map;

import com.zz.CTI.Bean.AgentUser;
import com.zz.CTI.Bean.Dept;

public interface AgentUserDao {
	
	List<Dept> getDepts();
	
	AgentUser getAgentUserByUserNo(String userNo);
	
	List<AgentUser> getAgentUsers(Map<String, Object> searchMap);
	
	Long getAgentUsersPageSum(Map<String, Object> searchMap);
	
	int addAgent(AgentUser agentUser);
	
	int addAgentPermission(AgentUser agentUser);
	
	int deleteAgent(AgentUser agentUser);
	
	int deleteAgentPermissionByUserNo(AgentUser agentUser);
	
	int deleteAgents(List<AgentUser> agentUsers);
	
	int deleteAgentPermissionsByAgentUsers(List<AgentUser> agentUsers);
	
	int editAgentUser(AgentUser agentUser);
}
