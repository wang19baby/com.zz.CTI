package com.zz.CTI.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * 坐席实体类
 * 
 * @author zhou.zhang
 *
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private String userNo;			// 员工号
	private String workno; 			// 坐席工号
	private String name; 			// 座席名称
	private String phonenumber; 	// 座机号
	private int status; 			// 坐席状态
	private int groupid; 			// 座席班组ID
	private String groupname; 		// 坐席班组名称
	private List<Object> skilllist; // 座席技能对象列表
	private String mediatype; 		// 座席媒体类型
	private int vdnid; 				// 所属VDN ID
	private long currentstatetime; 	// 当前状态时长
	private long logindate; 		// 签入时间
	private List<Object> callids;   // 未使用改参数

	public User() {
		super();
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getWorkno() {
		return workno;
	}

	public void setWorkno(String workno) {
		this.workno = workno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public List<Object> getSkilllist() {
		return skilllist;
	}

	public void setSkilllist(List<Object> skilllist) {
		this.skilllist = skilllist;
	}

	public String getMediatype() {
		return mediatype;
	}

	public void setMediatype(String mediatype) {
		this.mediatype = mediatype;
	}

	public int getVdnid() {
		return vdnid;
	}

	public void setVdnid(int vdnid) {
		this.vdnid = vdnid;
	}

	public long getCurrentstatetime() {
		return currentstatetime;
	}

	public void setCurrentstatetime(long currentstatetime) {
		this.currentstatetime = currentstatetime;
	}

	public long getLogindate() {
		return logindate;
	}

	public void setLogindate(long logindate) {
		this.logindate = logindate;
	}

	public List<Object> getCallids() {
		return callids;
	}

	public void setCallids(List<Object> callids) {
		this.callids = callids;
	}

	@Override
	public String toString() {
		return "User [userNo=" + userNo + ", workno=" + workno + ", name=" + name + ", phonenumber=" + phonenumber
				+ ", status=" + status + ", groupid=" + groupid + ", groupname=" + groupname + ", skilllist="
				+ skilllist + ", mediatype=" + mediatype + ", vdnid=" + vdnid + ", currentstatetime=" + currentstatetime
				+ ", logindate=" + logindate + ", callids=" + callids + "]";
	}
}
