package com.zz.CTI.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zz.CTI.Bean.AgentCall;
import com.zz.CTI.Bean.AgentPermission;
import com.zz.CTI.Bean.AgentRecord;
import com.zz.CTI.Bean.AgentRole;
import com.zz.CTI.Bean.AgentUser;
import com.zz.CTI.dao.CTIDao;
import com.zz.CTI.huawei.util.StringUtils;
import com.zz.CTI.service.CTIService;

import net.sf.json.JSONObject;


@Service("ctiService")
public class CTIServiceImpl implements CTIService {
	@Resource
	private CTIDao ctiDao;

	@Override
	public String getErrorMsg(String retcode) {
		return ctiDao.getErrorMsg(retcode);
	}
	
	@Transactional
	@Override
	public int insertAgentCall(AgentCall agentCall) {
		return ctiDao.insertAgentCall(agentCall);
	}

	@Transactional
	@Override
	public int updateAgentCall(AgentCall agentCall) {
		return ctiDao.updateAgentCall(agentCall);
	}

	@Override
	public String getCallRecordList(Map<String, Object> searchMap) {
		List<AgentCall> agentCalls = ctiDao.getCallRecordList(searchMap);
 		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", agentCalls);
		jsonObject.put("retcode", "0");
		jsonObject.put("count", getCallRecordPageSum(searchMap));
		return jsonObject.toString();
	}

	@Override
	public Long getCallRecordPageSum(Map<String, Object> searchMap) {
		return ctiDao.getCallRecordPageSum(searchMap);
	}

	@Transactional
	@Override
	public int insertAgentRecord(AgentRecord agentRecord) {
		return ctiDao.insertAgentRecord(agentRecord);
	}

	@Transactional
	@Override
	public String updateAgentRecord(AgentRecord agentRecord) {
 		JSONObject jsonObject = new JSONObject();
		if (ctiDao.updateAgentRecord(agentRecord) == 1) {
			jsonObject.put("retcode", "0");
		} else {
			jsonObject.put("retcode", "500-100");
		}
		return jsonObject.toString();
	}

	@Override
	public String getAgentRecordList(Map<String, Object> searchMap) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", ctiDao.getAgentRecordList(searchMap));
		jsonObject.put("retcode", "0");
		jsonObject.put("count", getAgentRecordPageSum(searchMap));
		return jsonObject.toString();
	}
	
	/**
	 * 根据录音流水号查询录音
	 */
	@Override
	public String getAgentRecordByRecordId(String recordId) {
		AgentRecord agentRecord = ctiDao.getAgentRecordByRecordId(recordId);
		JSONObject jsonObject = new JSONObject();
		if (StringUtils.isNullOrBlank(agentRecord.getFileName())) {
			// 录音地址为空
			jsonObject.put("retcode", "500-200");
		} else {
			jsonObject.put("result", agentRecord);
			jsonObject.put("retcode", "0");
		}
		return jsonObject.toString();
	}

	@Override
	public Long getAgentRecordPageSum(Map<String, Object> searchMap) {
		return ctiDao.getAgentRecordPageSum(searchMap);
	}

	/**
	 * 根据员工号查询角色
	 */
	@Override
	public AgentUser getAgentUserByUserNo(String userNo) {
		return ctiDao.getAgentUserByUserNo(userNo);
	}

	/**
	 * 查询角色
	 */
	@Override
	public List<AgentRole> getAgentRoles() {
		return ctiDao.getAgentRoles();
	}

	/**
	 * 查询权限
	 */
	@Override
	public List<AgentPermission> getAgentPermissions() {
		return ctiDao.getAgentPermissions();
	}

}
