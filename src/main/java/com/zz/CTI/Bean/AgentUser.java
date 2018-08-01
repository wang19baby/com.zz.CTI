package com.zz.CTI.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * 坐席用户
 * 
 * @author zhou.zhang
 *
 */
public class AgentUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userNo;								// 员工号
	private String workNo; 								// 坐席工号
	private String workName; 							// 座席名称
	private String phoneNumber; 						// 座机号
	private String agentProfile;						// 坐席简介
	private Dept dept;                                  // 部门
	private AgentRole agentRole; 						// 坐席角色
	private List<AgentPermission> agentPermissions;		// 坐席权限
	private int count;									// mybatis判断是否存在

	public AgentUser() {
		super();
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getWorkNo() {
		return workNo;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getAgentProfile() {
		return agentProfile;
	}

	public void setAgentProfile(String agentProfile) {
		this.agentProfile = agentProfile;
	}
	
	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public AgentRole getAgentRole() {
		return agentRole;
	}

	public void setAgentRole(AgentRole agentRole) {
		this.agentRole = agentRole;
	}
	
	public List<AgentPermission> getAgentPermissions() {
		return agentPermissions;
	}

	public void setAgentPermissions(List<AgentPermission> agentPermissions) {
		this.agentPermissions = agentPermissions;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "AgentUser [userNo=" + userNo + ", workNo=" + workNo + ", workName=" + workName + ", phoneNumber="
				+ phoneNumber + ", agentProfile=" + agentProfile + ", dept=" + dept + ", agentRole=" + agentRole
				+ ", agentPermissions=" + agentPermissions + "]";
	}
}
