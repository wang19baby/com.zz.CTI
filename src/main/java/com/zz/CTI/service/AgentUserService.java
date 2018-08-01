package com.zz.CTI.service;

import java.util.List;
import java.util.Map;

import com.zz.CTI.Bean.AgentUser;
import com.zz.CTI.Bean.Dept;

import net.sf.json.JSONObject;

public interface AgentUserService {
	
	public List<Dept> getDepts();
	
	public AgentUser getAgentUserByUserNo(String userNo);
	
	public String getAgentUsers(Map<String, Object> searchMap);
	
	public Long getAgentUsersPageSum(Map<String, Object> searchMap);
	
	public JSONObject addAgent(AgentUser agentUser);
	
	public JSONObject deleteAgent(AgentUser agentUser);
	
	public JSONObject deleteAgents(List<AgentUser> agentUsers);
	
	public JSONObject editAgentUser(AgentUser agentUser);
}
