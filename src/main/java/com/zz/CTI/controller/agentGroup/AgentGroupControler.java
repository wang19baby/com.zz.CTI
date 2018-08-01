package com.zz.CTI.controller.agentGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zz.CTI.Shiro.ShiroUtil;
import com.zz.CTI.controller.BaseController;
import com.zz.CTI.huawei.common.GlobalConstant;
import com.zz.CTI.huawei.service.AgentService;
import com.zz.CTI.service.CTIService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 坐席班组
 * @author zhou.zhang
 *
 */
@Controller
@RequestMapping("/get")
public class AgentGroupControler extends BaseController {
	@Resource
	private CTIService ctiService;
	
	/**
	 * 查询座席信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/Msg", produces = "text/html;charset=UTF-8")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public String Agent(HttpServletRequest request, HttpServletResponse response){
		String result = "";
		String type = request.getParameter("type"); //请求类型
		String workNo = ShiroUtil.getUserInfo().getWorkno();
		
        switch(type) {
        
        //查询坐席技能
        case GlobalConstant.AGENTSKILL:
        	result = AgentService.agentskillsbyworkno(workNo, request.getParameter("agentid"));
            break;
        
        //查询指定座席详细座席信息
        case GlobalConstant.AGENTBYWORKNO:
            result = AgentService.agentbyworkno(workNo, request.getParameter("agentid"));
            break;
            
	    //关键字模糊查询在线座席
	    case GlobalConstant.ONLINEAGENTONVDNBYKEYWORD:
	        result = AgentService.agentinfonoskills(workNo, (List<String>) JSONArray.toCollection(JSONArray
	        		.fromObject(request.getParameter("keyList")), String.class));
	        break;
            
        //批量查询坐席列表信息
        case GlobalConstant.AGENTINFO:
        	List<String> list = new ArrayList<>();
        	Set<Entry<String, String>> entries = ((Map<String,String>) JSONObject.
        			fromObject(AgentService.agentwasnameonvdn(workNo)).get("result")).entrySet();
			for (Entry<String, String> entry : entries) {
	            list.add(entry.getKey()); //工号放入List
			}
            result = AgentService.agentinfonoskills(workNo, list); //批量查询坐席列表信息
            break;
            
        //查询指定座席班组信息
        case GlobalConstant.GROUPBYAGENT:
            result = AgentService.groupbyagent(workNo, "112");
            break;
            
        //查询指定班组的座席信息
        case GlobalConstant.AGENTONGROUPEX:
            result = AgentService.agentongroupex(workNo, "1");
            break;
            
        //查询所在VDN所有座席当前状态信息
        case GlobalConstant.ALLAGENTSTATUS:
            result = AgentService.allagentstatus(workNo);
            break;
            
        //查询所属VDN在线座席信息
        case GlobalConstant.ONLIGNEAGENTONVDN:
        	result = AgentService.onlineagentonvdn(workNo);
            break;
            
        //查询所属班组的坐席信息
        case GlobalConstant.AGENTBYGROUP:
        	int groupid =  JSONObject.fromObject(AgentService.
        			groupbyagent(workNo, workNo)).getJSONObject("result").getInt("id"); //获取坐席所属班组id
        	result = AgentService.onlineagentonvdn(workNo); //获取所属VDN在线座席信息
			List<JSONObject> listJson = (List<JSONObject>) JSONObject.fromObject(result).get("result");
        	if (listJson != null && listJson.size() > 0) {
        		Iterator<JSONObject> json = listJson.iterator();
        		while (json.hasNext()) {
        			JSONObject jsonObject = json.next();
        			if (jsonObject.getInt("groupid") != groupid) { //判断是否为同组ID
						json.remove();
					}
        		}
        	}
        	JSONObject jsonObject = new JSONObject();
        	jsonObject.put("message", JSONObject.fromObject(result).get("message"));
        	jsonObject.put("result", listJson);
        	jsonObject.put("retcode", JSONObject.fromObject(result).get("retcode"));
        	result = jsonObject.toString();
            break;
            
        //查询指定班组的在线坐席信息
        case GlobalConstant.ONLIGNEAGENTGROUP:
        	int groupId =  Integer.parseInt(request.getParameter("groupId")); //获取需查询班组id
        	result = AgentService.onlineagentonvdn(workNo); //获取所属VDN在线座席信息
        	
        	if (groupId == 0) {
        		 break;
			}
        	
			List<JSONObject> listJson2 = (List<JSONObject>) JSONObject.fromObject(result).get("result");
        	if (listJson2 != null && listJson2.size() > 0) {
        		Iterator<JSONObject> json = listJson2.iterator();
        		while (json.hasNext()) {
        			JSONObject jsonObject2 = json.next();
        			if (jsonObject2.getInt("groupid") != groupId) { //判断是否为同组ID
						json.remove();
					}
        		}
        	}
        	JSONObject jsonObject2 = new JSONObject();
        	jsonObject2.put("message", JSONObject.fromObject(result).get("message"));
        	jsonObject2.put("result", listJson2);
        	jsonObject2.put("retcode", JSONObject.fromObject(result).get("retcode"));
        	result = jsonObject2.toString();
            break;
            
        //查询所在VDN所有班组信息
        case GlobalConstant.GROUPONVDN:
            result = AgentService.grouponvdn(workNo);
            break;
            
        default:
            break;
        }
		
    	JSONObject jsonresult = JSONObject.fromObject(result); //返回信息转Json
    	
        if("0".equals(jsonresult.getString("retcode"))){
        	// 操作成功
        }else {
            //操作失败
        	jsonresult.put("error_msg", ctiService.getErrorMsg(jsonresult.getString("retcode"))); //返回错误描述
        }
        
        // 返回信息
        return jsonresult.toString();
    }
}
