package com.zz.CTI.Bean;

import java.io.Serializable;

/**
 * 坐席权限
 * 
 * @author zhou.zhang
 *
 */
public class AgentPermission implements Serializable {

	private static final long serialVersionUID = 1L;

	private String permissionId; 	// 权限Id
	private String permissionTag; 	// 权限标识
	private String permissionName; 	// 权限名称

	public AgentPermission() {
		super();
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionTag() {
		return permissionTag;
	}

	public void setPermissionTag(String permissionTag) {
		this.permissionTag = permissionTag;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	@Override
	public String toString() {
		return "AgentPermission [permissionId=" + permissionId + ", permissionTag=" + permissionTag
				+ ", permissionName=" + permissionName + "]";
	}
}
