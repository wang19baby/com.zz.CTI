package com.zz.CTI.Bean;

import java.io.Serializable;

/**
 * 坐席角色
 * 
 * @author zhou.zhang
 *
 */
public class AgentRole implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String roleId; 		// 角色ID
	private String roleTag; 	// 角色标识
	private String roleName; 	// 角色名称

	public AgentRole() {
		super();
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleTag() {
		return roleTag;
	}

	public void setRoleTag(String roleTag) {
		this.roleTag = roleTag;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "AgentRole [roleId=" + roleId + ", roleTag=" + roleTag + ", roleName=" + roleName + "]";
	}

}
