package com.zz.CTI.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zz.CTI.Bean.AgentUser;
import com.zz.CTI.Bean.Dept;
import com.zz.CTI.Util.Logs;
import com.zz.CTI.Util.MyException;
import com.zz.CTI.dao.AgentUserDao;
import com.zz.CTI.dao.CTIDao;
import com.zz.CTI.service.AgentUserService;

import net.sf.json.JSONObject;

@Service("agentUserService")
public class AgentUserServiceImpl implements AgentUserService {
	@Resource
	private AgentUserDao agentUserDao;
	
	@Resource
	private CTIDao ctiDao;
	
	/**
	 * 查询部门字典
	 */
	@Override
	public List<Dept> getDepts() {
		return agentUserDao.getDepts();
	}

	/**
	 * 根据员工号查询账号基本配置信息
	 */
	@Override
	public AgentUser getAgentUserByUserNo(String userNo) {
		return agentUserDao.getAgentUserByUserNo(userNo);
	}
	
	@Override
	public Long getAgentUsersPageSum(Map<String, Object> searchMap) {
		return agentUserDao.getAgentUsersPageSum(searchMap);
	}

	/**
	 * 查询用户列表
	 */
	@Override
	public String getAgentUsers(Map<String, Object> searchMap) {
		List<AgentUser> agentUsers = agentUserDao.getAgentUsers(searchMap);
 		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", agentUsers);
		jsonObject.put("retcode", "0");
		jsonObject.put("count", getAgentUsersPageSum(searchMap));
		return jsonObject.toString();
	}
	
	/**
	 * 增加用户
	 */
	@Transactional
	@Override
	public JSONObject addAgent(AgentUser agentUser) throws MyException {
		JSONObject jsonObject = new JSONObject();
		try {
			agentUserDao.addAgent(agentUser);
			agentUserDao.addAgentPermission(agentUser);
		} catch (Exception e) {
			Logs.error("增加用户失败：" + e.getMessage());
			throw new MyException("900-100", ctiDao.getErrorMsg("900-100"));
		}
		jsonObject.put("retcode", "0");
		return jsonObject;
	}

	/**
	 * 删除用户
	 */
	@Transactional
	@Override
	public JSONObject deleteAgent(AgentUser agentUser) throws MyException {
 		JSONObject jsonObject = new JSONObject();
		try {
			agentUserDao.deleteAgentPermissionByUserNo(agentUser);
			agentUserDao.deleteAgent(agentUser);
		} catch (Exception e) {
			Logs.error("删除用户失败：" + e.getMessage());
			throw new MyException("900-101", ctiDao.getErrorMsg("900-101"));
		}
		jsonObject.put("retcode", "0");
		return jsonObject;
	}

	/**
	 * 批量删除用户
	 */
	@Transactional
	@Override
	public JSONObject deleteAgents(List<AgentUser> agentUsers) throws MyException {
		JSONObject jsonObject = new JSONObject();
		try {
			agentUserDao.deleteAgentPermissionsByAgentUsers(agentUsers);
			agentUserDao.deleteAgents(agentUsers);
		} catch (Exception e) {
			Logs.error("批量删除用户失败：" + e.getMessage());
			throw new MyException("900-102", ctiDao.getErrorMsg("900-102"));
		}
		jsonObject.put("retcode", "0");
		return jsonObject;
	}

	/**
	 * 编辑用户
	 */
	@Transactional
	@Override
	public JSONObject editAgentUser(AgentUser agentUser) throws MyException {
		JSONObject jsonObject = new JSONObject();
		try {
			agentUserDao.editAgentUser(agentUser);
			agentUserDao.deleteAgentPermissionByUserNo(agentUser);
			agentUserDao.addAgentPermission(agentUser);
		} catch (Exception e) {
			Logs.error("编辑用户失败：" + e.getMessage());
			throw new MyException("900-103", ctiDao.getErrorMsg("900-103"));
		} 
		jsonObject.put("retcode", "0");
		return jsonObject;
	}
}
