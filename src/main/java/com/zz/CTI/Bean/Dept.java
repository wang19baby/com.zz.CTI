package com.zz.CTI.Bean;

import java.io.Serializable;

/**
 * 部门
 * @author zhou.zhang
 *
 */
public class Dept implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String deptId; 		// 部门Id
	private String deptName; 	// 部门名称
	
	public Dept() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	@Override
	public String toString() {
		return "Dept [deptId=" + deptId + ", deptName=" + deptName + "]";
	}
}
