package com.zz.CTI.controller.agentGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zz.CTI.Bean.AgentUser;
import com.zz.CTI.Util.MyException;
import com.zz.CTI.controller.BaseController;
import com.zz.CTI.service.AgentUserService;
import com.zz.CTI.service.CTIService;

import net.sf.json.JSONObject;

/**
 * 管理坐席
 * @author zhou.zhang
 *
 */
@Controller
@RequestMapping("/agentManage")
public class AgentManageController extends BaseController {
	@Resource
	private CTIService ctiService;
	
	@Resource
	private AgentUserService agentUserService;
	
	/**
	 * 用户列表
	 * @return
	 */
	@RequiresRoles(value = { "superAdmin", "Admin", "Receptionist" }, logical = Logical.OR)
	@RequestMapping(value="/agentList", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String agentList(HttpServletRequest request, HttpServletResponse response) {
    	Map<String, Object> searchMap = new HashMap<String, Object>();
    	searchMap.put("userNo", request.getParameter("userNo"));
    	searchMap.put("workNo", request.getParameter("workNo"));
    	searchMap.put("workName", request.getParameter("workName"));
    	searchMap.put("deptId", request.getParameter("deptId"));
    	searchMap.put("page",  Integer.parseInt(request.getParameter("page")));
    	searchMap.put("limit",  Integer.parseInt(request.getParameter("limit")));
		return agentUserService.getAgentUsers(searchMap);
    }
	
	/**
	 * 增加用户
	 * @param agentUser
	 * @return
	 */
	@RequiresRoles(value = { "superAdmin" })
	@RequestMapping(value="/addAgent", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addAgent(@RequestBody AgentUser agentUser) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = agentUserService.addAgent(agentUser);
		} catch (MyException e) {
			jsonObject.put("retcode", e.getErrorCode());
			jsonObject.put("error_msg", e.getMessage());
		}
		return jsonObject.toString();
    }
	
	/**
	 * 删除用户
	 * @param agentUser
	 * @return
	 */
	@RequiresRoles(value = { "superAdmin" })
	@RequestMapping(value="/deleteAgent", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteAgent(@RequestBody AgentUser agentUser) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = agentUserService.deleteAgent(agentUser);
		} catch (MyException e) {
			jsonObject.put("retcode", e.getErrorCode());
			jsonObject.put("error_msg", e.getMessage());
		}
		return jsonObject.toString();
    }
	
	/**
	 * 批量删除用户
	 * @param agentUser
	 * @return
	 */
	@RequiresRoles(value = { "superAdmin" })
	@RequestMapping(value="/deleteAgents", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteAgents(@RequestBody List<AgentUser> agentUsers) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = agentUserService.deleteAgents(agentUsers);
		} catch (MyException e) {
			jsonObject.put("retcode", e.getErrorCode());
			jsonObject.put("error_msg", e.getMessage());
		}
		return jsonObject.toString();
    }
	
	/**
	 * 编辑用户
	 * @param agentUser
	 * @return
	 */
	@RequiresRoles(value = { "superAdmin", "Admin", "Receptionist" }, logical = Logical.OR)
	@RequestMapping(value="/editAgent", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String editAgent(@RequestBody AgentUser agentUser) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = agentUserService.editAgentUser(agentUser);
		} catch (MyException e) {
			jsonObject.put("retcode", e.getErrorCode());
			jsonObject.put("error_msg", e.getMessage());
		}
		return jsonObject.toString();
    }
}
