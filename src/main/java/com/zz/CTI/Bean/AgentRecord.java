package com.zz.CTI.Bean;

import java.io.Serializable;

/**
 * 坐席录音实体
 * 
 * @author zhou.zhang
 *
 */
public class AgentRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	private String recordId; 		// 录音流水号
	private String callId; 			// 呼叫流水号
	private String userNo; 			// 员工号
	private String workNo;			// 坐席工号
	private String recordTitle; 	// 录音标题
	private String locationId; 		// 录音对应的中心节点ID
	private String recordStartDate; // 录音开始时间
	private String recordEndDate; 	// 录音结束时间时间
	private String fileName; 		// 录音文件地址
	private int count;				// mybatis判断recordId是否存在
	private String type; 			// 录音操作类型

	public AgentRecord() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AgentRecord(String recordId, String callId, String userNo, String workNo, String recordTitle,
			String locationId, String recordStartDate, String recordEndDate, String fileName) {
		super();
		this.recordId = recordId;
		this.callId = callId;
		this.userNo = userNo;
		this.workNo = workNo;
		this.recordTitle = recordTitle;
		this.locationId = locationId;
		this.recordStartDate = recordStartDate;
		this.recordEndDate = recordEndDate;
		this.fileName = fileName;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
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

	public String getRecordTitle() {
		return recordTitle;
	}

	public void setRecordTitle(String recordTitle) {
		this.recordTitle = recordTitle;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getRecordStartDate() {
		return recordStartDate;
	}

	public void setRecordStartDate(String recordStartDate) {
		this.recordStartDate = recordStartDate;
	}

	public String getRecordEndDate() {
		return recordEndDate;
	}

	public void setRecordEndDate(String recordEndDate) {
		this.recordEndDate = recordEndDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "AgentRecord [recordId=" + recordId + ", callId=" + callId + ", userNo=" + userNo + ", workNo=" + workNo
				+ ", recordTitle=" + recordTitle + ", locationId=" + locationId + ", recordStartDate=" + recordStartDate
				+ ", recordEndDate=" + recordEndDate + ", fileName=" + fileName + ", type=" + type + "]";
	}
}
