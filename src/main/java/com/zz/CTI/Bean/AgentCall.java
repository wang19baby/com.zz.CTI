package com.zz.CTI.Bean;

import java.io.Serializable;

/**
 * 坐席呼叫实体
 * 
 * @author zhou.zhang
 *
 */
public class AgentCall implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userNo; 				// 员工号
	private String workNo; 				// 坐席工号
	private String callId; 				// 呼叫ID
	private String phoneNumber; 		// 座机号
	private String otherPhone; 			// 振铃方的电话号码
	private String date; 				// 日期
	private int type; 					// 呼叫类型(1:呼出；2：来电；)
	private boolean result;				// 结果(未通话false 0,通话 true 1)
	private int count;					// mybatis判断callId是否存在
	

	public AgentCall() {
		super();
	}
	
	public AgentCall(String userNo, String workNo, String callId, String phoneNumber, String otherPhone, String date,
			int type, boolean result) {
		super();
		this.userNo = userNo;
		this.workNo = workNo;
		this.callId = callId;
		this.phoneNumber = phoneNumber;
		this.otherPhone = otherPhone;
		this.date = date;
		this.type = type;
		this.result = result;
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

	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public String getOtherPhone() {
		return otherPhone;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setOtherPhone(String otherPhone) {
		this.otherPhone = otherPhone;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "AgentCall [userNo=" + userNo + ", workNo=" + workNo + ", callId=" + callId + ", phoneNumber="
				+ phoneNumber + ", otherPhone=" + otherPhone + ", date=" + date + ", type=" + type + ", result="
				+ result + "]";
	}
}
