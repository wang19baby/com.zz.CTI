package com.zz.CTI.dao;

import java.util.List;
import java.util.Map;

import com.zz.CTI.Bean.AgentCall;
import com.zz.CTI.Bean.AgentPermission;
import com.zz.CTI.Bean.AgentRecord;
import com.zz.CTI.Bean.AgentRole;
import com.zz.CTI.Bean.AgentUser;

public interface CTIDao {
	
	String getErrorMsg(String retcode);
	
	int insertAgentCall(AgentCall agentCall);
	
	int updateAgentCall(AgentCall agentCall);
	
	List<AgentCall> getCallRecordList(Map<String, Object> searchMap);
	
	Long getCallRecordPageSum(Map<String, Object> searchMap);
	
	int insertAgentRecord(AgentRecord agentRecord);
	
	int updateAgentRecord(AgentRecord agentRecord);
	
	AgentRecord getAgentRecordByRecordId(String recordId);
	
	List<AgentRecord> getAgentRecordList(Map<String, Object> searchMap);
	
	Long getAgentRecordPageSum(Map<String, Object> searchMap);
	
	AgentUser getAgentUserByUserNo(String userNo);
	
	List<AgentPermission> getAgentPermissionsByUserNo(String userNo);
	
	List<AgentRole> getAgentRoles();
	
	List<AgentPermission> getAgentPermissions();
}
