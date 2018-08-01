package com.zz.CTI.Util.TagUtil.DataDictionary;

import java.util.ArrayList;
import java.util.List;

import com.zz.CTI.Util.SpringContextUtil;
import com.zz.CTI.service.AgentUserService;
import com.zz.CTI.service.CTIService;

/**
 * 数据字典标签
 * @author zhou.zhang
 *
 */
public class DataDictionaryTag {
	private static CTIService ctiService = (CTIService) SpringContextUtil.getBean("ctiService");
	
	private static AgentUserService agentUserService = (AgentUserService) SpringContextUtil.getBean("agentUserService");
	
	/**
	 * 获取数据字典
	 * @param <T>
	 * @param datakey
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static  <T> List<T> getDataDictionary(String datakey) {
		List<T> dataList = new ArrayList<T>();
		
		switch (datakey) {
		
		case "depts":
			// 部门
			dataList =  (List<T>) agentUserService.getDepts();
			break;
		
		case "agentRoles":
			// 用户角色
			dataList =  (List<T>) ctiService.getAgentRoles();
			break;
			
		case "agentPermissions":
			// 用户权限
			dataList =  (List<T>) ctiService.getAgentPermissions();
			break;

		default:
			break;
		}
		return dataList;
	}

}
