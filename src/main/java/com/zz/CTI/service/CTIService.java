package com.zz.CTI.service;

import java.util.List;
import java.util.Map;

import com.zz.CTI.Bean.AgentCall;
import com.zz.CTI.Bean.AgentPermission;
import com.zz.CTI.Bean.AgentRecord;
import com.zz.CTI.Bean.AgentRole;
import com.zz.CTI.Bean.AgentUser;

public interface CTIService {
	
	public String getErrorMsg(String retcode);
	
	// 通讯记录插入
	public int insertAgentCall(AgentCall agentCall);
	
	// 更新通讯记录 通话成功
	public int updateAgentCall(AgentCall agentCall);
	
	// 查询自身通话记录
	public String getCallRecordList(Map<String, Object> searchMap);
	
	// 查询通话记录总页数
	public Long getCallRecordPageSum(Map<String, Object> searchMap);
	
	// 录音记录插入
	public int insertAgentRecord(AgentRecord agentRecord);
	
	// 更新录音记录
	public String updateAgentRecord(AgentRecord agentRecord);
	
	// 根据留言流水号查询录音
	public String getAgentRecordByRecordId(String recordId);
	
	// 查询自身录音记录
	public String getAgentRecordList(Map<String, Object> searchMap);
	
	// 查询录音记录总页数
	public Long getAgentRecordPageSum(Map<String, Object> searchMap);
	
	public AgentUser getAgentUserByUserNo(String userNo);
	
	public List<AgentRole> getAgentRoles();
	
	public List<AgentPermission> getAgentPermissions();
}
